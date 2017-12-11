package com.lookbr.backend.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A ConsultantVote.
 */
@Entity
@Table(name = "consultant_vote")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "consultantvote")
public class ConsultantVote implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "consultant_profile_photo_url")
    private String consultantProfilePhotoUrl;

    @ManyToOne
    private Look look;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsultantProfilePhotoUrl() {
        return consultantProfilePhotoUrl;
    }

    public ConsultantVote consultantProfilePhotoUrl(String consultantProfilePhotoUrl) {
        this.consultantProfilePhotoUrl = consultantProfilePhotoUrl;
        return this;
    }

    public void setConsultantProfilePhotoUrl(String consultantProfilePhotoUrl) {
        this.consultantProfilePhotoUrl = consultantProfilePhotoUrl;
    }

    public Look getLook() {
        return look;
    }

    public ConsultantVote look(Look look) {
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
        ConsultantVote consultantVote = (ConsultantVote) o;
        if (consultantVote.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultantVote.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsultantVote{" +
            "id=" + getId() +
            ", consultantProfilePhotoUrl='" + getConsultantProfilePhotoUrl() + "'" +
            "}";
    }
}
