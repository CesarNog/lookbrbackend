package com.lookbr.backend.repository.search;

import com.lookbr.backend.domain.Closet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Closet entity.
 */
public interface ClosetSearchRepository extends ElasticsearchRepository<Closet, Long> {
}
