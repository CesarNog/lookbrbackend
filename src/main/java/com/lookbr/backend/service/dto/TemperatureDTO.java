package com.lookbr.backend.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Temperature entity.
 */
public class TemperatureDTO implements Serializable {

    private Long id;

    private String value;

    private String type;

    private Long inspirationId;

    private Long lookId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getInspirationId() {
        return inspirationId;
    }

    public void setInspirationId(Long inspirationId) {
        this.inspirationId = inspirationId;
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

        TemperatureDTO temperatureDTO = (TemperatureDTO) o;
        if(temperatureDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), temperatureDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TemperatureDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
