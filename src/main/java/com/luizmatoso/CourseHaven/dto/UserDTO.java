package com.luizmatoso.CourseHaven.dto;

import com.luizmatoso.CourseHaven.enums.UserRole;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private UserRole userRole;
}
