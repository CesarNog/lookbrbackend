package com.lookbr.backend.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import com.lookbr.backend.domain.enumeration.LoginType;


/**
 * A Signup.
 */
@Entity
@Table(name = "signup")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "signup")
public class Signup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "login_type")
    private LoginType loginType;

    @Column(name = "jhi_password")
    private String password;

    @Column(name = "profile_photo_url")
    private String profilePhotoUrl;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @Column(name = "username")
    private String username;

    @Column(name = "token")
    private String token;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public Signup email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public Signup loginType(LoginType loginType) {
        this.loginType = loginType;
        return this;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getPassword() {
        return password;
    }

    public Signup password(String password) {
        this.password = password;
        return this;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public Signup profilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
        return this;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public Signup profilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
        return this;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getUsername() {
        return username;
    }

    public Signup username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public Signup token(String token) {
        this.token = token;
        return this;
    }

    public void setToken(String token) {
        this.token = token;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Signup signup = (Signup) o;
        if (signup.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), signup.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Signup{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", loginType='" + getLoginType() + "'" +
            ", password='" + getPassword() + "'" +
            ", profilePhotoUrl='" + getProfilePhotoUrl() + "'" +
            ", profilePhoto='" + getProfilePhoto() + "'" +
            ", username='" + getUsername() + "'" +
            ", token='" + getToken() + "'" +
            "}";
    }
}
