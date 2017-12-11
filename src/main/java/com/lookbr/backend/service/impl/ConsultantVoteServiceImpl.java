package com.lookbr.backend.service.impl;

import com.lookbr.backend.service.ConsultantVoteService;
import com.lookbr.backend.domain.ConsultantVote;
import com.lookbr.backend.repository.ConsultantVoteRepository;
import com.lookbr.backend.repository.search.ConsultantVoteSearchRepository;
import com.lookbr.backend.service.dto.ConsultantVoteDTO;
import com.lookbr.backend.service.mapper.ConsultantVoteMapper;
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
 * Service Implementation for managing ConsultantVote.
 */
@Service
@Transactional
public class ConsultantVoteServiceImpl implements ConsultantVoteService{

    private final Logger log = LoggerFactory.getLogger(ConsultantVoteServiceImpl.class);

    private final ConsultantVoteRepository consultantVoteRepository;

    private final ConsultantVoteMapper consultantVoteMapper;

    private final ConsultantVoteSearchRepository consultantVoteSearchRepository;

    public ConsultantVoteServiceImpl(ConsultantVoteRepository consultantVoteRepository, ConsultantVoteMapper consultantVoteMapper, ConsultantVoteSearchRepository consultantVoteSearchRepository) {
        this.consultantVoteRepository = consultantVoteRepository;
        this.consultantVoteMapper = consultantVoteMapper;
        this.consultantVoteSearchRepository = consultantVoteSearchRepository;
    }

    /**
     * Save a consultantVote.
     *
     * @param consultantVoteDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConsultantVoteDTO save(ConsultantVoteDTO consultantVoteDTO) {
        log.debug("Request to save ConsultantVote : {}", consultantVoteDTO);
        ConsultantVote consultantVote = consultantVoteMapper.toEntity(consultantVoteDTO);
        consultantVote = consultantVoteRepository.save(consultantVote);
        ConsultantVoteDTO result = consultantVoteMapper.toDto(consultantVote);
        consultantVoteSearchRepository.save(consultantVote);
        return result;
    }

    /**
     * Get all the consultantVotes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConsultantVoteDTO> findAll() {
        log.debug("Request to get all ConsultantVotes");
        return consultantVoteRepository.findAll().stream()
            .map(consultantVoteMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one consultantVote by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ConsultantVoteDTO findOne(Long id) {
        log.debug("Request to get ConsultantVote : {}", id);
        ConsultantVote consultantVote = consultantVoteRepository.findOne(id);
        return consultantVoteMapper.toDto(consultantVote);
    }

    /**
     * Delete the consultantVote by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConsultantVote : {}", id);
        consultantVoteRepository.delete(id);
        consultantVoteSearchRepository.delete(id);
    }

    /**
     * Search for the consultantVote corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ConsultantVoteDTO> search(String query) {
        log.debug("Request to search ConsultantVotes for query {}", query);
        return StreamSupport
            .stream(consultantVoteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(consultantVoteMapper::toDto)
            .collect(Collectors.toList());
    }
}
