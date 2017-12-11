package com.lookbr.backend.repository.search;

import com.lookbr.backend.domain.DayTime;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DayTime entity.
 */
public interface DayTimeSearchRepository extends ElasticsearchRepository<DayTime, Long> {
}
