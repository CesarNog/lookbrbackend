package com.lookbr.backend.web.rest;

import com.lookbr.backend.LookbrbackendApp;

import com.lookbr.backend.domain.Login;
import com.lookbr.backend.repository.LoginRepository;
import com.lookbr.backend.service.LoginService;
import com.lookbr.backend.service.dto.LoginDTO;
import com.lookbr.backend.service.mapper.LoginMapper;
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
 * Test class for the LoginResource REST controller.
 *
 * @see LoginResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LookbrbackendApp.class)
public class LoginResourceIntTest {

    private static final LoginType DEFAULT_LOGIN_TYPE = LoginType.FACEBOOK;
    private static final LoginType UPDATED_LOGIN_TYPE = LoginType.EMAIL;

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private LoginMapper loginMapper;

    @Autowired
    private LoginService loginService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLoginMockMvc;

    private Login login;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LoginResource loginResource = new LoginResource(loginService);
        this.restLoginMockMvc = MockMvcBuilders.standaloneSetup(loginResource)
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
    public static Login createEntity(EntityManager em) {
        Login login = new Login()
            .loginType(DEFAULT_LOGIN_TYPE)
            .username(DEFAULT_USERNAME)
            .email(DEFAULT_EMAIL)
            .password(DEFAULT_PASSWORD);
        return login;
    }

    @Before
    public void initTest() {
        login = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogin() throws Exception {
        int databaseSizeBeforeCreate = loginRepository.findAll().size();

        // Create the Login
        LoginDTO loginDTO = loginMapper.toDto(login);
        restLoginMockMvc.perform(post("/api/logins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginDTO)))
            .andExpect(status().isCreated());

        // Validate the Login in the database
        List<Login> loginList = loginRepository.findAll();
        assertThat(loginList).hasSize(databaseSizeBeforeCreate + 1);
        Login testLogin = loginList.get(loginList.size() - 1);
        assertThat(testLogin.getLoginType()).isEqualTo(DEFAULT_LOGIN_TYPE);
        assertThat(testLogin.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testLogin.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testLogin.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void createLoginWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = loginRepository.findAll().size();

        // Create the Login with an existing ID
        login.setId(1L);
        LoginDTO loginDTO = loginMapper.toDto(login);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLoginMockMvc.perform(post("/api/logins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Login in the database
        List<Login> loginList = loginRepository.findAll();
        assertThat(loginList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllLogins() throws Exception {
        // Initialize the database
        loginRepository.saveAndFlush(login);

        // Get all the loginList
        restLoginMockMvc.perform(get("/api/logins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(login.getId().intValue())))
            .andExpect(jsonPath("$.[*].loginType").value(hasItem(DEFAULT_LOGIN_TYPE.toString())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD.toString())));
    }

    @Test
    @Transactional
    public void getLogin() throws Exception {
        // Initialize the database
        loginRepository.saveAndFlush(login);

        // Get the login
        restLoginMockMvc.perform(get("/api/logins/{id}", login.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(login.getId().intValue()))
            .andExpect(jsonPath("$.loginType").value(DEFAULT_LOGIN_TYPE.toString()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLogin() throws Exception {
        // Get the login
        restLoginMockMvc.perform(get("/api/logins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogin() throws Exception {
        // Initialize the database
        loginRepository.saveAndFlush(login);
        int databaseSizeBeforeUpdate = loginRepository.findAll().size();

        // Update the login
        Login updatedLogin = loginRepository.findOne(login.getId());
        // Disconnect from session so that the updates on updatedLogin are not directly saved in db
        em.detach(updatedLogin);
        updatedLogin
            .loginType(UPDATED_LOGIN_TYPE)
            .username(UPDATED_USERNAME)
            .email(UPDATED_EMAIL)
            .password(UPDATED_PASSWORD);
        LoginDTO loginDTO = loginMapper.toDto(updatedLogin);

        restLoginMockMvc.perform(put("/api/logins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginDTO)))
            .andExpect(status().isOk());

        // Validate the Login in the database
        List<Login> loginList = loginRepository.findAll();
        assertThat(loginList).hasSize(databaseSizeBeforeUpdate);
        Login testLogin = loginList.get(loginList.size() - 1);
        assertThat(testLogin.getLoginType()).isEqualTo(UPDATED_LOGIN_TYPE);
        assertThat(testLogin.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testLogin.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testLogin.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingLogin() throws Exception {
        int databaseSizeBeforeUpdate = loginRepository.findAll().size();

        // Create the Login
        LoginDTO loginDTO = loginMapper.toDto(login);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLoginMockMvc.perform(put("/api/logins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(loginDTO)))
            .andExpect(status().isCreated());

        // Validate the Login in the database
        List<Login> loginList = loginRepository.findAll();
        assertThat(loginList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLogin() throws Exception {
        // Initialize the database
        loginRepository.saveAndFlush(login);
        int databaseSizeBeforeDelete = loginRepository.findAll().size();

        // Get the login
        restLoginMockMvc.perform(delete("/api/logins/{id}", login.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Login> loginList = loginRepository.findAll();
        assertThat(loginList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Login.class);
        Login login1 = new Login();
        login1.setId(1L);
        Login login2 = new Login();
        login2.setId(login1.getId());
        assertThat(login1).isEqualTo(login2);
        login2.setId(2L);
        assertThat(login1).isNotEqualTo(login2);
        login1.setId(null);
        assertThat(login1).isNotEqualTo(login2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LoginDTO.class);
        LoginDTO loginDTO1 = new LoginDTO();
        loginDTO1.setId(1L);
        LoginDTO loginDTO2 = new LoginDTO();
        assertThat(loginDTO1).isNotEqualTo(loginDTO2);
        loginDTO2.setId(loginDTO1.getId());
        assertThat(loginDTO1).isEqualTo(loginDTO2);
        loginDTO2.setId(2L);
        assertThat(loginDTO1).isNotEqualTo(loginDTO2);
        loginDTO1.setId(null);
        assertThat(loginDTO1).isNotEqualTo(loginDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(loginMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(loginMapper.fromId(null)).isNull();
    }
}
