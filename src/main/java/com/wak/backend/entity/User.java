package com.wak.backend.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Long id;
    private String company;
    private String username;
    private String name;
    private String phone;
    private String gender;
    private String email;
    private String password;
    private Integer status;
    private LocalDateTime lastModifiedTime;
}
