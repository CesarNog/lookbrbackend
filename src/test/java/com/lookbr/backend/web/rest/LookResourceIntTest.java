package com.lookbr.backend.web.rest;

import com.lookbr.backend.LookbrbackendApp;

import com.lookbr.backend.domain.Look;
import com.lookbr.backend.repository.LookRepository;
import com.lookbr.backend.service.LookService;
import com.lookbr.backend.service.dto.LookDTO;
import com.lookbr.backend.service.mapper.LookMapper;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.lookbr.backend.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the LookResource REST controller.
 *
 * @see LookResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LookbrbackendApp.class)
public class LookResourceIntTest {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final String DEFAULT_TEMPERATURE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPERATURE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DAY_TIME = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DAY_TIME = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_PICTURE_INDEX = 1;
    private static final Integer UPDATED_PICTURE_INDEX = 2;

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private LookRepository lookRepository;

    @Autowired
    private LookMapper lookMapper;

    @Autowired
    private LookService lookService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLookMockMvc;

    private Look look;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LookResource lookResource = new LookResource(lookService);
        this.restLookMockMvc = MockMvcBuilders.standaloneSetup(lookResource)
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
    public static Look createEntity(EntityManager em) {
        Look look = new Look()
            .userId(DEFAULT_USER_ID)
            .temperature(DEFAULT_TEMPERATURE)
            .dayTime(DEFAULT_DAY_TIME)
            .pictureIndex(DEFAULT_PICTURE_INDEX)
            .url(DEFAULT_URL);
        return look;
    }

    @Before
    public void initTest() {
        look = createEntity(em);
    }

    @Test
    @Transactional
    public void createLook() throws Exception {
        int databaseSizeBeforeCreate = lookRepository.findAll().size();

        // Create the Look
        LookDTO lookDTO = lookMapper.toDto(look);
        restLookMockMvc.perform(post("/api/looks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookDTO)))
            .andExpect(status().isCreated());

        // Validate the Look in the database
        List<Look> lookList = lookRepository.findAll();
        assertThat(lookList).hasSize(databaseSizeBeforeCreate + 1);
        Look testLook = lookList.get(lookList.size() - 1);
        assertThat(testLook.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testLook.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testLook.getDayTime()).isEqualTo(DEFAULT_DAY_TIME);
        assertThat(testLook.getPictureIndex()).isEqualTo(DEFAULT_PICTURE_INDEX);
        assertThat(testLook.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createLookWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = lookRepository.findAll().size();

        // Create the Look with an existing ID
        look.setId(1L);
        LookDTO lookDTO = lookMapper.toDto(look);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLookMockMvc.perform(post("/api/looks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Look in the database
        List<Look> lookList = lookRepository.findAll();
        assertThat(lookList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLooks() throws Exception {
        // Initialize the database
        lookRepository.saveAndFlush(look);

        // Get all the lookList
        restLookMockMvc.perform(get("/api/looks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(look.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID.toString())))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.toString())))
            .andExpect(jsonPath("$.[*].dayTime").value(hasItem(DEFAULT_DAY_TIME.toString())))
            .andExpect(jsonPath("$.[*].pictureIndex").value(hasItem(DEFAULT_PICTURE_INDEX)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));
    }

    @Test
    @Transactional
    public void getLook() throws Exception {
        // Initialize the database
        lookRepository.saveAndFlush(look);

        // Get the look
        restLookMockMvc.perform(get("/api/looks/{id}", look.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(look.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID.toString()))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE.toString()))
            .andExpect(jsonPath("$.dayTime").value(DEFAULT_DAY_TIME.toString()))
            .andExpect(jsonPath("$.pictureIndex").value(DEFAULT_PICTURE_INDEX))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLook() throws Exception {
        // Get the look
        restLookMockMvc.perform(get("/api/looks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLook() throws Exception {
        // Initialize the database
        lookRepository.saveAndFlush(look);
        int databaseSizeBeforeUpdate = lookRepository.findAll().size();

        // Update the look
        Look updatedLook = lookRepository.findOne(look.getId());
        // Disconnect from session so that the updates on updatedLook are not directly saved in db
        em.detach(updatedLook);
        updatedLook
            .userId(UPDATED_USER_ID)
            .temperature(UPDATED_TEMPERATURE)
            .dayTime(UPDATED_DAY_TIME)
            .pictureIndex(UPDATED_PICTURE_INDEX)
            .url(UPDATED_URL);
        LookDTO lookDTO = lookMapper.toDto(updatedLook);

        restLookMockMvc.perform(put("/api/looks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookDTO)))
            .andExpect(status().isOk());

        // Validate the Look in the database
        List<Look> lookList = lookRepository.findAll();
        assertThat(lookList).hasSize(databaseSizeBeforeUpdate);
        Look testLook = lookList.get(lookList.size() - 1);
        assertThat(testLook.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testLook.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testLook.getDayTime()).isEqualTo(UPDATED_DAY_TIME);
        assertThat(testLook.getPictureIndex()).isEqualTo(UPDATED_PICTURE_INDEX);
        assertThat(testLook.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingLook() throws Exception {
        int databaseSizeBeforeUpdate = lookRepository.findAll().size();

        // Create the Look
        LookDTO lookDTO = lookMapper.toDto(look);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLookMockMvc.perform(put("/api/looks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(lookDTO)))
            .andExpect(status().isCreated());

        // Validate the Look in the database
        List<Look> lookList = lookRepository.findAll();
        assertThat(lookList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLook() throws Exception {
        // Initialize the database
        lookRepository.saveAndFlush(look);
        int databaseSizeBeforeDelete = lookRepository.findAll().size();

        // Get the look
        restLookMockMvc.perform(delete("/api/looks/{id}", look.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Look> lookList = lookRepository.findAll();
        assertThat(lookList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Look.class);
        Look look1 = new Look();
        look1.setId(1L);
        Look look2 = new Look();
        look2.setId(look1.getId());
        assertThat(look1).isEqualTo(look2);
        look2.setId(2L);
        assertThat(look1).isNotEqualTo(look2);
        look1.setId(null);
        assertThat(look1).isNotEqualTo(look2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LookDTO.class);
        LookDTO lookDTO1 = new LookDTO();
        lookDTO1.setId(1L);
        LookDTO lookDTO2 = new LookDTO();
        assertThat(lookDTO1).isNotEqualTo(lookDTO2);
        lookDTO2.setId(lookDTO1.getId());
        assertThat(lookDTO1).isEqualTo(lookDTO2);
        lookDTO2.setId(2L);
        assertThat(lookDTO1).isNotEqualTo(lookDTO2);
        lookDTO1.setId(null);
        assertThat(lookDTO1).isNotEqualTo(lookDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(lookMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(lookMapper.fromId(null)).isNull();
    }
}
