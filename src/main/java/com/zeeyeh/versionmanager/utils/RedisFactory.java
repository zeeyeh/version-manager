package com.zeeyeh.versionmanager.utils;

import com.zeeyeh.versionmanager.entity.RedisDatabaseType;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Component
public class RedisFactory {
    @Resource
    private ApplicationContext applicationContext;

    public RedisTemplate<String, Object> use(String id) {
        RedisConnectionFactory connectionFactory = applicationContext.getBean(id, RedisConnectionFactory.class);
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    public RedisTemplate<String, Object> use(RedisDatabaseType type) {
        return use(type.getValue());
    }
}
