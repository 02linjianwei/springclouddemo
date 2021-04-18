package com.business.rabbitmqdemo.common;

import com.business.rabbitmqdemo.loginlogyw.dto.UserLoginDto;
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
public class LoginPublisher {
    private static final Logger log = LoggerFactory.getLogger(LoginPublisher.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Environment environment;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public void sendAutoMsg(UserLoginDto message) {
        if (message != null) {
            try {
                //消息传输格式
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                //指定消息模型中的交换机
                rabbitTemplate.setExchange(environment.getProperty("mq.login.exchange.name"));
                //指定消息模型中的路由
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.login.routing.key.name"));
                Message msg = MessageBuilder.withBody(objectMapper.writeValueAsBytes(message)).build();
                rabbitTemplate.convertAndSend(msg, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties messageProperties = message.getMessageProperties();
                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,UserLoginDto.class);
                        return message;
                    }
                });
                log.info("登录信息-生产者-发送消息:{}",message);
            } catch (Exception e) {
                log.error("登录信息-生产者-发送消息发生异常:{}",message,e.fillInStackTrace());
            }
        }
    }
}
