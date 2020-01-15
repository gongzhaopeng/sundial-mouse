package cn.benbenedu.sundial.mouse.configuration;

import lombok.Data;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Data
@ConfigurationProperties(prefix = "cache.redis")
public class CustomRedisCacheProperties {

    private Map<String, CacheProperties.Redis> caches;
}
