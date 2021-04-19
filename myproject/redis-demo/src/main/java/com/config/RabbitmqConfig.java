package com.config;

import com.business.rabbitmqdemo.common.KnowIedgeManualConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitmqConfig {
    private static final Logger log = LoggerFactory.getLogger(RabbitmqConfig.class);
    @Autowired
    private CachingConnectionFactory connectionFactory;
    @Autowired
    private Environment environment;
    @Autowired
    private KnowIedgeManualConsumer knowIedgeManualConsumer;


    /**
     * 监听器容器工厂实例
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
    /**
     * 监听器容器工厂实例
     * 单一消费者实例的配置
     * @return
     */
    @Bean("simpleRabbitListenerContainerAuto")
    public SimpleRabbitListenerContainerFactory listenerContainerAuto() {
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
        //设置确认消费模式为自动确认消费AUTO
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }
    /**
     * 监听器容器工厂实例
     * 单一消费者实例的配置
     * @return
     */
    @Bean("SimpleMessageListenerContainerManual")
    public SimpleMessageListenerContainer listenerContainerManual(@Qualifier("manualQueue") Queue manualQueue) {
        //定义消息监听器所在的容器工厂
        SimpleMessageListenerContainer factory = new SimpleMessageListenerContainer();
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
        //设置确认消费模式为自动确认消费AUTO
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //指定该容器的监听队列
        factory.setQueues(manualQueue);
        //指定该容器中消息监听器,即消费者
        factory.setMessageListener(knowIedgeManualConsumer);
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
    //==================================start=================================
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
    //==================================end=================================
    //==================================start=================================
    @Bean(name="objectQueue")
    public Queue objectQueue() {
        return new Queue(environment.getProperty("mq.object.info.queue.name"),true);
    }
    /**
     * 创建交换机
     * @return
     */
    @Bean
    public DirectExchange objectExchange() {
        return new DirectExchange(environment.getProperty("mq.object.info.exchange.name"),true,false);
    }

    /**
     * 创建绑定
     * @return
     */
    @Bean
    public Binding objectBinging() {
        return BindingBuilder.bind(objectQueue()).to(objectExchange()).with(environment.getProperty("mq.object.info.routing.key.name"));
    }
    //==================================end=================================
    //==================================start=================================
    @Bean(name="fanoutQueueOne")
    public Queue fanoutQueueOne() {
        return new Queue(environment.getProperty("mq.fanout.queue.one.name"),true);
    }
    @Bean(name="fanoutQueueTwo")
    public Queue fanoutQueueTwo() {
        return new Queue(environment.getProperty("mq.fanout.queue.two.name"),true);
    }
    /**
     * 创建交换机
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(environment.getProperty("mq.fanout.exchange.name"),true,false);
    }
    /**
     * 创建绑定
     * @return
     */
    @Bean
    public Binding fanoutBingingOne() {
        return BindingBuilder.bind(fanoutQueueOne()).to(fanoutExchange());
    }
    /**
     * 创建绑定
     * @return
     */
    @Bean
    public Binding fanoutBingingTwo() {
        return BindingBuilder.bind(fanoutQueueTwo()).to(fanoutExchange());
    }

    //==================================end=================================
//==================================start=================================
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(environment.getProperty("mq.topic.exchange.name"));
    }
    @Bean(name="topicQueueOne")
    public Queue topicQueueOne() {
        return new Queue(environment.getProperty("mq.topic.queue.one.name"),true);
    }
    @Bean(name="topicQueueTwo")
    public Queue topicQueueTwo() {
        return new Queue(environment.getProperty("mq.topic.queue.two.name"),true);
    }
    /**
     * 创建绑定
     * @return
     */
    @Bean
    public Binding topicBingingOne() {
        return BindingBuilder.bind(topicQueueOne()).to(topicExchange()).with(environment.getProperty("mq.topic.routing.key.one.name"));
    }
    /**
     * 创建绑定
     * @return
     */
    @Bean
    public Binding topicBingingTwo() {
        return BindingBuilder.bind(topicQueueTwo()).to(topicExchange()).with(environment.getProperty("mq.topic.routing.key.two.name"));
    }
    //==================================end=================================

    //==================================start=================================
    @Bean
    public DirectExchange autoExchange() {
        return new DirectExchange(environment.getProperty("mq.auto.knowledge.exchange.name"));
    }
    @Bean(name="autoQueue")
    public Queue autoQueueOne() {
        return new Queue(environment.getProperty("mq.auto.knowledge.queue.name"),true);
    }
    /**
     * 创建绑定
     * @return
     */
    @Bean
    public Binding autoBinging() {
        return BindingBuilder.bind(autoQueueOne()).to(autoExchange()).with(environment.getProperty("mq.auto.knowledge.routing.key.name"));
    }
    //==================================end=================================
    //==================================start=================================
    @Bean(name="manualQueue")
    public Queue manualQueue() {
        return new Queue(environment.getProperty("mq.manual.knowledge.queue.name"),true);
    }
    @Bean
    public TopicExchange manualExchange() {
        return new TopicExchange(environment.getProperty("mq.manual.knowledge.exchange.name"));
    }
    /**
     * 创建绑定
     * @return
     */
    @Bean
    public Binding manualBinging() {
        return BindingBuilder.bind(manualQueue()).to(manualExchange()).with(environment.getProperty("mq.manual.knowledge.routing.key.name"));
    }
    //==================================end=================================
    //==================================start=================================
    @Bean(name="loginQueue")
    public Queue loginQueue() {
        return new Queue(environment.getProperty("mq.login.queue.name"),true);
    }
    @Bean
    public TopicExchange loginExchange() {
        return new TopicExchange(environment.getProperty("mq.login.exchange.name"));
    }
    /**
     * 创建绑定
     * @return
     */
    @Bean
    public Binding loginBinging() {
        return BindingBuilder.bind(loginQueue()).to(loginExchange()).with(environment.getProperty("mq.login.routing.key.name"));
    }

    //==================================end=================================
    //==================================start=================================
    @Bean
    public Queue basicDeadQueue() {
        Map<String,Object> args = new HashMap<>();
        //创建死信交换机
        args.put("x-dead-letter-exchange",environment.getProperty("mq.dead.exchange.name"));
        // 创建死信路由
        args.put("x-dead-letter-routing-key",environment.getProperty("mq.dead.routing.key.name"));
        //设定ttl，单位为ms,在这里指的是10s
        args.put("x-message-ttl",10000);
        return new Queue(environment.getProperty("mq.dead.queue.name"),true,false,false,args);
    }

    @Bean
    public TopicExchange baxicProducerExchange() {
        return new TopicExchange(environment.getProperty("mq.producer.basic.exchange.name"),true,false);
    }
    @Bean
    public Binding basicProducerBinding() {
        return BindingBuilder.bind(basicDeadQueue()).to(baxicProducerExchange()).with(environment.getProperty("mq.producer.basic.routing.key.name"));
    }
   @Bean
    public Queue realConsumerQueue() {
        return new Queue(environment.getProperty("mq.consumer.real.queue.name"),true);
    }
    @Bean
    public TopicExchange basicDeadExchange() {
        return new TopicExchange(environment.getProperty("mq.dead.exchange.name"),true,false);
    }
    @Bean
    public Binding basicDeadBinding() {
        return BindingBuilder.bind(realConsumerQueue()).to(basicDeadExchange()).with(environment.getProperty("mq.dead.routing.key.name"));
    }

    //==================================end=================================
}
