package com.lookbr.backend.web.rest;

import com.lookbr.backend.LookbrbackendApp;

import com.lookbr.backend.domain.Occasion;
import com.lookbr.backend.repository.OccasionRepository;
import com.lookbr.backend.service.OccasionService;
import com.lookbr.backend.service.dto.OccasionDTO;
import com.lookbr.backend.service.mapper.OccasionMapper;
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
 * Test class for the OccasionResource REST controller.
 *
 * @see OccasionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LookbrbackendApp.class)
public class OccasionResourceIntTest {

    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer UPDATED_PAGE = 2;

    @Autowired
    private OccasionRepository occasionRepository;

    @Autowired
    private OccasionMapper occasionMapper;

    @Autowired
    private OccasionService occasionService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOccasionMockMvc;

    private Occasion occasion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OccasionResource occasionResource = new OccasionResource(occasionService);
        this.restOccasionMockMvc = MockMvcBuilders.standaloneSetup(occasionResource)
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
    public static Occasion createEntity(EntityManager em) {
        Occasion occasion = new Occasion()
            .page(DEFAULT_PAGE);
        return occasion;
    }

    @Before
    public void initTest() {
        occasion = createEntity(em);
    }

    @Test
    @Transactional
    public void createOccasion() throws Exception {
        int databaseSizeBeforeCreate = occasionRepository.findAll().size();

        // Create the Occasion
        OccasionDTO occasionDTO = occasionMapper.toDto(occasion);
        restOccasionMockMvc.perform(post("/api/occasions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(occasionDTO)))
            .andExpect(status().isCreated());

        // Validate the Occasion in the database
        List<Occasion> occasionList = occasionRepository.findAll();
        assertThat(occasionList).hasSize(databaseSizeBeforeCreate + 1);
        Occasion testOccasion = occasionList.get(occasionList.size() - 1);
        assertThat(testOccasion.getPage()).isEqualTo(DEFAULT_PAGE);
    }

    @Test
    @Transactional
    public void createOccasionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = occasionRepository.findAll().size();

        // Create the Occasion with an existing ID
        occasion.setId(1L);
        OccasionDTO occasionDTO = occasionMapper.toDto(occasion);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOccasionMockMvc.perform(post("/api/occasions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(occasionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Occasion in the database
        List<Occasion> occasionList = occasionRepository.findAll();
        assertThat(occasionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOccasions() throws Exception {
        // Initialize the database
        occasionRepository.saveAndFlush(occasion);

        // Get all the occasionList
        restOccasionMockMvc.perform(get("/api/occasions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(occasion.getId().intValue())))
            .andExpect(jsonPath("$.[*].page").value(hasItem(DEFAULT_PAGE)));
    }

    @Test
    @Transactional
    public void getOccasion() throws Exception {
        // Initialize the database
        occasionRepository.saveAndFlush(occasion);

        // Get the occasion
        restOccasionMockMvc.perform(get("/api/occasions/{id}", occasion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(occasion.getId().intValue()))
            .andExpect(jsonPath("$.page").value(DEFAULT_PAGE));
    }

    @Test
    @Transactional
    public void getNonExistingOccasion() throws Exception {
        // Get the occasion
        restOccasionMockMvc.perform(get("/api/occasions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOccasion() throws Exception {
        // Initialize the database
        occasionRepository.saveAndFlush(occasion);
        int databaseSizeBeforeUpdate = occasionRepository.findAll().size();

        // Update the occasion
        Occasion updatedOccasion = occasionRepository.findOne(occasion.getId());
        // Disconnect from session so that the updates on updatedOccasion are not directly saved in db
        em.detach(updatedOccasion);
        updatedOccasion
            .page(UPDATED_PAGE);
        OccasionDTO occasionDTO = occasionMapper.toDto(updatedOccasion);

        restOccasionMockMvc.perform(put("/api/occasions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(occasionDTO)))
            .andExpect(status().isOk());

        // Validate the Occasion in the database
        List<Occasion> occasionList = occasionRepository.findAll();
        assertThat(occasionList).hasSize(databaseSizeBeforeUpdate);
        Occasion testOccasion = occasionList.get(occasionList.size() - 1);
        assertThat(testOccasion.getPage()).isEqualTo(UPDATED_PAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingOccasion() throws Exception {
        int databaseSizeBeforeUpdate = occasionRepository.findAll().size();

        // Create the Occasion
        OccasionDTO occasionDTO = occasionMapper.toDto(occasion);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOccasionMockMvc.perform(put("/api/occasions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(occasionDTO)))
            .andExpect(status().isCreated());

        // Validate the Occasion in the database
        List<Occasion> occasionList = occasionRepository.findAll();
        assertThat(occasionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOccasion() throws Exception {
        // Initialize the database
        occasionRepository.saveAndFlush(occasion);
        int databaseSizeBeforeDelete = occasionRepository.findAll().size();

        // Get the occasion
        restOccasionMockMvc.perform(delete("/api/occasions/{id}", occasion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Occasion> occasionList = occasionRepository.findAll();
        assertThat(occasionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Occasion.class);
        Occasion occasion1 = new Occasion();
        occasion1.setId(1L);
        Occasion occasion2 = new Occasion();
        occasion2.setId(occasion1.getId());
        assertThat(occasion1).isEqualTo(occasion2);
        occasion2.setId(2L);
        assertThat(occasion1).isNotEqualTo(occasion2);
        occasion1.setId(null);
        assertThat(occasion1).isNotEqualTo(occasion2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OccasionDTO.class);
        OccasionDTO occasionDTO1 = new OccasionDTO();
        occasionDTO1.setId(1L);
        OccasionDTO occasionDTO2 = new OccasionDTO();
        assertThat(occasionDTO1).isNotEqualTo(occasionDTO2);
        occasionDTO2.setId(occasionDTO1.getId());
        assertThat(occasionDTO1).isEqualTo(occasionDTO2);
        occasionDTO2.setId(2L);
        assertThat(occasionDTO1).isNotEqualTo(occasionDTO2);
        occasionDTO1.setId(null);
        assertThat(occasionDTO1).isNotEqualTo(occasionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(occasionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(occasionMapper.fromId(null)).isNull();
    }
}
