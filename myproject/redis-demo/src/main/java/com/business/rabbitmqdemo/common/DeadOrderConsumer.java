package com.business.rabbitmqdemo.common;

import com.business.rabbitmqdemo.orderyw.entity.UserOrderEntity;
import com.business.rabbitmqdemo.orderyw.mapper.UserOrderMapper;
import com.business.rabbitmqdemo.orderyw.service.IUserOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class DeadOrderConsumer {
    private static final Logger log = LoggerFactory.getLogger(DeadOrderConsumer.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Environment environment;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private UserOrderMapper userOrderMapper;
    @Autowired
    private IUserOrderService userOrderService;
    /**
     *基于AUTO的确认消费模式 -消费者-其中:
     * queues指监听的队列
     * containerFactory指监听器所在的容器工厂
     * @param
     */
    @RabbitListener(queues = "${mq.consumer.order.real.queue.name}",containerFactory = "simpleRabbitListenerContainerAuto")
    public void consumeAutoMsg(@Payload Long id) {
        try {
            UserOrderEntity userOrderEntity = userOrderMapper.selectById(id);
            if (userOrderEntity != null && "1".equals(userOrderEntity.getStatus())) {
                userOrderService.updateUserOrderRecord(userOrderEntity);
            }
            log.info("下单支付超时死信队列实战-消费者-监听消费到消息:{}",id);
        } catch (Exception e) {
            log.error("下单支付超时死信队列实战-消费者-发生异常",e.fillInStackTrace());
        }
    }
}
