package dev.vorstu.exception;

public class AlreadyExistsException extends RuntimeException {
    public AlreadyExistsException(String message, String e) {
        super(message);
    }
}
