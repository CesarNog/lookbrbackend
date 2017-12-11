package com.lookbr.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lookbr.backend.service.OccasionService;
import com.lookbr.backend.web.rest.errors.BadRequestAlertException;
import com.lookbr.backend.web.rest.util.HeaderUtil;
import com.lookbr.backend.service.dto.OccasionDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Occasion.
 */
@RestController
@RequestMapping("/api")
public class OccasionResource {

    private final Logger log = LoggerFactory.getLogger(OccasionResource.class);

    private static final String ENTITY_NAME = "occasion";

    private final OccasionService occasionService;

    public OccasionResource(OccasionService occasionService) {
        this.occasionService = occasionService;
    }

    /**
     * POST  /occasions : Create a new occasion.
     *
     * @param occasionDTO the occasionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new occasionDTO, or with status 400 (Bad Request) if the occasion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/occasions")
    @Timed
    public ResponseEntity<OccasionDTO> createOccasion(@RequestBody OccasionDTO occasionDTO) throws URISyntaxException {
        log.debug("REST request to save Occasion : {}", occasionDTO);
        if (occasionDTO.getId() != null) {
            throw new BadRequestAlertException("A new occasion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OccasionDTO result = occasionService.save(occasionDTO);
        return ResponseEntity.created(new URI("/api/occasions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /occasions : Updates an existing occasion.
     *
     * @param occasionDTO the occasionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated occasionDTO,
     * or with status 400 (Bad Request) if the occasionDTO is not valid,
     * or with status 500 (Internal Server Error) if the occasionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/occasions")
    @Timed
    public ResponseEntity<OccasionDTO> updateOccasion(@RequestBody OccasionDTO occasionDTO) throws URISyntaxException {
        log.debug("REST request to update Occasion : {}", occasionDTO);
        if (occasionDTO.getId() == null) {
            return createOccasion(occasionDTO);
        }
        OccasionDTO result = occasionService.save(occasionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, occasionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /occasions : get all the occasions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of occasions in body
     */
    @GetMapping("/occasions")
    @Timed
    public List<OccasionDTO> getAllOccasions() {
        log.debug("REST request to get all Occasions");
        return occasionService.findAll();
        }

    /**
     * GET  /occasions/:id : get the "id" occasion.
     *
     * @param id the id of the occasionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the occasionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/occasions/{id}")
    @Timed
    public ResponseEntity<OccasionDTO> getOccasion(@PathVariable Long id) {
        log.debug("REST request to get Occasion : {}", id);
        OccasionDTO occasionDTO = occasionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(occasionDTO));
    }

    /**
     * DELETE  /occasions/:id : delete the "id" occasion.
     *
     * @param id the id of the occasionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/occasions/{id}")
    @Timed
    public ResponseEntity<Void> deleteOccasion(@PathVariable Long id) {
        log.debug("REST request to delete Occasion : {}", id);
        occasionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/occasions?query=:query : search for the occasion corresponding
     * to the query.
     *
     * @param query the query of the occasion search
     * @return the result of the search
     */
    @GetMapping("/_search/occasions")
    @Timed
    public List<OccasionDTO> searchOccasions(@RequestParam String query) {
        log.debug("REST request to search Occasions for query {}", query);
        return occasionService.search(query);
    }

}
