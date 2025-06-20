package com.deltoi.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.deltoi.app.dto.QueryDto;
import com.deltoi.app.entity.Query;
import com.deltoi.app.service.QueryService;

/**
 * Controller for managing query operations.
 */
@RestController
@RequestMapping("/api/queries")
public class QueryController {

    @Autowired
    private QueryService queryService;

    /**
     * Adds a new query for the authenticated user.
     * @param queryDto The query details
     * @return ResponseEntity with the created Query
     */
    @PostMapping
    public ResponseEntity<QueryDto> addQuery(@RequestBody QueryDto queryDto) {
        Query query = queryService.addQuery(queryDto.getTitle(), queryDto.getDescription(), queryDto.getStatus());
        QueryDto responseDto = mapToDto(query);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Retrieves all queries for the authenticated user.
     * @return ResponseEntity with a list of Query DTOs
     */
    @GetMapping
    public ResponseEntity<List<QueryDto>> getAllQueries() {
        List<Query> queries = queryService.getAllQueries();
        List<QueryDto> dtoList = queries.stream().map(this::mapToDto).collect(Collectors.toList());
        return ResponseEntity.ok(dtoList);
    }

    /**
     * Updates a query for the authenticated user.
     * @param id The query ID
     * @param queryDto The updated query details
     * @return ResponseEntity with the updated Query
     */
    @PutMapping("/{id}")
    public ResponseEntity<QueryDto> updateQuery(@PathVariable Long id, @RequestBody QueryDto queryDto) {
        Query query = queryService.updateQuery(id, queryDto.getTitle(), queryDto.getDescription(), queryDto.getStatus());
        QueryDto responseDto = mapToDto(query);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Moves a query to TRASH for the authenticated user (from dashboard or archive).
     * @param id The query ID
     * @return ResponseEntity with the updated Query
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<QueryDto> moveToTrash(@PathVariable Long id) {
        Query query = queryService.moveToTrash(id);
        QueryDto responseDto = mapToDto(query);
        return ResponseEntity.ok(responseDto);
    }

    /**
     * Permanently deletes a query from TRASH for the authenticated user.
     * @param id The query ID
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/trash/{id}")
    public ResponseEntity<Void> permanentDelete(@PathVariable Long id) {
        queryService.permanentDelete(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Maps a Query entity to a QueryDto.
     * @param query The Query entity
     * @return The mapped QueryDto
     */
    private QueryDto mapToDto(Query query) {
        return new QueryDto(query.getId(), query.getTitle(), query.getDescription(), query.getStatus());
    }
}