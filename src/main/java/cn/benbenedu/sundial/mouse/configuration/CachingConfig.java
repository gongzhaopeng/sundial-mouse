package cn.benbenedu.sundial.mouse.configuration;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.*;

@Configuration
@EnableCaching
@EnableConfigurationProperties({
        CacheProperties.class,
        CustomRedisCacheProperties.class
})
public class CachingConfig {

    @Bean
    public RedisCacheManager redisCacheManager(
            RedisConnectionFactory redisConnectionFactory,
            CacheProperties cacheProperties,
            ObjectMapper objectMapper,
            CustomRedisCacheProperties customRedisCacheProperties) {

        final var defaultConfiguration = defaultRedisCacheConfiguration(
                cacheProperties, objectMapper);

        final var builder = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultConfiguration);
        final var cacheNames = cacheProperties.getCacheNames();
        if (!cacheNames.isEmpty()) {
            builder.initialCacheNames(Set.copyOf(cacheNames));
        }

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        Optional.ofNullable(customRedisCacheProperties)
                .map(CustomRedisCacheProperties::getCaches)
                .ifPresent(caches -> caches.forEach((key, prop) ->
                        cacheConfigurations.put(
                                key,
                                handleRedisCacheConfiguration(prop, defaultConfiguration))
                ));
        builder.withInitialCacheConfigurations(cacheConfigurations);

        builder.disableCreateOnMissingCache();

        return builder.build();
    }

    private RedisCacheConfiguration defaultRedisCacheConfiguration(
            CacheProperties cacheProperties,
            ObjectMapper objectMapper) {

        final var redisProperties = cacheProperties.getRedis();

        var config = RedisCacheConfiguration.defaultCacheConfig();

        config = config.serializeKeysWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new StringRedisSerializer()));

        final var jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(Object.class);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        config = config.serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(getJackson2JsonRedisSerializer()));

        return handleRedisCacheConfiguration(redisProperties, config);
    }

    private Jackson2JsonRedisSerializer<Object> getJackson2JsonRedisSerializer() {
        final var jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(Object.class);

        final var om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        return jackson2JsonRedisSerializer;
    }

    private RedisCacheConfiguration handleRedisCacheConfiguration(
            CacheProperties.Redis redisProperties,
            RedisCacheConfiguration config) {

        if (Objects.isNull(redisProperties)) {
            return config;
        }

        if (redisProperties.getTimeToLive() != null) {
            config = config.entryTtl(redisProperties.getTimeToLive());
        }
        if (redisProperties.getKeyPrefix() != null) {
            config = config.prefixKeysWith(redisProperties.getKeyPrefix());
        }
        if (!redisProperties.isCacheNullValues()) {
            config = config.disableCachingNullValues();
        }
        if (!redisProperties.isUseKeyPrefix()) {
            config = config.disableKeyPrefix();
        }

        return config;
    }
}
