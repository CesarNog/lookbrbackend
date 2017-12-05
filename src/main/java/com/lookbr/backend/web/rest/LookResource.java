package com.lookbr.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lookbr.backend.service.LookService;
import com.lookbr.backend.web.rest.errors.BadRequestAlertException;
import com.lookbr.backend.web.rest.util.HeaderUtil;
import com.lookbr.backend.service.dto.LookDTO;
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
 * REST controller for managing Look.
 */
@RestController
@RequestMapping("/api")
public class LookResource {

    private final Logger log = LoggerFactory.getLogger(LookResource.class);

    private static final String ENTITY_NAME = "look";

    private final LookService lookService;

    public LookResource(LookService lookService) {
        this.lookService = lookService;
    }

    /**
     * POST  /looks : Create a new look.
     *
     * @param lookDTO the lookDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new lookDTO, or with status 400 (Bad Request) if the look has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/looks")
    @Timed
    public ResponseEntity<LookDTO> createLook(@RequestBody LookDTO lookDTO) throws URISyntaxException {
        log.debug("REST request to save Look : {}", lookDTO);
        if (lookDTO.getId() != null) {
            throw new BadRequestAlertException("A new look cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LookDTO result = lookService.save(lookDTO);
        return ResponseEntity.created(new URI("/api/looks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /looks : Updates an existing look.
     *
     * @param lookDTO the lookDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated lookDTO,
     * or with status 400 (Bad Request) if the lookDTO is not valid,
     * or with status 500 (Internal Server Error) if the lookDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/looks")
    @Timed
    public ResponseEntity<LookDTO> updateLook(@RequestBody LookDTO lookDTO) throws URISyntaxException {
        log.debug("REST request to update Look : {}", lookDTO);
        if (lookDTO.getId() == null) {
            return createLook(lookDTO);
        }
        LookDTO result = lookService.save(lookDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, lookDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /looks : get all the looks.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of looks in body
     */
    @GetMapping("/looks")
    @Timed
    public List<LookDTO> getAllLooks() {
        log.debug("REST request to get all Looks");
        return lookService.findAll();
        }

    /**
     * GET  /looks/:id : get the "id" look.
     *
     * @param id the id of the lookDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the lookDTO, or with status 404 (Not Found)
     */
    @GetMapping("/looks/{id}")
    @Timed
    public ResponseEntity<LookDTO> getLook(@PathVariable Long id) {
        log.debug("REST request to get Look : {}", id);
        LookDTO lookDTO = lookService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(lookDTO));
    }

    /**
     * DELETE  /looks/:id : delete the "id" look.
     *
     * @param id the id of the lookDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/looks/{id}")
    @Timed
    public ResponseEntity<Void> deleteLook(@PathVariable Long id) {
        log.debug("REST request to delete Look : {}", id);
        lookService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
