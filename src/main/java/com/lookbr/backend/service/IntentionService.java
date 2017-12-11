package com.lookbr.backend.service;

import com.lookbr.backend.service.dto.IntentionDTO;
import java.util.List;

/**
 * Service Interface for managing Intention.
 */
public interface IntentionService {

    /**
     * Save a intention.
     *
     * @param intentionDTO the entity to save
     * @return the persisted entity
     */
    IntentionDTO save(IntentionDTO intentionDTO);

    /**
     * Get all the intentions.
     *
     * @return the list of entities
     */
    List<IntentionDTO> findAll();

    /**
     * Get the "id" intention.
     *
     * @param id the id of the entity
     * @return the entity
     */
    IntentionDTO findOne(Long id);

    /**
     * Delete the "id" intention.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the intention corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<IntentionDTO> search(String query);
}
