package com.lookbr.backend.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache("users", jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.SocialUserConnection.class.getName(), jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Login.class.getName(), jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Signup.class.getName(), jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Timeline.class.getName(), jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Timeline.class.getName() + ".inspirations", jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Consultant.class.getName(), jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Consultant.class.getName() + ".socialMedias", jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.SocialMedia.class.getName(), jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Intention.class.getName(), jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Inspiration.class.getName(), jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Inspiration.class.getName() + ".intentions", jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Inspiration.class.getName() + ".occasions", jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Inspiration.class.getName() + ".temperatures", jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Inspiration.class.getName() + ".dayTimes", jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Occasion.class.getName(), jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Look.class.getName(), jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Look.class.getName() + ".consultantsVotes", jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Look.class.getName() + ".comments", jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Look.class.getName() + ".intentions", jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Look.class.getName() + ".occasions", jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Look.class.getName() + ".temperatures", jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Look.class.getName() + ".consultants", jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Comment.class.getName(), jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.ConsultantVote.class.getName(), jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Closet.class.getName(), jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Closet.class.getName() + ".looks", jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.Temperature.class.getName(), jcacheConfiguration);
            cm.createCache(com.lookbr.backend.domain.DayTime.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
