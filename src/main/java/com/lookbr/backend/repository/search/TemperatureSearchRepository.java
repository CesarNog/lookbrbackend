package com.lookbr.backend.repository.search;

import com.lookbr.backend.domain.Temperature;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Temperature entity.
 */
public interface TemperatureSearchRepository extends ElasticsearchRepository<Temperature, Long> {
}
