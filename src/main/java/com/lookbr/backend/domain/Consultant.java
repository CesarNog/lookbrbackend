package com.lookbr.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.lookbr.backend.domain.enumeration.Status;


/**
 * A Consultant.
 */
@Entity
@Table(name = "consultant")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Consultant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "consultant_id")
    private String consultantId;

    @Column(name = "consultant_name")
    private String consultantName;

    @Column(name = "consultant_description")
    private String consultantDescription;

    @Column(name = "consultant_cover_photo_url")
    private String consultantCoverPhotoURL;

    @Column(name = "consultant_profile_photo_url")
    private String consultantProfilePhotoURL;

    @Column(name = "charge")
    private Integer charge;

    @Column(name = "inspiration_url")
    private String inspirationURL;

    @Column(name = "profile_photo")
    private String profilePhoto;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "page")
    private Integer page;

    @OneToMany(mappedBy = "consultant")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<SocialMedia> socialMedias = new HashSet<>();

    @ManyToOne
    private Look look;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsultantId() {
        return consultantId;
    }

    public Consultant consultantId(String consultantId) {
        this.consultantId = consultantId;
        return this;
    }

    public void setConsultantId(String consultantId) {
        this.consultantId = consultantId;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public Consultant consultantName(String consultantName) {
        this.consultantName = consultantName;
        return this;
    }

    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }

    public String getConsultantDescription() {
        return consultantDescription;
    }

    public Consultant consultantDescription(String consultantDescription) {
        this.consultantDescription = consultantDescription;
        return this;
    }

    public void setConsultantDescription(String consultantDescription) {
        this.consultantDescription = consultantDescription;
    }

    public String getConsultantCoverPhotoURL() {
        return consultantCoverPhotoURL;
    }

    public Consultant consultantCoverPhotoURL(String consultantCoverPhotoURL) {
        this.consultantCoverPhotoURL = consultantCoverPhotoURL;
        return this;
    }

    public void setConsultantCoverPhotoURL(String consultantCoverPhotoURL) {
        this.consultantCoverPhotoURL = consultantCoverPhotoURL;
    }

    public String getConsultantProfilePhotoURL() {
        return consultantProfilePhotoURL;
    }

    public Consultant consultantProfilePhotoURL(String consultantProfilePhotoURL) {
        this.consultantProfilePhotoURL = consultantProfilePhotoURL;
        return this;
    }

    public void setConsultantProfilePhotoURL(String consultantProfilePhotoURL) {
        this.consultantProfilePhotoURL = consultantProfilePhotoURL;
    }

    public Integer getCharge() {
        return charge;
    }

    public Consultant charge(Integer charge) {
        this.charge = charge;
        return this;
    }

    public void setCharge(Integer charge) {
        this.charge = charge;
    }

    public String getInspirationURL() {
        return inspirationURL;
    }

    public Consultant inspirationURL(String inspirationURL) {
        this.inspirationURL = inspirationURL;
        return this;
    }

    public void setInspirationURL(String inspirationURL) {
        this.inspirationURL = inspirationURL;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public Consultant profilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
        return this;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public Status getStatus() {
        return status;
    }

    public Consultant status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getPage() {
        return page;
    }

    public Consultant page(Integer page) {
        this.page = page;
        return this;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Set<SocialMedia> getSocialMedias() {
        return socialMedias;
    }

    public Consultant socialMedias(Set<SocialMedia> socialMedias) {
        this.socialMedias = socialMedias;
        return this;
    }

    public Consultant addSocialMedias(SocialMedia socialMedia) {
        this.socialMedias.add(socialMedia);
        socialMedia.setConsultant(this);
        return this;
    }

    public Consultant removeSocialMedias(SocialMedia socialMedia) {
        this.socialMedias.remove(socialMedia);
        socialMedia.setConsultant(null);
        return this;
    }

    public void setSocialMedias(Set<SocialMedia> socialMedias) {
        this.socialMedias = socialMedias;
    }

    public Look getLook() {
        return look;
    }

    public Consultant look(Look look) {
        this.look = look;
        return this;
    }

    public void setLook(Look look) {
        this.look = look;
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
        Consultant consultant = (Consultant) o;
        if (consultant.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultant.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Consultant{" +
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
