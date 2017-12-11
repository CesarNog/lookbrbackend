package com.lookbr.backend.service;

import com.lookbr.backend.service.dto.ClosetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Closet.
 */
public interface ClosetService {

    /**
     * Save a closet.
     *
     * @param closetDTO the entity to save
     * @return the persisted entity
     */
    ClosetDTO save(ClosetDTO closetDTO);

    /**
     * Get all the closets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClosetDTO> findAll(Pageable pageable);

    /**
     * Get the "id" closet.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ClosetDTO findOne(Long id);

    /**
     * Delete the "id" closet.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the closet corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClosetDTO> search(String query, Pageable pageable);
}
