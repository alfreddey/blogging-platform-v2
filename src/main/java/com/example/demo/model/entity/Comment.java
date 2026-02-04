package com.example.demo.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Comment {
    private String id;
    private String authorEmail;
    private String text;
    private Date createdAt;
}
