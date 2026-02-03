package com.example.demo.model.entity;

import lombok.Data;

@Data
public class Comment {
    private String id;
    private String authorEmail;
    private String text;
}
