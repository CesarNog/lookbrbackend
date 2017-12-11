package com.lookbr.backend.service.impl;

import com.lookbr.backend.service.LoginService;
import com.lookbr.backend.domain.Login;
import com.lookbr.backend.repository.LoginRepository;
import com.lookbr.backend.repository.search.LoginSearchRepository;
import com.lookbr.backend.service.dto.LoginDTO;
import com.lookbr.backend.service.mapper.LoginMapper;
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
 * Service Implementation for managing Login.
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService{

    private final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    private final LoginRepository loginRepository;

    private final LoginMapper loginMapper;

    private final LoginSearchRepository loginSearchRepository;

    public LoginServiceImpl(LoginRepository loginRepository, LoginMapper loginMapper, LoginSearchRepository loginSearchRepository) {
        this.loginRepository = loginRepository;
        this.loginMapper = loginMapper;
        this.loginSearchRepository = loginSearchRepository;
    }

    /**
     * Save a login.
     *
     * @param loginDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LoginDTO save(LoginDTO loginDTO) {
        log.debug("Request to save Login : {}", loginDTO);
        Login login = loginMapper.toEntity(loginDTO);
        login = loginRepository.save(login);
        LoginDTO result = loginMapper.toDto(login);
        loginSearchRepository.save(login);
        return result;
    }

    /**
     * Get all the logins.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LoginDTO> findAll() {
        log.debug("Request to get all Logins");
        return loginRepository.findAll().stream()
            .map(loginMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one login by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LoginDTO findOne(Long id) {
        log.debug("Request to get Login : {}", id);
        Login login = loginRepository.findOne(id);
        return loginMapper.toDto(login);
    }

    /**
     * Delete the login by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Login : {}", id);
        loginRepository.delete(id);
        loginSearchRepository.delete(id);
    }

    /**
     * Search for the login corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<LoginDTO> search(String query) {
        log.debug("Request to search Logins for query {}", query);
        return StreamSupport
            .stream(loginSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(loginMapper::toDto)
            .collect(Collectors.toList());
    }
}
