package com.lookbr.backend.repository;

import com.lookbr.backend.domain.Intention;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Intention entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntentionRepository extends JpaRepository<Intention, Long> {

}
