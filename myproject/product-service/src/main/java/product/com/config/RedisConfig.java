package product.com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.web.client.RestTemplate;
import product.com.interceptor.JWTOAuthTokenInterceptor;

import java.util.Collections;
import java.util.List;

@Configuration
public class RedisConfig {
    @Autowired
   private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    @Qualifier("oauth2ClientContext")
    private OAuth2ClientContext oAuth2ClientContext;
    @Autowired
    private OAuth2ProtectedResourceDetails details;
    @Bean
    @LoadBalanced
    protected RedisTemplate redisTemplate() {
     RedisTemplate redisTemplate = new RedisTemplate();
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
//    @Bean
//    @LoadBalanced
//    public OAuth2RestTemplate oAuth2RestTemplate() {
//        return new OAuth2RestTemplate(details,oAuth2ClientContext);
//    }

    @Bean
    @Primary
    //@LoadBalanced
    public RestTemplate oAuth2RestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List interceptors = restTemplate.getInterceptors();
        if (interceptors == null) {
            restTemplate.setInterceptors(
                    Collections.singletonList(new JWTOAuthTokenInterceptor())
            );
        } else {
            interceptors.add(new JWTOAuthTokenInterceptor());
            restTemplate.setInterceptors(interceptors);
        }
        return restTemplate;
    }
}
