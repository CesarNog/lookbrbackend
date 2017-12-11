package com.lookbr.backend.repository;

import com.lookbr.backend.domain.Inspiration;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Inspiration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InspirationRepository extends JpaRepository<Inspiration, Long> {

}
