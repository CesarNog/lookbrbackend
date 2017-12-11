package com.lookbr.backend.repository.search;

import com.lookbr.backend.domain.Signup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Signup entity.
 */
public interface SignupSearchRepository extends ElasticsearchRepository<Signup, Long> {
}
