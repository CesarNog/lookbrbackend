package com.lookbr.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lookbr.backend.service.InspirationService;
import com.lookbr.backend.web.rest.errors.BadRequestAlertException;
import com.lookbr.backend.web.rest.util.HeaderUtil;
import com.lookbr.backend.service.dto.InspirationDTO;
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
 * REST controller for managing Inspiration.
 */
@RestController
@RequestMapping("/api")
public class InspirationResource {

    private final Logger log = LoggerFactory.getLogger(InspirationResource.class);

    private static final String ENTITY_NAME = "inspiration";

    private final InspirationService inspirationService;

    public InspirationResource(InspirationService inspirationService) {
        this.inspirationService = inspirationService;
    }

    /**
     * POST  /inspirations : Create a new inspiration.
     *
     * @param inspirationDTO the inspirationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inspirationDTO, or with status 400 (Bad Request) if the inspiration has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inspirations")
    @Timed
    public ResponseEntity<InspirationDTO> createInspiration(@RequestBody InspirationDTO inspirationDTO) throws URISyntaxException {
        log.debug("REST request to save Inspiration : {}", inspirationDTO);
        if (inspirationDTO.getId() != null) {
            throw new BadRequestAlertException("A new inspiration cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InspirationDTO result = inspirationService.save(inspirationDTO);
        return ResponseEntity.created(new URI("/api/inspirations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inspirations : Updates an existing inspiration.
     *
     * @param inspirationDTO the inspirationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inspirationDTO,
     * or with status 400 (Bad Request) if the inspirationDTO is not valid,
     * or with status 500 (Internal Server Error) if the inspirationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inspirations")
    @Timed
    public ResponseEntity<InspirationDTO> updateInspiration(@RequestBody InspirationDTO inspirationDTO) throws URISyntaxException {
        log.debug("REST request to update Inspiration : {}", inspirationDTO);
        if (inspirationDTO.getId() == null) {
            return createInspiration(inspirationDTO);
        }
        InspirationDTO result = inspirationService.save(inspirationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inspirationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inspirations : get all the inspirations.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of inspirations in body
     */
    @GetMapping("/inspirations")
    @Timed
    public List<InspirationDTO> getAllInspirations() {
        log.debug("REST request to get all Inspirations");
        return inspirationService.findAll();
        }

    /**
     * GET  /inspirations/:id : get the "id" inspiration.
     *
     * @param id the id of the inspirationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inspirationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/inspirations/{id}")
    @Timed
    public ResponseEntity<InspirationDTO> getInspiration(@PathVariable Long id) {
        log.debug("REST request to get Inspiration : {}", id);
        InspirationDTO inspirationDTO = inspirationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(inspirationDTO));
    }

    /**
     * DELETE  /inspirations/:id : delete the "id" inspiration.
     *
     * @param id the id of the inspirationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inspirations/{id}")
    @Timed
    public ResponseEntity<Void> deleteInspiration(@PathVariable Long id) {
        log.debug("REST request to delete Inspiration : {}", id);
        inspirationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
