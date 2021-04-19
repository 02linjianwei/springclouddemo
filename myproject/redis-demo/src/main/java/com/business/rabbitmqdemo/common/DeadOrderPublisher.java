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
public class DeadOrderPublisher {
    private static final Logger log = LoggerFactory.getLogger(DeadOrderPublisher.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Environment environment;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendAutoMsg(Long id) {
            try {
                //消息传输格式
                rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
                //指定消息模型中的交换机
                rabbitTemplate.setExchange(environment.getProperty("mq.producer.order.exchange.name"));
                //指定消息模型中的路由
                rabbitTemplate.setRoutingKey(environment.getProperty("mq.producer.order.routing.key.name"));
                rabbitTemplate.convertAndSend(id, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        MessageProperties messageProperties = message.getMessageProperties();
                        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        messageProperties.setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, Long.class);
                        //当消息和队列同时都设置了TTL时则取较时间的值
                        messageProperties.setExpiration(String.valueOf(10000));
                        return message;
                    }
                });
                log.info("下单支付超时死信队列实战-生产者-发送消息入死信队列:{}", id);
            } catch (Exception e) {
                log.error("下单支付超时死信队列实战-生产者-发送消息入死信队列异常:{}", id, e.fillInStackTrace());
            }

    }
}
