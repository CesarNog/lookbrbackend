package com.lookbr.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lookbr.backend.service.DayTimeService;
import com.lookbr.backend.web.rest.errors.BadRequestAlertException;
import com.lookbr.backend.web.rest.util.HeaderUtil;
import com.lookbr.backend.service.dto.DayTimeDTO;
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
 * REST controller for managing DayTime.
 */
@RestController
@RequestMapping("/api")
public class DayTimeResource {

    private final Logger log = LoggerFactory.getLogger(DayTimeResource.class);

    private static final String ENTITY_NAME = "dayTime";

    private final DayTimeService dayTimeService;

    public DayTimeResource(DayTimeService dayTimeService) {
        this.dayTimeService = dayTimeService;
    }

    /**
     * POST  /day-times : Create a new dayTime.
     *
     * @param dayTimeDTO the dayTimeDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new dayTimeDTO, or with status 400 (Bad Request) if the dayTime has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/day-times")
    @Timed
    public ResponseEntity<DayTimeDTO> createDayTime(@RequestBody DayTimeDTO dayTimeDTO) throws URISyntaxException {
        log.debug("REST request to save DayTime : {}", dayTimeDTO);
        if (dayTimeDTO.getId() != null) {
            throw new BadRequestAlertException("A new dayTime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DayTimeDTO result = dayTimeService.save(dayTimeDTO);
        return ResponseEntity.created(new URI("/api/day-times/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /day-times : Updates an existing dayTime.
     *
     * @param dayTimeDTO the dayTimeDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated dayTimeDTO,
     * or with status 400 (Bad Request) if the dayTimeDTO is not valid,
     * or with status 500 (Internal Server Error) if the dayTimeDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/day-times")
    @Timed
    public ResponseEntity<DayTimeDTO> updateDayTime(@RequestBody DayTimeDTO dayTimeDTO) throws URISyntaxException {
        log.debug("REST request to update DayTime : {}", dayTimeDTO);
        if (dayTimeDTO.getId() == null) {
            return createDayTime(dayTimeDTO);
        }
        DayTimeDTO result = dayTimeService.save(dayTimeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, dayTimeDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /day-times : get all the dayTimes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of dayTimes in body
     */
    @GetMapping("/day-times")
    @Timed
    public List<DayTimeDTO> getAllDayTimes() {
        log.debug("REST request to get all DayTimes");
        return dayTimeService.findAll();
        }

    /**
     * GET  /day-times/:id : get the "id" dayTime.
     *
     * @param id the id of the dayTimeDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the dayTimeDTO, or with status 404 (Not Found)
     */
    @GetMapping("/day-times/{id}")
    @Timed
    public ResponseEntity<DayTimeDTO> getDayTime(@PathVariable Long id) {
        log.debug("REST request to get DayTime : {}", id);
        DayTimeDTO dayTimeDTO = dayTimeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(dayTimeDTO));
    }

    /**
     * DELETE  /day-times/:id : delete the "id" dayTime.
     *
     * @param id the id of the dayTimeDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/day-times/{id}")
    @Timed
    public ResponseEntity<Void> deleteDayTime(@PathVariable Long id) {
        log.debug("REST request to delete DayTime : {}", id);
        dayTimeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
