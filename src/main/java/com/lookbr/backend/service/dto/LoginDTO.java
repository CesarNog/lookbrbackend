package com.lookbr.backend.service.dto;


import java.io.Serializable;
import java.util.Objects;
import com.lookbr.backend.domain.enumeration.LoginType;

/**
 * A DTO for the Login entity.
 */
public class LoginDTO implements Serializable {

    private Long id;

    private LoginType loginType;

    private String username;

    private String email;

    private String password;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
            ", username='" + getUsername() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }
}
