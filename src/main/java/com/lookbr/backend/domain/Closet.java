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
 * A Closet.
 */
@Entity
@Table(name = "closet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Closet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "page")
    private Integer page;

    @OneToMany(mappedBy = "closet")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Look> looks = new HashSet<>();

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

    public Closet page(Integer page) {
        this.page = page;
        return this;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Set<Look> getLooks() {
        return looks;
    }

    public Closet looks(Set<Look> looks) {
        this.looks = looks;
        return this;
    }

    public Closet addLook(Look look) {
        this.looks.add(look);
        look.setCloset(this);
        return this;
    }

    public Closet removeLook(Look look) {
        this.looks.remove(look);
        look.setCloset(null);
        return this;
    }

    public void setLooks(Set<Look> looks) {
        this.looks = looks;
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
        Closet closet = (Closet) o;
        if (closet.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), closet.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Closet{" +
            "id=" + getId() +
            ", page=" + getPage() +
            "}";
    }
}
