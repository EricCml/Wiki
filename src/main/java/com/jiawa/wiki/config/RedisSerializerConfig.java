package com.jiawa.wiki.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisSerializerConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        // 将redisTemplate的key序列化方式更改为StringRedisSerializer
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // 将redisTemplate的value序列化方式更改为GenericJackson2JsonRedisSerializer
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // 设置redis连接
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
