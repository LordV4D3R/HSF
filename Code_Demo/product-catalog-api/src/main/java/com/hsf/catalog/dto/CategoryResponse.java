package com.hsf.catalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO trả về thông tin Danh mục cho Client.
 */
@Schema(description = "Thông tin phản hồi của Danh mục")
public class CategoryResponse {

    @Schema(description = "ID danh mục", example = "1")
    private Integer id;

    @Schema(description = "Mã danh mục", example = "CAT_PHONE")
    private String categoryCode;

    @Schema(description = "Tên danh mục", example = "Điện thoại thông minh")
    private String categoryName;

    @Schema(description = "Mô tả", example = "Các dòng smartphone chính hãng")
    private String description;

    @Schema(description = "Trạng thái hoạt động", example = "true")
    private Boolean isActive;

    public CategoryResponse() {
    }

    public CategoryResponse(Integer id, String categoryCode, String categoryName, String description, Boolean isActive) {
        this.id = id;
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.description = description;
        this.isActive = isActive;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }
}
