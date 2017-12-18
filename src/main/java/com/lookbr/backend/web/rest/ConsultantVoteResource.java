package com.lookbr.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lookbr.backend.service.ConsultantVoteService;
import com.lookbr.backend.web.rest.errors.BadRequestAlertException;
import com.lookbr.backend.web.rest.util.HeaderUtil;
import com.lookbr.backend.service.dto.ConsultantVoteDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ConsultantVote.
 */
@RestController
@RequestMapping("/api")
public class ConsultantVoteResource {

    private final Logger log = LoggerFactory.getLogger(ConsultantVoteResource.class);

    private static final String ENTITY_NAME = "consultantVote";

    private final ConsultantVoteService consultantVoteService;

    public ConsultantVoteResource(ConsultantVoteService consultantVoteService) {
        this.consultantVoteService = consultantVoteService;
    }

    /**
     * POST  /consultant-votes : Create a new consultantVote.
     *
     * @param consultantVoteDTO the consultantVoteDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new consultantVoteDTO, or with status 400 (Bad Request) if the consultantVote has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/consultant-votes")
    @Timed
    public ResponseEntity<ConsultantVoteDTO> createConsultantVote(@RequestBody ConsultantVoteDTO consultantVoteDTO) throws URISyntaxException {
        log.debug("REST request to save ConsultantVote : {}", consultantVoteDTO);
        if (consultantVoteDTO.getId() != null) {
            throw new BadRequestAlertException("A new consultantVote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsultantVoteDTO result = consultantVoteService.save(consultantVoteDTO);
        return ResponseEntity.created(new URI("/api/consultant-votes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /consultant-votes : Updates an existing consultantVote.
     *
     * @param consultantVoteDTO the consultantVoteDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated consultantVoteDTO,
     * or with status 400 (Bad Request) if the consultantVoteDTO is not valid,
     * or with status 500 (Internal Server Error) if the consultantVoteDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/consultant-votes")
    @Timed
    public ResponseEntity<ConsultantVoteDTO> updateConsultantVote(@RequestBody ConsultantVoteDTO consultantVoteDTO) throws URISyntaxException {
        log.debug("REST request to update ConsultantVote : {}", consultantVoteDTO);
        if (consultantVoteDTO.getId() == null) {
            return createConsultantVote(consultantVoteDTO);
        }
        ConsultantVoteDTO result = consultantVoteService.save(consultantVoteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, consultantVoteDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /consultant-votes : get all the consultantVotes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of consultantVotes in body
     */
    @GetMapping("/consultant-votes")
    @Timed
    public List<ConsultantVoteDTO> getAllConsultantVotes() {
        log.debug("REST request to get all ConsultantVotes");
        return consultantVoteService.findAll();
        }

    /**
     * GET  /consultant-votes/:id : get the "id" consultantVote.
     *
     * @param id the id of the consultantVoteDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the consultantVoteDTO, or with status 404 (Not Found)
     */
    @GetMapping("/consultant-votes/{id}")
    @Timed
    public ResponseEntity<ConsultantVoteDTO> getConsultantVote(@PathVariable Long id) {
        log.debug("REST request to get ConsultantVote : {}", id);
        ConsultantVoteDTO consultantVoteDTO = consultantVoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(consultantVoteDTO));
    }

    /**
     * DELETE  /consultant-votes/:id : delete the "id" consultantVote.
     *
     * @param id the id of the consultantVoteDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/consultant-votes/{id}")
    @Timed
    public ResponseEntity<Void> deleteConsultantVote(@PathVariable Long id) {
        log.debug("REST request to delete ConsultantVote : {}", id);
        consultantVoteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
