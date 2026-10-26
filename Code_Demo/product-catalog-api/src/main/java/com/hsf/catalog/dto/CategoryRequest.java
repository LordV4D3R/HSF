package com.hsf.catalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO nhận dữ liệu khi thêm mới hoặc cập nhật Category.
 */
@Schema(description = "Yêu cầu tạo mới hoặc cập nhật Danh mục")
public class CategoryRequest {

    @Schema(description = "Mã danh mục (duy nhất, không dấu cách)", example = "CAT_TABLET")
    @NotBlank(message = "Mã danh mục không được để trống")
    @Size(min = 3, max = 20, message = "Mã danh mục phải có độ dài từ 3 đến 20 ký tự")
    @Pattern(regexp = "^[A-Za-z0-9_]+$", message = "Mã danh mục không được chứa khoảng trắng hoặc ký tự đặc biệt")
    private String categoryCode;

    @Schema(description = "Tên danh mục", example = "Máy tính bảng")
    @NotBlank(message = "Tên danh mục không được để trống")
    @Size(max = 100, message = "Tên danh mục tối đa 100 ký tự")
    private String categoryName;

    @Schema(description = "Mô tả danh mục", example = "Các dòng iPad, Galaxy Tab chính hãng")
    @Size(max = 255, message = "Mô tả tối đa 255 ký tự")
    private String description;

    // Constructors
    public CategoryRequest() {
    }

    public CategoryRequest(String categoryCode, String categoryName, String description) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.description = description;
    }

    // Getters and Setters
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
}
