package com.lookbr.backend.service;

import com.lookbr.backend.service.dto.ConsultantDTO;
import java.util.List;

/**
 * Service Interface for managing Consultant.
 */
public interface ConsultantService {

    /**
     * Save a consultant.
     *
     * @param consultantDTO the entity to save
     * @return the persisted entity
     */
    ConsultantDTO save(ConsultantDTO consultantDTO);

    /**
     * Get all the consultants.
     *
     * @return the list of entities
     */
    List<ConsultantDTO> findAll();

    /**
     * Get the "id" consultant.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ConsultantDTO findOne(Long id);

    /**
     * Delete the "id" consultant.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
