package com.business.rabbitmqdemo.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class BasicPublisher {
    private static final Logger log = LoggerFactory.getLogger(BasicPublisher.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment environment;

    public void sendMsg(String message) {
        if (!Strings.isNullOrEmpty(message)) {
            try {
                //消息传输格式
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                //指定消息模型中的交换机
                rabbitTemplate.setExchange(environment.getProperty("mq.basic.info.exchange.name"));
                //指定消息模型中的路由
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.basic.info.routing.key.name"));
                Message msg = MessageBuilder.withBody(message.getBytes(StandardCharsets.UTF_8)).build();
                rabbitTemplate.convertAndSend(msg);
                log.info("基本消息模型-生产者-发送消息:{}",message);
            } catch (Exception e) {
                log.error("基本消息模型-生产者-发送消息发生异常:{}",message,e.fillInStackTrace());
            }
        }
    }
}
