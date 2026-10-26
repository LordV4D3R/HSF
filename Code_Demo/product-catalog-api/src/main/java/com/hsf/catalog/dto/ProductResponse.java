package com.hsf.catalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO trả về thông tin Sản phẩm cho Client (đã làm phẳng thông tin Category).
 */
@Schema(description = "Thông tin phản hồi của Sản phẩm")
public class ProductResponse {

    @Schema(description = "ID sản phẩm", example = "1")
    private Long id;

    @Schema(description = "Mã SKU", example = "IP15PM-256")
    private String sku;

    @Schema(description = "Tên sản phẩm", example = "iPhone 15 Pro Max 256GB")
    private String productName;

    @Schema(description = "Giá bán", example = "29990000.00")
    private BigDecimal price;

    @Schema(description = "Số lượng tồn kho", example = "25")
    private Integer stockQuantity;

    @Schema(description = "Mô tả sản phẩm", example = "Các dòng smartphone chính hãng")
    private String description;

    @Schema(description = "Trạng thái", example = "AVAILABLE")
    private String status;

    @Schema(description = "Thời gian tạo")
    private LocalDateTime createdAt;

    @Schema(description = "Thời gian cập nhật")
    private LocalDateTime updatedAt;

    @Schema(description = "ID danh mục", example = "1")
    private Integer categoryId;

    @Schema(description = "Tên danh mục", example = "Điện thoại thông minh")
    private String categoryName;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String sku, String productName, BigDecimal price, 
                           Integer stockQuantity, String description, String status, 
                           LocalDateTime createdAt, LocalDateTime updatedAt, 
                           Integer categoryId, String categoryName) {
        this.id = id;
        this.sku = sku;
        this.productName = productName;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
