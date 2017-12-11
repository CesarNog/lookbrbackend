package com.lookbr.backend.service.impl;

import com.lookbr.backend.service.OccasionService;
import com.lookbr.backend.domain.Occasion;
import com.lookbr.backend.repository.OccasionRepository;
import com.lookbr.backend.repository.search.OccasionSearchRepository;
import com.lookbr.backend.service.dto.OccasionDTO;
import com.lookbr.backend.service.mapper.OccasionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Occasion.
 */
@Service
@Transactional
public class OccasionServiceImpl implements OccasionService{

    private final Logger log = LoggerFactory.getLogger(OccasionServiceImpl.class);

    private final OccasionRepository occasionRepository;

    private final OccasionMapper occasionMapper;

    private final OccasionSearchRepository occasionSearchRepository;

    public OccasionServiceImpl(OccasionRepository occasionRepository, OccasionMapper occasionMapper, OccasionSearchRepository occasionSearchRepository) {
        this.occasionRepository = occasionRepository;
        this.occasionMapper = occasionMapper;
        this.occasionSearchRepository = occasionSearchRepository;
    }

    /**
     * Save a occasion.
     *
     * @param occasionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public OccasionDTO save(OccasionDTO occasionDTO) {
        log.debug("Request to save Occasion : {}", occasionDTO);
        Occasion occasion = occasionMapper.toEntity(occasionDTO);
        occasion = occasionRepository.save(occasion);
        OccasionDTO result = occasionMapper.toDto(occasion);
        occasionSearchRepository.save(occasion);
        return result;
    }

    /**
     * Get all the occasions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OccasionDTO> findAll() {
        log.debug("Request to get all Occasions");
        return occasionRepository.findAll().stream()
            .map(occasionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one occasion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public OccasionDTO findOne(Long id) {
        log.debug("Request to get Occasion : {}", id);
        Occasion occasion = occasionRepository.findOne(id);
        return occasionMapper.toDto(occasion);
    }

    /**
     * Delete the occasion by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Occasion : {}", id);
        occasionRepository.delete(id);
        occasionSearchRepository.delete(id);
    }

    /**
     * Search for the occasion corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<OccasionDTO> search(String query) {
        log.debug("Request to search Occasions for query {}", query);
        return StreamSupport
            .stream(occasionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(occasionMapper::toDto)
            .collect(Collectors.toList());
    }
}
