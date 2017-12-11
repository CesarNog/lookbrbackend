package com.lookbr.backend.service;

import com.lookbr.backend.service.dto.TimelineDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Timeline.
 */
public interface TimelineService {

    /**
     * Save a timeline.
     *
     * @param timelineDTO the entity to save
     * @return the persisted entity
     */
    TimelineDTO save(TimelineDTO timelineDTO);

    /**
     * Get all the timelines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TimelineDTO> findAll(Pageable pageable);

    /**
     * Get the "id" timeline.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TimelineDTO findOne(Long id);

    /**
     * Delete the "id" timeline.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the timeline corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TimelineDTO> search(String query, Pageable pageable);
}
