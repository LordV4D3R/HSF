package com.hsf.catalog.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * =========================================================================================
 * THỰC THỂ CATEGORY (DANH MỤC SẢN PHẨM)
 * =========================================================================================
 * Ánh xạ tương ứng với bảng 'categories' trong SQL Server theo cách tiếp cận Database First.
 * =========================================================================================
 */
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_code", nullable = false, unique = true, length = 20)
    private String categoryCode;

    @Column(name = "category_name", nullable = false, length = 100)
    private String categoryName;

    @Column(name = "description", length = 255)
    private String description;

    /**
     * Cờ đánh dấu xóa mềm:
     * - true: Đang hoạt động (hiển thị trên các API GET).
     * - false: Đã xóa mềm (ẩn khỏi danh sách hiển thị).
     */
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    /**
     * QUAN HỆ 1 - N VỚI PRODUCT (Inverse Side):
     * - mappedBy = "category": Phía Product giữ khóa ngoại category_id.
     * - FetchType.LAZY: Tải lười để tối ưu hiệu năng bộ nhớ.
     */
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    // Constructors
    public Category() {
    }

    public Category(String categoryCode, String categoryName, String description) {
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.description = description;
        this.isActive = true;
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
