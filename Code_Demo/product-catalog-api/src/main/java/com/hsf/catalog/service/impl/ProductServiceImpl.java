package com.hsf.catalog.service.impl;

import com.hsf.catalog.dto.ProductRequest;
import com.hsf.catalog.dto.ProductResponse;
import com.hsf.catalog.entity.Category;
import com.hsf.catalog.entity.Product;
import com.hsf.catalog.exception.DuplicateResourceException;
import com.hsf.catalog.exception.ResourceNotFoundException;
import com.hsf.catalog.repository.CategoryRepository;
import com.hsf.catalog.repository.ProductRepository;
import com.hsf.catalog.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getProducts(int page, int size, Integer categoryId, String keyword) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        
        // Chuẩn hóa từ khóa tìm kiếm (nếu rỗng thì gán null)
        String searchKeyword = (keyword != null && !keyword.trim().isEmpty()) ? keyword.trim() : null;

        Page<Product> productPage = productRepository.findProductsWithFilter(categoryId, searchKeyword, pageable);
        return productPage.map(this::mapToResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với ID: " + id));
        return mapToResponse(product);
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        // 1. Kiểm tra Category có tồn tại và đang active không
        Category category = categoryRepository.findByIdAndIsActiveTrue(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục hợp lệ với ID: " + request.getCategoryId()));

        // 2. Kiểm tra SKU duy nhất
        if (productRepository.existsBySku(request.getSku())) {
            throw new DuplicateResourceException("Mã SKU '" + request.getSku() + "' đã tồn tại trong hệ thống");
        }

        Product product = new Product();
        product.setCategory(category);
        product.setSku(request.getSku());
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setDescription(request.getDescription());

        // 3. Nghiệp vụ: Tự động gán status = OUT_OF_STOCK nếu stockQuantity == 0
        if (request.getStockQuantity() == 0) {
            product.setStatus("OUT_OF_STOCK");
        } else if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            product.setStatus(request.getStatus().trim());
        } else {
            product.setStatus("AVAILABLE");
        }

        Product saved = productRepository.save(product);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với ID: " + id));

        // Kiểm tra Category mới
        Category category = categoryRepository.findByIdAndIsActiveTrue(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục hợp lệ với ID: " + request.getCategoryId()));

        // Kiểm tra nếu đổi SKU thì không được trùng sản phẩm khác
        if (productRepository.existsBySkuAndIdNot(request.getSku(), id)) {
            throw new DuplicateResourceException("Mã SKU '" + request.getSku() + "' đã được sử dụng bởi sản phẩm khác");
        }

        product.setCategory(category);
        product.setSku(request.getSku());
        product.setProductName(request.getProductName());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());
        product.setDescription(request.getDescription());

        // Nghiệp vụ tự động đổi status theo tồn kho
        if (request.getStockQuantity() == 0) {
            product.setStatus("OUT_OF_STOCK");
        } else if ("OUT_OF_STOCK".equalsIgnoreCase(product.getStatus()) && request.getStockQuantity() > 0) {
            product.setStatus("AVAILABLE");
        } else if (request.getStatus() != null && !request.getStatus().trim().isEmpty()) {
            product.setStatus(request.getStatus().trim());
        }

        Product updated = productRepository.save(product);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với ID: " + id));

        // Cập nhật trạng thái ngừng kinh doanh (DISCONTINUED) hoặc xóa
        product.setStatus("DISCONTINUED");
        productRepository.save(product);
    }

    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getSku(),
                product.getProductName(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getDescription(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getCategory() != null ? product.getCategory().getCategoryName() : null
        );
    }
}
