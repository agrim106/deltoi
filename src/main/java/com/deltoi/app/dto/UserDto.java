package com.deltoi.app.dto;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * Data Transfer Object for User profile responses.
 */
@Data
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private LocalDateTime createdAt;

    // Constructor for mapping from entity
    public UserDto(Long id, String username, String email, LocalDateTime createdAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.createdAt = createdAt;
    }
}