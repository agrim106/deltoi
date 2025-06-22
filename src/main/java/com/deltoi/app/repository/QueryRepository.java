package com.deltoi.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Keep this for the annotation
import org.springframework.data.repository.query.Param;

// No import for com.deltoi.app.entity.Query to avoid collision
// Use fully qualified name instead

/**
 * Repository for Query entity, providing CRUD operations.
 */
public interface QueryRepository extends JpaRepository<com.deltoi.app.entity.Query, Long> {
    // Custom method to find queries by user (infers from user.id)
    List<com.deltoi.app.entity.Query> findByUserId(Long userId);

    // Optional method to find by status (for archive/trash)
    List<com.deltoi.app.entity.Query> findByStatus(String status);

    // Custom query to search by keyword in title or description for a specific user
    @Query("SELECT q FROM com.deltoi.app.entity.Query q WHERE q.user.id = :userId AND (LOWER(q.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(q.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<com.deltoi.app.entity.Query> findByUserIdAndKeyword(@Param("userId") Long userId, @Param("keyword") String keyword);
}