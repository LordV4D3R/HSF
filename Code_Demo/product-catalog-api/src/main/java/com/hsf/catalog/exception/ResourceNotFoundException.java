package com.hsf.catalog.exception;

/**
 * Ngoại lệ ném ra khi không tìm thấy tài nguyên (Category hoặc Product theo ID).
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
