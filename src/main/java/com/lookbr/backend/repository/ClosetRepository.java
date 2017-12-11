package com.lookbr.backend.repository;

import com.lookbr.backend.domain.Closet;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Closet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClosetRepository extends JpaRepository<Closet, Long> {

}
