package com.lookbr.backend.repository;

import com.lookbr.backend.domain.Occasion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Occasion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OccasionRepository extends JpaRepository<Occasion, Long> {

}
