package com.castle.drive.config;

import com.castle.drive.framework.redis.CustomStringRedisSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

/**
 * @author YuLong
 * @Date 2024/2/1 10:39
 * @Classname RedisConfig
 * @Description Redis配置类
 */
@Configuration
public class RedisConfig {

    @Value("${redis.projectPrefix}")
    private String projectPrefix;

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // 自定义的string序列化器和fastjson序列化器
        CustomStringRedisSerializer customStringRedisSerializer = new CustomStringRedisSerializer(projectPrefix);
        // jackson 序列化器
        GenericJackson2JsonRedisSerializer jsonRedisSerializer = new GenericJackson2JsonRedisSerializer();
        // kv 序列化
        redisTemplate.setKeySerializer(customStringRedisSerializer);
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        // hash 序列化
        redisTemplate.setHashKeySerializer(customStringRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}
