package com.lookbr.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Look.
 */
@Entity
@Table(name = "look")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "look")
public class Look implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "temperature")
    private String temperature;

    @Column(name = "day_time")
    private LocalDate dayTime;

    @Column(name = "picture_index")
    private Integer pictureIndex;

    @Column(name = "url")
    private String url;

    @OneToMany(mappedBy = "look")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ConsultantVote> consultantsVotes = new HashSet<>();

    @OneToMany(mappedBy = "look")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "look")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Intention> intentions = new HashSet<>();

    @OneToMany(mappedBy = "look")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Occasion> occasions = new HashSet<>();

    @OneToMany(mappedBy = "look")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Temperature> temperatures = new HashSet<>();

    @OneToMany(mappedBy = "look")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Consultant> consultants = new HashSet<>();

    @ManyToOne
    private Closet closet;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public Look userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTemperature() {
        return temperature;
    }

    public Look temperature(String temperature) {
        this.temperature = temperature;
        return this;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public LocalDate getDayTime() {
        return dayTime;
    }

    public Look dayTime(LocalDate dayTime) {
        this.dayTime = dayTime;
        return this;
    }

    public void setDayTime(LocalDate dayTime) {
        this.dayTime = dayTime;
    }

    public Integer getPictureIndex() {
        return pictureIndex;
    }

    public Look pictureIndex(Integer pictureIndex) {
        this.pictureIndex = pictureIndex;
        return this;
    }

    public void setPictureIndex(Integer pictureIndex) {
        this.pictureIndex = pictureIndex;
    }

    public String getUrl() {
        return url;
    }

    public Look url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<ConsultantVote> getConsultantsVotes() {
        return consultantsVotes;
    }

    public Look consultantsVotes(Set<ConsultantVote> consultantVotes) {
        this.consultantsVotes = consultantVotes;
        return this;
    }

    public Look addConsultantsVotes(ConsultantVote consultantVote) {
        this.consultantsVotes.add(consultantVote);
        consultantVote.setLook(this);
        return this;
    }

    public Look removeConsultantsVotes(ConsultantVote consultantVote) {
        this.consultantsVotes.remove(consultantVote);
        consultantVote.setLook(null);
        return this;
    }

    public void setConsultantsVotes(Set<ConsultantVote> consultantVotes) {
        this.consultantsVotes = consultantVotes;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Look comments(Set<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public Look addComments(Comment comment) {
        this.comments.add(comment);
        comment.setLook(this);
        return this;
    }

    public Look removeComments(Comment comment) {
        this.comments.remove(comment);
        comment.setLook(null);
        return this;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Intention> getIntentions() {
        return intentions;
    }

    public Look intentions(Set<Intention> intentions) {
        this.intentions = intentions;
        return this;
    }

    public Look addIntentions(Intention intention) {
        this.intentions.add(intention);
        intention.setLook(this);
        return this;
    }

    public Look removeIntentions(Intention intention) {
        this.intentions.remove(intention);
        intention.setLook(null);
        return this;
    }

    public void setIntentions(Set<Intention> intentions) {
        this.intentions = intentions;
    }

    public Set<Occasion> getOccasions() {
        return occasions;
    }

    public Look occasions(Set<Occasion> occasions) {
        this.occasions = occasions;
        return this;
    }

    public Look addOccasions(Occasion occasion) {
        this.occasions.add(occasion);
        occasion.setLook(this);
        return this;
    }

    public Look removeOccasions(Occasion occasion) {
        this.occasions.remove(occasion);
        occasion.setLook(null);
        return this;
    }

    public void setOccasions(Set<Occasion> occasions) {
        this.occasions = occasions;
    }

    public Set<Temperature> getTemperatures() {
        return temperatures;
    }

    public Look temperatures(Set<Temperature> temperatures) {
        this.temperatures = temperatures;
        return this;
    }

    public Look addTemperature(Temperature temperature) {
        this.temperatures.add(temperature);
        temperature.setLook(this);
        return this;
    }

    public Look removeTemperature(Temperature temperature) {
        this.temperatures.remove(temperature);
        temperature.setLook(null);
        return this;
    }

    public void setTemperatures(Set<Temperature> temperatures) {
        this.temperatures = temperatures;
    }

    public Set<Consultant> getConsultants() {
        return consultants;
    }

    public Look consultants(Set<Consultant> consultants) {
        this.consultants = consultants;
        return this;
    }

    public Look addConsultants(Consultant consultant) {
        this.consultants.add(consultant);
        consultant.setLook(this);
        return this;
    }

    public Look removeConsultants(Consultant consultant) {
        this.consultants.remove(consultant);
        consultant.setLook(null);
        return this;
    }

    public void setConsultants(Set<Consultant> consultants) {
        this.consultants = consultants;
    }

    public Closet getCloset() {
        return closet;
    }

    public Look closet(Closet closet) {
        this.closet = closet;
        return this;
    }

    public void setCloset(Closet closet) {
        this.closet = closet;
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
        Look look = (Look) o;
        if (look.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), look.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Look{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", temperature='" + getTemperature() + "'" +
            ", dayTime='" + getDayTime() + "'" +
            ", pictureIndex=" + getPictureIndex() +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
