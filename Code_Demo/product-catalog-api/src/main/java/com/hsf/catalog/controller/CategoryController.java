package com.hsf.catalog.controller;

import com.hsf.catalog.dto.CategoryRequest;
import com.hsf.catalog.dto.CategoryResponse;
import com.hsf.catalog.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "1. Quản lý Danh mục (Categories)", description = "Các API thao tác CRUD trên danh mục sản phẩm")
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "Lấy danh sách toàn bộ danh mục đang hoạt động (is_active = true)")
    @ApiResponse(responseCode = "200", description = "Lấy dữ liệu thành công")
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllActiveCategories() {
        return ResponseEntity.ok(categoryService.getAllActiveCategories());
    }

    @Operation(summary = "Xem chi tiết 1 danh mục theo ID")
    @ApiResponse(responseCode = "200", description = "Tìm thấy danh mục")
    @ApiResponse(responseCode = "404", description = "Không tìm thấy danh mục")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Integer id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @Operation(summary = "Thêm mới 1 danh mục")
    @ApiResponse(responseCode = "201", description = "Tạo danh mục thành công")
    @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ hoặc mã danh mục đã tồn tại")
    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse response = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Cập nhật thông tin danh mục")
    @ApiResponse(responseCode = "200", description = "Cập nhật thành công")
    @ApiResponse(responseCode = "400", description = "Mã danh mục mới bị trùng")
    @ApiResponse(responseCode = "404", description = "Không tìm thấy danh mục cần sửa")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Integer id,
                                                          @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(categoryService.updateCategory(id, request));
    }

    @Operation(summary = "Xóa mềm danh mục (chuyển is_active = false)")
    @ApiResponse(responseCode = "204", description = "Xóa mềm thành công")
    @ApiResponse(responseCode = "404", description = "Không tìm thấy danh mục cần xóa")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Integer id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
