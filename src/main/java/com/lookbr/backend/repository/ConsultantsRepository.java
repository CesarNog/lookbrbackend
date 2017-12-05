package com.lookbr.backend.repository;

import com.lookbr.backend.domain.Consultants;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Consultants entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultantsRepository extends JpaRepository<Consultants, Long> {

}
