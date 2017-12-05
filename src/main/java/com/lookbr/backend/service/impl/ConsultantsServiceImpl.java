package com.lookbr.backend.service.impl;

import com.lookbr.backend.service.ConsultantsService;
import com.lookbr.backend.domain.Consultants;
import com.lookbr.backend.repository.ConsultantsRepository;
import com.lookbr.backend.service.dto.ConsultantsDTO;
import com.lookbr.backend.service.mapper.ConsultantsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Consultants.
 */
@Service
@Transactional
public class ConsultantsServiceImpl implements ConsultantsService{

    private final Logger log = LoggerFactory.getLogger(ConsultantsServiceImpl.class);

    private final ConsultantsRepository consultantsRepository;

    private final ConsultantsMapper consultantsMapper;

    public ConsultantsServiceImpl(ConsultantsRepository consultantsRepository, ConsultantsMapper consultantsMapper) {
        this.consultantsRepository = consultantsRepository;
        this.consultantsMapper = consultantsMapper;
    }

    /**
     * Save a consultants.
     *
     * @param consultantsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ConsultantsDTO save(ConsultantsDTO consultantsDTO) {
        log.debug("Request to save Consultants : {}", consultantsDTO);
        Consultants consultants = consultantsMapper.toEntity(consultantsDTO);
        consultants = consultantsRepository.save(consultants);
        return consultantsMapper.toDto(consultants);
    }

    /**
     * Get all the consultants.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ConsultantsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Consultants");
        return consultantsRepository.findAll(pageable)
            .map(consultantsMapper::toDto);
    }

    /**
     * Get one consultants by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ConsultantsDTO findOne(Long id) {
        log.debug("Request to get Consultants : {}", id);
        Consultants consultants = consultantsRepository.findOne(id);
        return consultantsMapper.toDto(consultants);
    }

    /**
     * Delete the consultants by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Consultants : {}", id);
        consultantsRepository.delete(id);
    }
}
