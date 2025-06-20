package com.deltoi.app.dto;

import lombok.Data;

/**
 * DTO for query API requests and responses.
 */
@Data
public class QueryDto {
    private Long id;
    private String title;
    private String description;
    private String status;

    public QueryDto(Long id, String title, String description, String status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }
}