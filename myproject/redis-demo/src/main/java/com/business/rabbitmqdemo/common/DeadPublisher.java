package com.business.rabbitmqdemo.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DeadPublisher {
    private static final Logger log = LoggerFactory.getLogger(DeadPublisher.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Environment environment;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendAutoMsg(DeadInfo message) {
        if (message != null) {
            try {
                //消息传输格式
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                //指定消息模型中的交换机
                rabbitTemplate.setExchange(environment.getProperty("mq.producer.basic.exchange.name"));
                //指定消息模型中的路由
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.producer.basic.routing.key.name"));
                Message msg = MessageBuilder.withBody(objectMapper.writeValueAsBytes(message)).build();
                rabbitTemplate.convertAndSend(message, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties messageProperties = message.getMessageProperties();
                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, DeadInfo.class);
                        //当消息和队列同时都设置了TTL时则取较时间的值
                        messageProperties.setExpiration(String.valueOf(10000));
                        return message;
                    }
                });
                log.info("死信队列实战-生产者-发送消息入死信队列:{}", message);
            } catch (Exception e) {
                log.error("死信队列实战-生产者-发送消息入死信队列异常:{}", message, e.fillInStackTrace());
            }
        }
    }
}
