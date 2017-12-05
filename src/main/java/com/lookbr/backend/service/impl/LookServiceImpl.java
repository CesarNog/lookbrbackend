package com.lookbr.backend.service.impl;

import com.lookbr.backend.service.LookService;
import com.lookbr.backend.domain.Look;
import com.lookbr.backend.repository.LookRepository;
import com.lookbr.backend.service.dto.LookDTO;
import com.lookbr.backend.service.mapper.LookMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Look.
 */
@Service
@Transactional
public class LookServiceImpl implements LookService{

    private final Logger log = LoggerFactory.getLogger(LookServiceImpl.class);

    private final LookRepository lookRepository;

    private final LookMapper lookMapper;

    public LookServiceImpl(LookRepository lookRepository, LookMapper lookMapper) {
        this.lookRepository = lookRepository;
        this.lookMapper = lookMapper;
    }

    /**
     * Save a look.
     *
     * @param lookDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LookDTO save(LookDTO lookDTO) {
        log.debug("Request to save Look : {}", lookDTO);
        Look look = lookMapper.toEntity(lookDTO);
        look = lookRepository.save(look);
        return lookMapper.toDto(look);
    }

    /**
     * Get all the looks.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LookDTO> findAll() {
        log.debug("Request to get all Looks");
        return lookRepository.findAll().stream()
            .map(lookMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one look by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LookDTO findOne(Long id) {
        log.debug("Request to get Look : {}", id);
        Look look = lookRepository.findOne(id);
        return lookMapper.toDto(look);
    }

    /**
     * Delete the look by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Look : {}", id);
        lookRepository.delete(id);
    }
}
