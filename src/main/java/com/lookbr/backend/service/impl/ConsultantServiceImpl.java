package com.lookbr.backend.service.impl;

import com.lookbr.backend.service.ConsultantService;
import com.lookbr.backend.domain.Consultant;
import com.lookbr.backend.repository.ConsultantRepository;
import com.lookbr.backend.repository.search.ConsultantSearchRepository;
import com.lookbr.backend.service.dto.ConsultantDTO;
import com.lookbr.backend.service.mapper.ConsultantMapper;
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
 * Service Implementation for managing Consultant.
 */
@Service
@Transactional
public class ConsultantServiceImpl implements ConsultantService{

    private final Logger log = LoggerFactory.getLogger(ConsultantServiceImpl.class);

    private final ConsultantRepository consultantRepository;

    private final ConsultantMapper consultantMapper;

    private final ConsultantSearchRepository consultantSearchRepository;

    public ConsultantServiceImpl(ConsultantRepository consultantRepository, ConsultantMapper consultantMapper, ConsultantSearchRepository consultantSearchRepository) {
        this.consultantRepository = consultantRepository;
        this.consultantMapper = consultantMapper;
        this.consultantSearchRepository = consultantSearchRepository;
    }

    /**
     * Save a consultant.
     *
     * @param consultantDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConsultantDTO save(ConsultantDTO consultantDTO) {
        log.debug("Request to save Consultant : {}", consultantDTO);
        Consultant consultant = consultantMapper.toEntity(consultantDTO);
        consultant = consultantRepository.save(consultant);
        ConsultantDTO result = consultantMapper.toDto(consultant);
        consultantSearchRepository.save(consultant);
        return result;
    }

    /**
     * Get all the consultants.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConsultantDTO> findAll() {
        log.debug("Request to get all Consultants");
        return consultantRepository.findAll().stream()
            .map(consultantMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one consultant by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ConsultantDTO findOne(Long id) {
        log.debug("Request to get Consultant : {}", id);
        Consultant consultant = consultantRepository.findOne(id);
        return consultantMapper.toDto(consultant);
    }

    /**
     * Delete the consultant by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Consultant : {}", id);
        consultantRepository.delete(id);
        consultantSearchRepository.delete(id);
    }

    /**
     * Search for the consultant corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConsultantDTO> search(String query) {
        log.debug("Request to search Consultants for query {}", query);
        return StreamSupport
            .stream(consultantSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(consultantMapper::toDto)
            .collect(Collectors.toList());
    }
}
