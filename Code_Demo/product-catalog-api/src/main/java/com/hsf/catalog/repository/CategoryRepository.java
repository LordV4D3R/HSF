package com.hsf.catalog.repository;

import com.hsf.catalog.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository thao tác với bảng 'categories'.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    /**
     * Lấy tất cả danh mục đang hoạt động (Soft delete filter: is_active = true).
     */
    List<Category> findByIsActiveTrue();

    /**
     * Lấy chi tiết danh mục theo ID và còn đang hoạt động.
     */
    Optional<Category> findByIdAndIsActiveTrue(Integer id);

    /**
     * Kiểm tra xem mã danh mục đã tồn tại chưa (phục vụ validate trùng lặp).
     */
    boolean existsByCategoryCode(String categoryCode);

    /**
     * Kiểm tra xem mã danh mục đã tồn tại ở bản ghi khác chưa (phục vụ cập nhật).
     */
    boolean existsByCategoryCodeAndIdNot(String categoryCode, Integer id);
}
