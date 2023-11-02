package me.royryando.example.newsservice.newsservice.config;

import me.royryando.example.newsservice.newsservice.model.News;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@Configuration
@EnableCaching
public class RedisConfiguration {
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // You can customize serializers, key and value serializers, etc., here if needed.
        // For example, set serializers like redisTemplate.setKeySerializer() and redisTemplate.setValueSerializer().

        return redisTemplate;
    }

    /*@Bean
    public RedisCacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        // You can customize cache settings here, such as cache names and expiration times.

        return cacheManager;
    }*/
}
