package com.lookbr.backend.service.impl;

import com.lookbr.backend.service.IntentionService;
import com.lookbr.backend.domain.Intention;
import com.lookbr.backend.repository.IntentionRepository;
import com.lookbr.backend.repository.search.IntentionSearchRepository;
import com.lookbr.backend.service.dto.IntentionDTO;
import com.lookbr.backend.service.mapper.IntentionMapper;
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
 * Service Implementation for managing Intention.
 */
@Service
@Transactional
public class IntentionServiceImpl implements IntentionService{

    private final Logger log = LoggerFactory.getLogger(IntentionServiceImpl.class);

    private final IntentionRepository intentionRepository;

    private final IntentionMapper intentionMapper;

    private final IntentionSearchRepository intentionSearchRepository;

    public IntentionServiceImpl(IntentionRepository intentionRepository, IntentionMapper intentionMapper, IntentionSearchRepository intentionSearchRepository) {
        this.intentionRepository = intentionRepository;
        this.intentionMapper = intentionMapper;
        this.intentionSearchRepository = intentionSearchRepository;
    }

    /**
     * Save a intention.
     *
     * @param intentionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public IntentionDTO save(IntentionDTO intentionDTO) {
        log.debug("Request to save Intention : {}", intentionDTO);
        Intention intention = intentionMapper.toEntity(intentionDTO);
        intention = intentionRepository.save(intention);
        IntentionDTO result = intentionMapper.toDto(intention);
        intentionSearchRepository.save(intention);
        return result;
    }

    /**
     * Get all the intentions.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<IntentionDTO> findAll() {
        log.debug("Request to get all Intentions");
        return intentionRepository.findAll().stream()
            .map(intentionMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one intention by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public IntentionDTO findOne(Long id) {
        log.debug("Request to get Intention : {}", id);
        Intention intention = intentionRepository.findOne(id);
        return intentionMapper.toDto(intention);
    }

    /**
     * Delete the intention by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Intention : {}", id);
        intentionRepository.delete(id);
        intentionSearchRepository.delete(id);
    }

    /**
     * Search for the intention corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<IntentionDTO> search(String query) {
        log.debug("Request to search Intentions for query {}", query);
        return StreamSupport
            .stream(intentionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(intentionMapper::toDto)
            .collect(Collectors.toList());
    }
}
