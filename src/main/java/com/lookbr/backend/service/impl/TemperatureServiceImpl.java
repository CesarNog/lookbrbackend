package com.lookbr.backend.service.impl;

import com.lookbr.backend.service.TemperatureService;
import com.lookbr.backend.domain.Temperature;
import com.lookbr.backend.repository.TemperatureRepository;
import com.lookbr.backend.service.dto.TemperatureDTO;
import com.lookbr.backend.service.mapper.TemperatureMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Temperature.
 */
@Service
@Transactional
public class TemperatureServiceImpl implements TemperatureService{

    private final Logger log = LoggerFactory.getLogger(TemperatureServiceImpl.class);

    private final TemperatureRepository temperatureRepository;

    private final TemperatureMapper temperatureMapper;

    public TemperatureServiceImpl(TemperatureRepository temperatureRepository, TemperatureMapper temperatureMapper) {
        this.temperatureRepository = temperatureRepository;
        this.temperatureMapper = temperatureMapper;
    }

    /**
     * Save a temperature.
     *
     * @param temperatureDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TemperatureDTO save(TemperatureDTO temperatureDTO) {
        log.debug("Request to save Temperature : {}", temperatureDTO);
        Temperature temperature = temperatureMapper.toEntity(temperatureDTO);
        temperature = temperatureRepository.save(temperature);
        return temperatureMapper.toDto(temperature);
    }

    /**
     * Get all the temperatures.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TemperatureDTO> findAll() {
        log.debug("Request to get all Temperatures");
        return temperatureRepository.findAll().stream()
            .map(temperatureMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one temperature by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TemperatureDTO findOne(Long id) {
        log.debug("Request to get Temperature : {}", id);
        Temperature temperature = temperatureRepository.findOne(id);
        return temperatureMapper.toDto(temperature);
    }

    /**
     * Delete the temperature by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Temperature : {}", id);
        temperatureRepository.delete(id);
    }
}
