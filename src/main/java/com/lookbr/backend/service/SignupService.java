package com.lookbr.backend.service;

import com.lookbr.backend.service.dto.SignupDTO;
import java.util.List;

/**
 * Service Interface for managing Signup.
 */
public interface SignupService {

    /**
     * Save a signup.
     *
     * @param signupDTO the entity to save
     * @return the persisted entity
     */
    SignupDTO save(SignupDTO signupDTO);

    /**
     * Get all the signups.
     *
     * @return the list of entities
     */
    List<SignupDTO> findAll();

    /**
     * Get the "id" signup.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SignupDTO findOne(Long id);

    /**
     * Delete the "id" signup.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
