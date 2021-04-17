package com.business.rabbitmqdemo.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class KnowIedgeManualPublisher {
    private static final Logger log = LoggerFactory.getLogger(KnowIedgeManualPublisher.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Environment environment;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void sendManualMsg(KnowledgeInfo message) {
        if (message != null) {
            try {
                //消息传输格式
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                //指定消息模型中的交换机
                rabbitTemplate.setExchange(environment.getProperty("mq.manual.knowledge.exchange.name"));
                //指定消息模型中的路由
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.manual.knowledge.routing.key.name"));
                Message msg = MessageBuilder.withBody(objectMapper.writeValueAsBytes(message)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
                rabbitTemplate.convertAndSend(msg);
                log.info("基于manual机制-生产者-发送消息:{}",message);
            } catch (Exception e) {
                log.error("基于manual机制-生产者-发送消息发生异常:{}",message,e.fillInStackTrace());
            }
        }
    }
}
