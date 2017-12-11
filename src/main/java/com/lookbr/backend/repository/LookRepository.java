package com.lookbr.backend.repository;

import com.lookbr.backend.domain.Look;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Look entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LookRepository extends JpaRepository<Look, Long> {

}
