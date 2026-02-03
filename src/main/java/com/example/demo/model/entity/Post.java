package com.example.demo.model.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Post {
    private String id;
    private String title;
    private String content;
    private String authorEmail;
    private List<String> tags;
    private List<Comment> comments;
    private Date createdAt;
}
