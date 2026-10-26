package com.hsf.demo.repository;

import com.hsf.demo.entity.Major;
import com.hsf.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Tầng Repository: Kế thừa JpaRepository để thao tác với CSDL.
 * JpaRepository<Student, Long>:
 * - Student: Entity cần quản lý
 * - Long: Kiểu dữ liệu của khóa chính (id)
 *
 * Spring Data JPA đã cung cấp sẵn các phương thức CRUD như:
 * - findAll(): Lấy tất cả
 * - findById(id): Lấy theo ID
 * - save(student): Lưu mới hoặc cập nhật
 * - deleteById(id): Xóa theo ID
 * - existsById(id): Kiểm tra tồn tại theo ID
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Query Method: Spring Data JPA tự động phân tích tên hàm để sinh câu lệnh SQL:
    // SELECT * FROM students WHERE major = ?
    List<Student> findByMajor(Major major);
}
