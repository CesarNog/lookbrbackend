package com.lookbr.backend.web.rest;

import com.lookbr.backend.LookbrbackendApp;

import com.lookbr.backend.domain.ConsultantVote;
import com.lookbr.backend.repository.ConsultantVoteRepository;
import com.lookbr.backend.service.ConsultantVoteService;
import com.lookbr.backend.service.dto.ConsultantVoteDTO;
import com.lookbr.backend.service.mapper.ConsultantVoteMapper;
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
 * Test class for the ConsultantVoteResource REST controller.
 *
 * @see ConsultantVoteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LookbrbackendApp.class)
public class ConsultantVoteResourceIntTest {

    private static final String DEFAULT_CONSULTANT_PROFILE_PHOTO_URL = "AAAAAAAAAA";
    private static final String UPDATED_CONSULTANT_PROFILE_PHOTO_URL = "BBBBBBBBBB";

    @Autowired
    private ConsultantVoteRepository consultantVoteRepository;

    @Autowired
    private ConsultantVoteMapper consultantVoteMapper;

    @Autowired
    private ConsultantVoteService consultantVoteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restConsultantVoteMockMvc;

    private ConsultantVote consultantVote;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConsultantVoteResource consultantVoteResource = new ConsultantVoteResource(consultantVoteService);
        this.restConsultantVoteMockMvc = MockMvcBuilders.standaloneSetup(consultantVoteResource)
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
    public static ConsultantVote createEntity(EntityManager em) {
        ConsultantVote consultantVote = new ConsultantVote()
            .consultantProfilePhotoUrl(DEFAULT_CONSULTANT_PROFILE_PHOTO_URL);
        return consultantVote;
    }

    @Before
    public void initTest() {
        consultantVote = createEntity(em);
    }

