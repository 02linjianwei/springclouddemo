package product.com.productyw.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import product.com.productyw.Entity.User;
import product.com.productyw.repository.UserRedisRepository;
import product.com.productyw.service.UserRemoteClient;
import product.com.productyw.service.UserService;
@Service
public class UserServiceImpl implements UserService {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected UserRemoteClient userRemoteClient;
    @Autowired
    protected UserRedisRepository userRedisRepository;
    @Override
    public User comments(Long id) {
        return null;
    }

    @Override
    public User load(Long id) {
        User user = this.userRedisRepository.findOne(id);
        if (null != user) {
            this.logger.info("已从REDIS缓存中获取到用户:{}的信息",id);
            return user;
        }
        this.logger.info("REDIS缓存中不存在用户：｛｝的信息，尝试从远程进行加载",id);
        user = this.userRemoteClient.load(id);
        if (null != user) {
            this.userRedisRepository.save(user);
        }
        return user;
    }
}
