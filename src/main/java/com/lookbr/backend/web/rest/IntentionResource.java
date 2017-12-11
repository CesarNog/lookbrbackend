package com.lookbr.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lookbr.backend.service.IntentionService;
import com.lookbr.backend.web.rest.errors.BadRequestAlertException;
import com.lookbr.backend.web.rest.util.HeaderUtil;
import com.lookbr.backend.service.dto.IntentionDTO;
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
 * REST controller for managing Intention.
 */
@RestController
@RequestMapping("/api")
public class IntentionResource {

    private final Logger log = LoggerFactory.getLogger(IntentionResource.class);

    private static final String ENTITY_NAME = "intention";

    private final IntentionService intentionService;

    public IntentionResource(IntentionService intentionService) {
        this.intentionService = intentionService;
    }

    /**
     * POST  /intentions : Create a new intention.
     *
     * @param intentionDTO the intentionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new intentionDTO, or with status 400 (Bad Request) if the intention has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/intentions")
    @Timed
    public ResponseEntity<IntentionDTO> createIntention(@RequestBody IntentionDTO intentionDTO) throws URISyntaxException {
        log.debug("REST request to save Intention : {}", intentionDTO);
        if (intentionDTO.getId() != null) {
            throw new BadRequestAlertException("A new intention cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IntentionDTO result = intentionService.save(intentionDTO);
        return ResponseEntity.created(new URI("/api/intentions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /intentions : Updates an existing intention.
     *
     * @param intentionDTO the intentionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated intentionDTO,
     * or with status 400 (Bad Request) if the intentionDTO is not valid,
     * or with status 500 (Internal Server Error) if the intentionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/intentions")
    @Timed
    public ResponseEntity<IntentionDTO> updateIntention(@RequestBody IntentionDTO intentionDTO) throws URISyntaxException {
        log.debug("REST request to update Intention : {}", intentionDTO);
        if (intentionDTO.getId() == null) {
            return createIntention(intentionDTO);
        }
        IntentionDTO result = intentionService.save(intentionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, intentionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /intentions : get all the intentions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of intentions in body
     */
    @GetMapping("/intentions")
    @Timed
    public List<IntentionDTO> getAllIntentions() {
        log.debug("REST request to get all Intentions");
        return intentionService.findAll();
        }

    /**
     * GET  /intentions/:id : get the "id" intention.
     *
     * @param id the id of the intentionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the intentionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/intentions/{id}")
    @Timed
    public ResponseEntity<IntentionDTO> getIntention(@PathVariable Long id) {
        log.debug("REST request to get Intention : {}", id);
        IntentionDTO intentionDTO = intentionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(intentionDTO));
    }

    /**
     * DELETE  /intentions/:id : delete the "id" intention.
     *
     * @param id the id of the intentionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/intentions/{id}")
    @Timed
    public ResponseEntity<Void> deleteIntention(@PathVariable Long id) {
        log.debug("REST request to delete Intention : {}", id);
        intentionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/intentions?query=:query : search for the intention corresponding
     * to the query.
     *
     * @param query the query of the intention search
     * @return the result of the search
     */
    @GetMapping("/_search/intentions")
    @Timed
    public List<IntentionDTO> searchIntentions(@RequestParam String query) {
        log.debug("REST request to search Intentions for query {}", query);
        return intentionService.search(query);
    }

}
