package com.business.rabbitmqdemo.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class KnowIedgeManualConsumer implements ChannelAwareMessageListener {
    private static final Logger log = LoggerFactory.getLogger(KnowIedgeManualConsumer.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Environment environment;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        MessageProperties messageProperties = message.getMessageProperties();
        long deliveryTag = messageProperties.getDeliveryTag();
        try {
            byte[] msg = message.getBody();
            KnowledgeInfo info = objectMapper.readValue(msg,KnowledgeInfo.class);
            log.info("确认消费模式-人为手动确认消费-监听器监听消费消息-内容为:{}",info);
            //执行完业务逻辑后，手动进行确认消费，其中第一个参数为:消息的分发标识(全局唯一),第二个参数:是否允许批量确认消费
            channel.basicAck(deliveryTag,true);
        } catch (Exception e) {
            log.info("确认消费模式-人为手动确认消费-监听器监听消费消息-发生异常:",e.fillInStackTrace());
            //如果在处理消息的过程中发生了异常,则依旧需要人为手动确认消费掉该消息,否则该消息将一直留在队列中,从而导致消息的重复消费。
           channel.basicReject(deliveryTag,false);
        }
    }
}
