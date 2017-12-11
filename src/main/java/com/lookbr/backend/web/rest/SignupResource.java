package com.lookbr.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lookbr.backend.service.SignupService;
import com.lookbr.backend.web.rest.errors.BadRequestAlertException;
import com.lookbr.backend.web.rest.util.HeaderUtil;
import com.lookbr.backend.service.dto.SignupDTO;
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
 * REST controller for managing Signup.
 */
@RestController
@RequestMapping("/api")
public class SignupResource {

    private final Logger log = LoggerFactory.getLogger(SignupResource.class);

    private static final String ENTITY_NAME = "signup";

    private final SignupService signupService;

    public SignupResource(SignupService signupService) {
        this.signupService = signupService;
    }

    /**
     * POST  /signups : Create a new signup.
     *
     * @param signupDTO the signupDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new signupDTO, or with status 400 (Bad Request) if the signup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/signups")
    @Timed
    public ResponseEntity<SignupDTO> createSignup(@RequestBody SignupDTO signupDTO) throws URISyntaxException {
        log.debug("REST request to save Signup : {}", signupDTO);
        if (signupDTO.getId() != null) {
            throw new BadRequestAlertException("A new signup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SignupDTO result = signupService.save(signupDTO);
        return ResponseEntity.created(new URI("/api/signups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /signups : Updates an existing signup.
     *
     * @param signupDTO the signupDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated signupDTO,
     * or with status 400 (Bad Request) if the signupDTO is not valid,
     * or with status 500 (Internal Server Error) if the signupDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/signups")
    @Timed
    public ResponseEntity<SignupDTO> updateSignup(@RequestBody SignupDTO signupDTO) throws URISyntaxException {
        log.debug("REST request to update Signup : {}", signupDTO);
        if (signupDTO.getId() == null) {
            return createSignup(signupDTO);
        }
        SignupDTO result = signupService.save(signupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, signupDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /signups : get all the signups.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of signups in body
     */
    @GetMapping("/signups")
    @Timed
    public List<SignupDTO> getAllSignups() {
        log.debug("REST request to get all Signups");
        return signupService.findAll();
        }

    /**
     * GET  /signups/:id : get the "id" signup.
     *
     * @param id the id of the signupDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the signupDTO, or with status 404 (Not Found)
     */
    @GetMapping("/signups/{id}")
    @Timed
    public ResponseEntity<SignupDTO> getSignup(@PathVariable Long id) {
        log.debug("REST request to get Signup : {}", id);
        SignupDTO signupDTO = signupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(signupDTO));
    }

    /**
     * DELETE  /signups/:id : delete the "id" signup.
     *
     * @param id the id of the signupDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/signups/{id}")
    @Timed
    public ResponseEntity<Void> deleteSignup(@PathVariable Long id) {
        log.debug("REST request to delete Signup : {}", id);
        signupService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/signups?query=:query : search for the signup corresponding
     * to the query.
     *
     * @param query the query of the signup search
     * @return the result of the search
     */
    @GetMapping("/_search/signups")
    @Timed
    public List<SignupDTO> searchSignups(@RequestParam String query) {
        log.debug("REST request to search Signups for query {}", query);
        return signupService.search(query);
    }

}
