package com.hsf.demo.service.impl;

import com.hsf.demo.dto.StudentRequestDTO;
import com.hsf.demo.dto.StudentResponseDTO;
import com.hsf.demo.entity.Major;
import com.hsf.demo.entity.Student;
import com.hsf.demo.repository.StudentRepository;
import com.hsf.demo.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * IMPLEMENTATION CỤ THỂ CỦA INTERFACE StudentService:
 * - Chứa thuật toán và các bước xử lý thực tế với Database.
 * - Nhận RequestDTO -> Chuyển thành Entity -> Gọi Repository lưu vào DB -> Chuyển Entity thành ResponseDTO trả về.
 */
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    public List<StudentResponseDTO> getAllStudents(Major major) {
        // 1. Lấy dữ liệu Entity từ DB
        List<Student> students = (major != null)
                ? studentRepository.findByMajor(major)
                : studentRepository.findAll();

        // 2. Chuyển đổi (Mapping) danh sách Entity -> Danh sách Response DTO
        return students.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<StudentResponseDTO> getStudentById(Long id) {
        // Tìm Entity theo ID, nếu tìm thấy thì map sang Response DTO
        return studentRepository.findById(id)
                .map(this::mapToResponseDTO);
    }

    @Override
    public StudentResponseDTO createStudent(StudentRequestDTO requestDTO) {
        // 1. Chuyển đổi Request DTO sang Entity để lưu vào DB
        Student student = new Student();
        student.setName(requestDTO.getName());
        student.setEmail(requestDTO.getEmail());
        student.setAge(requestDTO.getAge());
        student.setMajor(requestDTO.getMajor());

        // 2. Lưu Entity vào CSDL (DB tự sinh ID)
        Student savedStudent = studentRepository.save(student);

        // 3. Chuyển đổi Entity đã lưu sang Response DTO để trả về cho Client
        return mapToResponseDTO(savedStudent);
    }

    @Override
    public Optional<StudentResponseDTO> updateStudent(Long id, StudentRequestDTO requestDTO) {
        return studentRepository.findById(id).map(existingStudent -> {
            // Cập nhật thông tin từ Request DTO vào Entity hiện tại
            existingStudent.setName(requestDTO.getName());
            existingStudent.setEmail(requestDTO.getEmail());
            existingStudent.setAge(requestDTO.getAge());
            existingStudent.setMajor(requestDTO.getMajor());

            // Lưu thay đổi vào DB
            Student updatedStudent = studentRepository.save(existingStudent);

            // Chuyển sang Response DTO trả về
            return mapToResponseDTO(updatedStudent);
        });
    }

    @Override
    public boolean deleteStudent(Long id) {
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Hàm phụ trợ (Helper method): Chuyển đổi từ Entity sang Response DTO.
     * Viết tường minh bằng tay giúp sinh viên dễ hiểu bản chất của việc chuyển đổi dữ liệu.
     */
    private StudentResponseDTO mapToResponseDTO(Student student) {
        return new StudentResponseDTO(
                student.getId(),
                student.getName(),
                student.getEmail(),
                student.getAge(),
                student.getMajor()
        );
    }
}
