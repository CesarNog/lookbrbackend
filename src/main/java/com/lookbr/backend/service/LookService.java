package com.lookbr.backend.service;

import com.lookbr.backend.service.dto.LookDTO;
import java.util.List;

/**
 * Service Interface for managing Look.
 */
public interface LookService {

    /**
     * Save a look.
     *
     * @param lookDTO the entity to save
     * @return the persisted entity
     */
    LookDTO save(LookDTO lookDTO);

    /**
     * Get all the looks.
     *
     * @return the list of entities
     */
    List<LookDTO> findAll();

    /**
     * Get the "id" look.
     *
     * @param id the id of the entity
     * @return the entity
     */
    LookDTO findOne(Long id);

    /**
     * Delete the "id" look.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
