package com.lookbr.backend.web.rest;

import com.lookbr.backend.LookbrbackendApp;

import com.lookbr.backend.domain.Closet;
import com.lookbr.backend.repository.ClosetRepository;
import com.lookbr.backend.service.ClosetService;
import com.lookbr.backend.service.dto.ClosetDTO;
import com.lookbr.backend.service.mapper.ClosetMapper;
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
 * Test class for the ClosetResource REST controller.
 *
 * @see ClosetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LookbrbackendApp.class)
public class ClosetResourceIntTest {

    private static final Integer DEFAULT_PAGE = 1;
    private static final Integer UPDATED_PAGE = 2;

    @Autowired
    private ClosetRepository closetRepository;

    @Autowired
    private ClosetMapper closetMapper;

    @Autowired
    private ClosetService closetService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restClosetMockMvc;

    private Closet closet;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClosetResource closetResource = new ClosetResource(closetService);
        this.restClosetMockMvc = MockMvcBuilders.standaloneSetup(closetResource)
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
    public static Closet createEntity(EntityManager em) {
        Closet closet = new Closet()
            .page(DEFAULT_PAGE);
        return closet;
    }

    @Before
    public void initTest() {
        closet = createEntity(em);
    }

    @Test
    @Transactional
    public void createCloset() throws Exception {
        int databaseSizeBeforeCreate = closetRepository.findAll().size();

        // Create the Closet
        ClosetDTO closetDTO = closetMapper.toDto(closet);
        restClosetMockMvc.perform(post("/api/closets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(closetDTO)))
            .andExpect(status().isCreated());

        // Validate the Closet in the database
        List<Closet> closetList = closetRepository.findAll();
        assertThat(closetList).hasSize(databaseSizeBeforeCreate + 1);
        Closet testCloset = closetList.get(closetList.size() - 1);
        assertThat(testCloset.getPage()).isEqualTo(DEFAULT_PAGE);
    }

    @Test
    @Transactional
    public void createClosetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = closetRepository.findAll().size();

        // Create the Closet with an existing ID
        closet.setId(1L);
        ClosetDTO closetDTO = closetMapper.toDto(closet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClosetMockMvc.perform(post("/api/closets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(closetDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Closet in the database
        List<Closet> closetList = closetRepository.findAll();
        assertThat(closetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllClosets() throws Exception {
        // Initialize the database
        closetRepository.saveAndFlush(closet);

        // Get all the closetList
        restClosetMockMvc.perform(get("/api/closets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(closet.getId().intValue())))
            .andExpect(jsonPath("$.[*].page").value(hasItem(DEFAULT_PAGE)));
    }

    @Test
    @Transactional
    public void getCloset() throws Exception {
        // Initialize the database
        closetRepository.saveAndFlush(closet);

        // Get the closet
        restClosetMockMvc.perform(get("/api/closets/{id}", closet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(closet.getId().intValue()))
            .andExpect(jsonPath("$.page").value(DEFAULT_PAGE));
    }

    @Test
    @Transactional
    public void getNonExistingCloset() throws Exception {
        // Get the closet
        restClosetMockMvc.perform(get("/api/closets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCloset() throws Exception {
        // Initialize the database
        closetRepository.saveAndFlush(closet);
        int databaseSizeBeforeUpdate = closetRepository.findAll().size();

        // Update the closet
        Closet updatedCloset = closetRepository.findOne(closet.getId());
        // Disconnect from session so that the updates on updatedCloset are not directly saved in db
        em.detach(updatedCloset);
        updatedCloset
            .page(UPDATED_PAGE);
        ClosetDTO closetDTO = closetMapper.toDto(updatedCloset);

        restClosetMockMvc.perform(put("/api/closets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(closetDTO)))
            .andExpect(status().isOk());

        // Validate the Closet in the database
        List<Closet> closetList = closetRepository.findAll();
        assertThat(closetList).hasSize(databaseSizeBeforeUpdate);
        Closet testCloset = closetList.get(closetList.size() - 1);
        assertThat(testCloset.getPage()).isEqualTo(UPDATED_PAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingCloset() throws Exception {
        int databaseSizeBeforeUpdate = closetRepository.findAll().size();

        // Create the Closet
        ClosetDTO closetDTO = closetMapper.toDto(closet);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restClosetMockMvc.perform(put("/api/closets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(closetDTO)))
            .andExpect(status().isCreated());

        // Validate the Closet in the database
        List<Closet> closetList = closetRepository.findAll();
        assertThat(closetList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCloset() throws Exception {
        // Initialize the database
        closetRepository.saveAndFlush(closet);
        int databaseSizeBeforeDelete = closetRepository.findAll().size();

        // Get the closet
        restClosetMockMvc.perform(delete("/api/closets/{id}", closet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Closet> closetList = closetRepository.findAll();
        assertThat(closetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Closet.class);
        Closet closet1 = new Closet();
        closet1.setId(1L);
        Closet closet2 = new Closet();
        closet2.setId(closet1.getId());
        assertThat(closet1).isEqualTo(closet2);
        closet2.setId(2L);
        assertThat(closet1).isNotEqualTo(closet2);
        closet1.setId(null);
        assertThat(closet1).isNotEqualTo(closet2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClosetDTO.class);
        ClosetDTO closetDTO1 = new ClosetDTO();
        closetDTO1.setId(1L);
        ClosetDTO closetDTO2 = new ClosetDTO();
        assertThat(closetDTO1).isNotEqualTo(closetDTO2);
        closetDTO2.setId(closetDTO1.getId());
        assertThat(closetDTO1).isEqualTo(closetDTO2);
        closetDTO2.setId(2L);
        assertThat(closetDTO1).isNotEqualTo(closetDTO2);
        closetDTO1.setId(null);
        assertThat(closetDTO1).isNotEqualTo(closetDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(closetMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(closetMapper.fromId(null)).isNull();
    }
}
