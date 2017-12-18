package com.lookbr.backend.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A Intention.
 */
@Entity
@Table(name = "intention")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Intention implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "page")
    private Integer page;

    @ManyToOne
    private Inspiration inspiration;

    @ManyToOne
    private Look look;

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

    public Intention page(Integer page) {
        this.page = page;
        return this;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Inspiration getInspiration() {
        return inspiration;
    }

    public Intention inspiration(Inspiration inspiration) {
        this.inspiration = inspiration;
        return this;
    }

    public void setInspiration(Inspiration inspiration) {
        this.inspiration = inspiration;
    }

    public Look getLook() {
        return look;
    }

    public Intention look(Look look) {
        this.look = look;
        return this;
    }

    public void setLook(Look look) {
        this.look = look;
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
        Intention intention = (Intention) o;
        if (intention.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), intention.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Intention{" +
            "id=" + getId() +
            ", page=" + getPage() +
            "}";
    }
}
