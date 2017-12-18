package com.lookbr.backend.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


/**
 * A Comment.
 */
@Entity
@Table(name = "comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "jhi_comment")
    private String comment;

    @Column(name = "consultant_profile_photo")
    private String consultantProfilePhoto;

    @Column(name = "consultant_name")
    private String consultantName;

    @Column(name = "date_voted")
    private LocalDate dateVoted;

    @ManyToOne
    private Look look;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public Comment comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getConsultantProfilePhoto() {
        return consultantProfilePhoto;
    }

    public Comment consultantProfilePhoto(String consultantProfilePhoto) {
        this.consultantProfilePhoto = consultantProfilePhoto;
        return this;
    }

    public void setConsultantProfilePhoto(String consultantProfilePhoto) {
        this.consultantProfilePhoto = consultantProfilePhoto;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public Comment consultantName(String consultantName) {
        this.consultantName = consultantName;
        return this;
    }

    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }

    public LocalDate getDateVoted() {
        return dateVoted;
    }

    public Comment dateVoted(LocalDate dateVoted) {
        this.dateVoted = dateVoted;
        return this;
    }

    public void setDateVoted(LocalDate dateVoted) {
        this.dateVoted = dateVoted;
    }

    public Look getLook() {
        return look;
    }

    public Comment look(Look look) {
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
        Comment comment = (Comment) o;
        if (comment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Comment{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", consultantProfilePhoto='" + getConsultantProfilePhoto() + "'" +
            ", consultantName='" + getConsultantName() + "'" +
            ", dateVoted='" + getDateVoted() + "'" +
            "}";
    }
}
