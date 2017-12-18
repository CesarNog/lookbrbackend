package com.lookbr.backend.repository;

import com.lookbr.backend.domain.DayTime;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DayTime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DayTimeRepository extends JpaRepository<DayTime, Long> {

}
