package com.hsf.catalog.repository;

import com.hsf.catalog.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Repository thao tác với bảng 'products'.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Kiểm tra SKU đã tồn tại chưa.
     */
    boolean existsBySku(String sku);

    /**
     * Kiểm tra SKU đã tồn tại ở sản phẩm khác chưa (phục vụ update).
     */
    boolean existsBySkuAndIdNot(String sku, Long id);

    /**
     * Truy vấn phân trang linh hoạt kết hợp lọc theo categoryId và keyword (tìm kiếm cả theo Tên sản phẩm hoặc SKU).
     */
    @Query("SELECT p FROM Product p WHERE " +
           "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
           "(:keyword IS NULL OR LOWER(p.productName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "                OR LOWER(p.sku) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Product> findProductsWithFilter(@Param("categoryId") Integer categoryId,
                                         @Param("keyword") String keyword,
                                         Pageable pageable);
}
