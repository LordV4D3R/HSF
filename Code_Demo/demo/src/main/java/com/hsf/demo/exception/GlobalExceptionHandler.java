package com.hsf.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Xử lý ngoại lệ tập trung (Global Exception Handler) cho toàn bộ ứng dụng.
 * @RestControllerAdvice: Lắng nghe tất cả các Controller, bắt lỗi và định dạng lại JSON trả về.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Bắt lỗi khi @Valid kiểm tra dữ liệu DTO thất bại.
     * Gom tất cả các lỗi của từng trường vào một Map { tên_trường : thông_báo_lỗi }
     * và trả về mã HTTP 400 BAD REQUEST.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        // Duyệt qua danh sách các lỗi trên từng trường dữ liệu
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // Trả về HTTP 400 Bad Request kèm chi tiết các trường bị lỗi
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
