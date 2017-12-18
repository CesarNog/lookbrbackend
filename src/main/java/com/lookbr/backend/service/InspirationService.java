package com.lookbr.backend.service;

import com.lookbr.backend.service.dto.InspirationDTO;
import java.util.List;

/**
 * Service Interface for managing Inspiration.
 */
public interface InspirationService {

    /**
     * Save a inspiration.
     *
     * @param inspirationDTO the entity to save
     * @return the persisted entity
     */
    InspirationDTO save(InspirationDTO inspirationDTO);

    /**
     * Get all the inspirations.
     *
     * @return the list of entities
     */
    List<InspirationDTO> findAll();

    /**
     * Get the "id" inspiration.
     *
     * @param id the id of the entity
     * @return the entity
     */
    InspirationDTO findOne(Long id);

    /**
     * Delete the "id" inspiration.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
