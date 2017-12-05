package com.lookbr.backend.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Consultants entity.
 */
public class ConsultantsDTO implements Serializable {

    private Long id;

    private Integer page;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConsultantsDTO consultantsDTO = (ConsultantsDTO) o;
        if(consultantsDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), consultantsDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConsultantsDTO{" +
            "id=" + getId() +
            ", page=" + getPage() +
            "}";
    }
}
