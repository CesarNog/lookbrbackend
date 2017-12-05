package com.lookbr.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lookbr.backend.service.ConsultantsService;
import com.lookbr.backend.web.rest.errors.BadRequestAlertException;
import com.lookbr.backend.web.rest.util.HeaderUtil;
import com.lookbr.backend.web.rest.util.PaginationUtil;
import com.lookbr.backend.service.dto.ConsultantsDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Consultants.
 */
@RestController
@RequestMapping("/api")
public class ConsultantsResource {

    private final Logger log = LoggerFactory.getLogger(ConsultantsResource.class);

    private static final String ENTITY_NAME = "consultants";

    private final ConsultantsService consultantsService;

    public ConsultantsResource(ConsultantsService consultantsService) {
        this.consultantsService = consultantsService;
    }

    /**
     * POST  /consultants : Create a new consultants.
     *
     * @param consultantsDTO the consultantsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new consultantsDTO, or with status 400 (Bad Request) if the consultants has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/consultants")
    @Timed
    public ResponseEntity<ConsultantsDTO> createConsultants(@RequestBody ConsultantsDTO consultantsDTO) throws URISyntaxException {
        log.debug("REST request to save Consultants : {}", consultantsDTO);
        if (consultantsDTO.getId() != null) {
            throw new BadRequestAlertException("A new consultants cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsultantsDTO result = consultantsService.save(consultantsDTO);
        return ResponseEntity.created(new URI("/api/consultants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /consultants : Updates an existing consultants.
     *
     * @param consultantsDTO the consultantsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated consultantsDTO,
     * or with status 400 (Bad Request) if the consultantsDTO is not valid,
     * or with status 500 (Internal Server Error) if the consultantsDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/consultants")
    @Timed
    public ResponseEntity<ConsultantsDTO> updateConsultants(@RequestBody ConsultantsDTO consultantsDTO) throws URISyntaxException {
        log.debug("REST request to update Consultants : {}", consultantsDTO);
        if (consultantsDTO.getId() == null) {
            return createConsultants(consultantsDTO);
        }
        ConsultantsDTO result = consultantsService.save(consultantsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, consultantsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /consultants : get all the consultants.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of consultants in body
     */
    @GetMapping("/consultants")
    @Timed
    public ResponseEntity<List<ConsultantsDTO>> getAllConsultants(Pageable pageable) {
        log.debug("REST request to get a page of Consultants");
        Page<ConsultantsDTO> page = consultantsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/consultants");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /consultants/:id : get the "id" consultants.
     *
     * @param id the id of the consultantsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the consultantsDTO, or with status 404 (Not Found)
     */
    @GetMapping("/consultants/{id}")
    @Timed
    public ResponseEntity<ConsultantsDTO> getConsultants(@PathVariable Long id) {
        log.debug("REST request to get Consultants : {}", id);
        ConsultantsDTO consultantsDTO = consultantsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(consultantsDTO));
    }

    /**
     * DELETE  /consultants/:id : delete the "id" consultants.
     *
     * @param id the id of the consultantsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/consultants/{id}")
    @Timed
    public ResponseEntity<Void> deleteConsultants(@PathVariable Long id) {
        log.debug("REST request to delete Consultants : {}", id);
        consultantsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
