package com.business.rabbitmqdemo.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class KnowIedgePublisher {
    private static final Logger log = LoggerFactory.getLogger(KnowIedgePublisher.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Environment environment;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void sendAutoMsg(KnowledgeInfo message) {
        if (message != null) {
            try {
                //消息传输格式
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                //指定消息模型中的交换机
                rabbitTemplate.setExchange(environment.getProperty("mq.auto.knowledge.exchange.name"));
                //指定消息模型中的路由
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.auto.knowledge.routing.key.name"));
                Message msg = MessageBuilder.withBody(objectMapper.writeValueAsBytes(message)).build();
                rabbitTemplate.convertAndSend(msg);
                log.info("基于AUTO机制-生产者-发送消息:{}",message);
            } catch (Exception e) {
                log.error("基于AUTO机制-生产者-发送消息发生异常:{}",message,e.fillInStackTrace());
            }
        }
    }
}
