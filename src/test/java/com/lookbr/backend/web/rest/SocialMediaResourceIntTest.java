package com.lookbr.backend.web.rest;

import com.lookbr.backend.LookbrbackendApp;

import com.lookbr.backend.domain.SocialMedia;
import com.lookbr.backend.repository.SocialMediaRepository;
import com.lookbr.backend.service.SocialMediaService;
import com.lookbr.backend.service.dto.SocialMediaDTO;
import com.lookbr.backend.service.mapper.SocialMediaMapper;
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
 * Test class for the SocialMediaResource REST controller.
 *
 * @see SocialMediaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LookbrbackendApp.class)
public class SocialMediaResourceIntTest {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private SocialMediaRepository socialMediaRepository;

    @Autowired
    private SocialMediaMapper socialMediaMapper;

    @Autowired
    private SocialMediaService socialMediaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSocialMediaMockMvc;

    private SocialMedia socialMedia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SocialMediaResource socialMediaResource = new SocialMediaResource(socialMediaService);
        this.restSocialMediaMockMvc = MockMvcBuilders.standaloneSetup(socialMediaResource)
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
    public static SocialMedia createEntity(EntityManager em) {
        SocialMedia socialMedia = new SocialMedia()
            .type(DEFAULT_TYPE)
            .url(DEFAULT_URL);
        return socialMedia;
    }

    @Before
    public void initTest() {
        socialMedia = createEntity(em);
    }

    @Test
    @Transactional
    public void createSocialMedia() throws Exception {
        int databaseSizeBeforeCreate = socialMediaRepository.findAll().size();

        // Create the SocialMedia
        SocialMediaDTO socialMediaDTO = socialMediaMapper.toDto(socialMedia);
        restSocialMediaMockMvc.perform(post("/api/social-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialMediaDTO)))
            .andExpect(status().isCreated());

        // Validate the SocialMedia in the database
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeCreate + 1);
        SocialMedia testSocialMedia = socialMediaList.get(socialMediaList.size() - 1);
        assertThat(testSocialMedia.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testSocialMedia.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createSocialMediaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = socialMediaRepository.findAll().size();

        // Create the SocialMedia with an existing ID
        socialMedia.setId(1L);
        SocialMediaDTO socialMediaDTO = socialMediaMapper.toDto(socialMedia);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocialMediaMockMvc.perform(post("/api/social-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialMediaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SocialMedia in the database
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSocialMedias() throws Exception {
        // Initialize the database
        socialMediaRepository.saveAndFlush(socialMedia);

        // Get all the socialMediaList
        restSocialMediaMockMvc.perform(get("/api/social-medias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(socialMedia.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())));
    }

    @Test
    @Transactional
    public void getSocialMedia() throws Exception {
        // Initialize the database
        socialMediaRepository.saveAndFlush(socialMedia);

        // Get the socialMedia
        restSocialMediaMockMvc.perform(get("/api/social-medias/{id}", socialMedia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(socialMedia.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSocialMedia() throws Exception {
        // Get the socialMedia
        restSocialMediaMockMvc.perform(get("/api/social-medias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSocialMedia() throws Exception {
        // Initialize the database
        socialMediaRepository.saveAndFlush(socialMedia);
        int databaseSizeBeforeUpdate = socialMediaRepository.findAll().size();

        // Update the socialMedia
        SocialMedia updatedSocialMedia = socialMediaRepository.findOne(socialMedia.getId());
        // Disconnect from session so that the updates on updatedSocialMedia are not directly saved in db
        em.detach(updatedSocialMedia);
        updatedSocialMedia
            .type(UPDATED_TYPE)
            .url(UPDATED_URL);
        SocialMediaDTO socialMediaDTO = socialMediaMapper.toDto(updatedSocialMedia);

        restSocialMediaMockMvc.perform(put("/api/social-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialMediaDTO)))
            .andExpect(status().isOk());

        // Validate the SocialMedia in the database
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeUpdate);
        SocialMedia testSocialMedia = socialMediaList.get(socialMediaList.size() - 1);
        assertThat(testSocialMedia.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testSocialMedia.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingSocialMedia() throws Exception {
        int databaseSizeBeforeUpdate = socialMediaRepository.findAll().size();

        // Create the SocialMedia
        SocialMediaDTO socialMediaDTO = socialMediaMapper.toDto(socialMedia);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSocialMediaMockMvc.perform(put("/api/social-medias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(socialMediaDTO)))
            .andExpect(status().isCreated());

        // Validate the SocialMedia in the database
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSocialMedia() throws Exception {
        // Initialize the database
        socialMediaRepository.saveAndFlush(socialMedia);
        int databaseSizeBeforeDelete = socialMediaRepository.findAll().size();

        // Get the socialMedia
        restSocialMediaMockMvc.perform(delete("/api/social-medias/{id}", socialMedia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<SocialMedia> socialMediaList = socialMediaRepository.findAll();
        assertThat(socialMediaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocialMedia.class);
        SocialMedia socialMedia1 = new SocialMedia();
        socialMedia1.setId(1L);
        SocialMedia socialMedia2 = new SocialMedia();
        socialMedia2.setId(socialMedia1.getId());
        assertThat(socialMedia1).isEqualTo(socialMedia2);
        socialMedia2.setId(2L);
        assertThat(socialMedia1).isNotEqualTo(socialMedia2);
        socialMedia1.setId(null);
        assertThat(socialMedia1).isNotEqualTo(socialMedia2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocialMediaDTO.class);
        SocialMediaDTO socialMediaDTO1 = new SocialMediaDTO();
        socialMediaDTO1.setId(1L);
        SocialMediaDTO socialMediaDTO2 = new SocialMediaDTO();
        assertThat(socialMediaDTO1).isNotEqualTo(socialMediaDTO2);
        socialMediaDTO2.setId(socialMediaDTO1.getId());
        assertThat(socialMediaDTO1).isEqualTo(socialMediaDTO2);
        socialMediaDTO2.setId(2L);
        assertThat(socialMediaDTO1).isNotEqualTo(socialMediaDTO2);
        socialMediaDTO1.setId(null);
        assertThat(socialMediaDTO1).isNotEqualTo(socialMediaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(socialMediaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(socialMediaMapper.fromId(null)).isNull();
    }
}
