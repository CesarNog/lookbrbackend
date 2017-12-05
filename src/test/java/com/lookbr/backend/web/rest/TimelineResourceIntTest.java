package com.lookbr.backend.web.rest;

import com.lookbr.backend.LookbrbackendApp;

import com.lookbr.backend.domain.Timeline;
import com.lookbr.backend.repository.TimelineRepository;
import com.lookbr.backend.service.TimelineService;
import com.lookbr.backend.service.dto.TimelineDTO;
import com.lookbr.backend.service.mapper.TimelineMapper;
import com.lookbr.backend.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.lookbr.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TimelineResource REST controller.
 *
 * @see TimelineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LookbrbackendApp.class)
public class TimelineResourceIntTest {

    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer UPDATED_PAGE = 2;

    @Autowired
    private TimelineRepository timelineRepository;

    @Autowired
    private TimelineMapper timelineMapper;

    @Autowired
    private TimelineService timelineService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTimelineMockMvc;

    private Timeline timeline;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TimelineResource timelineResource = new TimelineResource(timelineService);
        this.restTimelineMockMvc = MockMvcBuilders.standaloneSetup(timelineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Timeline createEntity(EntityManager em) {
        Timeline timeline = new Timeline()
            .page(DEFAULT_PAGE);
        return timeline;
    }

    @Before
    public void initTest() {
        timeline = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimeline() throws Exception {
        int databaseSizeBeforeCreate = timelineRepository.findAll().size();

        // Create the Timeline
        TimelineDTO timelineDTO = timelineMapper.toDto(timeline);
        restTimelineMockMvc.perform(post("/api/timelines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timelineDTO)))
            .andExpect(status().isCreated());

        // Validate the Timeline in the database
        List<Timeline> timelineList = timelineRepository.findAll();
        assertThat(timelineList).hasSize(databaseSizeBeforeCreate + 1);
        Timeline testTimeline = timelineList.get(timelineList.size() - 1);
        assertThat(testTimeline.getPage()).isEqualTo(DEFAULT_PAGE);
    }

    @Test
    @Transactional
    public void createTimelineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timelineRepository.findAll().size();

        // Create the Timeline with an existing ID
        timeline.setId(1L);
        TimelineDTO timelineDTO = timelineMapper.toDto(timeline);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimelineMockMvc.perform(post("/api/timelines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timelineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Timeline in the database
        List<Timeline> timelineList = timelineRepository.findAll();
        assertThat(timelineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTimelines() throws Exception {
        // Initialize the database
        timelineRepository.saveAndFlush(timeline);

        // Get all the timelineList
        restTimelineMockMvc.perform(get("/api/timelines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeline.getId().intValue())))
            .andExpect(jsonPath("$.[*].page").value(hasItem(DEFAULT_PAGE)));
    }

    @Test
    @Transactional
    public void getTimeline() throws Exception {
        // Initialize the database
        timelineRepository.saveAndFlush(timeline);

        // Get the timeline
        restTimelineMockMvc.perform(get("/api/timelines/{id}", timeline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timeline.getId().intValue()))
            .andExpect(jsonPath("$.page").value(DEFAULT_PAGE));
    }

    @Test
    @Transactional
    public void getNonExistingTimeline() throws Exception {
        // Get the timeline
        restTimelineMockMvc.perform(get("/api/timelines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeline() throws Exception {
        // Initialize the database
        timelineRepository.saveAndFlush(timeline);
        int databaseSizeBeforeUpdate = timelineRepository.findAll().size();

        // Update the timeline
        Timeline updatedTimeline = timelineRepository.findOne(timeline.getId());
        // Disconnect from session so that the updates on updatedTimeline are not directly saved in db
        em.detach(updatedTimeline);
        updatedTimeline
            .page(UPDATED_PAGE);
        TimelineDTO timelineDTO = timelineMapper.toDto(updatedTimeline);

        restTimelineMockMvc.perform(put("/api/timelines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timelineDTO)))
            .andExpect(status().isOk());

        // Validate the Timeline in the database
        List<Timeline> timelineList = timelineRepository.findAll();
        assertThat(timelineList).hasSize(databaseSizeBeforeUpdate);
        Timeline testTimeline = timelineList.get(timelineList.size() - 1);
        assertThat(testTimeline.getPage()).isEqualTo(UPDATED_PAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingTimeline() throws Exception {
        int databaseSizeBeforeUpdate = timelineRepository.findAll().size();

        // Create the Timeline
        TimelineDTO timelineDTO = timelineMapper.toDto(timeline);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTimelineMockMvc.perform(put("/api/timelines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timelineDTO)))
            .andExpect(status().isCreated());

        // Validate the Timeline in the database
        List<Timeline> timelineList = timelineRepository.findAll();
        assertThat(timelineList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTimeline() throws Exception {
        // Initialize the database
        timelineRepository.saveAndFlush(timeline);
        int databaseSizeBeforeDelete = timelineRepository.findAll().size();

        // Get the timeline
        restTimelineMockMvc.perform(delete("/api/timelines/{id}", timeline.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Timeline> timelineList = timelineRepository.findAll();
        assertThat(timelineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Timeline.class);
        Timeline timeline1 = new Timeline();
        timeline1.setId(1L);
        Timeline timeline2 = new Timeline();
        timeline2.setId(timeline1.getId());
        assertThat(timeline1).isEqualTo(timeline2);
        timeline2.setId(2L);
        assertThat(timeline1).isNotEqualTo(timeline2);
        timeline1.setId(null);
        assertThat(timeline1).isNotEqualTo(timeline2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TimelineDTO.class);
        TimelineDTO timelineDTO1 = new TimelineDTO();
        timelineDTO1.setId(1L);
        TimelineDTO timelineDTO2 = new TimelineDTO();
        assertThat(timelineDTO1).isNotEqualTo(timelineDTO2);
        timelineDTO2.setId(timelineDTO1.getId());
        assertThat(timelineDTO1).isEqualTo(timelineDTO2);
        timelineDTO2.setId(2L);
        assertThat(timelineDTO1).isNotEqualTo(timelineDTO2);
        timelineDTO1.setId(null);
        assertThat(timelineDTO1).isNotEqualTo(timelineDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(timelineMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(timelineMapper.fromId(null)).isNull();
    }
}
