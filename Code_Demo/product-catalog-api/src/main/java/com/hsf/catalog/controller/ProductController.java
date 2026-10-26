package com.hsf.catalog.controller;

import com.hsf.catalog.dto.ProductRequest;
import com.hsf.catalog.dto.ProductResponse;
import com.hsf.catalog.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "2. Quản lý Sản phẩm (Products)", description = "Các API thao tác CRUD và tìm kiếm, phân trang sản phẩm")
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Phân trang danh sách sản phẩm, có hỗ trợ lọc theo danh mục và tìm kiếm từ khóa")
    @ApiResponse(responseCode = "200", description = "Lấy dữ liệu phân trang thành công")
    @GetMapping
    public ResponseEntity<Page<ProductResponse>> getProducts(
            @Parameter(description = "Số trang (bắt đầu từ 0)", example = "0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Số phần tử mỗi trang", example = "10")
            @RequestParam(defaultValue = "10") int size,

            @Parameter(description = "Lọc theo ID danh mục (tùy chọn)", example = "1")
            @RequestParam(required = false) Integer categoryId,

            @Parameter(description = "Từ khóa tìm kiếm theo Tên sản phẩm hoặc SKU (tùy chọn)", example = "Pro")
            @RequestParam(required = false) String keyword) {

        Page<ProductResponse> products = productService.getProducts(page, size, categoryId, keyword);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Xem chi tiết sản phẩm theo ID")
    @ApiResponse(responseCode = "200", description = "Tìm thấy sản phẩm")
    @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Thêm mới sản phẩm")
    @ApiResponse(responseCode = "201", description = "Thêm sản phẩm thành công")
    @ApiResponse(responseCode = "400", description = "Dữ liệu validation không hợp lệ hoặc SKU đã tồn tại")
    @ApiResponse(responseCode = "404", description = "Category ID không tồn tại")
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductResponse response = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Cập nhật thông tin sản phẩm")
    @ApiResponse(responseCode = "200", description = "Cập nhật thành công")
    @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ hoặc SKU trùng")
    @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm hoặc danh mục")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id,
                                                        @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @Operation(summary = "Xóa sản phẩm (hoặc chuyển trạng thái sang DISCONTINUED)")
    @ApiResponse(responseCode = "204", description = "Xử lý thành công")
    @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm cần xóa")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
