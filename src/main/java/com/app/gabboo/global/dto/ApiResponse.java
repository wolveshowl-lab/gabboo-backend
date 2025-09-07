package com.app.gabboo.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private int status;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<T>(200, message, data);
    }

    public static <T> ApiResponse<T> fail(int status, String message) {
        return new ApiResponse<T>(status, message, null);
    }
}
