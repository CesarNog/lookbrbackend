package com.lookbr.backend.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.lookbr.backend.service.TimelineService;
import com.lookbr.backend.web.rest.errors.BadRequestAlertException;
import com.lookbr.backend.web.rest.util.HeaderUtil;
import com.lookbr.backend.web.rest.util.PaginationUtil;
import com.lookbr.backend.service.dto.TimelineDTO;
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
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Timeline.
 */
@RestController
@RequestMapping("/api")
public class TimelineResource {

    private final Logger log = LoggerFactory.getLogger(TimelineResource.class);

    private static final String ENTITY_NAME = "timeline";

    private final TimelineService timelineService;

    public TimelineResource(TimelineService timelineService) {
        this.timelineService = timelineService;
    }

    /**
     * POST  /timelines : Create a new timeline.
     *
     * @param timelineDTO the timelineDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new timelineDTO, or with status 400 (Bad Request) if the timeline has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/timelines")
    @Timed
    public ResponseEntity<TimelineDTO> createTimeline(@RequestBody TimelineDTO timelineDTO) throws URISyntaxException {
        log.debug("REST request to save Timeline : {}", timelineDTO);
        if (timelineDTO.getId() != null) {
            throw new BadRequestAlertException("A new timeline cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TimelineDTO result = timelineService.save(timelineDTO);
        return ResponseEntity.created(new URI("/api/timelines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /timelines : Updates an existing timeline.
     *
     * @param timelineDTO the timelineDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated timelineDTO,
     * or with status 400 (Bad Request) if the timelineDTO is not valid,
     * or with status 500 (Internal Server Error) if the timelineDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/timelines")
    @Timed
    public ResponseEntity<TimelineDTO> updateTimeline(@RequestBody TimelineDTO timelineDTO) throws URISyntaxException {
        log.debug("REST request to update Timeline : {}", timelineDTO);
        if (timelineDTO.getId() == null) {
            return createTimeline(timelineDTO);
        }
        TimelineDTO result = timelineService.save(timelineDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, timelineDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /timelines : get all the timelines.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of timelines in body
     */
    @GetMapping("/timelines")
    @Timed
    public ResponseEntity<List<TimelineDTO>> getAllTimelines(Pageable pageable) {
        log.debug("REST request to get a page of Timelines");
        Page<TimelineDTO> page = timelineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/timelines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /timelines/:id : get the "id" timeline.
     *
     * @param id the id of the timelineDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the timelineDTO, or with status 404 (Not Found)
     */
    @GetMapping("/timelines/{id}")
    @Timed
    public ResponseEntity<TimelineDTO> getTimeline(@PathVariable Long id) {
        log.debug("REST request to get Timeline : {}", id);
        TimelineDTO timelineDTO = timelineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(timelineDTO));
    }

    /**
     * DELETE  /timelines/:id : delete the "id" timeline.
     *
     * @param id the id of the timelineDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/timelines/{id}")
    @Timed
    public ResponseEntity<Void> deleteTimeline(@PathVariable Long id) {
        log.debug("REST request to delete Timeline : {}", id);
        timelineService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/timelines?query=:query : search for the timeline corresponding
     * to the query.
     *
     * @param query the query of the timeline search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/timelines")
    @Timed
    public ResponseEntity<List<TimelineDTO>> searchTimelines(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Timelines for query {}", query);
        Page<TimelineDTO> page = timelineService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/timelines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
