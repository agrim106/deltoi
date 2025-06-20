package com.deltoi.app.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing a query in the deltoi application.
 */
@Entity
@Table(name = "queries")
@Data
@NoArgsConstructor
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", nullable = true, length = 255)
    private String description;

    @Column(name = "status", nullable = false, length = 255)
    private String status; // PENDING, ARCHIVED, TRASH

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Query(String title, String description, String status, User user) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }
}