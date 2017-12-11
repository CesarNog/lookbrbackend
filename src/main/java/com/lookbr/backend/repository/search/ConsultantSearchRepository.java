package com.lookbr.backend.repository.search;

import com.lookbr.backend.domain.Consultant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Consultant entity.
 */
public interface ConsultantSearchRepository extends ElasticsearchRepository<Consultant, Long> {
}
