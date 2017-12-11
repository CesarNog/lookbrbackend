package com.lookbr.backend.web.rest;

import com.lookbr.backend.LookbrbackendApp;

import com.lookbr.backend.domain.Intention;
import com.lookbr.backend.repository.IntentionRepository;
import com.lookbr.backend.service.IntentionService;
import com.lookbr.backend.repository.search.IntentionSearchRepository;
import com.lookbr.backend.service.dto.IntentionDTO;
import com.lookbr.backend.service.mapper.IntentionMapper;
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
 * Test class for the IntentionResource REST controller.
 *
 * @see IntentionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LookbrbackendApp.class)
public class IntentionResourceIntTest {

    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer UPDATED_PAGE = 2;

    @Autowired
    private IntentionRepository intentionRepository;

    @Autowired
    private IntentionMapper intentionMapper;

    @Autowired
    private IntentionService intentionService;

    @Autowired
    private IntentionSearchRepository intentionSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restIntentionMockMvc;

    private Intention intention;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final IntentionResource intentionResource = new IntentionResource(intentionService);
        this.restIntentionMockMvc = MockMvcBuilders.standaloneSetup(intentionResource)
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
    public static Intention createEntity(EntityManager em) {
        Intention intention = new Intention()
            .page(DEFAULT_PAGE);
        return intention;
    }

    @Before
    public void initTest() {
        intentionSearchRepository.deleteAll();
        intention = createEntity(em);
    }

    @Test
    @Transactional
    public void createIntention() throws Exception {
        int databaseSizeBeforeCreate = intentionRepository.findAll().size();

        // Create the Intention
        IntentionDTO intentionDTO = intentionMapper.toDto(intention);
        restIntentionMockMvc.perform(post("/api/intentions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intentionDTO)))
            .andExpect(status().isCreated());

        // Validate the Intention in the database
        List<Intention> intentionList = intentionRepository.findAll();
        assertThat(intentionList).hasSize(databaseSizeBeforeCreate + 1);
        Intention testIntention = intentionList.get(intentionList.size() - 1);
        assertThat(testIntention.getPage()).isEqualTo(DEFAULT_PAGE);

        // Validate the Intention in Elasticsearch
        Intention intentionEs = intentionSearchRepository.findOne(testIntention.getId());
        assertThat(intentionEs).isEqualToComparingFieldByField(testIntention);
    }

    @Test
    @Transactional
    public void createIntentionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = intentionRepository.findAll().size();

        // Create the Intention with an existing ID
        intention.setId(1L);
        IntentionDTO intentionDTO = intentionMapper.toDto(intention);

        // An entity with an existing ID cannot be created, so this API call must fail
        restIntentionMockMvc.perform(post("/api/intentions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intentionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Intention in the database
        List<Intention> intentionList = intentionRepository.findAll();
        assertThat(intentionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllIntentions() throws Exception {
        // Initialize the database
        intentionRepository.saveAndFlush(intention);

        // Get all the intentionList
        restIntentionMockMvc.perform(get("/api/intentions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intention.getId().intValue())))
            .andExpect(jsonPath("$.[*].page").value(hasItem(DEFAULT_PAGE)));
    }

    @Test
    @Transactional
    public void getIntention() throws Exception {
        // Initialize the database
        intentionRepository.saveAndFlush(intention);

        // Get the intention
        restIntentionMockMvc.perform(get("/api/intentions/{id}", intention.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(intention.getId().intValue()))
            .andExpect(jsonPath("$.page").value(DEFAULT_PAGE));
    }

    @Test
    @Transactional
    public void getNonExistingIntention() throws Exception {
        // Get the intention
        restIntentionMockMvc.perform(get("/api/intentions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateIntention() throws Exception {
        // Initialize the database
        intentionRepository.saveAndFlush(intention);
        intentionSearchRepository.save(intention);
        int databaseSizeBeforeUpdate = intentionRepository.findAll().size();

        // Update the intention
        Intention updatedIntention = intentionRepository.findOne(intention.getId());
        // Disconnect from session so that the updates on updatedIntention are not directly saved in db
        em.detach(updatedIntention);
        updatedIntention
            .page(UPDATED_PAGE);
        IntentionDTO intentionDTO = intentionMapper.toDto(updatedIntention);

        restIntentionMockMvc.perform(put("/api/intentions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intentionDTO)))
            .andExpect(status().isOk());

        // Validate the Intention in the database
        List<Intention> intentionList = intentionRepository.findAll();
        assertThat(intentionList).hasSize(databaseSizeBeforeUpdate);
        Intention testIntention = intentionList.get(intentionList.size() - 1);
        assertThat(testIntention.getPage()).isEqualTo(UPDATED_PAGE);

        // Validate the Intention in Elasticsearch
        Intention intentionEs = intentionSearchRepository.findOne(testIntention.getId());
        assertThat(intentionEs).isEqualToComparingFieldByField(testIntention);
    }

    @Test
    @Transactional
    public void updateNonExistingIntention() throws Exception {
        int databaseSizeBeforeUpdate = intentionRepository.findAll().size();

        // Create the Intention
        IntentionDTO intentionDTO = intentionMapper.toDto(intention);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restIntentionMockMvc.perform(put("/api/intentions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(intentionDTO)))
            .andExpect(status().isCreated());

        // Validate the Intention in the database
        List<Intention> intentionList = intentionRepository.findAll();
        assertThat(intentionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteIntention() throws Exception {
        // Initialize the database
        intentionRepository.saveAndFlush(intention);
        intentionSearchRepository.save(intention);
        int databaseSizeBeforeDelete = intentionRepository.findAll().size();

        // Get the intention
        restIntentionMockMvc.perform(delete("/api/intentions/{id}", intention.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean intentionExistsInEs = intentionSearchRepository.exists(intention.getId());
        assertThat(intentionExistsInEs).isFalse();

        // Validate the database is empty
        List<Intention> intentionList = intentionRepository.findAll();
        assertThat(intentionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchIntention() throws Exception {
        // Initialize the database
        intentionRepository.saveAndFlush(intention);
        intentionSearchRepository.save(intention);

        // Search the intention
        restIntentionMockMvc.perform(get("/api/_search/intentions?query=id:" + intention.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(intention.getId().intValue())))
            .andExpect(jsonPath("$.[*].page").value(hasItem(DEFAULT_PAGE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Intention.class);
        Intention intention1 = new Intention();
        intention1.setId(1L);
        Intention intention2 = new Intention();
        intention2.setId(intention1.getId());
        assertThat(intention1).isEqualTo(intention2);
        intention2.setId(2L);
        assertThat(intention1).isNotEqualTo(intention2);
        intention1.setId(null);
        assertThat(intention1).isNotEqualTo(intention2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(IntentionDTO.class);
        IntentionDTO intentionDTO1 = new IntentionDTO();
        intentionDTO1.setId(1L);
        IntentionDTO intentionDTO2 = new IntentionDTO();
        assertThat(intentionDTO1).isNotEqualTo(intentionDTO2);
        intentionDTO2.setId(intentionDTO1.getId());
        assertThat(intentionDTO1).isEqualTo(intentionDTO2);
        intentionDTO2.setId(2L);
        assertThat(intentionDTO1).isNotEqualTo(intentionDTO2);
        intentionDTO1.setId(null);
        assertThat(intentionDTO1).isNotEqualTo(intentionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(intentionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(intentionMapper.fromId(null)).isNull();
    }
}
