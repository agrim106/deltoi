package com.deltoi.app.dto;

import lombok.Data;

/**
 * DTO for user signup request.
 */
@Data
public class UserSignupRequest {
    private String username;
    private String password;
    private String email;
}