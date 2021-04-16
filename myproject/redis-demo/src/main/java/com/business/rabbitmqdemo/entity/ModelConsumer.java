package com.business.rabbitmqdemo.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ModelConsumer {
    private static final Logger log = LoggerFactory.getLogger(ModelConsumer.class);
    @Autowired
    private ObjectMapper objectMapper;

    /**
     *
     * @param msg
     */
    @RabbitListener(queues = "${mq.fanout.queue.one.name}",containerFactory = "simpleRabbitListenerContainerFactory")
    public void consumeFanoutMsgOne(@Payload byte[] msg) {
        try {
            EventInfo info = objectMapper.readValue(msg,EventInfo.class);
            log.info("消息模型fanoutExchange-one消费者-监听消费到消息:{}",info);
        } catch (Exception e) {
            log.error("消息模型fanoutExchange-one-消费者-发生异常",e.fillInStackTrace());
        }
    }
    @RabbitListener(queues = "${mq.fanout.queue.two.name}",containerFactory = "simpleRabbitListenerContainerFactory")
    public void consumeFanoutMsgTwo(@Payload byte[] msg) {
        try {
            EventInfo info = objectMapper.readValue(msg,EventInfo.class);
            log.info("消息模型fanoutExchange-two消费者-监听消费到消息:{}",info);
        } catch (Exception e) {
            log.error("消息模型fanoutExchange-two-消费者-发生异常",e.fillInStackTrace());
        }
    }
    /**
     *
     * @param msg
     */
    @RabbitListener(queues = "${mq.topic.queue.one.name}",containerFactory = "simpleRabbitListenerContainerFactory")
    public void consumeTopicMsgOne(@Payload byte[] msg) {
        try {
            String message = new String(msg,"utf-8");
            //EventInfo info = objectMapper.readValue(msg,EventInfo.class);
            log.info("消息模型topicExchange-*-消费者-监听消费到消息:{}",message);
        } catch (Exception e) {
            log.error("消息模型topicExchange-one-*-消费者-发生异常",e.fillInStackTrace());
        }
    }
    @RabbitListener(queues = "${mq.topic.queue.two.name}",containerFactory = "simpleRabbitListenerContainerFactory")
    public void consumeTopicMsgTwo(@Payload byte[] msg) {
        try {
            String message = new String(msg,"utf-8");
            //EventInfo info = objectMapper.readValue(msg,EventInfo.class);
            log.info("消息模型topicExchange-#-消费者-监听消费到消息:{}",message);
        } catch (Exception e) {
            log.error("消息模型topicExchange-#-消费者-发生异常",e.fillInStackTrace());
        }
    }
}
