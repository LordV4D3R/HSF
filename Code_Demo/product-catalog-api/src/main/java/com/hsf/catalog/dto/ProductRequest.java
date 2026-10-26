package com.hsf.catalog.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO nhận dữ liệu khi thêm mới hoặc cập nhật Sản phẩm.
 */
@Schema(description = "Yêu cầu tạo mới hoặc cập nhật Sản phẩm")
public class ProductRequest {

    @Schema(description = "ID của danh mục sản phẩm", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Category ID không được để trống")
    private Integer categoryId;

    @Schema(description = "Mã SKU duy nhất", example = "IP16PM-512", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "SKU không được để trống")
    @Size(max = 30, message = "SKU tối đa 30 ký tự")
    private String sku;

    @Schema(description = "Tên sản phẩm", example = "iPhone 16 Pro Max 512GB", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 3, max = 150, message = "Tên sản phẩm phải từ 3 đến 150 ký tự")
    private String productName;

    @Schema(description = "Giá bán sản phẩm", example = "34990000.00", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Giá sản phẩm không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá sản phẩm phải lớn hơn 0")
    private BigDecimal price;

    @Schema(description = "Số lượng tồn kho", example = "20", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "Số lượng tồn kho không được để trống")
    @Min(value = 0, message = "Số lượng tồn kho không được âm")
    private Integer stockQuantity;

    @Schema(description = "Mô tả sản phẩm", example = "Chip A18 Pro, khung titan cao cấp")
    private String description;

    @Schema(description = "Trạng thái sản phẩm (AVAILABLE, OUT_OF_STOCK, DISCONTINUED)", example = "AVAILABLE")
    private String status;

    public ProductRequest() {
    }

    // Getters and Setters
    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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
}
