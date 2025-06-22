package com.deltoi.app.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.deltoi.app.entity.Query;
import com.deltoi.app.entity.User;
import com.deltoi.app.repository.QueryRepository;
import com.deltoi.app.repository.UserRepository;

/**
 * Service for managing query operations.
 */
@Service
public class QueryService {

    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private UserRepository userRepository;

    // Define valid status values based on your database constraint
    private static final Set<String> VALID_STATUSES = new HashSet<>(Arrays.asList("PENDING", "TRASH", "ARCHIVED"));

    /**
     * Adds a new query for the authenticated user.
     * @param title The query title
     * @param description The query description
     * @param status The initial status (e.g., PENDING)
     * @return The saved Query entity
     * @throws RuntimeException if user is not found or status is invalid
     */
    public Query addQuery(String title, String description, String status) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        if (status != null && !VALID_STATUSES.contains(status.toUpperCase())) {
            throw new RuntimeException("Invalid status value: " + status + ". Allowed values are PENDING, TRASH, ARCHIVED.");
        }

        Query query = new Query(title, description, status != null ? status.toUpperCase() : "PENDING", user);
        return queryRepository.save(query);
    }

    /**
     * Retrieves all queries for the authenticated user.
     * @return List of Query entities
     */
    public List<Query> getAllQueries() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));
        return queryRepository.findByUserId(user.getId());
    }

    /**
     * Updates a query for the authenticated user.
     * @param id The query ID
     * @param title The new title
     * @param description The new description
     * @param status The new status
     * @return The updated Query entity
     * @throws RuntimeException if query or user not found or status is invalid
     */
    public Query updateQuery(Long id, String title, String description, String status) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        Query query = queryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Query not found: " + id));
        if (!query.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Query does not belong to the authenticated user");
        }

        if (status != null && !VALID_STATUSES.contains(status.toUpperCase())) {
            throw new RuntimeException("Invalid status value: " + status + ". Allowed values are PENDING, TRASH, ARCHIVED.");
        }

        query.setTitle(title != null ? title : query.getTitle());
        query.setDescription(description != null ? description : query.getDescription());
        if (status != null) {
            query.setStatus(status.toUpperCase());
        }
        return queryRepository.save(query);
    }

    /**
     * Moves a query to TRASH for the authenticated user.
     * @param id The query ID
     * @return The updated Query entity
     * @throws RuntimeException if query or user not found
     */
    public Query moveToTrash(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        Query query = queryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Query not found: " + id));
        if (!query.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Query does not belong to the authenticated user");
        }

        if (!"PENDING".equals(query.getStatus()) && !"ARCHIVED".equals(query.getStatus())) {
            throw new RuntimeException("Only PENDING or ARCHIVED queries can be moved to TRASH");
        }

        query.setStatus("TRASH");
        return queryRepository.save(query);
    }

    /**
     * Permanently deletes a query from TRASH for the authenticated user.
     * @param id The query ID
     * @throws RuntimeException if query or user not found
     */
    public void permanentDelete(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found: " + email));

        Query query = queryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Query not found: " + id));
        if (!query.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Query does not belong to the authenticated user");
        }

        if (!"TRASH".equals(query.getStatus())) {
            throw new RuntimeException("Only TRASHed queries can be permanently deleted");
        }

        queryRepository.delete(query);
    }
}