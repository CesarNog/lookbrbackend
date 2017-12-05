package com.lookbr.backend.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


/**
 * A Look.
 */
@Entity
@Table(name = "look")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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
            "}";
    }
}
