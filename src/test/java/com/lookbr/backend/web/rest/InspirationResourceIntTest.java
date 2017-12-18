package com.lookbr.backend.web.rest;

import com.lookbr.backend.LookbrbackendApp;

import com.lookbr.backend.domain.Inspiration;
import com.lookbr.backend.repository.InspirationRepository;
import com.lookbr.backend.service.InspirationService;
import com.lookbr.backend.service.dto.InspirationDTO;
import com.lookbr.backend.service.mapper.InspirationMapper;
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
 * Test class for the InspirationResource REST controller.
 *
 * @see InspirationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LookbrbackendApp.class)
public class InspirationResourceIntTest {

    private static final String DEFAULT_CONSULTANT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONSULTANT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONSULTANT_PROFILE_PHOTO_URL = "AAAAAAAAAA";
    private static final String UPDATED_CONSULTANT_PROFILE_PHOTO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_INSPIRATION_URL = "AAAAAAAAAA";
    private static final String UPDATED_INSPIRATION_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer UPDATED_PAGE = 2;

    @Autowired
    private InspirationRepository inspirationRepository;

    @Autowired
    private InspirationMapper inspirationMapper;

    @Autowired
    private InspirationService inspirationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInspirationMockMvc;

    private Inspiration inspiration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InspirationResource inspirationResource = new InspirationResource(inspirationService);
        this.restInspirationMockMvc = MockMvcBuilders.standaloneSetup(inspirationResource)
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
    public static Inspiration createEntity(EntityManager em) {
        Inspiration inspiration = new Inspiration()
            .consultantName(DEFAULT_CONSULTANT_NAME)
            .consultantProfilePhotoURL(DEFAULT_CONSULTANT_PROFILE_PHOTO_URL)
            .inspirationURL(DEFAULT_INSPIRATION_URL)
            .page(DEFAULT_PAGE);
        return inspiration;
    }

    @Before
    public void initTest() {
        inspiration = createEntity(em);
    }

