package com.codej.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
                .map(e ->   e.getField().concat(": ").concat(Objects.toString(e.getDefaultMessage(), "")))
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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<CustomErrorRecord> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                                   WebRequest request) {
        Throwable rootCause = ex.getRootCause();
        String rootMessage = rootCause != null ? rootCause.getMessage() : ex.getMessage();

        if (rootMessage != null && rootMessage.contains("Duplicate entry")) {
            Pattern p = Pattern.compile("Duplicate entry '(.+?)' for key '(.+?)'");
            Matcher m = p.matcher(rootMessage);
            if (m.find()) {
                String value = m.group(1);
                String key = m.group(2);
                String userMsg = String.format("Entrada duplicada: '%s' para la clave única '%s'", value, key);
                CustomErrorRecord error = new CustomErrorRecord(LocalDateTime.now(), userMsg, request.getDescription(false));
                return new ResponseEntity<>(error, HttpStatus.CONFLICT);
            }
        }

        CustomErrorRecord error = new CustomErrorRecord(LocalDateTime.now(),
                "Violación de integridad de datos", request.getDescription(false));
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}
