package com.hsf.catalog.service.impl;

import com.hsf.catalog.dto.CategoryRequest;
import com.hsf.catalog.dto.CategoryResponse;
import com.hsf.catalog.entity.Category;
import com.hsf.catalog.exception.DuplicateResourceException;
import com.hsf.catalog.exception.ResourceNotFoundException;
import com.hsf.catalog.repository.CategoryRepository;
import com.hsf.catalog.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllActiveCategories() {
        return categoryRepository.findByIsActiveTrue().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Integer id) {
        Category category = categoryRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục với ID: " + id));
        return mapToResponse(category);
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        // Kiểm tra trùng mã danh mục
        if (categoryRepository.existsByCategoryCode(request.getCategoryCode())) {
            throw new DuplicateResourceException("Mã danh mục '" + request.getCategoryCode() + "' đã tồn tại trong hệ thống");
        }

        Category category = new Category();
        category.setCategoryCode(request.getCategoryCode());
        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());
        category.setIsActive(true);

        Category saved = categoryRepository.save(category);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Integer id, CategoryRequest request) {
        Category category = categoryRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục với ID: " + id));

        // Kiểm tra nếu đổi categoryCode thì không được trùng với bản ghi khác
        if (categoryRepository.existsByCategoryCodeAndIdNot(request.getCategoryCode(), id)) {
            throw new DuplicateResourceException("Mã danh mục '" + request.getCategoryCode() + "' đã được sử dụng bởi danh mục khác");
        }

        category.setCategoryCode(request.getCategoryCode());
        category.setCategoryName(request.getCategoryName());
        category.setDescription(request.getDescription());

        Category updated = categoryRepository.save(category);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void deleteCategory(Integer id) {
        Category category = categoryRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục với ID: " + id));

        // THỰC HIỆN XÓA MỀM (Soft delete): chuyển is_active = false
        category.setIsActive(false);
        categoryRepository.save(category);
    }

    private CategoryResponse mapToResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getCategoryCode(),
                category.getCategoryName(),
                category.getDescription(),
                category.getIsActive()
        );
    }
}
