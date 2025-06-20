package com.deltoi.app.dto;

import lombok.Data;

/**
 * DTO for user login request.
 */
@Data
public class UserLoginRequest {
    private String email; // Changed from username
    private String password;
}