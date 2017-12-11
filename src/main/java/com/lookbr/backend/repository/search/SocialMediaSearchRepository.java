package com.lookbr.backend.repository.search;

import com.lookbr.backend.domain.SocialMedia;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SocialMedia entity.
 */
public interface SocialMediaSearchRepository extends ElasticsearchRepository<SocialMedia, Long> {
}
