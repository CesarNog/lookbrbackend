package com.lookbr.backend.repository;

import com.lookbr.backend.domain.Signup;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Signup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SignupRepository extends JpaRepository<Signup, Long> {

}
