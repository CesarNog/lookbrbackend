package com.lookbr.backend.repository;

import com.lookbr.backend.domain.ConsultantVote;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ConsultantVote entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConsultantVoteRepository extends JpaRepository<ConsultantVote, Long> {

}
