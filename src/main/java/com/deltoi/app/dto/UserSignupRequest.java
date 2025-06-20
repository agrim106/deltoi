package com.deltoi.app.dto;

import lombok.Data;

@Data
public class UserSignupRequest {
    private String username;
    private String password;
    private String email;
}