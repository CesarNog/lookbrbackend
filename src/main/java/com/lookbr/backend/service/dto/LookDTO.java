package com.lookbr.backend.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Look entity.
 */
public class LookDTO implements Serializable {

    private Long id;

    private String userId;

    private String temperature;

    private LocalDate dayTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public LocalDate getDayTime() {
        return dayTime;
    }

    public void setDayTime(LocalDate dayTime) {
        this.dayTime = dayTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LookDTO lookDTO = (LookDTO) o;
        if(lookDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lookDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LookDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", temperature='" + getTemperature() + "'" +
            ", dayTime='" + getDayTime() + "'" +
            "}";
    }
}
