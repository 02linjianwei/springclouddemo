package com.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RabbitmqConfig {
    private static final Logger log = LoggerFactory.getLogger(RabbitmqConfig.class);
    @Autowired
    private CachingConnectionFactory connectionFactory;
    @Autowired
    private Environment environment;

    /**
     * 单一消费者实例的配置
     * @return
     */
    @Bean("simpleRabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory listenerContainer() {
        //定义消息监听器所在的容器工厂
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //设置容器工厂所用的实例
        factory.setConnectionFactory(connectionFactory);
        //设置消息在传输中的格式,在这里采用JSON的格式进行传输
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //设置并发消费者实例的初始数量
        factory.setConcurrentConsumers(1);
        //设置并发消费者实例的最大数量
        factory.setMaxConcurrentConsumers(1);
        //设置并发消费者实例中每个实例拉取的消息数量
        factory.setPrefetchCount(1);
        return factory;
    }
    @Bean("multiListenerContainer")
    public SimpleRabbitListenerContainerFactory multiListenerContainer() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //设置容器工厂所用的实例
        factory.setConnectionFactory(connectionFactory);
        //设置消息在传输中的格式,在这里采用JSON的格式进行传输
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        //设置消息的确认消费模式.在这里为NONE,表示不需要确认消费
        factory.setAcknowledgeMode(AcknowledgeMode.NONE);
        //设置并发消费者实例的初始数量
        factory.setConcurrentConsumers(10);
        //设置并发消费者实例的最大数量
        factory.setMaxConcurrentConsumers(15);
        //设置并发消费者实例中每个实例拉取的消息数量
        factory.setPrefetchCount(10);
        return factory;
    }
    @Bean
    public RabbitTemplate rabbitTemplate() {
        //设置 发送消息后进行确认
        connectionFactory.setPublisherConfirms(true);
        //设置 发送消息后返回确认信息
        connectionFactory.setPublisherReturns(true);
        //构造发送消息组件实例对象
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        //发送消息后,如果发送成功,则输出 消息发送成功的反馈消息
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean b, String s) {
                log.info("消息发送成功:correlationData({}),ack({}),case({})",correlationData,b,s);
            }
        });
        //发送消息后，如果发送失败,则输出 消息发送失败-的反馈信息
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int i, String s, String s1, String s2) {
                log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}",s1,s2,i,s,message);
            }
        });
        return rabbitTemplate;
    }

    /**
     * 创建队列
     * @return
     */
    @Bean("basicQueue")
    public Queue basicQueue() {
        return new Queue(environment.getProperty("mq.basic.info.queue.name"),true);
    }

    /**
     * 创建交换机
     * @return
     */
    @Bean
    public DirectExchange basicExchange() {
        return new DirectExchange(environment.getProperty("mq.basic.info.exchange.name"),true,false);
    }

    /**
     * 创建绑定
     * @return
     */
    @Bean
    public Binding basicBinging() {
        return BindingBuilder.bind(basicQueue()).to(basicExchange()).with(environment.getProperty("mq.basic.info.routing.key.name"));
    }

}
