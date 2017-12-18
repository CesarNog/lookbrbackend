package com.lookbr.backend.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the SocialMedia entity.
 */
public class SocialMediaDTO implements Serializable {

    private Long id;

    private String type;

    private String url;

    private Long consultantId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(Long consultantId) {
        this.consultantId = consultantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SocialMediaDTO socialMediaDTO = (SocialMediaDTO) o;
        if(socialMediaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), socialMediaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SocialMediaDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
