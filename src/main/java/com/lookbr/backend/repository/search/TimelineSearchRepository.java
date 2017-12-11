package com.lookbr.backend.repository.search;

import com.lookbr.backend.domain.Timeline;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Timeline entity.
 */
public interface TimelineSearchRepository extends ElasticsearchRepository<Timeline, Long> {
}
