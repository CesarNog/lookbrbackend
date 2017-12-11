package com.lookbr.backend.repository.search;

import com.lookbr.backend.domain.Intention;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Intention entity.
 */
public interface IntentionSearchRepository extends ElasticsearchRepository<Intention, Long> {
}
