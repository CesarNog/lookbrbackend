package com.lookbr.backend.service;

import com.lookbr.backend.service.dto.ConsultantVoteDTO;
import java.util.List;

/**
 * Service Interface for managing ConsultantVote.
 */
public interface ConsultantVoteService {

    /**
     * Save a consultantVote.
     *
     * @param consultantVoteDTO the entity to save
     * @return the persisted entity
     */
    ConsultantVoteDTO save(ConsultantVoteDTO consultantVoteDTO);

    /**
     * Get all the consultantVotes.
     *
     * @return the list of entities
     */
    List<ConsultantVoteDTO> findAll();

    /**
     * Get the "id" consultantVote.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ConsultantVoteDTO findOne(Long id);

    /**
     * Delete the "id" consultantVote.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the consultantVote corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ConsultantVoteDTO> search(String query);
}
