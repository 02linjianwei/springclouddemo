package product.com.productyw.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import product.com.productyw.Entity.User;

import javax.annotation.PostConstruct;

@Repository
public class UserRedisRepository {
    public static final String USER_KEY_PRE="user:";
    @Autowired
    private RedisTemplate redisTemplate;
    private ValueOperations<String,User> operations;
@PostConstruct
    private void init() {
        this.operations = this.redisTemplate.opsForValue();
    }

    public void save(User user) {
    this.operations.set(this.buildKey(user.getId()),user);
    }

    public User findOne(Long userId) {
    String key = this.buildKey(userId);
        if (!this.redisTemplate.hasKey(key)) {
            return  null;
        }
        return this.operations.get(key);
    }

    protected String buildKey(Long userId) {
    return USER_KEY_PRE+String.valueOf(userId);
    }

    public void delete(Long userId) {
    this.redisTemplate.delete(this.buildKey(userId));
    }
}
