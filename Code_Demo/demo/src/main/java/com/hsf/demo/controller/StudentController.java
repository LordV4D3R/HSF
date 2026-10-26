package com.hsf.demo.controller;

import com.hsf.demo.dto.StudentRequestDTO;
import com.hsf.demo.dto.StudentResponseDTO;
import com.hsf.demo.entity.Major;
import com.hsf.demo.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * CONTROLLER: Tầng giao tiếp bên ngoài (API Layer).
 * 
 * LƯU Ý QUAN TRỌNG VỀ LIÊN KẾT LỎNG (LOOSE COUPLING):
 * - Controller chỉ tiêm (inject) Interface `StudentService`, hoàn toàn KHÔNG phụ thuộc vào `StudentServiceImpl`.
 * - Controller hoàn toàn KHÔNG dùng trực tiếp Entity `Student`, mà chỉ giao tiếp qua `RequestDTO` và `ResponseDTO`.
 */
@Tag(name = "1. Quản lý Sinh viên", description = "Các API CRUD đầy đủ: GET, POST, PUT, DELETE kết hợp DTO và Enum")
@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    // TIÊM INTERFACE: Loose Coupling (không phụ thuộc vào StudentServiceImpl)
    private final StudentService studentService;

    /**
     * =========================================================================
     * 1. GET: Lấy danh sách sinh viên (Minh họa @RequestParam và Enum)
     * =========================================================================
     */
    @Operation(summary = "Lấy danh sách tất cả sinh viên hoặc lọc theo ngành",
               description = "Minh họa sử dụng @RequestParam và Enum. Nếu không truyền param sẽ lấy tất cả.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lấy danh sách thành công (HTTP 200 OK)")
    })
    @GetMapping
    public ResponseEntity<List<StudentResponseDTO>> getAllStudents(
            @Parameter(description = "Chuyên ngành sinh viên (Enum, không bắt buộc)")
            @RequestParam(required = false) Major major) {

        List<StudentResponseDTO> students = studentService.getAllStudents(major);
        return ResponseEntity.ok(students);
    }

    /**
     * =========================================================================
     * 2. GET theo ID: Lấy chi tiết sinh viên (Minh họa @PathVariable và 404 Not Found)
     * =========================================================================
     */
    @Operation(summary = "Lấy thông tin sinh viên theo ID",
               description = "Minh họa @PathVariable. Nếu tìm thấy trả về 200 OK, ngược lại trả về 404 Not Found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tìm thấy sinh viên thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sinh viên với ID tương ứng")
    })
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> getStudentById(
            @Parameter(description = "ID của sinh viên cần tìm", example = "1")
            @PathVariable Long id) {

        Optional<StudentResponseDTO> studentOptional = studentService.getStudentById(id);

        if (studentOptional.isPresent()) {
            return ResponseEntity.ok(studentOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * =========================================================================
     * 3. POST: Thêm mới sinh viên (Minh họa @RequestBody & HttpStatus.CREATED)
     * =========================================================================
     */
    @Operation(summary = "Thêm mới một sinh viên",
               description = "Minh họa @RequestBody, @Valid và mã 201 CREATED. Nhận vào StudentRequestDTO (có validate dữ liệu).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tạo mới sinh viên thành công (HTTP 201 Created)"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ (Validation failed - HTTP 400 Bad Request)")
    })
    @PostMapping
    public ResponseEntity<StudentResponseDTO> createStudent(
            @Valid @RequestBody StudentRequestDTO requestDTO) {

        StudentResponseDTO createdStudent = studentService.createStudent(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    /**
     * =========================================================================
     * 4. PUT: Cập nhật thông tin sinh viên (Kết hợp @PathVariable & @RequestBody)
     * =========================================================================
     */
    @Operation(summary = "Cập nhật thông tin sinh viên theo ID",
               description = "Kết hợp @PathVariable, @RequestBody và @Valid. Nếu có ID trả về 200 OK, nếu không có trả về 404 Not Found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cập nhật thành công"),
            @ApiResponse(responseCode = "400", description = "Dữ liệu không hợp lệ (Validation failed - HTTP 400 Bad Request)"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sinh viên để cập nhật")
    })
    @PutMapping("/{id}")
    public ResponseEntity<StudentResponseDTO> updateStudent(
            @Parameter(description = "ID sinh viên cần cập nhật", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody StudentRequestDTO requestDTO) {

        Optional<StudentResponseDTO> updatedStudent = studentService.updateStudent(id, requestDTO);

        if (updatedStudent.isPresent()) {
            return ResponseEntity.ok(updatedStudent.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * =========================================================================
     * 5. DELETE: Xóa sinh viên theo ID (Minh họa @PathVariable)
     * =========================================================================
     */
    @Operation(summary = "Xóa sinh viên theo ID",
               description = "Minh họa @PathVariable. Nếu xóa thành công trả về 200 OK, nếu không tìm thấy trả về 404 Not Found.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Xóa thành công"),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sinh viên để xóa")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(
            @Parameter(description = "ID sinh viên cần xóa", example = "1")
            @PathVariable Long id) {

        boolean isDeleted = studentService.deleteStudent(id);

        if (isDeleted) {
            return ResponseEntity.ok("Xóa sinh viên có ID = " + id + " thành công!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
