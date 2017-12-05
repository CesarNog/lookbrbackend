package com.lookbr.backend.service.impl;

import com.lookbr.backend.service.SignupService;
import com.lookbr.backend.domain.Signup;
import com.lookbr.backend.repository.SignupRepository;
import com.lookbr.backend.service.dto.SignupDTO;
import com.lookbr.backend.service.mapper.SignupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Signup.
 */
@Service
@Transactional
public class SignupServiceImpl implements SignupService{

    private final Logger log = LoggerFactory.getLogger(SignupServiceImpl.class);

    private final SignupRepository signupRepository;

    private final SignupMapper signupMapper;

    public SignupServiceImpl(SignupRepository signupRepository, SignupMapper signupMapper) {
        this.signupRepository = signupRepository;
        this.signupMapper = signupMapper;
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
        return signupMapper.toDto(signup);
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
    }
}
