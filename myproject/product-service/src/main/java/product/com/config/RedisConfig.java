package product.com.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import product.com.productyw.Entity.User;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {
    @Value("${spring.redis.host:}")
    private String host;
    @Value("${spring.redis.port:6379}")
    private int port;
    @Value("${spring.redis.password:}")
    private String password;
    @Value("${spring.redis.timeout:0}")
    private int timeout;
    @Value("${spring.redis.pool.max-active:100}")
    private int maxTotal =100;
    @Value("${spring.redis.pool.max-idle:20}")
    private int maxIdle =20;
    @Value("${spring.redis.pool.max-wait-millis:15000}")
    private long maxWaitMillis =15000;
@Bean
    public JedisConnectionFactory redisConnectionFactory() {
    JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setMaxTotal(maxTotal);
    poolConfig.setMaxIdle(maxIdle);
    poolConfig.setMaxWaitMillis(maxWaitMillis);
    poolConfig.setTestOnBorrow(true);
    poolConfig.setTestOnReturn(true);
    poolConfig.setTestWhileIdle(true);
    JedisConnectionFactory factory = new JedisConnectionFactory();
    factory.setHostName(host);
    factory.setPort(port);
    if (null != this.password && this.password.length() > 0) {
        factory.setPassword(password);
    }
    factory.setTimeout(timeout);
    factory.setUsePool(true);
    factory.setPoolConfig(poolConfig);
    return factory;
    }
    @Bean
    protected RedisTemplate buildRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
     RedisTemplate redisTemplate = new RedisTemplate();
     redisTemplate.setConnectionFactory(redisConnectionFactory);
     //key 的序列化处理方式，直接使用字符串
     redisTemplate.setKeySerializer(new StringRedisSerializer());
     //value 的序列化方式，转换成JSON字符串
     redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
     return redisTemplate;
    }
    @Bean
    public RedisTemplate<String, User> userRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
    return this.buildRedisTemplate(redisConnectionFactory);
    }
}
