package com.hsf.demo.dto;

import com.hsf.demo.entity.Major;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response DTO (Data Transfer Object):
 * - Đối tượng chuyên dùng để TRẢ VỀ dữ liệu cho Client (ResponseBody).
 *
 * TẠI SAO DÙNG DTO MÀ KHÔNG DÙNG ENTITY TRỰC TIẾP?
 * 1. Che giấu thông tin nhạy cảm: Nếu Entity có password, mã thẻ căn cước, audit log... DTO giúp giấu đi.
 * 2. Linh hoạt: Có thể format lại dữ liệu, thêm các trường tính toán (ví dụ: isAdult, majorName...) mà DB không cần lưu.
 * 3. Tránh lỗi vòng lặp (Infinite Recursion) khi quan hệ @OneToMany, @ManyToOne trong JPA.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponseDTO {

    private Long id;       // ID sinh viên (do DB tự sinh)
    private String name;   // Họ và tên
    private String email;  // Email
    private Integer age;   // Tuổi
    private Major major;   // Chuyên ngành
}
