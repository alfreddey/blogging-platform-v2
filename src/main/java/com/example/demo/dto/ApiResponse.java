package com.example.demo.dto;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {
    public HttpStatus status;
    public String message;
    public T data;

    public ApiResponse(HttpStatus status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
