package com.lookbr.backend.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Look entity.
 */
public class LookDTO implements Serializable {

    private Long id;

    private String userId;

    private String temperature;

    private LocalDate dayTime;

    private Integer pictureIndex;

    private String url;

    private Long closetId;

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

    public Integer getPictureIndex() {
        return pictureIndex;
    }

    public void setPictureIndex(Integer pictureIndex) {
        this.pictureIndex = pictureIndex;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getClosetId() {
        return closetId;
    }

    public void setClosetId(Long closetId) {
        this.closetId = closetId;
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
            ", pictureIndex=" + getPictureIndex() +
            ", url='" + getUrl() + "'" +
            "}";
    }
}
