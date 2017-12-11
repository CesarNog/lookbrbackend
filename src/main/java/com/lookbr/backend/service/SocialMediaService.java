package com.lookbr.backend.service;

import com.lookbr.backend.service.dto.SocialMediaDTO;
import java.util.List;

/**
 * Service Interface for managing SocialMedia.
 */
public interface SocialMediaService {

    /**
     * Save a socialMedia.
     *
     * @param socialMediaDTO the entity to save
     * @return the persisted entity
     */
    SocialMediaDTO save(SocialMediaDTO socialMediaDTO);

    /**
     * Get all the socialMedias.
     *
     * @return the list of entities
     */
    List<SocialMediaDTO> findAll();

    /**
     * Get the "id" socialMedia.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SocialMediaDTO findOne(Long id);

    /**
     * Delete the "id" socialMedia.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the socialMedia corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<SocialMediaDTO> search(String query);
}
