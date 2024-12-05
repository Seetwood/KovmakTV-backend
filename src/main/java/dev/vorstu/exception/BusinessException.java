package dev.vorstu.exception;

public class BusinessException extends RuntimeException{
    public BusinessException(String message, String eMessage) {
        super(message);
    }
}
