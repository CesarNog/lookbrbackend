package com.lookbr.backend.repository.search;

import com.lookbr.backend.domain.Login;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Login entity.
 */
public interface LoginSearchRepository extends ElasticsearchRepository<Login, Long> {
}
