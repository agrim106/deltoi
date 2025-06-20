package com.deltoi.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deltoi.app.entity.Query;

/**
 * Repository for Query entity, providing CRUD operations.
 */
public interface QueryRepository extends JpaRepository<Query, Long> {
    // Custom method to find queries by user
    List<Query> findByUserId(Long userId);

    // Optional method to find by status (for archive/trash)
    List<Query> findByStatus(String status);
}