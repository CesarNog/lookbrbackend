package com.lookbr.backend.repository.search;

import com.lookbr.backend.domain.ConsultantVote;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ConsultantVote entity.
 */
public interface ConsultantVoteSearchRepository extends ElasticsearchRepository<ConsultantVote, Long> {
}
