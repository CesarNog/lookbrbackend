package com.lookbr.backend.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Inspiration entity.
 */
public class InspirationDTO implements Serializable {

    private Long id;

    private String consultantName;

    private String consultantProfilePhotoURL;

    private String inspirationURL;

    private Integer page;

    private Long timelineId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }

    public String getConsultantProfilePhotoURL() {
        return consultantProfilePhotoURL;
    }

    public void setConsultantProfilePhotoURL(String consultantProfilePhotoURL) {
        this.consultantProfilePhotoURL = consultantProfilePhotoURL;
    }

    public String getInspirationURL() {
        return inspirationURL;
    }

    public void setInspirationURL(String inspirationURL) {
        this.inspirationURL = inspirationURL;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Long getTimelineId() {
        return timelineId;
    }

    public void setTimelineId(Long timelineId) {
        this.timelineId = timelineId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InspirationDTO inspirationDTO = (InspirationDTO) o;
        if(inspirationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inspirationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InspirationDTO{" +
            "id=" + getId() +
            ", consultantName='" + getConsultantName() + "'" +
            ", consultantProfilePhotoURL='" + getConsultantProfilePhotoURL() + "'" +
            ", inspirationURL='" + getInspirationURL() + "'" +
            ", page=" + getPage() +
            "}";
    }
}
