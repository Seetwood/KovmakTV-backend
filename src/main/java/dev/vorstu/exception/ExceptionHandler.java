package dev.vorstu.exception;

import dev.vorstu.dto.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> handleException(NotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomMinioException.class)
    public ResponseEntity<ErrorMessage> handleMyMinioException(CustomMinioException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorMessage> handleAlreadyExistsException(AlreadyExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorMessage(exception.getMessage()));
    }
}