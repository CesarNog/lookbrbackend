package com.lookbr.backend.repository.search;

import com.lookbr.backend.domain.Look;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Look entity.
 */
public interface LookSearchRepository extends ElasticsearchRepository<Look, Long> {
}
