package com.lookbr.backend.web.rest;

import com.lookbr.backend.LookbrbackendApp;

import com.lookbr.backend.domain.Consultant;
import com.lookbr.backend.repository.ConsultantRepository;
import com.lookbr.backend.service.ConsultantService;
import com.lookbr.backend.repository.search.ConsultantSearchRepository;
import com.lookbr.backend.service.dto.ConsultantDTO;
import com.lookbr.backend.service.mapper.ConsultantMapper;
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

import com.lookbr.backend.domain.enumeration.Status;
/**
 * Test class for the ConsultantResource REST controller.
 *
 * @see ConsultantResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LookbrbackendApp.class)
public class ConsultantResourceIntTest {

    private static final String DEFAULT_CONSULTANT_ID = "AAAAAAAAAA";
    private static final String UPDATED_CONSULTANT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CONSULTANT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONSULTANT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONSULTANT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CONSULTANT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CONSULTANT_COVER_PHOTO_URL = "AAAAAAAAAA";
    private static final String UPDATED_CONSULTANT_COVER_PHOTO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CONSULTANT_PROFILE_PHOTO_URL = "AAAAAAAAAA";
    private static final String UPDATED_CONSULTANT_PROFILE_PHOTO_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_CHARGE = 1;
    private static final Integer UPDATED_CHARGE = 2;

    private static final String DEFAULT_INSPIRATION_URL = "AAAAAAAAAA";
    private static final String UPDATED_INSPIRATION_URL = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILE_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_PHOTO = "BBBBBBBBBB";

    private static final Status DEFAULT_STATUS = Status.VERDE;
    private static final Status UPDATED_STATUS = Status.AMARELO;

    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer UPDATED_PAGE = 2;

    @Autowired
    private ConsultantRepository consultantRepository;

    @Autowired
    private ConsultantMapper consultantMapper;

    @Autowired
    private ConsultantService consultantService;

    @Autowired
    private ConsultantSearchRepository consultantSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConsultantMockMvc;

    private Consultant consultant;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsultantResource consultantResource = new ConsultantResource(consultantService);
        this.restConsultantMockMvc = MockMvcBuilders.standaloneSetup(consultantResource)
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
    public static Consultant createEntity(EntityManager em) {
        Consultant consultant = new Consultant()
            .consultantId(DEFAULT_CONSULTANT_ID)
            .consultantName(DEFAULT_CONSULTANT_NAME)
            .consultantDescription(DEFAULT_CONSULTANT_DESCRIPTION)
            .consultantCoverPhotoURL(DEFAULT_CONSULTANT_COVER_PHOTO_URL)
            .consultantProfilePhotoURL(DEFAULT_CONSULTANT_PROFILE_PHOTO_URL)
            .charge(DEFAULT_CHARGE)
            .inspirationURL(DEFAULT_INSPIRATION_URL)
            .profilePhoto(DEFAULT_PROFILE_PHOTO)
            .status(DEFAULT_STATUS)
            .page(DEFAULT_PAGE);
        return consultant;
    }

    @Before
    public void initTest() {
        consultantSearchRepository.deleteAll();
        consultant = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsultant() throws Exception {
        int databaseSizeBeforeCreate = consultantRepository.findAll().size();

        // Create the Consultant
        ConsultantDTO consultantDTO = consultantMapper.toDto(consultant);
        restConsultantMockMvc.perform(post("/api/consultants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultantDTO)))
            .andExpect(status().isCreated());

        // Validate the Consultant in the database
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeCreate + 1);
        Consultant testConsultant = consultantList.get(consultantList.size() - 1);
        assertThat(testConsultant.getConsultantId()).isEqualTo(DEFAULT_CONSULTANT_ID);
        assertThat(testConsultant.getConsultantName()).isEqualTo(DEFAULT_CONSULTANT_NAME);
        assertThat(testConsultant.getConsultantDescription()).isEqualTo(DEFAULT_CONSULTANT_DESCRIPTION);
        assertThat(testConsultant.getConsultantCoverPhotoURL()).isEqualTo(DEFAULT_CONSULTANT_COVER_PHOTO_URL);
        assertThat(testConsultant.getConsultantProfilePhotoURL()).isEqualTo(DEFAULT_CONSULTANT_PROFILE_PHOTO_URL);
        assertThat(testConsultant.getCharge()).isEqualTo(DEFAULT_CHARGE);
        assertThat(testConsultant.getInspirationURL()).isEqualTo(DEFAULT_INSPIRATION_URL);
        assertThat(testConsultant.getProfilePhoto()).isEqualTo(DEFAULT_PROFILE_PHOTO);
        assertThat(testConsultant.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testConsultant.getPage()).isEqualTo(DEFAULT_PAGE);

        // Validate the Consultant in Elasticsearch
        Consultant consultantEs = consultantSearchRepository.findOne(testConsultant.getId());
        assertThat(consultantEs).isEqualToComparingFieldByField(testConsultant);
    }

    @Test
    @Transactional
    public void createConsultantWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consultantRepository.findAll().size();

        // Create the Consultant with an existing ID
        consultant.setId(1L);
        ConsultantDTO consultantDTO = consultantMapper.toDto(consultant);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultantMockMvc.perform(post("/api/consultants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Consultant in the database
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConsultants() throws Exception {
        // Initialize the database
        consultantRepository.saveAndFlush(consultant);

        // Get all the consultantList
        restConsultantMockMvc.perform(get("/api/consultants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultant.getId().intValue())))
            .andExpect(jsonPath("$.[*].consultantId").value(hasItem(DEFAULT_CONSULTANT_ID.toString())))
            .andExpect(jsonPath("$.[*].consultantName").value(hasItem(DEFAULT_CONSULTANT_NAME.toString())))
            .andExpect(jsonPath("$.[*].consultantDescription").value(hasItem(DEFAULT_CONSULTANT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].consultantCoverPhotoURL").value(hasItem(DEFAULT_CONSULTANT_COVER_PHOTO_URL.toString())))
            .andExpect(jsonPath("$.[*].consultantProfilePhotoURL").value(hasItem(DEFAULT_CONSULTANT_PROFILE_PHOTO_URL.toString())))
            .andExpect(jsonPath("$.[*].charge").value(hasItem(DEFAULT_CHARGE)))
            .andExpect(jsonPath("$.[*].inspirationURL").value(hasItem(DEFAULT_INSPIRATION_URL.toString())))
            .andExpect(jsonPath("$.[*].profilePhoto").value(hasItem(DEFAULT_PROFILE_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].page").value(hasItem(DEFAULT_PAGE)));
    }

    @Test
    @Transactional
    public void getConsultant() throws Exception {
        // Initialize the database
        consultantRepository.saveAndFlush(consultant);

        // Get the consultant
        restConsultantMockMvc.perform(get("/api/consultants/{id}", consultant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consultant.getId().intValue()))
            .andExpect(jsonPath("$.consultantId").value(DEFAULT_CONSULTANT_ID.toString()))
            .andExpect(jsonPath("$.consultantName").value(DEFAULT_CONSULTANT_NAME.toString()))
            .andExpect(jsonPath("$.consultantDescription").value(DEFAULT_CONSULTANT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.consultantCoverPhotoURL").value(DEFAULT_CONSULTANT_COVER_PHOTO_URL.toString()))
            .andExpect(jsonPath("$.consultantProfilePhotoURL").value(DEFAULT_CONSULTANT_PROFILE_PHOTO_URL.toString()))
            .andExpect(jsonPath("$.charge").value(DEFAULT_CHARGE))
            .andExpect(jsonPath("$.inspirationURL").value(DEFAULT_INSPIRATION_URL.toString()))
            .andExpect(jsonPath("$.profilePhoto").value(DEFAULT_PROFILE_PHOTO.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.page").value(DEFAULT_PAGE));
    }

    @Test
    @Transactional
    public void getNonExistingConsultant() throws Exception {
        // Get the consultant
        restConsultantMockMvc.perform(get("/api/consultants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsultant() throws Exception {
        // Initialize the database
        consultantRepository.saveAndFlush(consultant);
        consultantSearchRepository.save(consultant);
        int databaseSizeBeforeUpdate = consultantRepository.findAll().size();

        // Update the consultant
        Consultant updatedConsultant = consultantRepository.findOne(consultant.getId());
        // Disconnect from session so that the updates on updatedConsultant are not directly saved in db
        em.detach(updatedConsultant);
        updatedConsultant
            .consultantId(UPDATED_CONSULTANT_ID)
            .consultantName(UPDATED_CONSULTANT_NAME)
            .consultantDescription(UPDATED_CONSULTANT_DESCRIPTION)
            .consultantCoverPhotoURL(UPDATED_CONSULTANT_COVER_PHOTO_URL)
            .consultantProfilePhotoURL(UPDATED_CONSULTANT_PROFILE_PHOTO_URL)
            .charge(UPDATED_CHARGE)
            .inspirationURL(UPDATED_INSPIRATION_URL)
            .profilePhoto(UPDATED_PROFILE_PHOTO)
            .status(UPDATED_STATUS)
            .page(UPDATED_PAGE);
        ConsultantDTO consultantDTO = consultantMapper.toDto(updatedConsultant);

        restConsultantMockMvc.perform(put("/api/consultants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultantDTO)))
            .andExpect(status().isOk());

        // Validate the Consultant in the database
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeUpdate);
        Consultant testConsultant = consultantList.get(consultantList.size() - 1);
        assertThat(testConsultant.getConsultantId()).isEqualTo(UPDATED_CONSULTANT_ID);
        assertThat(testConsultant.getConsultantName()).isEqualTo(UPDATED_CONSULTANT_NAME);
        assertThat(testConsultant.getConsultantDescription()).isEqualTo(UPDATED_CONSULTANT_DESCRIPTION);
        assertThat(testConsultant.getConsultantCoverPhotoURL()).isEqualTo(UPDATED_CONSULTANT_COVER_PHOTO_URL);
        assertThat(testConsultant.getConsultantProfilePhotoURL()).isEqualTo(UPDATED_CONSULTANT_PROFILE_PHOTO_URL);
        assertThat(testConsultant.getCharge()).isEqualTo(UPDATED_CHARGE);
        assertThat(testConsultant.getInspirationURL()).isEqualTo(UPDATED_INSPIRATION_URL);
        assertThat(testConsultant.getProfilePhoto()).isEqualTo(UPDATED_PROFILE_PHOTO);
        assertThat(testConsultant.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testConsultant.getPage()).isEqualTo(UPDATED_PAGE);

        // Validate the Consultant in Elasticsearch
        Consultant consultantEs = consultantSearchRepository.findOne(testConsultant.getId());
        assertThat(consultantEs).isEqualToComparingFieldByField(testConsultant);
    }

    @Test
    @Transactional
    public void updateNonExistingConsultant() throws Exception {
        int databaseSizeBeforeUpdate = consultantRepository.findAll().size();

        // Create the Consultant
        ConsultantDTO consultantDTO = consultantMapper.toDto(consultant);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConsultantMockMvc.perform(put("/api/consultants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultantDTO)))
            .andExpect(status().isCreated());

        // Validate the Consultant in the database
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConsultant() throws Exception {
        // Initialize the database
        consultantRepository.saveAndFlush(consultant);
        consultantSearchRepository.save(consultant);
        int databaseSizeBeforeDelete = consultantRepository.findAll().size();

        // Get the consultant
        restConsultantMockMvc.perform(delete("/api/consultants/{id}", consultant.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate Elasticsearch is empty
        boolean consultantExistsInEs = consultantSearchRepository.exists(consultant.getId());
        assertThat(consultantExistsInEs).isFalse();

        // Validate the database is empty
        List<Consultant> consultantList = consultantRepository.findAll();
        assertThat(consultantList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void searchConsultant() throws Exception {
        // Initialize the database
        consultantRepository.saveAndFlush(consultant);
        consultantSearchRepository.save(consultant);

        // Search the consultant
        restConsultantMockMvc.perform(get("/api/_search/consultants?query=id:" + consultant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultant.getId().intValue())))
            .andExpect(jsonPath("$.[*].consultantId").value(hasItem(DEFAULT_CONSULTANT_ID.toString())))
            .andExpect(jsonPath("$.[*].consultantName").value(hasItem(DEFAULT_CONSULTANT_NAME.toString())))
            .andExpect(jsonPath("$.[*].consultantDescription").value(hasItem(DEFAULT_CONSULTANT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].consultantCoverPhotoURL").value(hasItem(DEFAULT_CONSULTANT_COVER_PHOTO_URL.toString())))
            .andExpect(jsonPath("$.[*].consultantProfilePhotoURL").value(hasItem(DEFAULT_CONSULTANT_PROFILE_PHOTO_URL.toString())))
            .andExpect(jsonPath("$.[*].charge").value(hasItem(DEFAULT_CHARGE)))
            .andExpect(jsonPath("$.[*].inspirationURL").value(hasItem(DEFAULT_INSPIRATION_URL.toString())))
            .andExpect(jsonPath("$.[*].profilePhoto").value(hasItem(DEFAULT_PROFILE_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].page").value(hasItem(DEFAULT_PAGE)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Consultant.class);
        Consultant consultant1 = new Consultant();
        consultant1.setId(1L);
        Consultant consultant2 = new Consultant();
        consultant2.setId(consultant1.getId());
        assertThat(consultant1).isEqualTo(consultant2);
        consultant2.setId(2L);
        assertThat(consultant1).isNotEqualTo(consultant2);
        consultant1.setId(null);
        assertThat(consultant1).isNotEqualTo(consultant2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsultantDTO.class);
        ConsultantDTO consultantDTO1 = new ConsultantDTO();
        consultantDTO1.setId(1L);
        ConsultantDTO consultantDTO2 = new ConsultantDTO();
        assertThat(consultantDTO1).isNotEqualTo(consultantDTO2);
        consultantDTO2.setId(consultantDTO1.getId());
        assertThat(consultantDTO1).isEqualTo(consultantDTO2);
        consultantDTO2.setId(2L);
        assertThat(consultantDTO1).isNotEqualTo(consultantDTO2);
        consultantDTO1.setId(null);
        assertThat(consultantDTO1).isNotEqualTo(consultantDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(consultantMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(consultantMapper.fromId(null)).isNull();
    }
}
