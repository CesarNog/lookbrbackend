package com.lookbr.backend.service.impl;

import com.lookbr.backend.service.SignupService;
import com.lookbr.backend.domain.Signup;
import com.lookbr.backend.repository.SignupRepository;
import com.lookbr.backend.repository.search.SignupSearchRepository;
import com.lookbr.backend.service.dto.SignupDTO;
import com.lookbr.backend.service.mapper.SignupMapper;
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
 * Service Implementation for managing Signup.
 */
@Service
@Transactional
public class SignupServiceImpl implements SignupService{

    private final Logger log = LoggerFactory.getLogger(SignupServiceImpl.class);

    private final SignupRepository signupRepository;

    private final SignupMapper signupMapper;

    private final SignupSearchRepository signupSearchRepository;

    public SignupServiceImpl(SignupRepository signupRepository, SignupMapper signupMapper, SignupSearchRepository signupSearchRepository) {
        this.signupRepository = signupRepository;
        this.signupMapper = signupMapper;
        this.signupSearchRepository = signupSearchRepository;
    }

    /**
     * Save a signup.
     *
     * @param signupDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SignupDTO save(SignupDTO signupDTO) {
        log.debug("Request to save Signup : {}", signupDTO);
        Signup signup = signupMapper.toEntity(signupDTO);
        signup = signupRepository.save(signup);
        SignupDTO result = signupMapper.toDto(signup);
        signupSearchRepository.save(signup);
        return result;
    }

    /**
     * Get all the signups.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SignupDTO> findAll() {
        log.debug("Request to get all Signups");
        return signupRepository.findAll().stream()
            .map(signupMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one signup by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SignupDTO findOne(Long id) {
        log.debug("Request to get Signup : {}", id);
        Signup signup = signupRepository.findOne(id);
        return signupMapper.toDto(signup);
    }

    /**
     * Delete the signup by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Signup : {}", id);
        signupRepository.delete(id);
        signupSearchRepository.delete(id);
    }

    /**
     * Search for the signup corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SignupDTO> search(String query) {
        log.debug("Request to search Signups for query {}", query);
        return StreamSupport
            .stream(signupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(signupMapper::toDto)
            .collect(Collectors.toList());
    }
}
