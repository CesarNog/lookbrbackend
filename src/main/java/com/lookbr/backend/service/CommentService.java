package com.lookbr.backend.service;

import com.lookbr.backend.service.dto.CommentDTO;
import java.util.List;

/**
 * Service Interface for managing Comment.
 */
public interface CommentService {

    /**
     * Save a comment.
     *
     * @param commentDTO the entity to save
     * @return the persisted entity
     */
    CommentDTO save(CommentDTO commentDTO);

    /**
     * Get all the comments.
     *
     * @return the list of entities
     */
    List<CommentDTO> findAll();

    /**
     * Get the "id" comment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CommentDTO findOne(Long id);

    /**
     * Delete the "id" comment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the comment corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<CommentDTO> search(String query);
}
