package com.lookbr.backend.service;

import com.lookbr.backend.service.dto.TemperatureDTO;
import java.util.List;

/**
 * Service Interface for managing Temperature.
 */
public interface TemperatureService {

    /**
     * Save a temperature.
     *
     * @param temperatureDTO the entity to save
     * @return the persisted entity
     */
    TemperatureDTO save(TemperatureDTO temperatureDTO);

    /**
     * Get all the temperatures.
     *
     * @return the list of entities
     */
    List<TemperatureDTO> findAll();

    /**
     * Get the "id" temperature.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TemperatureDTO findOne(Long id);

    /**
     * Delete the "id" temperature.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the temperature corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<TemperatureDTO> search(String query);
}
