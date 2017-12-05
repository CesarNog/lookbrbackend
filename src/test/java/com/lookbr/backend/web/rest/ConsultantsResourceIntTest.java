package com.lookbr.backend.web.rest;

import com.lookbr.backend.LookbrbackendApp;

import com.lookbr.backend.domain.Consultants;
import com.lookbr.backend.repository.ConsultantsRepository;
import com.lookbr.backend.service.ConsultantsService;
import com.lookbr.backend.service.dto.ConsultantsDTO;
import com.lookbr.backend.service.mapper.ConsultantsMapper;
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
 * Test class for the ConsultantsResource REST controller.
 *
 * @see ConsultantsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LookbrbackendApp.class)
public class ConsultantsResourceIntTest {

    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer UPDATED_PAGE = 2;

    @Autowired
    private ConsultantsRepository consultantsRepository;

    @Autowired
    private ConsultantsMapper consultantsMapper;

    @Autowired
    private ConsultantsService consultantsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConsultantsMockMvc;

    private Consultants consultants;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsultantsResource consultantsResource = new ConsultantsResource(consultantsService);
        this.restConsultantsMockMvc = MockMvcBuilders.standaloneSetup(consultantsResource)
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
    public static Consultants createEntity(EntityManager em) {
        Consultants consultants = new Consultants()
            .page(DEFAULT_PAGE);
        return consultants;
    }

    @Before
    public void initTest() {
        consultants = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsultants() throws Exception {
        int databaseSizeBeforeCreate = consultantsRepository.findAll().size();

        // Create the Consultants
        ConsultantsDTO consultantsDTO = consultantsMapper.toDto(consultants);
        restConsultantsMockMvc.perform(post("/api/consultants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultantsDTO)))
            .andExpect(status().isCreated());

        // Validate the Consultants in the database
        List<Consultants> consultantsList = consultantsRepository.findAll();
        assertThat(consultantsList).hasSize(databaseSizeBeforeCreate + 1);
        Consultants testConsultants = consultantsList.get(consultantsList.size() - 1);
        assertThat(testConsultants.getPage()).isEqualTo(DEFAULT_PAGE);
    }

    @Test
    @Transactional
    public void createConsultantsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consultantsRepository.findAll().size();

        // Create the Consultants with an existing ID
        consultants.setId(1L);
        ConsultantsDTO consultantsDTO = consultantsMapper.toDto(consultants);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultantsMockMvc.perform(post("/api/consultants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultantsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Consultants in the database
        List<Consultants> consultantsList = consultantsRepository.findAll();
        assertThat(consultantsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConsultants() throws Exception {
        // Initialize the database
        consultantsRepository.saveAndFlush(consultants);

        // Get all the consultantsList
        restConsultantsMockMvc.perform(get("/api/consultants?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultants.getId().intValue())))
            .andExpect(jsonPath("$.[*].page").value(hasItem(DEFAULT_PAGE)));
    }

    @Test
    @Transactional
    public void getConsultants() throws Exception {
        // Initialize the database
        consultantsRepository.saveAndFlush(consultants);

        // Get the consultants
        restConsultantsMockMvc.perform(get("/api/consultants/{id}", consultants.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consultants.getId().intValue()))
            .andExpect(jsonPath("$.page").value(DEFAULT_PAGE));
    }

    @Test
    @Transactional
    public void getNonExistingConsultants() throws Exception {
        // Get the consultants
        restConsultantsMockMvc.perform(get("/api/consultants/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsultants() throws Exception {
        // Initialize the database
        consultantsRepository.saveAndFlush(consultants);
        int databaseSizeBeforeUpdate = consultantsRepository.findAll().size();

        // Update the consultants
        Consultants updatedConsultants = consultantsRepository.findOne(consultants.getId());
        // Disconnect from session so that the updates on updatedConsultants are not directly saved in db
        em.detach(updatedConsultants);
        updatedConsultants
            .page(UPDATED_PAGE);
        ConsultantsDTO consultantsDTO = consultantsMapper.toDto(updatedConsultants);

        restConsultantsMockMvc.perform(put("/api/consultants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultantsDTO)))
            .andExpect(status().isOk());

        // Validate the Consultants in the database
        List<Consultants> consultantsList = consultantsRepository.findAll();
        assertThat(consultantsList).hasSize(databaseSizeBeforeUpdate);
        Consultants testConsultants = consultantsList.get(consultantsList.size() - 1);
        assertThat(testConsultants.getPage()).isEqualTo(UPDATED_PAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingConsultants() throws Exception {
        int databaseSizeBeforeUpdate = consultantsRepository.findAll().size();

        // Create the Consultants
        ConsultantsDTO consultantsDTO = consultantsMapper.toDto(consultants);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConsultantsMockMvc.perform(put("/api/consultants")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultantsDTO)))
            .andExpect(status().isCreated());

        // Validate the Consultants in the database
        List<Consultants> consultantsList = consultantsRepository.findAll();
        assertThat(consultantsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConsultants() throws Exception {
        // Initialize the database
        consultantsRepository.saveAndFlush(consultants);
        int databaseSizeBeforeDelete = consultantsRepository.findAll().size();

        // Get the consultants
        restConsultantsMockMvc.perform(delete("/api/consultants/{id}", consultants.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Consultants> consultantsList = consultantsRepository.findAll();
        assertThat(consultantsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Consultants.class);
        Consultants consultants1 = new Consultants();
        consultants1.setId(1L);
        Consultants consultants2 = new Consultants();
        consultants2.setId(consultants1.getId());
        assertThat(consultants1).isEqualTo(consultants2);
        consultants2.setId(2L);
        assertThat(consultants1).isNotEqualTo(consultants2);
        consultants1.setId(null);
        assertThat(consultants1).isNotEqualTo(consultants2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsultantsDTO.class);
        ConsultantsDTO consultantsDTO1 = new ConsultantsDTO();
        consultantsDTO1.setId(1L);
        ConsultantsDTO consultantsDTO2 = new ConsultantsDTO();
        assertThat(consultantsDTO1).isNotEqualTo(consultantsDTO2);
        consultantsDTO2.setId(consultantsDTO1.getId());
        assertThat(consultantsDTO1).isEqualTo(consultantsDTO2);
        consultantsDTO2.setId(2L);
        assertThat(consultantsDTO1).isNotEqualTo(consultantsDTO2);
        consultantsDTO1.setId(null);
        assertThat(consultantsDTO1).isNotEqualTo(consultantsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(consultantsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(consultantsMapper.fromId(null)).isNull();
    }
}
