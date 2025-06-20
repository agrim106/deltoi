package com.deltoi.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.deltoi.app.entity.User;

/**
 * Repository for User entity.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // Replace findByUsername with findByEmail
    // Remove Optional<User> findByUsername(String username); if present
}