package com.lookbr.backend.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import com.lookbr.backend.domain.enumeration.Status;

/**
 * A DTO for the Consultant entity.
 */
public class ConsultantDTO implements Serializable {

    private Long id;

    private String consultantId;

    private String consultantName;

    private String consultantDescription;

    private String consultantCoverPhotoURL;

    private String consultantProfilePhotoURL;

    private Integer charge;

    private String inspirationURL;

    private String profilePhoto;

    private Status status;

    private Integer page;

    private Long lookId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(String consultantId) {
        this.consultantId = consultantId;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }

    public String getConsultantDescription() {
        return consultantDescription;
    }

    public void setConsultantDescription(String consultantDescription) {
        this.consultantDescription = consultantDescription;
    }

    public String getConsultantCoverPhotoURL() {
        return consultantCoverPhotoURL;
    }

    public void setConsultantCoverPhotoURL(String consultantCoverPhotoURL) {
        this.consultantCoverPhotoURL = consultantCoverPhotoURL;
    }

    public String getConsultantProfilePhotoURL() {
        return consultantProfilePhotoURL;
    }

    public void setConsultantProfilePhotoURL(String consultantProfilePhotoURL) {
        this.consultantProfilePhotoURL = consultantProfilePhotoURL;
    }

    public Integer getCharge() {
        return charge;
    }

    public void setCharge(Integer charge) {
        this.charge = charge;
    }

    public String getInspirationURL() {
        return inspirationURL;
    }

    public void setInspirationURL(String inspirationURL) {
        this.inspirationURL = inspirationURL;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
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

        ConsultantDTO consultantDTO = (ConsultantDTO) o;
        if(consultantDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultantDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsultantDTO{" +
            "id=" + getId() +
            ", consultantId='" + getConsultantId() + "'" +
            ", consultantName='" + getConsultantName() + "'" +
            ", consultantDescription='" + getConsultantDescription() + "'" +
            ", consultantCoverPhotoURL='" + getConsultantCoverPhotoURL() + "'" +
            ", consultantProfilePhotoURL='" + getConsultantProfilePhotoURL() + "'" +
            ", charge=" + getCharge() +
            ", inspirationURL='" + getInspirationURL() + "'" +
            ", profilePhoto='" + getProfilePhoto() + "'" +
            ", status='" + getStatus() + "'" +
            ", page=" + getPage() +
            "}";
    }
}
