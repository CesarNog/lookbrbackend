package com.lookbr.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lookbr.backend.service.ClosetService;
import com.lookbr.backend.web.rest.errors.BadRequestAlertException;
import com.lookbr.backend.web.rest.util.HeaderUtil;
import com.lookbr.backend.web.rest.util.PaginationUtil;
import com.lookbr.backend.service.dto.ClosetDTO;
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
 * REST controller for managing Closet.
 */
@RestController
@RequestMapping("/api")
public class ClosetResource {

    private final Logger log = LoggerFactory.getLogger(ClosetResource.class);

    private static final String ENTITY_NAME = "closet";

    private final ClosetService closetService;

    public ClosetResource(ClosetService closetService) {
        this.closetService = closetService;
    }

    /**
     * POST  /closets : Create a new closet.
     *
     * @param closetDTO the closetDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new closetDTO, or with status 400 (Bad Request) if the closet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/closets")
    @Timed
    public ResponseEntity<ClosetDTO> createCloset(@RequestBody ClosetDTO closetDTO) throws URISyntaxException {
        log.debug("REST request to save Closet : {}", closetDTO);
        if (closetDTO.getId() != null) {
            throw new BadRequestAlertException("A new closet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClosetDTO result = closetService.save(closetDTO);
        return ResponseEntity.created(new URI("/api/closets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /closets : Updates an existing closet.
     *
     * @param closetDTO the closetDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated closetDTO,
     * or with status 400 (Bad Request) if the closetDTO is not valid,
     * or with status 500 (Internal Server Error) if the closetDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/closets")
    @Timed
    public ResponseEntity<ClosetDTO> updateCloset(@RequestBody ClosetDTO closetDTO) throws URISyntaxException {
        log.debug("REST request to update Closet : {}", closetDTO);
        if (closetDTO.getId() == null) {
            return createCloset(closetDTO);
        }
        ClosetDTO result = closetService.save(closetDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, closetDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /closets : get all the closets.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of closets in body
     */
    @GetMapping("/closets")
    @Timed
    public ResponseEntity<List<ClosetDTO>> getAllClosets(Pageable pageable) {
        log.debug("REST request to get a page of Closets");
        Page<ClosetDTO> page = closetService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/closets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /closets/:id : get the "id" closet.
     *
     * @param id the id of the closetDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the closetDTO, or with status 404 (Not Found)
     */
    @GetMapping("/closets/{id}")
    @Timed
    public ResponseEntity<ClosetDTO> getCloset(@PathVariable Long id) {
        log.debug("REST request to get Closet : {}", id);
        ClosetDTO closetDTO = closetService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(closetDTO));
    }

    /**
     * DELETE  /closets/:id : delete the "id" closet.
     *
     * @param id the id of the closetDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/closets/{id}")
    @Timed
    public ResponseEntity<Void> deleteCloset(@PathVariable Long id) {
        log.debug("REST request to delete Closet : {}", id);
        closetService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
