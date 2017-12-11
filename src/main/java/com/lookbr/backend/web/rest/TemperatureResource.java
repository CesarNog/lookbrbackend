package com.lookbr.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lookbr.backend.service.TemperatureService;
import com.lookbr.backend.web.rest.errors.BadRequestAlertException;
import com.lookbr.backend.web.rest.util.HeaderUtil;
import com.lookbr.backend.service.dto.TemperatureDTO;
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
 * REST controller for managing Temperature.
 */
@RestController
@RequestMapping("/api")
public class TemperatureResource {

    private final Logger log = LoggerFactory.getLogger(TemperatureResource.class);

    private static final String ENTITY_NAME = "temperature";

    private final TemperatureService temperatureService;

    public TemperatureResource(TemperatureService temperatureService) {
        this.temperatureService = temperatureService;
    }

    /**
     * POST  /temperatures : Create a new temperature.
     *
     * @param temperatureDTO the temperatureDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new temperatureDTO, or with status 400 (Bad Request) if the temperature has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/temperatures")
    @Timed
    public ResponseEntity<TemperatureDTO> createTemperature(@RequestBody TemperatureDTO temperatureDTO) throws URISyntaxException {
        log.debug("REST request to save Temperature : {}", temperatureDTO);
        if (temperatureDTO.getId() != null) {
            throw new BadRequestAlertException("A new temperature cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TemperatureDTO result = temperatureService.save(temperatureDTO);
        return ResponseEntity.created(new URI("/api/temperatures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /temperatures : Updates an existing temperature.
     *
     * @param temperatureDTO the temperatureDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated temperatureDTO,
     * or with status 400 (Bad Request) if the temperatureDTO is not valid,
     * or with status 500 (Internal Server Error) if the temperatureDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/temperatures")
    @Timed
    public ResponseEntity<TemperatureDTO> updateTemperature(@RequestBody TemperatureDTO temperatureDTO) throws URISyntaxException {
        log.debug("REST request to update Temperature : {}", temperatureDTO);
        if (temperatureDTO.getId() == null) {
            return createTemperature(temperatureDTO);
        }
        TemperatureDTO result = temperatureService.save(temperatureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, temperatureDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /temperatures : get all the temperatures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of temperatures in body
     */
    @GetMapping("/temperatures")
    @Timed
    public List<TemperatureDTO> getAllTemperatures() {
        log.debug("REST request to get all Temperatures");
        return temperatureService.findAll();
        }

    /**
     * GET  /temperatures/:id : get the "id" temperature.
     *
     * @param id the id of the temperatureDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the temperatureDTO, or with status 404 (Not Found)
     */
    @GetMapping("/temperatures/{id}")
    @Timed
    public ResponseEntity<TemperatureDTO> getTemperature(@PathVariable Long id) {
        log.debug("REST request to get Temperature : {}", id);
        TemperatureDTO temperatureDTO = temperatureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(temperatureDTO));
    }

    /**
     * DELETE  /temperatures/:id : delete the "id" temperature.
     *
     * @param id the id of the temperatureDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/temperatures/{id}")
    @Timed
    public ResponseEntity<Void> deleteTemperature(@PathVariable Long id) {
        log.debug("REST request to delete Temperature : {}", id);
        temperatureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/temperatures?query=:query : search for the temperature corresponding
     * to the query.
     *
     * @param query the query of the temperature search
     * @return the result of the search
     */
    @GetMapping("/_search/temperatures")
    @Timed
    public List<TemperatureDTO> searchTemperatures(@RequestParam String query) {
        log.debug("REST request to search Temperatures for query {}", query);
        return temperatureService.search(query);
    }

}
