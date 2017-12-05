package com.lookbr.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lookbr.backend.service.LoginService;
import com.lookbr.backend.web.rest.errors.BadRequestAlertException;
import com.lookbr.backend.web.rest.util.HeaderUtil;
import com.lookbr.backend.service.dto.LoginDTO;
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
 * REST controller for managing Login.
 */
@RestController
@RequestMapping("/api")
public class LoginResource {

    private final Logger log = LoggerFactory.getLogger(LoginResource.class);

    private static final String ENTITY_NAME = "login";

    private final LoginService loginService;

    public LoginResource(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * POST  /logins : Create a new login.
     *
     * @param loginDTO the loginDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new loginDTO, or with status 400 (Bad Request) if the login has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/logins")
    @Timed
    public ResponseEntity<LoginDTO> createLogin(@RequestBody LoginDTO loginDTO) throws URISyntaxException {
        log.debug("REST request to save Login : {}", loginDTO);
        if (loginDTO.getId() != null) {
            throw new BadRequestAlertException("A new login cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LoginDTO result = loginService.save(loginDTO);
        return ResponseEntity.created(new URI("/api/logins/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /logins : Updates an existing login.
     *
     * @param loginDTO the loginDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated loginDTO,
     * or with status 400 (Bad Request) if the loginDTO is not valid,
     * or with status 500 (Internal Server Error) if the loginDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/logins")
    @Timed
    public ResponseEntity<LoginDTO> updateLogin(@RequestBody LoginDTO loginDTO) throws URISyntaxException {
        log.debug("REST request to update Login : {}", loginDTO);
        if (loginDTO.getId() == null) {
            return createLogin(loginDTO);
        }
        LoginDTO result = loginService.save(loginDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, loginDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /logins : get all the logins.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of logins in body
     */
    @GetMapping("/logins")
    @Timed
    public List<LoginDTO> getAllLogins() {
        log.debug("REST request to get all Logins");
        return loginService.findAll();
        }

    /**
     * GET  /logins/:id : get the "id" login.
     *
     * @param id the id of the loginDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the loginDTO, or with status 404 (Not Found)
     */
    @GetMapping("/logins/{id}")
    @Timed
    public ResponseEntity<LoginDTO> getLogin(@PathVariable Long id) {
        log.debug("REST request to get Login : {}", id);
        LoginDTO loginDTO = loginService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(loginDTO));
    }

    /**
     * DELETE  /logins/:id : delete the "id" login.
     *
     * @param id the id of the loginDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/logins/{id}")
    @Timed
    public ResponseEntity<Void> deleteLogin(@PathVariable Long id) {
        log.debug("REST request to delete Login : {}", id);
        loginService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
