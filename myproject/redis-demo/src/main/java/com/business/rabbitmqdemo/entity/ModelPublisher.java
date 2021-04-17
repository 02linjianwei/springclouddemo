package com.business.rabbitmqdemo.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
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

import java.nio.charset.StandardCharsets;

@Component
public class ModelPublisher {
    private static final Logger log = LoggerFactory.getLogger(ModelPublisher.class);
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private Environment environment;

    public void sendMsg(EventInfo eventInfo) {
        if (eventInfo != null) {
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.fanout.exchange.name"));
                Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(eventInfo)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
                rabbitTemplate.convertAndSend(message);
                log.info("fanoutExchange消息模型-生产者-发送消息:{}",message);

            } catch (Exception e) {
                log.error("fanoutExchange消息模型-生产者-发送消息异常:{}",eventInfo,e.fillInStackTrace());
            }
        }
    }

    public void sendMsgTopic(String msg, String routingKey) {
        if (!Strings.isNullOrEmpty(msg) && !Strings.isNullOrEmpty(routingKey)) {
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.topic.exchange.name"));
                rabbitTemplate.setRoutingKey(routingKey);
                Message message = MessageBuilder.withBody(msg.getBytes(StandardCharsets.UTF_8)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
                rabbitTemplate.convertAndSend(message);
                log.info("topicExchange消息模型-生产者-发送消息:{} 路由:{}",message,routingKey);
            } catch (Exception e) {
                log.error("topicExchange消息模型-生产者-发送消息异常:{}",msg,e.fillInStackTrace());
            }
        }
    }

}
