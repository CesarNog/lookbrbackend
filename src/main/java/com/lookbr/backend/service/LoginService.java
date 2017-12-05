package com.lookbr.backend.service;

import com.lookbr.backend.service.dto.LoginDTO;
import java.util.List;

/**
 * Service Interface for managing Login.
 */
public interface LoginService {

    /**
     * Save a login.
     *
     * @param loginDTO the entity to save
     * @return the persisted entity
     */
    LoginDTO save(LoginDTO loginDTO);

    /**
     * Get all the logins.
     *
     * @return the list of entities
     */
    List<LoginDTO> findAll();

    /**
     * Get the "id" login.
     *
     * @param id the id of the entity
     * @return the entity
     */
    LoginDTO findOne(Long id);

    /**
     * Delete the "id" login.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
