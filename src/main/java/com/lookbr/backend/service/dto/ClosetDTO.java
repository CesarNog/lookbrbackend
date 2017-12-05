package com.lookbr.backend.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Closet entity.
 */
public class ClosetDTO implements Serializable {

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

        ClosetDTO closetDTO = (ClosetDTO) o;
        if(closetDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), closetDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ClosetDTO{" +
            "id=" + getId() +
            ", page=" + getPage() +
            "}";
    }
}
