package javatest.comtest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;


public class RedisTest  extends BaseAppManager {
    private static final Logger log = LoggerFactory.getLogger(RedisTest.class);
    @Autowired
    private RedisTemplate redisTemplate;
@Test
    public void one() {
        final String content = "实战";
        final  String key = "redis:template:one:string";
    ValueOperations valueOperations = redisTemplate.opsForValue();
    valueOperations.set(key,content);
    Object result = valueOperations.get(key);
    log.info("============"+result);
    }
}
