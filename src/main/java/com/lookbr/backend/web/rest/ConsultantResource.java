package com.lookbr.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lookbr.backend.service.ConsultantService;
import com.lookbr.backend.web.rest.errors.BadRequestAlertException;
import com.lookbr.backend.web.rest.util.HeaderUtil;
import com.lookbr.backend.service.dto.ConsultantDTO;
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
 * REST controller for managing Consultant.
 */
@RestController
@RequestMapping("/api")
public class ConsultantResource {

    private final Logger log = LoggerFactory.getLogger(ConsultantResource.class);

    private static final String ENTITY_NAME = "consultant";

    private final ConsultantService consultantService;

    public ConsultantResource(ConsultantService consultantService) {
        this.consultantService = consultantService;
    }

    /**
     * POST  /consultants : Create a new consultant.
     *
     * @param consultantDTO the consultantDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new consultantDTO, or with status 400 (Bad Request) if the consultant has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/consultants")
    @Timed
    public ResponseEntity<ConsultantDTO> createConsultant(@RequestBody ConsultantDTO consultantDTO) throws URISyntaxException {
        log.debug("REST request to save Consultant : {}", consultantDTO);
        if (consultantDTO.getId() != null) {
            throw new BadRequestAlertException("A new consultant cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConsultantDTO result = consultantService.save(consultantDTO);
        return ResponseEntity.created(new URI("/api/consultants/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /consultants : Updates an existing consultant.
     *
     * @param consultantDTO the consultantDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated consultantDTO,
     * or with status 400 (Bad Request) if the consultantDTO is not valid,
     * or with status 500 (Internal Server Error) if the consultantDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/consultants")
    @Timed
    public ResponseEntity<ConsultantDTO> updateConsultant(@RequestBody ConsultantDTO consultantDTO) throws URISyntaxException {
        log.debug("REST request to update Consultant : {}", consultantDTO);
        if (consultantDTO.getId() == null) {
            return createConsultant(consultantDTO);
        }
        ConsultantDTO result = consultantService.save(consultantDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, consultantDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /consultants : get all the consultants.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of consultants in body
     */
    @GetMapping("/consultants")
    @Timed
    public List<ConsultantDTO> getAllConsultants() {
        log.debug("REST request to get all Consultants");
        return consultantService.findAll();
        }

    /**
     * GET  /consultants/:id : get the "id" consultant.
     *
     * @param id the id of the consultantDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the consultantDTO, or with status 404 (Not Found)
     */
    @GetMapping("/consultants/{id}")
    @Timed
    public ResponseEntity<ConsultantDTO> getConsultant(@PathVariable Long id) {
        log.debug("REST request to get Consultant : {}", id);
        ConsultantDTO consultantDTO = consultantService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(consultantDTO));
    }

    /**
     * DELETE  /consultants/:id : delete the "id" consultant.
     *
     * @param id the id of the consultantDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/consultants/{id}")
    @Timed
    public ResponseEntity<Void> deleteConsultant(@PathVariable Long id) {
        log.debug("REST request to delete Consultant : {}", id);
        consultantService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
