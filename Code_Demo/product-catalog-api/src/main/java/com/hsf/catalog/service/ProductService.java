package com.hsf.catalog.service;

import com.hsf.catalog.dto.ProductRequest;
import com.hsf.catalog.dto.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<ProductResponse> getProducts(int page, int size, Integer categoryId, String keyword);
    ProductResponse getProductById(Long id);
    ProductResponse createProduct(ProductRequest request);
    ProductResponse updateProduct(Long id, ProductRequest request);
    void deleteProduct(Long id);
}
