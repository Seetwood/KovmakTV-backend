package dev.vorstu.exception;

public class CustomMinioException extends RuntimeException {
    public CustomMinioException(String message) {
        super(message);
    }
}