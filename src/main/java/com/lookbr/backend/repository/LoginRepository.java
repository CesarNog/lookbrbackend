package com.lookbr.backend.repository;

import com.lookbr.backend.domain.Login;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Login entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LoginRepository extends JpaRepository<Login, Long> {

}
