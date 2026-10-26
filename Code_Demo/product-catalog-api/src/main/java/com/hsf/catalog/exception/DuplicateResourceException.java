package com.hsf.catalog.exception;

/**
 * Ngoại lệ ném ra khi trùng lặp khóa duy nhất (category_code hoặc sku đã tồn tại).
 */
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
