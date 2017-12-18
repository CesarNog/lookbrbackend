package com.lookbr.backend.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the ConsultantVote entity.
 */
public class ConsultantVoteDTO implements Serializable {

    private Long id;

    private String consultantProfilePhotoUrl;

    private Long lookId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsultantProfilePhotoUrl() {
        return consultantProfilePhotoUrl;
    }

    public void setConsultantProfilePhotoUrl(String consultantProfilePhotoUrl) {
        this.consultantProfilePhotoUrl = consultantProfilePhotoUrl;
    }

    public Long getLookId() {
        return lookId;
    }

    public void setLookId(Long lookId) {
        this.lookId = lookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConsultantVoteDTO consultantVoteDTO = (ConsultantVoteDTO) o;
        if(consultantVoteDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultantVoteDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsultantVoteDTO{" +
            "id=" + getId() +
            ", consultantProfilePhotoUrl='" + getConsultantProfilePhotoUrl() + "'" +
            "}";
    }
}
