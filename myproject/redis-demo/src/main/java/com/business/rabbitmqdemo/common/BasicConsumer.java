package com.business.rabbitmqdemo.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class BasicConsumer {
    private static final Logger log = LoggerFactory.getLogger(BasicConsumer.class);
    @Autowired
    private ObjectMapper objectMapper;
@RabbitListener(queues = "${mq.basic.info.queue.name}",containerFactory = "simpleRabbitListenerContainerFactory")
    public void consumeMsg(@Payload byte[] msg) {
        try {
            String message = new String(msg,"utf-8");
            log.info("基本消息模型-消费者-监听消费到消息:{}",message);
        } catch (Exception e) {
            log.error("基本消息模型-消费者-发生异常:",e.fillInStackTrace());
        }
    }
    @RabbitListener(queues = "${mq.object.info.queue.name}",containerFactory ="simpleRabbitListenerContainerFactory")
    public void consumeObjectMsg(@Payload Person person) {
        try {
            log.info("基本消息模型-消费者-监听消费到消息:{}",person);
        } catch (Exception e) {
            log.error("基本消息模型-消费者-发生异常:",e.fillInStackTrace());
        }
    }
}
