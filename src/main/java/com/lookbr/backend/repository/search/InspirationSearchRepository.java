package com.lookbr.backend.repository.search;

import com.lookbr.backend.domain.Inspiration;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Inspiration entity.
 */
public interface InspirationSearchRepository extends ElasticsearchRepository<Inspiration, Long> {
}
