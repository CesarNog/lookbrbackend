package com.lookbr.backend.service.impl;

import com.lookbr.backend.service.LoginService;
import com.lookbr.backend.domain.Login;
import com.lookbr.backend.repository.LoginRepository;
import com.lookbr.backend.service.dto.LoginDTO;
import com.lookbr.backend.service.mapper.LoginMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Login.
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService{

    private final Logger log = LoggerFactory.getLogger(LoginServiceImpl.class);

    private final LoginRepository loginRepository;

    private final LoginMapper loginMapper;

    public LoginServiceImpl(LoginRepository loginRepository, LoginMapper loginMapper) {
        this.loginRepository = loginRepository;
        this.loginMapper = loginMapper;
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
        return loginMapper.toDto(login);
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
    }
}
