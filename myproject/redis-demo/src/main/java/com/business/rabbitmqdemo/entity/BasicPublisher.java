package com.business.rabbitmqdemo.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
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

    /**
     * 发送对象类型的消息
     * @param person
     */
    public void sendObjectMsg(Person person) {
        if (person != null) {
            try {
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                rabbitTemplate.setExchange(environment.getProperty("mq.object.info.exchange.name"));
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.object.info.routing.key.name"));
                rabbitTemplate.convertAndSend(person, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        //获取消息的属性
                        MessageProperties messageProperties = message.getMessageProperties();
                        //设置消息的持久化模型
                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        //设置消息的类型
                        messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME,Person.class);
                        //返回消息实例
                        return message;
                    }
                });
                log.info("基本消息模型-生产者-发送对象类型的消息:{}",person);
            } catch (Exception e) {
                log.error("基本消息模型-生产者-发送对象类型消息发生异常:{}",person,e.fillInStackTrace());
            }
        }
    }
}
