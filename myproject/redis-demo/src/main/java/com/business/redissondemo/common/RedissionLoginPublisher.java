package com.business.redissondemo.common;

import com.business.rabbitmqdemo.loginlogyw.dto.UserLoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class RedissionLoginPublisher {
    private static final Logger log = LoggerFactory.getLogger(RedissionLoginPublisher.class);
    private static final String topickey = "redissonUserLoginTopicKey";
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Environment environment;
    @Autowired
    private RedissonClient redissonClient;
    public void sendAutoMsg(UserLoginDto message) {
        if (message != null) {
            try {
                //创建主题
                RTopic rTopic = redissonClient.getTopic(topickey);
                rTopic.publishAsync(message);
                log.info("redisson登录信息-生产者-发送消息:{}",message);
            } catch (Exception e) {
                log.error("redisson登录信息-生产者-发送消息发生异常:{}",message,e.fillInStackTrace());
            }
        }
    }
}
