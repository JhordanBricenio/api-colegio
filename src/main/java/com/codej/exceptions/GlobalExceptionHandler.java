package com.codej.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler  extends ResponseEntityExceptionHandler {
     public ResponseEntity<Map<String, String>> handleResourceNotFoundException(ResourceNotFoundException ex){
         Map<String, String> response= new HashMap<>();
            response.put("message", ex.getMessage());
         return new  ResponseEntity<>(response, HttpStatus.NOT_FOUND);
     }
}
