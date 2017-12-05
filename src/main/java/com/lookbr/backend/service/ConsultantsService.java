package com.lookbr.backend.service;

import com.lookbr.backend.service.dto.ConsultantsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Consultants.
 */
public interface ConsultantsService {

    /**
     * Save a consultants.
     *
     * @param consultantsDTO the entity to save
     * @return the persisted entity
     */
    ConsultantsDTO save(ConsultantsDTO consultantsDTO);

    /**
     * Get all the consultants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ConsultantsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" consultants.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ConsultantsDTO findOne(Long id);

    /**
     * Delete the "id" consultants.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
