package com.business.redissondemo.common;

import com.business.rabbitmqdemo.loginlogyw.dto.UserLoginDto;
import com.business.rabbitmqdemo.loginlogyw.service.ISysLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class RedissionLoginConsumer implements ApplicationRunner, Ordered {
    private static final Logger log = LoggerFactory.getLogger(RedissionLoginConsumer.class);
    private static final String topickey = "redissonUserLoginTopicKey";
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Environment environment;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private ISysLogService sysLogService;
    /**
     *基于AUTO的确认消费模式 -消费者-其中:
     * queues指监听的队列
     * containerFactory指监听器所在的容器工厂
     * @param
     */
    @RabbitListener(queues = "${mq.login.queue.name}",containerFactory = "simpleRabbitListenerContainerAuto")
    public void consumeAutoMsg(@Payload UserLoginDto userLoginDto) {
        try {
            //String message = new String(msg,"utf-8");
           sysLogService.recordLog(userLoginDto);
            log.info("login登录-消费者-监听消费到消息:{}",userLoginDto);
        } catch (Exception e) {
            log.error("login登录-消费者-发生异常",e.fillInStackTrace());
        }
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        try {
            RTopic rTopic = redissonClient.getTopic(topickey);
            rTopic.addListener(UserLoginDto.class, new MessageListener<UserLoginDto>() {
                @Override
                public void onMessage(CharSequence charSequence, UserLoginDto userLoginDto) {
                    if (userLoginDto != null) {
                        sysLogService.recordLog(userLoginDto);
                    }
                    log.info("redission login登录-消费者-监听消费到消息:{}",userLoginDto);
                }
            });

        } catch (Exception e) {
            log.error("redission login登录-消费者-发生异常",e.fillInStackTrace());
        }
    }

    /**
     * 设置项目启动时也跟着启动
     * @return
     */

    @Override
    public int getOrder() {
        return 0;
    }
}
