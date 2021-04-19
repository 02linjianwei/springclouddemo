package com.business.rabbitmqdemo.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class DeadConsumer {
    private static final Logger log = LoggerFactory.getLogger(DeadConsumer.class);
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Environment environment;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    /**
     *基于AUTO的确认消费模式 -消费者-其中:
     * queues指监听的队列
     * containerFactory指监听器所在的容器工厂
     * @param
     */
    @RabbitListener(queues = "${mq.consumer.real.queue.name}",containerFactory = "simpleRabbitListenerContainerAuto")
    public void consumeAutoMsg(@Payload DeadInfo deadInfo) {
        try {
            //String message = new String(msg,"utf-8");
            //KnowledgeInfo info = objectMapper.readValue(msg,KnowledgeInfo.class);
            log.info("死信队列实战-消费者-监听消费到消息:{}",deadInfo);
        } catch (Exception e) {
            log.error("死信队列实战-消费者-发生异常",e.fillInStackTrace());
        }
    }
}
