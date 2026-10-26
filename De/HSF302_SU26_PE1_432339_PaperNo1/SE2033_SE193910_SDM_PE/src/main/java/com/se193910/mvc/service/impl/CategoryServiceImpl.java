package com.se193910.mvc.service.impl;

import com.se193910.mvc.dto.CategoryDTO;
import com.se193910.mvc.repository.CategoryRepository;
import com.se193910.mvc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryDTO::categoryDTO)
                .toList();
    }
}
