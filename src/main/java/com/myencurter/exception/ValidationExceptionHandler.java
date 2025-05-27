package com.myencurter.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, List<String>> errors = new HashMap<>();

        errors.put("errors", e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage()).toList());

        return ResponseEntity.badRequest().body(errors);
    }
}