    @Test
    @Transactional
    public void createConsultantVote() throws Exception {
        int databaseSizeBeforeCreate = consultantVoteRepository.findAll().size();

        // Create the ConsultantVote
        ConsultantVoteDTO consultantVoteDTO = consultantVoteMapper.toDto(consultantVote);
        restConsultantVoteMockMvc.perform(post("/api/consultant-votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultantVoteDTO)))
            .andExpect(status().isCreated());

        // Validate the ConsultantVote in the database
        List<ConsultantVote> consultantVoteList = consultantVoteRepository.findAll();
        assertThat(consultantVoteList).hasSize(databaseSizeBeforeCreate + 1);
        ConsultantVote testConsultantVote = consultantVoteList.get(consultantVoteList.size() - 1);
        assertThat(testConsultantVote.getConsultantProfilePhotoUrl()).isEqualTo(DEFAULT_CONSULTANT_PROFILE_PHOTO_URL);
    }

    @Test
    @Transactional
    public void createConsultantVoteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = consultantVoteRepository.findAll().size();

        // Create the ConsultantVote with an existing ID
        consultantVote.setId(1L);
        ConsultantVoteDTO consultantVoteDTO = consultantVoteMapper.toDto(consultantVote);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConsultantVoteMockMvc.perform(post("/api/consultant-votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultantVoteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ConsultantVote in the database
        List<ConsultantVote> consultantVoteList = consultantVoteRepository.findAll();
        assertThat(consultantVoteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllConsultantVotes() throws Exception {
        // Initialize the database
        consultantVoteRepository.saveAndFlush(consultantVote);

        // Get all the consultantVoteList
        restConsultantVoteMockMvc.perform(get("/api/consultant-votes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(consultantVote.getId().intValue())))
            .andExpect(jsonPath("$.[*].consultantProfilePhotoUrl").value(hasItem(DEFAULT_CONSULTANT_PROFILE_PHOTO_URL.toString())));
    }

    @Test
    @Transactional
    public void getConsultantVote() throws Exception {
        // Initialize the database
        consultantVoteRepository.saveAndFlush(consultantVote);

        // Get the consultantVote
        restConsultantVoteMockMvc.perform(get("/api/consultant-votes/{id}", consultantVote.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(consultantVote.getId().intValue()))
            .andExpect(jsonPath("$.consultantProfilePhotoUrl").value(DEFAULT_CONSULTANT_PROFILE_PHOTO_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingConsultantVote() throws Exception {
        // Get the consultantVote
        restConsultantVoteMockMvc.perform(get("/api/consultant-votes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConsultantVote() throws Exception {
        // Initialize the database
        consultantVoteRepository.saveAndFlush(consultantVote);
        int databaseSizeBeforeUpdate = consultantVoteRepository.findAll().size();

        // Update the consultantVote
        ConsultantVote updatedConsultantVote = consultantVoteRepository.findOne(consultantVote.getId());
        // Disconnect from session so that the updates on updatedConsultantVote are not directly saved in db
        em.detach(updatedConsultantVote);
        updatedConsultantVote
            .consultantProfilePhotoUrl(UPDATED_CONSULTANT_PROFILE_PHOTO_URL);
        ConsultantVoteDTO consultantVoteDTO = consultantVoteMapper.toDto(updatedConsultantVote);

        restConsultantVoteMockMvc.perform(put("/api/consultant-votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultantVoteDTO)))
            .andExpect(status().isOk());

        // Validate the ConsultantVote in the database
        List<ConsultantVote> consultantVoteList = consultantVoteRepository.findAll();
        assertThat(consultantVoteList).hasSize(databaseSizeBeforeUpdate);
        ConsultantVote testConsultantVote = consultantVoteList.get(consultantVoteList.size() - 1);
        assertThat(testConsultantVote.getConsultantProfilePhotoUrl()).isEqualTo(UPDATED_CONSULTANT_PROFILE_PHOTO_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingConsultantVote() throws Exception {
        int databaseSizeBeforeUpdate = consultantVoteRepository.findAll().size();

        // Create the ConsultantVote
        ConsultantVoteDTO consultantVoteDTO = consultantVoteMapper.toDto(consultantVote);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restConsultantVoteMockMvc.perform(put("/api/consultant-votes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(consultantVoteDTO)))
            .andExpect(status().isCreated());

        // Validate the ConsultantVote in the database
        List<ConsultantVote> consultantVoteList = consultantVoteRepository.findAll();
        assertThat(consultantVoteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteConsultantVote() throws Exception {
        // Initialize the database
        consultantVoteRepository.saveAndFlush(consultantVote);
        int databaseSizeBeforeDelete = consultantVoteRepository.findAll().size();

        // Get the consultantVote
        restConsultantVoteMockMvc.perform(delete("/api/consultant-votes/{id}", consultantVote.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ConsultantVote> consultantVoteList = consultantVoteRepository.findAll();
        assertThat(consultantVoteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsultantVote.class);
        ConsultantVote consultantVote1 = new ConsultantVote();
        consultantVote1.setId(1L);
        ConsultantVote consultantVote2 = new ConsultantVote();
        consultantVote2.setId(consultantVote1.getId());
        assertThat(consultantVote1).isEqualTo(consultantVote2);
        consultantVote2.setId(2L);
        assertThat(consultantVote1).isNotEqualTo(consultantVote2);
        consultantVote1.setId(null);
        assertThat(consultantVote1).isNotEqualTo(consultantVote2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConsultantVoteDTO.class);
        ConsultantVoteDTO consultantVoteDTO1 = new ConsultantVoteDTO();
        consultantVoteDTO1.setId(1L);
        ConsultantVoteDTO consultantVoteDTO2 = new ConsultantVoteDTO();
        assertThat(consultantVoteDTO1).isNotEqualTo(consultantVoteDTO2);
        consultantVoteDTO2.setId(consultantVoteDTO1.getId());
        assertThat(consultantVoteDTO1).isEqualTo(consultantVoteDTO2);
        consultantVoteDTO2.setId(2L);
        assertThat(consultantVoteDTO1).isNotEqualTo(consultantVoteDTO2);
        consultantVoteDTO1.setId(null);
        assertThat(consultantVoteDTO1).isNotEqualTo(consultantVoteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(consultantVoteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(consultantVoteMapper.fromId(null)).isNull();
    }
}
