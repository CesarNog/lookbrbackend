package com.lookbr.backend.service.impl;

import com.lookbr.backend.service.ClosetService;
import com.lookbr.backend.domain.Closet;
import com.lookbr.backend.repository.ClosetRepository;
import com.lookbr.backend.service.dto.ClosetDTO;
import com.lookbr.backend.service.mapper.ClosetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Closet.
 */
@Service
@Transactional
public class ClosetServiceImpl implements ClosetService{

    private final Logger log = LoggerFactory.getLogger(ClosetServiceImpl.class);

    private final ClosetRepository closetRepository;

    private final ClosetMapper closetMapper;

    public ClosetServiceImpl(ClosetRepository closetRepository, ClosetMapper closetMapper) {
        this.closetRepository = closetRepository;
        this.closetMapper = closetMapper;
    }

    /**
     * Save a closet.
     *
     * @param closetDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClosetDTO save(ClosetDTO closetDTO) {
        log.debug("Request to save Closet : {}", closetDTO);
        Closet closet = closetMapper.toEntity(closetDTO);
        closet = closetRepository.save(closet);
        return closetMapper.toDto(closet);
    }

    /**
     * Get all the closets.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClosetDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Closets");
        return closetRepository.findAll(pageable)
            .map(closetMapper::toDto);
    }

    /**
     * Get one closet by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ClosetDTO findOne(Long id) {
        log.debug("Request to get Closet : {}", id);
        Closet closet = closetRepository.findOne(id);
        return closetMapper.toDto(closet);
    }

    /**
     * Delete the closet by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Closet : {}", id);
        closetRepository.delete(id);
    }
}
