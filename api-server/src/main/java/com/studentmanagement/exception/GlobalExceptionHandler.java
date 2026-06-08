package com.studentmanagement.exception;

import com.studentmanagement.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Xử lý ResourceNotFoundException
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFound(
            ResourceNotFoundException ex,
            WebRequest request) {
        log.warn("Resource not found: {}", ex.getMessage());
        
        ApiResponse<?> response = ApiResponse.error(
            HttpStatus.NOT_FOUND.value(),
            ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Xử lý BadRequestException
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiResponse<?>> handleBadRequest(
            BadRequestException ex,
            WebRequest request) {
        log.warn("Bad request: {}", ex.getMessage());
        
        ApiResponse<?> response = ApiResponse.error(
            HttpStatus.BAD_REQUEST.value(),
            ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Xử lý validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidationException(
            MethodArgumentNotValidException ex,
            WebRequest request) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
            errors.put(error.getField(), error.getDefaultMessage())
        );
        
        log.warn("Validation error: {}", errors);
        
        ApiResponse<?> response = ApiResponse.error(
            HttpStatus.BAD_REQUEST.value(),
            "Validation error",
            errors
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Xử lý access denied
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<?>> handleAccessDenied(
            AccessDeniedException ex,
            WebRequest request) {
        log.warn("Access denied: {}", ex.getMessage());
        
        ApiResponse<?> response = ApiResponse.error(
            HttpStatus.FORBIDDEN.value(),
            "Bạn không có quyền truy cập tài nguyên này"
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    /**
     * Xử lý tất cả các exception chưa được xử lý
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGlobalException(
            Exception ex,
            WebRequest request) {
        log.error("Unexpected error", ex);
        
        ApiResponse<?> response = ApiResponse.error(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "Đã xảy ra lỗi hệ thống. Vui lòng thử lại sau."
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
