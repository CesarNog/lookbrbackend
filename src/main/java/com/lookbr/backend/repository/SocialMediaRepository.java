package com.lookbr.backend.repository;

import com.lookbr.backend.domain.SocialMedia;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SocialMedia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocialMediaRepository extends JpaRepository<SocialMedia, Long> {

}
