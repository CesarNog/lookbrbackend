package com.lookbr.backend.repository.search;

import com.lookbr.backend.domain.Occasion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Occasion entity.
 */
public interface OccasionSearchRepository extends ElasticsearchRepository<Occasion, Long> {
}
