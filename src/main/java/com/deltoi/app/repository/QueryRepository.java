package com.deltoi.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deltoi.app.entity.Query;
import com.deltoi.app.entity.QueryStatus;

public interface QueryRepository extends JpaRepository<Query, Long> {
    List<Query> findByUserIdAndStatus(Long userId, QueryStatus status);
    List<Query> findByUserIdAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
        Long userId, String title, String description);
}