package com.hsf.demo.config;

import com.hsf.demo.entity.Major;
import com.hsf.demo.entity.Student;
import com.hsf.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Class cấu hình tự động chèn dữ liệu mẫu vào CSDL khi ứng dụng vừa khởi động.
 * Giúp sinh viên có thể test ngay các API GET mà không cần mất công tạo từ đầu.
 */
@Configuration
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final StudentRepository studentRepository;

    @Override
    public void run(String... args) {
        if (studentRepository.count() == 0) {
            Student s1 = new Student(null, "Nguyen Van A", "vana@fpt.edu.vn", 20, Major.SOFTWARE_ENGINEERING);
            Student s2 = new Student(null, "Tran Thi B", "thib@fpt.edu.vn", 21, Major.BUSINESS_ADMINISTRATION);
            Student s3 = new Student(null, "Le Van C", "vanc@fpt.edu.vn", 22, Major.GRAPHIC_DESIGN);

            studentRepository.saveAll(List.of(s1, s2, s3));
            System.out.println(">> Đã khởi tạo 3 sinh viên mẫu vào CSDL!");
        }
    }
}
