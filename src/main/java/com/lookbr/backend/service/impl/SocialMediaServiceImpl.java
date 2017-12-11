package com.lookbr.backend.service.impl;

import com.lookbr.backend.service.SocialMediaService;
import com.lookbr.backend.domain.SocialMedia;
import com.lookbr.backend.repository.SocialMediaRepository;
import com.lookbr.backend.repository.search.SocialMediaSearchRepository;
import com.lookbr.backend.service.dto.SocialMediaDTO;
import com.lookbr.backend.service.mapper.SocialMediaMapper;
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
 * Service Implementation for managing SocialMedia.
 */
@Service
@Transactional
public class SocialMediaServiceImpl implements SocialMediaService{

    private final Logger log = LoggerFactory.getLogger(SocialMediaServiceImpl.class);

    private final SocialMediaRepository socialMediaRepository;

    private final SocialMediaMapper socialMediaMapper;

    private final SocialMediaSearchRepository socialMediaSearchRepository;

    public SocialMediaServiceImpl(SocialMediaRepository socialMediaRepository, SocialMediaMapper socialMediaMapper, SocialMediaSearchRepository socialMediaSearchRepository) {
        this.socialMediaRepository = socialMediaRepository;
        this.socialMediaMapper = socialMediaMapper;
        this.socialMediaSearchRepository = socialMediaSearchRepository;
    }

    /**
     * Save a socialMedia.
     *
     * @param socialMediaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SocialMediaDTO save(SocialMediaDTO socialMediaDTO) {
        log.debug("Request to save SocialMedia : {}", socialMediaDTO);
        SocialMedia socialMedia = socialMediaMapper.toEntity(socialMediaDTO);
        socialMedia = socialMediaRepository.save(socialMedia);
        SocialMediaDTO result = socialMediaMapper.toDto(socialMedia);
        socialMediaSearchRepository.save(socialMedia);
        return result;
    }

    /**
     * Get all the socialMedias.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SocialMediaDTO> findAll() {
        log.debug("Request to get all SocialMedias");
        return socialMediaRepository.findAll().stream()
            .map(socialMediaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one socialMedia by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SocialMediaDTO findOne(Long id) {
        log.debug("Request to get SocialMedia : {}", id);
        SocialMedia socialMedia = socialMediaRepository.findOne(id);
        return socialMediaMapper.toDto(socialMedia);
    }

    /**
     * Delete the socialMedia by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SocialMedia : {}", id);
        socialMediaRepository.delete(id);
        socialMediaSearchRepository.delete(id);
    }

    /**
     * Search for the socialMedia corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<SocialMediaDTO> search(String query) {
        log.debug("Request to search SocialMedias for query {}", query);
        return StreamSupport
            .stream(socialMediaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .map(socialMediaMapper::toDto)
            .collect(Collectors.toList());
    }
}
