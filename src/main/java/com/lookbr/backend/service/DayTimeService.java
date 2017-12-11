package com.lookbr.backend.service;

import com.lookbr.backend.service.dto.DayTimeDTO;
import java.util.List;

/**
 * Service Interface for managing DayTime.
 */
public interface DayTimeService {

    /**
     * Save a dayTime.
     *
     * @param dayTimeDTO the entity to save
     * @return the persisted entity
     */
    DayTimeDTO save(DayTimeDTO dayTimeDTO);

    /**
     * Get all the dayTimes.
     *
     * @return the list of entities
     */
    List<DayTimeDTO> findAll();

    /**
     * Get the "id" dayTime.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DayTimeDTO findOne(Long id);

    /**
     * Delete the "id" dayTime.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the dayTime corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<DayTimeDTO> search(String query);
}
