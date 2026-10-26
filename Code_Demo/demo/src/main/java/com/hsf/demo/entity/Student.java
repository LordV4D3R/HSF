package com.hsf.demo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity Student: Đại diện cho bảng 'students' trong cơ sở dữ liệu.
 */
@Entity
@Table(name = "students")
@Data // Lombok: Tự động sinh Getter, Setter, toString, equals, hashCode
@NoArgsConstructor // Lombok: Sinh Constructor không tham số (JPA yêu cầu)
@AllArgsConstructor // Lombok: Sinh Constructor đầy đủ tham số
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Khóa chính (Primary Key), tự động tăng

    @Column(nullable = false)
    private String name; // Họ và tên sinh viên

    @Column(nullable = false, unique = true)
    private String email; // Email sinh viên (không được trùng)

    private Integer age; // Tuổi

    // @Enumerated(EnumType.STRING): Lưu giá trị Enum dưới dạng String vào DB (ví dụ: "SOFTWARE_ENGINEERING")
    // Không nên dùng ORDINAL vì khi thêm/sửa thứ tự Enum trong code sẽ làm sai lệch dữ liệu số (0, 1, 2...) trong DB.
    @Enumerated(EnumType.STRING)
    private Major major; // Chuyên ngành
}
