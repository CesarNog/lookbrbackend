package com.lookbr.backend.service.impl;

import com.lookbr.backend.service.ConsultantService;
import com.lookbr.backend.domain.Consultant;
import com.lookbr.backend.repository.ConsultantRepository;
import com.lookbr.backend.service.dto.ConsultantDTO;
import com.lookbr.backend.service.mapper.ConsultantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Consultant.
 */
@Service
@Transactional
public class ConsultantServiceImpl implements ConsultantService{

    private final Logger log = LoggerFactory.getLogger(ConsultantServiceImpl.class);

    private final ConsultantRepository consultantRepository;

    private final ConsultantMapper consultantMapper;

    public ConsultantServiceImpl(ConsultantRepository consultantRepository, ConsultantMapper consultantMapper) {
        this.consultantRepository = consultantRepository;
        this.consultantMapper = consultantMapper;
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
        return consultantMapper.toDto(consultant);
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
    }
}
