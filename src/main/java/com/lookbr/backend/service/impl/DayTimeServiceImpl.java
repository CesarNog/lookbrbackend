package com.lookbr.backend.service.impl;

import com.lookbr.backend.service.DayTimeService;
import com.lookbr.backend.domain.DayTime;
import com.lookbr.backend.repository.DayTimeRepository;
import com.lookbr.backend.service.dto.DayTimeDTO;
import com.lookbr.backend.service.mapper.DayTimeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing DayTime.
 */
@Service
@Transactional
public class DayTimeServiceImpl implements DayTimeService{

    private final Logger log = LoggerFactory.getLogger(DayTimeServiceImpl.class);

    private final DayTimeRepository dayTimeRepository;

    private final DayTimeMapper dayTimeMapper;

    public DayTimeServiceImpl(DayTimeRepository dayTimeRepository, DayTimeMapper dayTimeMapper) {
        this.dayTimeRepository = dayTimeRepository;
        this.dayTimeMapper = dayTimeMapper;
    }

    /**
     * Save a dayTime.
     *
     * @param dayTimeDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DayTimeDTO save(DayTimeDTO dayTimeDTO) {
        log.debug("Request to save DayTime : {}", dayTimeDTO);
        DayTime dayTime = dayTimeMapper.toEntity(dayTimeDTO);
        dayTime = dayTimeRepository.save(dayTime);
        return dayTimeMapper.toDto(dayTime);
    }

    /**
     * Get all the dayTimes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<DayTimeDTO> findAll() {
        log.debug("Request to get all DayTimes");
        return dayTimeRepository.findAll().stream()
            .map(dayTimeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one dayTime by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DayTimeDTO findOne(Long id) {
        log.debug("Request to get DayTime : {}", id);
        DayTime dayTime = dayTimeRepository.findOne(id);
        return dayTimeMapper.toDto(dayTime);
    }

    /**
     * Delete the dayTime by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DayTime : {}", id);
        dayTimeRepository.delete(id);
    }
}
