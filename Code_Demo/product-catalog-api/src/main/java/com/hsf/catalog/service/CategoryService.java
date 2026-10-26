package com.hsf.catalog.service;

import com.hsf.catalog.dto.CategoryRequest;
import com.hsf.catalog.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAllActiveCategories();
    CategoryResponse getCategoryById(Integer id);
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse updateCategory(Integer id, CategoryRequest request);
    void deleteCategory(Integer id); // Xóa mềm
}
