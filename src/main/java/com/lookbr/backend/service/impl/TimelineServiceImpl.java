package com.lookbr.backend.service.impl;

import com.lookbr.backend.service.TimelineService;
import com.lookbr.backend.domain.Timeline;
import com.lookbr.backend.repository.TimelineRepository;
import com.lookbr.backend.service.dto.TimelineDTO;
import com.lookbr.backend.service.mapper.TimelineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Timeline.
 */
@Service
@Transactional
public class TimelineServiceImpl implements TimelineService{

    private final Logger log = LoggerFactory.getLogger(TimelineServiceImpl.class);

    private final TimelineRepository timelineRepository;

    private final TimelineMapper timelineMapper;

    public TimelineServiceImpl(TimelineRepository timelineRepository, TimelineMapper timelineMapper) {
        this.timelineRepository = timelineRepository;
        this.timelineMapper = timelineMapper;
    }

    /**
     * Save a timeline.
     *
     * @param timelineDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TimelineDTO save(TimelineDTO timelineDTO) {
        log.debug("Request to save Timeline : {}", timelineDTO);
        Timeline timeline = timelineMapper.toEntity(timelineDTO);
        timeline = timelineRepository.save(timeline);
        return timelineMapper.toDto(timeline);
    }

    /**
     * Get all the timelines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TimelineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Timelines");
        return timelineRepository.findAll(pageable)
            .map(timelineMapper::toDto);
    }

    /**
     * Get one timeline by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TimelineDTO findOne(Long id) {
        log.debug("Request to get Timeline : {}", id);
        Timeline timeline = timelineRepository.findOne(id);
        return timelineMapper.toDto(timeline);
    }

    /**
     * Delete the timeline by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Timeline : {}", id);
        timelineRepository.delete(id);
    }
}
