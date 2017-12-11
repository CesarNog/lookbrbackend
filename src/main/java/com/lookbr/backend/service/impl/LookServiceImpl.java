package com.lookbr.backend.service.impl;

import com.lookbr.backend.service.LookService;
import com.lookbr.backend.domain.Look;
import com.lookbr.backend.repository.LookRepository;
import com.lookbr.backend.repository.search.LookSearchRepository;
import com.lookbr.backend.service.dto.LookDTO;
import com.lookbr.backend.service.mapper.LookMapper;
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
 * Service Implementation for managing Look.
 */
@Service
@Transactional
public class LookServiceImpl implements LookService{

    private final Logger log = LoggerFactory.getLogger(LookServiceImpl.class);

    private final LookRepository lookRepository;

    private final LookMapper lookMapper;

    private final LookSearchRepository lookSearchRepository;

    public LookServiceImpl(LookRepository lookRepository, LookMapper lookMapper, LookSearchRepository lookSearchRepository) {
        this.lookRepository = lookRepository;
        this.lookMapper = lookMapper;
        this.lookSearchRepository = lookSearchRepository;
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
        LookDTO result = lookMapper.toDto(look);
        lookSearchRepository.save(look);
        return result;
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
        lookSearchRepository.delete(id);
    }

    /**
     * Search for the look corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LookDTO> search(String query) {
        log.debug("Request to search Looks for query {}", query);
        return StreamSupport
            .stream(lookSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(lookMapper::toDto)
            .collect(Collectors.toList());
    }
}
