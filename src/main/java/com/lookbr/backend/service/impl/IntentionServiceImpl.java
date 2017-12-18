package com.lookbr.backend.service.impl;

import com.lookbr.backend.service.IntentionService;
import com.lookbr.backend.domain.Intention;
import com.lookbr.backend.repository.IntentionRepository;
import com.lookbr.backend.service.dto.IntentionDTO;
import com.lookbr.backend.service.mapper.IntentionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Intention.
 */
@Service
@Transactional
public class IntentionServiceImpl implements IntentionService{

    private final Logger log = LoggerFactory.getLogger(IntentionServiceImpl.class);

    private final IntentionRepository intentionRepository;

    private final IntentionMapper intentionMapper;

    public IntentionServiceImpl(IntentionRepository intentionRepository, IntentionMapper intentionMapper) {
        this.intentionRepository = intentionRepository;
        this.intentionMapper = intentionMapper;
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
        return intentionMapper.toDto(intention);
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
    }
}
