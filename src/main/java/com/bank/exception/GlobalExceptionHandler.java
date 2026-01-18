package com.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AmountInvalidException.class)
    public ResponseEntity<?> handleAmountInvalid(AmountInvalidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body(400, ex.getMessage()));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body(404, ex.getMessage()));
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<?> handleInsufficient(InsufficientBalanceException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body(409, ex.getMessage()));
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<?> handleDuplicate(UsernameAlreadyExistsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body(409, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body(500, "Internal server error"));
    }

    private Map<String, Object> body(int status, String msg) {
        return Map.of(
                "timestamp", Instant.now().toString(),
                "status", status,
                "error", msg
        );
    }
}

