package com.lookbr.backend.service;

import com.lookbr.backend.service.dto.OccasionDTO;
import java.util.List;

/**
 * Service Interface for managing Occasion.
 */
public interface OccasionService {

    /**
     * Save a occasion.
     *
     * @param occasionDTO the entity to save
     * @return the persisted entity
     */
    OccasionDTO save(OccasionDTO occasionDTO);

    /**
     * Get all the occasions.
     *
     * @return the list of entities
     */
    List<OccasionDTO> findAll();

    /**
     * Get the "id" occasion.
     *
     * @param id the id of the entity
     * @return the entity
     */
    OccasionDTO findOne(Long id);

    /**
     * Delete the "id" occasion.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the occasion corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<OccasionDTO> search(String query);
}
