package com.lookbr.backend.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Comment entity.
 */
public class CommentDTO implements Serializable {

    private Long id;

    private String comment;

    private String consultantProfilePhoto;

    private String consultantName;

    private LocalDate dateVoted;

    private Long lookId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getConsultantProfilePhoto() {
        return consultantProfilePhoto;
    }

    public void setConsultantProfilePhoto(String consultantProfilePhoto) {
        this.consultantProfilePhoto = consultantProfilePhoto;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }

    public LocalDate getDateVoted() {
        return dateVoted;
    }

    public void setDateVoted(LocalDate dateVoted) {
        this.dateVoted = dateVoted;
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

        CommentDTO commentDTO = (CommentDTO) o;
        if(commentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), commentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", consultantProfilePhoto='" + getConsultantProfilePhoto() + "'" +
            ", consultantName='" + getConsultantName() + "'" +
            ", dateVoted='" + getDateVoted() + "'" +
            "}";
    }
}
