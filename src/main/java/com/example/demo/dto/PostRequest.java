package com.example.demo.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PostRequest {
    private String title;
    private String content;
    private String authorEmail;
    private List<String> tags;
    private List<String> comments;
    private Date createdAt;
}