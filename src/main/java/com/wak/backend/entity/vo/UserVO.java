package com.wak.backend.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    //    private String company;
    private String username;
    private String name;
    private String phone;
    private String gender;
    private String email;
    //    private Integer status;
    private LocalDateTime lastModifiedTime;
}
