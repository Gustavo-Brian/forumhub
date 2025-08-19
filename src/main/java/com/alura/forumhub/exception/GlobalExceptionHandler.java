package com.alura.forumhub.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    record ApiError(Instant timestamp, int status, String error, String path, Map<String,String> fields){}

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> notFound(EntityNotFoundException ex, HttpServletRequest req){
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI(), null);
    }

    @ExceptionHandler(ForbiddenOperationException.class)
    public ResponseEntity<ApiError> forbidden(ForbiddenOperationException ex, HttpServletRequest req){
        return build(HttpStatus.FORBIDDEN, ex.getMessage(), req.getRequestURI(), null);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> validation(MethodArgumentNotValidException ex, HttpServletRequest req){
        Map<String,String> fields = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) fields.put(fe.getField(), fe.getDefaultMessage());
        return build(HttpStatus.BAD_REQUEST, "Validação falhou", req.getRequestURI(), fields);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> badRequest(IllegalArgumentException ex, HttpServletRequest req){
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI(), null);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> generic(Exception ex, HttpServletRequest req){
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno", req.getRequestURI(), null);
    }

    private ResponseEntity<ApiError> build(HttpStatus s, String msg, String path, Map<String,String> fields){
        return ResponseEntity.status(s).body(new ApiError(Instant.now(), s.value(), msg, path, fields));
    }
}
