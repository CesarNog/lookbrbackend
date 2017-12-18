package com.lookbr.backend.service.dto;


import java.io.Serializable;
import java.util.Objects;
import com.lookbr.backend.domain.enumeration.LoginType;

/**
 * A DTO for the Signup entity.
 */
public class SignupDTO implements Serializable {

    private Long id;

    private String email;

    private LoginType loginType;

    private String password;

    private String profilePhotoUrl;

    private String profilePhoto;

    private String username;

    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SignupDTO signupDTO = (SignupDTO) o;
        if(signupDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), signupDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SignupDTO{" +
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
