package com.codej.exceptions;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler  {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomErrorRecord> handleModelNotFoundException(ResourceNotFoundException ex,
                                                                          WebRequest request) {
        CustomErrorRecord error = new CustomErrorRecord(LocalDateTime.now(),
                ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorRecord> methodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                             WebRequest request) {

        String msg= ex.getBindingResult().getFieldErrors().stream()
                .map(e ->   e.getField().concat(": ").concat(e.getDefaultMessage()))
                .collect(Collectors.joining(", "));
        CustomErrorRecord error = new CustomErrorRecord(LocalDateTime.now(),
                msg, request.getDescription(false));

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateResourceException(DuplicateResourceException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Recurso duplicado");
        response.put("message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

}
