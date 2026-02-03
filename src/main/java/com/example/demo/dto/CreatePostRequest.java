package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreatePostRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Content is required")
    private String content;

    @NotBlank(message = "Author email is required")
    @Email(message = "Please provide a valid email address")
    private String authorEmail;

    private List<String> tags;
    private List<String> comments;
    private Date createdAt;
}
