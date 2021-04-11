package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    @Autowired
   private RedisConnectionFactory redisConnectionFactory;
    @Bean
    protected RedisTemplate<String,Object> redisTemplate() {
     RedisTemplate<String,Object> redisTemplate = new RedisTemplate<String,Object>();
     redisTemplate.setConnectionFactory(redisConnectionFactory);
     //key 的序列化处理方式，直接使用字符串
     redisTemplate.setKeySerializer(new StringRedisSerializer());
     //value 的序列化方式，转换成JSON字符串
     redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
     redisTemplate.setHashKeySerializer(new StringRedisSerializer());
     return redisTemplate;
    }
    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(redisConnectionFactory);
        return stringRedisTemplate;
    }
}
