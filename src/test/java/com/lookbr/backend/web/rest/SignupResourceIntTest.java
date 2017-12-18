package com.lookbr.backend.web.rest;

import com.lookbr.backend.LookbrbackendApp;

import com.lookbr.backend.domain.Signup;
import com.lookbr.backend.repository.SignupRepository;
import com.lookbr.backend.service.SignupService;
import com.lookbr.backend.service.dto.SignupDTO;
import com.lookbr.backend.service.mapper.SignupMapper;
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

import com.lookbr.backend.domain.enumeration.LoginType;
/**
 * Test class for the SignupResource REST controller.
 *
 * @see SignupResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LookbrbackendApp.class)
public class SignupResourceIntTest {

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final LoginType DEFAULT_LOGIN_TYPE = LoginType.FACEBOOK;
    private static final LoginType UPDATED_LOGIN_TYPE = LoginType.EMAIL;

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILE_PHOTO_URL = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_PHOTO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_PROFILE_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_PHOTO = "BBBBBBBBBB";

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN = "BBBBBBBBBB";

    @Autowired
    private SignupRepository signupRepository;

    @Autowired
    private SignupMapper signupMapper;

    @Autowired
    private SignupService signupService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restSignupMockMvc;

    private Signup signup;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SignupResource signupResource = new SignupResource(signupService);
        this.restSignupMockMvc = MockMvcBuilders.standaloneSetup(signupResource)
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
    public static Signup createEntity(EntityManager em) {
        Signup signup = new Signup()
            .email(DEFAULT_EMAIL)
            .loginType(DEFAULT_LOGIN_TYPE)
            .password(DEFAULT_PASSWORD)
            .profilePhotoUrl(DEFAULT_PROFILE_PHOTO_URL)
            .profilePhoto(DEFAULT_PROFILE_PHOTO)
            .username(DEFAULT_USERNAME)
            .token(DEFAULT_TOKEN);
        return signup;
    }

    @Before
    public void initTest() {
        signup = createEntity(em);
    }

    @Test
    @Transactional
    public void createSignup() throws Exception {
        int databaseSizeBeforeCreate = signupRepository.findAll().size();

        // Create the Signup
        SignupDTO signupDTO = signupMapper.toDto(signup);
        restSignupMockMvc.perform(post("/api/signups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signupDTO)))
            .andExpect(status().isCreated());

        // Validate the Signup in the database
        List<Signup> signupList = signupRepository.findAll();
        assertThat(signupList).hasSize(databaseSizeBeforeCreate + 1);
        Signup testSignup = signupList.get(signupList.size() - 1);
        assertThat(testSignup.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSignup.getLoginType()).isEqualTo(DEFAULT_LOGIN_TYPE);
        assertThat(testSignup.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testSignup.getProfilePhotoUrl()).isEqualTo(DEFAULT_PROFILE_PHOTO_URL);
        assertThat(testSignup.getProfilePhoto()).isEqualTo(DEFAULT_PROFILE_PHOTO);
        assertThat(testSignup.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testSignup.getToken()).isEqualTo(DEFAULT_TOKEN);
    }

    @Test
    @Transactional
    public void createSignupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = signupRepository.findAll().size();

        // Create the Signup with an existing ID
        signup.setId(1L);
        SignupDTO signupDTO = signupMapper.toDto(signup);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSignupMockMvc.perform(post("/api/signups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signupDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Signup in the database
        List<Signup> signupList = signupRepository.findAll();
        assertThat(signupList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllSignups() throws Exception {
        // Initialize the database
        signupRepository.saveAndFlush(signup);

        // Get all the signupList
        restSignupMockMvc.perform(get("/api/signups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(signup.getId().intValue())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].loginType").value(hasItem(DEFAULT_LOGIN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())))
            .andExpect(jsonPath("$.[*].profilePhotoUrl").value(hasItem(DEFAULT_PROFILE_PHOTO_URL.toString())))
            .andExpect(jsonPath("$.[*].profilePhoto").value(hasItem(DEFAULT_PROFILE_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].token").value(hasItem(DEFAULT_TOKEN.toString())));
    }

    @Test
    @Transactional
    public void getSignup() throws Exception {
        // Initialize the database
        signupRepository.saveAndFlush(signup);

        // Get the signup
        restSignupMockMvc.perform(get("/api/signups/{id}", signup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(signup.getId().intValue()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.loginType").value(DEFAULT_LOGIN_TYPE.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()))
            .andExpect(jsonPath("$.profilePhotoUrl").value(DEFAULT_PROFILE_PHOTO_URL.toString()))
            .andExpect(jsonPath("$.profilePhoto").value(DEFAULT_PROFILE_PHOTO.toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.token").value(DEFAULT_TOKEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSignup() throws Exception {
        // Get the signup
        restSignupMockMvc.perform(get("/api/signups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSignup() throws Exception {
        // Initialize the database
        signupRepository.saveAndFlush(signup);
        int databaseSizeBeforeUpdate = signupRepository.findAll().size();

        // Update the signup
        Signup updatedSignup = signupRepository.findOne(signup.getId());
        // Disconnect from session so that the updates on updatedSignup are not directly saved in db
        em.detach(updatedSignup);
        updatedSignup
            .email(UPDATED_EMAIL)
            .loginType(UPDATED_LOGIN_TYPE)
            .password(UPDATED_PASSWORD)
            .profilePhotoUrl(UPDATED_PROFILE_PHOTO_URL)
            .profilePhoto(UPDATED_PROFILE_PHOTO)
            .username(UPDATED_USERNAME)
            .token(UPDATED_TOKEN);
        SignupDTO signupDTO = signupMapper.toDto(updatedSignup);

        restSignupMockMvc.perform(put("/api/signups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signupDTO)))
            .andExpect(status().isOk());

        // Validate the Signup in the database
        List<Signup> signupList = signupRepository.findAll();
        assertThat(signupList).hasSize(databaseSizeBeforeUpdate);
        Signup testSignup = signupList.get(signupList.size() - 1);
        assertThat(testSignup.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSignup.getLoginType()).isEqualTo(UPDATED_LOGIN_TYPE);
        assertThat(testSignup.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testSignup.getProfilePhotoUrl()).isEqualTo(UPDATED_PROFILE_PHOTO_URL);
        assertThat(testSignup.getProfilePhoto()).isEqualTo(UPDATED_PROFILE_PHOTO);
        assertThat(testSignup.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testSignup.getToken()).isEqualTo(UPDATED_TOKEN);
    }

    @Test
    @Transactional
    public void updateNonExistingSignup() throws Exception {
        int databaseSizeBeforeUpdate = signupRepository.findAll().size();

        // Create the Signup
        SignupDTO signupDTO = signupMapper.toDto(signup);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restSignupMockMvc.perform(put("/api/signups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(signupDTO)))
            .andExpect(status().isCreated());

        // Validate the Signup in the database
        List<Signup> signupList = signupRepository.findAll();
        assertThat(signupList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteSignup() throws Exception {
        // Initialize the database
        signupRepository.saveAndFlush(signup);
        int databaseSizeBeforeDelete = signupRepository.findAll().size();

        // Get the signup
        restSignupMockMvc.perform(delete("/api/signups/{id}", signup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Signup> signupList = signupRepository.findAll();
        assertThat(signupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Signup.class);
        Signup signup1 = new Signup();
        signup1.setId(1L);
        Signup signup2 = new Signup();
        signup2.setId(signup1.getId());
        assertThat(signup1).isEqualTo(signup2);
        signup2.setId(2L);
        assertThat(signup1).isNotEqualTo(signup2);
        signup1.setId(null);
        assertThat(signup1).isNotEqualTo(signup2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SignupDTO.class);
        SignupDTO signupDTO1 = new SignupDTO();
        signupDTO1.setId(1L);
        SignupDTO signupDTO2 = new SignupDTO();
        assertThat(signupDTO1).isNotEqualTo(signupDTO2);
        signupDTO2.setId(signupDTO1.getId());
        assertThat(signupDTO1).isEqualTo(signupDTO2);
        signupDTO2.setId(2L);
        assertThat(signupDTO1).isNotEqualTo(signupDTO2);
        signupDTO1.setId(null);
        assertThat(signupDTO1).isNotEqualTo(signupDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(signupMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(signupMapper.fromId(null)).isNull();
    }
}
