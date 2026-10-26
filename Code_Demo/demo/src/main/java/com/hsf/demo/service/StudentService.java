package com.hsf.demo.service;

import com.hsf.demo.dto.StudentRequestDTO;
import com.hsf.demo.dto.StudentResponseDTO;
import com.hsf.demo.entity.Major;

import java.util.List;
import java.util.Optional;

/**
 * INTERFACE SERVICE: Định nghĩa "hợp đồng" (Contract) cho nghiệp vụ Student.
 * 
 * VÌ SAO DÙNG INTERFACE LẠI TẠO NÊN LIÊN KẾT LỎNG (LOOSE COUPLING)?
 * 1. Độc lập triển khai: Controller chỉ phụ thuộc vào "cần làm gì" (Interface),
 *    chứ KHÔNG phụ thuộc vào "làm như thế nào" (Class Implementation cụ thể).
 * 2. Dễ dàng Unit Test: Khi viết test cho Controller, ta có thể dễ dàng thay thế
 *    bằng một MockStudentService mà không cần chạy cơ sở dữ liệu thật.
 * 3. Dễ bảo trì & nâng cấp: Sau này muốn đổi từ JPA sang Redis Cache, NoSQL hoặc 
 *    gọi sang Microservice khác, ta chỉ cần tạo class Implementation mới 
 *    mà KHÔNG CẦN SỬA một dòng code nào trong Controller!
 */
public interface StudentService {

    /**
     * Lấy danh sách tất cả sinh viên hoặc lọc theo chuyên ngành.
     */
    List<StudentResponseDTO> getAllStudents(Major major);

    /**
     * Tìm kiếm sinh viên theo ID.
     */
    Optional<StudentResponseDTO> getStudentById(Long id);

    /**
     * Thêm mới sinh viên từ dữ liệu Request DTO.
     */
    StudentResponseDTO createStudent(StudentRequestDTO requestDTO);

    /**
     * Cập nhật thông tin sinh viên theo ID.
     */
    Optional<StudentResponseDTO> updateStudent(Long id, StudentRequestDTO requestDTO);

    /**
     * Xóa sinh viên theo ID.
     */
    boolean deleteStudent(Long id);
}
