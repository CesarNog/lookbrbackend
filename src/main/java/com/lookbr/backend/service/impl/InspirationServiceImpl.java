package com.lookbr.backend.service.impl;

import com.lookbr.backend.service.InspirationService;
import com.lookbr.backend.domain.Inspiration;
import com.lookbr.backend.repository.InspirationRepository;
import com.lookbr.backend.service.dto.InspirationDTO;
import com.lookbr.backend.service.mapper.InspirationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Inspiration.
 */
@Service
@Transactional
public class InspirationServiceImpl implements InspirationService{

    private final Logger log = LoggerFactory.getLogger(InspirationServiceImpl.class);

    private final InspirationRepository inspirationRepository;

    private final InspirationMapper inspirationMapper;

    public InspirationServiceImpl(InspirationRepository inspirationRepository, InspirationMapper inspirationMapper) {
        this.inspirationRepository = inspirationRepository;
        this.inspirationMapper = inspirationMapper;
    }

    /**
     * Save a inspiration.
     *
     * @param inspirationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InspirationDTO save(InspirationDTO inspirationDTO) {
        log.debug("Request to save Inspiration : {}", inspirationDTO);
        Inspiration inspiration = inspirationMapper.toEntity(inspirationDTO);
        inspiration = inspirationRepository.save(inspiration);
        return inspirationMapper.toDto(inspiration);
    }

    /**
     * Get all the inspirations.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<InspirationDTO> findAll() {
        log.debug("Request to get all Inspirations");
        return inspirationRepository.findAll().stream()
            .map(inspirationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one inspiration by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public InspirationDTO findOne(Long id) {
        log.debug("Request to get Inspiration : {}", id);
        Inspiration inspiration = inspirationRepository.findOne(id);
        return inspirationMapper.toDto(inspiration);
    }

    /**
     * Delete the inspiration by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Inspiration : {}", id);
        inspirationRepository.delete(id);
    }
}