    @Test
    @Transactional
    public void createInspiration() throws Exception {
        int databaseSizeBeforeCreate = inspirationRepository.findAll().size();

        // Create the Inspiration
        InspirationDTO inspirationDTO = inspirationMapper.toDto(inspiration);
        restInspirationMockMvc.perform(post("/api/inspirations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspirationDTO)))
            .andExpect(status().isCreated());

        // Validate the Inspiration in the database
        List<Inspiration> inspirationList = inspirationRepository.findAll();
        assertThat(inspirationList).hasSize(databaseSizeBeforeCreate + 1);
        Inspiration testInspiration = inspirationList.get(inspirationList.size() - 1);
        assertThat(testInspiration.getConsultantName()).isEqualTo(DEFAULT_CONSULTANT_NAME);
        assertThat(testInspiration.getConsultantProfilePhotoURL()).isEqualTo(DEFAULT_CONSULTANT_PROFILE_PHOTO_URL);
        assertThat(testInspiration.getInspirationURL()).isEqualTo(DEFAULT_INSPIRATION_URL);
        assertThat(testInspiration.getPage()).isEqualTo(DEFAULT_PAGE);
    }

    @Test
    @Transactional
    public void createInspirationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inspirationRepository.findAll().size();

        // Create the Inspiration with an existing ID
        inspiration.setId(1L);
        InspirationDTO inspirationDTO = inspirationMapper.toDto(inspiration);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspirationMockMvc.perform(post("/api/inspirations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspirationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inspiration in the database
        List<Inspiration> inspirationList = inspirationRepository.findAll();
        assertThat(inspirationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInspirations() throws Exception {
        // Initialize the database
        inspirationRepository.saveAndFlush(inspiration);

        // Get all the inspirationList
        restInspirationMockMvc.perform(get("/api/inspirations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspiration.getId().intValue())))
            .andExpect(jsonPath("$.[*].consultantName").value(hasItem(DEFAULT_CONSULTANT_NAME.toString())))
            .andExpect(jsonPath("$.[*].consultantProfilePhotoURL").value(hasItem(DEFAULT_CONSULTANT_PROFILE_PHOTO_URL.toString())))
            .andExpect(jsonPath("$.[*].inspirationURL").value(hasItem(DEFAULT_INSPIRATION_URL.toString())))
            .andExpect(jsonPath("$.[*].page").value(hasItem(DEFAULT_PAGE)));
    }

    @Test
    @Transactional
    public void getInspiration() throws Exception {
        // Initialize the database
        inspirationRepository.saveAndFlush(inspiration);

        // Get the inspiration
        restInspirationMockMvc.perform(get("/api/inspirations/{id}", inspiration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inspiration.getId().intValue()))
            .andExpect(jsonPath("$.consultantName").value(DEFAULT_CONSULTANT_NAME.toString()))
            .andExpect(jsonPath("$.consultantProfilePhotoURL").value(DEFAULT_CONSULTANT_PROFILE_PHOTO_URL.toString()))
            .andExpect(jsonPath("$.inspirationURL").value(DEFAULT_INSPIRATION_URL.toString()))
            .andExpect(jsonPath("$.page").value(DEFAULT_PAGE));
    }

    @Test
    @Transactional
    public void getNonExistingInspiration() throws Exception {
        // Get the inspiration
        restInspirationMockMvc.perform(get("/api/inspirations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInspiration() throws Exception {
        // Initialize the database
        inspirationRepository.saveAndFlush(inspiration);
        int databaseSizeBeforeUpdate = inspirationRepository.findAll().size();

        // Update the inspiration
        Inspiration updatedInspiration = inspirationRepository.findOne(inspiration.getId());
        // Disconnect from session so that the updates on updatedInspiration are not directly saved in db
        em.detach(updatedInspiration);
        updatedInspiration
            .consultantName(UPDATED_CONSULTANT_NAME)
            .consultantProfilePhotoURL(UPDATED_CONSULTANT_PROFILE_PHOTO_URL)
            .inspirationURL(UPDATED_INSPIRATION_URL)
            .page(UPDATED_PAGE);
        InspirationDTO inspirationDTO = inspirationMapper.toDto(updatedInspiration);

        restInspirationMockMvc.perform(put("/api/inspirations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspirationDTO)))
            .andExpect(status().isOk());

        // Validate the Inspiration in the database
        List<Inspiration> inspirationList = inspirationRepository.findAll();
        assertThat(inspirationList).hasSize(databaseSizeBeforeUpdate);
        Inspiration testInspiration = inspirationList.get(inspirationList.size() - 1);
        assertThat(testInspiration.getConsultantName()).isEqualTo(UPDATED_CONSULTANT_NAME);
        assertThat(testInspiration.getConsultantProfilePhotoURL()).isEqualTo(UPDATED_CONSULTANT_PROFILE_PHOTO_URL);
        assertThat(testInspiration.getInspirationURL()).isEqualTo(UPDATED_INSPIRATION_URL);
        assertThat(testInspiration.getPage()).isEqualTo(UPDATED_PAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingInspiration() throws Exception {
        int databaseSizeBeforeUpdate = inspirationRepository.findAll().size();

        // Create the Inspiration
        InspirationDTO inspirationDTO = inspirationMapper.toDto(inspiration);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInspirationMockMvc.perform(put("/api/inspirations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspirationDTO)))
            .andExpect(status().isCreated());

        // Validate the Inspiration in the database
        List<Inspiration> inspirationList = inspirationRepository.findAll();
        assertThat(inspirationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInspiration() throws Exception {
        // Initialize the database
        inspirationRepository.saveAndFlush(inspiration);
        int databaseSizeBeforeDelete = inspirationRepository.findAll().size();

        // Get the inspiration
        restInspirationMockMvc.perform(delete("/api/inspirations/{id}", inspiration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Inspiration> inspirationList = inspirationRepository.findAll();
        assertThat(inspirationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inspiration.class);
        Inspiration inspiration1 = new Inspiration();
        inspiration1.setId(1L);
        Inspiration inspiration2 = new Inspiration();
        inspiration2.setId(inspiration1.getId());
        assertThat(inspiration1).isEqualTo(inspiration2);
        inspiration2.setId(2L);
        assertThat(inspiration1).isNotEqualTo(inspiration2);
        inspiration1.setId(null);
        assertThat(inspiration1).isNotEqualTo(inspiration2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InspirationDTO.class);
        InspirationDTO inspirationDTO1 = new InspirationDTO();
        inspirationDTO1.setId(1L);
        InspirationDTO inspirationDTO2 = new InspirationDTO();
        assertThat(inspirationDTO1).isNotEqualTo(inspirationDTO2);
        inspirationDTO2.setId(inspirationDTO1.getId());
        assertThat(inspirationDTO1).isEqualTo(inspirationDTO2);
        inspirationDTO2.setId(2L);
        assertThat(inspirationDTO1).isNotEqualTo(inspirationDTO2);
        inspirationDTO1.setId(null);
        assertThat(inspirationDTO1).isNotEqualTo(inspirationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(inspirationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(inspirationMapper.fromId(null)).isNull();
    }
}
