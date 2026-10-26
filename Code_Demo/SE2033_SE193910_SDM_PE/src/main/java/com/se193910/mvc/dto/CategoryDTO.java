package com.se193910.mvc.dto;

import com.se193910.mvc.entity.Category;

import java.io.Serializable;

public class CategoryDTO implements Serializable {
    private Integer id;
    private String categoryName;

    public CategoryDTO() {
    }

    public CategoryDTO(Integer id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }

    public static CategoryDTO categoryDTO(Category category) {
        if (category == null) return null;
        return new CategoryDTO(category.getId(), category.getCategoryName());
    }

    public Category toEntity() {
        Category category = new Category();
        category.setId(this.id);
        category.setCategoryName(this.categoryName);
        return category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
