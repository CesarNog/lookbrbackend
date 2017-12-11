package com.lookbr.backend.web.rest;

import com.lookbr.backend.LookbrbackendApp;

import com.lookbr.backend.domain.DayTime;
import com.lookbr.backend.repository.DayTimeRepository;
import com.lookbr.backend.service.DayTimeService;
import com.lookbr.backend.repository.search.DayTimeSearchRepository;
import com.lookbr.backend.service.dto.DayTimeDTO;
import com.lookbr.backend.service.mapper.DayTimeMapper;
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
 * Test class for the DayTimeResource REST controller.
 *
 * @see DayTimeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LookbrbackendApp.class)
public class DayTimeResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    @Autowired
    private DayTimeRepository dayTimeRepository;

    @Autowired
    private DayTimeMapper dayTimeMapper;

    @Autowired
    private DayTimeService dayTimeService;

    @Autowired
    private DayTimeSearchRepository dayTimeSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDayTimeMockMvc;

    private DayTime dayTime;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DayTimeResource dayTimeResource = new DayTimeResource(dayTimeService);
        this.restDayTimeMockMvc = MockMvcBuilders.standaloneSetup(dayTimeResource)
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
    public static DayTime createEntity(EntityManager em) {
        DayTime dayTime = new DayTime()
            .value(DEFAULT_VALUE)
            .type(DEFAULT_TYPE);
        return dayTime;
    }

    @Before
    public void initTest() {
        dayTimeSearchRepository.deleteAll();
        dayTime = createEntity(em);
    }

    @Test
    @Transactional
    public void createDayTime() throws Exception {
        int databaseSizeBeforeCreate = dayTimeRepository.findAll().size();

        // Create the DayTime
        DayTimeDTO dayTimeDTO = dayTimeMapper.toDto(dayTime);
        restDayTimeMockMvc.perform(post("/api/day-times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayTimeDTO)))
            .andExpect(status().isCreated());

        // Validate the DayTime in the database
        List<DayTime> dayTimeList = dayTimeRepository.findAll();
        assertThat(dayTimeList).hasSize(databaseSizeBeforeCreate + 1);
        DayTime testDayTime = dayTimeList.get(dayTimeList.size() - 1);
        assertThat(testDayTime.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testDayTime.getType()).isEqualTo(DEFAULT_TYPE);

        // Validate the DayTime in Elasticsearch
        DayTime dayTimeEs = dayTimeSearchRepository.findOne(testDayTime.getId());
        assertThat(dayTimeEs).isEqualToComparingFieldByField(testDayTime);
    }

    @Test
    @Transactional
    public void createDayTimeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dayTimeRepository.findAll().size();

        // Create the DayTime with an existing ID
        dayTime.setId(1L);
        DayTimeDTO dayTimeDTO = dayTimeMapper.toDto(dayTime);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDayTimeMockMvc.perform(post("/api/day-times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayTimeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DayTime in the database
        List<DayTime> dayTimeList = dayTimeRepository.findAll();
        assertThat(dayTimeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDayTimes() throws Exception {
        // Initialize the database
        dayTimeRepository.saveAndFlush(dayTime);

        // Get all the dayTimeList
        restDayTimeMockMvc.perform(get("/api/day-times?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dayTime.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void getDayTime() throws Exception {
        // Initialize the database
        dayTimeRepository.saveAndFlush(dayTime);

        // Get the dayTime
        restDayTimeMockMvc.perform(get("/api/day-times/{id}", dayTime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dayTime.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDayTime() throws Exception {
        // Get the dayTime
        restDayTimeMockMvc.perform(get("/api/day-times/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDayTime() throws Exception {
        // Initialize the database
        dayTimeRepository.saveAndFlush(dayTime);
        dayTimeSearchRepository.save(dayTime);
        int databaseSizeBeforeUpdate = dayTimeRepository.findAll().size();

        // Update the dayTime
        DayTime updatedDayTime = dayTimeRepository.findOne(dayTime.getId());
        // Disconnect from session so that the updates on updatedDayTime are not directly saved in db
        em.detach(updatedDayTime);
        updatedDayTime
            .value(UPDATED_VALUE)
            .type(UPDATED_TYPE);
        DayTimeDTO dayTimeDTO = dayTimeMapper.toDto(updatedDayTime);

        restDayTimeMockMvc.perform(put("/api/day-times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayTimeDTO)))
            .andExpect(status().isOk());

        // Validate the DayTime in the database
        List<DayTime> dayTimeList = dayTimeRepository.findAll();
        assertThat(dayTimeList).hasSize(databaseSizeBeforeUpdate);
        DayTime testDayTime = dayTimeList.get(dayTimeList.size() - 1);
        assertThat(testDayTime.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testDayTime.getType()).isEqualTo(UPDATED_TYPE);

        // Validate the DayTime in Elasticsearch
        DayTime dayTimeEs = dayTimeSearchRepository.findOne(testDayTime.getId());
        assertThat(dayTimeEs).isEqualToComparingFieldByField(testDayTime);
    }

    @Test
    @Transactional
    public void updateNonExistingDayTime() throws Exception {
        int databaseSizeBeforeUpdate = dayTimeRepository.findAll().size();

        // Create the DayTime
        DayTimeDTO dayTimeDTO = dayTimeMapper.toDto(dayTime);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDayTimeMockMvc.perform(put("/api/day-times")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayTimeDTO)))
            .andExpect(status().isCreated());

        // Validate the DayTime in the database
        List<DayTime> dayTimeList = dayTimeRepository.findAll();
        assertThat(dayTimeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDayTime() throws Exception {
        // Initialize the database
        dayTimeRepository.saveAndFlush(dayTime);
        dayTimeSearchRepository.save(dayTime);
        int databaseSizeBeforeDelete = dayTimeRepository.findAll().size();

        // Get the dayTime
        restDayTimeMockMvc.perform(delete("/api/day-times/{id}", dayTime.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean dayTimeExistsInEs = dayTimeSearchRepository.exists(dayTime.getId());
        assertThat(dayTimeExistsInEs).isFalse();

        // Validate the database is empty
        List<DayTime> dayTimeList = dayTimeRepository.findAll();
        assertThat(dayTimeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchDayTime() throws Exception {
        // Initialize the database
        dayTimeRepository.saveAndFlush(dayTime);
        dayTimeSearchRepository.save(dayTime);

        // Search the dayTime
        restDayTimeMockMvc.perform(get("/api/_search/day-times?query=id:" + dayTime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dayTime.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DayTime.class);
        DayTime dayTime1 = new DayTime();
        dayTime1.setId(1L);
        DayTime dayTime2 = new DayTime();
        dayTime2.setId(dayTime1.getId());
        assertThat(dayTime1).isEqualTo(dayTime2);
        dayTime2.setId(2L);
        assertThat(dayTime1).isNotEqualTo(dayTime2);
        dayTime1.setId(null);
        assertThat(dayTime1).isNotEqualTo(dayTime2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DayTimeDTO.class);
        DayTimeDTO dayTimeDTO1 = new DayTimeDTO();
        dayTimeDTO1.setId(1L);
        DayTimeDTO dayTimeDTO2 = new DayTimeDTO();
        assertThat(dayTimeDTO1).isNotEqualTo(dayTimeDTO2);
        dayTimeDTO2.setId(dayTimeDTO1.getId());
        assertThat(dayTimeDTO1).isEqualTo(dayTimeDTO2);
        dayTimeDTO2.setId(2L);
        assertThat(dayTimeDTO1).isNotEqualTo(dayTimeDTO2);
        dayTimeDTO1.setId(null);
        assertThat(dayTimeDTO1).isNotEqualTo(dayTimeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dayTimeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dayTimeMapper.fromId(null)).isNull();
    }
}
