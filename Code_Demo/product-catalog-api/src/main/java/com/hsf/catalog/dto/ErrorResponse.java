package com.hsf.catalog.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Cấu trúc JSON chuẩn phản hồi khi có lỗi xảy ra trong hệ thống.
 */
@Schema(description = "Cấu trúc phản hồi lỗi chuẩn")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    @Schema(description = "Thời điểm xảy ra lỗi", example = "2026-10-08T15:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "Mã trạng thái HTTP", example = "400")
    private int status;

    @Schema(description = "Tên lỗi HTTP", example = "Bad Request")
    private String error;

    @Schema(description = "Thông báo chi tiết", example = "Dữ liệu không hợp lệ")
    private String message;

    @Schema(description = "Danh sách chi tiết các trường vi phạm validation")
    private Map<String, String> errors;

    public ErrorResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(int status, String error, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public ErrorResponse(int status, String error, Map<String, String> errors) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.errors = errors;
    }

    // Getters and Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
