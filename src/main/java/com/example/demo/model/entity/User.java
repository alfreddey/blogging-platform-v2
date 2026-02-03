package com.example.demo.model.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User {
    private String id;
    private String name;
    private String email;
    private String password;

    public User() {}

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String name, String email, String password) {
        this(null, name, email, password);
    }

}
