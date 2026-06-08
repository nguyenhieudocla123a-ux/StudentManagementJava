package com.studentmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    
    private int code;
    private boolean success;
    private String message;
    private T data;
    private Object errors;
    private LocalDateTime timestamp;

    /**
     * Tạo response thành công
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
            .code(200)
            .success(true)
            .message("Thành công")
            .data(data)
            .timestamp(LocalDateTime.now())
            .build();
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return ApiResponse.<T>builder()
            .code(200)
            .success(true)
            .message(message)
            .data(data)
            .timestamp(LocalDateTime.now())
            .build();
    }

    /**
     * Tạo response lỗi
     */
    public static <T> ApiResponse<T> error(int code, String message) {
        return ApiResponse.<T>builder()
            .code(code)
            .success(false)
            .message(message)
            .timestamp(LocalDateTime.now())
            .build();
    }

    public static <T> ApiResponse<T> error(int code, String message, Object errors) {
        return ApiResponse.<T>builder()
            .code(code)
            .success(false)
            .message(message)
            .errors(errors)
            .timestamp(LocalDateTime.now())
            .build();
    }
}
