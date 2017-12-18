package com.lookbr.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Inspiration.
 */
@Entity
@Table(name = "inspiration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Inspiration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "consultant_name")
    private String consultantName;

    @Column(name = "consultant_profile_photo_url")
    private String consultantProfilePhotoURL;

    @Column(name = "inspiration_url")
    private String inspirationURL;

    @Column(name = "page")
    private Integer page;

    @ManyToOne
    private Timeline timeline;

    @OneToMany(mappedBy = "inspiration")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Intention> intentions = new HashSet<>();

    @OneToMany(mappedBy = "inspiration")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Occasion> occasions = new HashSet<>();

    @OneToMany(mappedBy = "inspiration")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Temperature> temperatures = new HashSet<>();

    @OneToMany(mappedBy = "inspiration")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DayTime> dayTimes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConsultantName() {
        return consultantName;
    }

    public Inspiration consultantName(String consultantName) {
        this.consultantName = consultantName;
        return this;
    }

    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }

    public String getConsultantProfilePhotoURL() {
        return consultantProfilePhotoURL;
    }

    public Inspiration consultantProfilePhotoURL(String consultantProfilePhotoURL) {
        this.consultantProfilePhotoURL = consultantProfilePhotoURL;
        return this;
    }

    public void setConsultantProfilePhotoURL(String consultantProfilePhotoURL) {
        this.consultantProfilePhotoURL = consultantProfilePhotoURL;
    }

    public String getInspirationURL() {
        return inspirationURL;
    }

    public Inspiration inspirationURL(String inspirationURL) {
        this.inspirationURL = inspirationURL;
        return this;
    }

    public void setInspirationURL(String inspirationURL) {
        this.inspirationURL = inspirationURL;
    }

    public Integer getPage() {
        return page;
    }

    public Inspiration page(Integer page) {
        this.page = page;
        return this;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public Inspiration timeline(Timeline timeline) {
        this.timeline = timeline;
        return this;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public Set<Intention> getIntentions() {
        return intentions;
    }

    public Inspiration intentions(Set<Intention> intentions) {
        this.intentions = intentions;
        return this;
    }

    public Inspiration addIntentions(Intention intention) {
        this.intentions.add(intention);
        intention.setInspiration(this);
        return this;
    }

    public Inspiration removeIntentions(Intention intention) {
        this.intentions.remove(intention);
        intention.setInspiration(null);
        return this;
    }

    public void setIntentions(Set<Intention> intentions) {
        this.intentions = intentions;
    }

    public Set<Occasion> getOccasions() {
        return occasions;
    }

    public Inspiration occasions(Set<Occasion> occasions) {
        this.occasions = occasions;
        return this;
    }

    public Inspiration addOccasions(Occasion occasion) {
        this.occasions.add(occasion);
        occasion.setInspiration(this);
        return this;
    }

    public Inspiration removeOccasions(Occasion occasion) {
        this.occasions.remove(occasion);
        occasion.setInspiration(null);
        return this;
    }

    public void setOccasions(Set<Occasion> occasions) {
        this.occasions = occasions;
    }

    public Set<Temperature> getTemperatures() {
        return temperatures;
    }

    public Inspiration temperatures(Set<Temperature> temperatures) {
        this.temperatures = temperatures;
        return this;
    }

    public Inspiration addTemperature(Temperature temperature) {
        this.temperatures.add(temperature);
        temperature.setInspiration(this);
        return this;
    }

    public Inspiration removeTemperature(Temperature temperature) {
        this.temperatures.remove(temperature);
        temperature.setInspiration(null);
        return this;
    }

    public void setTemperatures(Set<Temperature> temperatures) {
        this.temperatures = temperatures;
    }

    public Set<DayTime> getDayTimes() {
        return dayTimes;
    }

    public Inspiration dayTimes(Set<DayTime> dayTimes) {
        this.dayTimes = dayTimes;
        return this;
    }

    public Inspiration addDayTime(DayTime dayTime) {
        this.dayTimes.add(dayTime);
        dayTime.setInspiration(this);
        return this;
    }

    public Inspiration removeDayTime(DayTime dayTime) {
        this.dayTimes.remove(dayTime);
        dayTime.setInspiration(null);
        return this;
    }

    public void setDayTimes(Set<DayTime> dayTimes) {
        this.dayTimes = dayTimes;
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
        Inspiration inspiration = (Inspiration) o;
        if (inspiration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inspiration.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Inspiration{" +
            "id=" + getId() +
            ", consultantName='" + getConsultantName() + "'" +
            ", consultantProfilePhotoURL='" + getConsultantProfilePhotoURL() + "'" +
            ", inspirationURL='" + getInspirationURL() + "'" +
            ", page=" + getPage() +
            "}";
    }
}
