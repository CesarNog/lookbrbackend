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
 * A Timeline.
 */
@Entity
@Table(name = "timeline")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Timeline implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "page")
    private Integer page;

    @OneToMany(mappedBy = "timeline")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Inspiration> inspirations = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public Timeline page(Integer page) {
        this.page = page;
        return this;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Set<Inspiration> getInspirations() {
        return inspirations;
    }

    public Timeline inspirations(Set<Inspiration> inspirations) {
        this.inspirations = inspirations;
        return this;
    }

    public Timeline addInspirations(Inspiration inspiration) {
        this.inspirations.add(inspiration);
        inspiration.setTimeline(this);
        return this;
    }

    public Timeline removeInspirations(Inspiration inspiration) {
        this.inspirations.remove(inspiration);
        inspiration.setTimeline(null);
        return this;
    }

    public void setInspirations(Set<Inspiration> inspirations) {
        this.inspirations = inspirations;
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
        Timeline timeline = (Timeline) o;
        if (timeline.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), timeline.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Timeline{" +
            "id=" + getId() +
            ", page=" + getPage() +
            "}";
    }
}
