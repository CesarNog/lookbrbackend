package com.lookbr.backend.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.lookbr.backend.domain.enumeration.LoginType;

/**
 * A DTO for the Login entity.
 */
public class LoginDTO implements Serializable {

    private Long id;

    private LoginType loginType;

    private String token;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LoginDTO loginDTO = (LoginDTO) o;
        if(loginDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), loginDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
            "id=" + getId() +
            ", loginType='" + getLoginType() + "'" +
            ", token='" + getToken() + "'" +
            "}";
    }
}
