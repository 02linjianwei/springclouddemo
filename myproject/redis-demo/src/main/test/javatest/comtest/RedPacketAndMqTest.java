package javatest.comtest;

import com.business.rabbitmqdemo.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.util.RedPacketUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RedPacketAndMqTest extends BaseAppManager{
    private static final Logger log = LoggerFactory.getLogger(RedPacketAndMqTest.class);
    @Autowired
    private Publisher publisher;
    @Autowired
    private BasicPublisher basicPublisher;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ModelPublisher modelPublisher;
@Test
    public void one() {
        Integer amount = 20;
        Integer total = 3;
        Integer sum =0;
    List<Integer> integerList = RedPacketUtil.divideRedPackage(amount,total);
    for (Integer i : integerList) {
        log.info("随机金额为:{}元",i);
       // log.info("随机金额为:{}分,即{}元",i,new BigDecimal(i.toString()).divide(new BigDecimal(100)));
        sum += i;
    }
    log.info("所有随机金锭叠加之和={}元",sum);
    }
    @Test
    public void two() {
      publisher.sendMsg();
    }
    @Test
    public void three() {
       String msg = "-------这是一串字符串消息-------";
       basicPublisher.sendMsg(msg);
    }
    @Test
    public void four() {
        Person person = new Person(1,"大圣","debug");
        basicPublisher.sendObjectMsg(person);
    }
    @Test
    public void five() {
        EventInfo eventInfo = new EventInfo(1,"增删改查模块","基于fanoutExchange的消息","这是基于fanoutExchang的消息模型");
        modelPublisher.sendMsg(eventInfo);
    }
    @Test
    public void six() {
    String msg = "这是topicExchange消息模型的消息";
    String routingKeyOne = "local.rabbitmqdemo.mq.topic.routing.java.key";
    String routingKeyTwo = "local.rabbitmqdemo.mq.topic.routing.php.python.key";
    String routingKeyThree = "local.rabbitmqdemo.mq.topic.routing.key";
    //modelPublisher.sendMsgTopic(msg,routingKeyOne);
    //modelPublisher.sendMsgTopic(msg,routingKeyTwo);
    modelPublisher.sendMsgTopic(msg,routingKeyThree);
    }
}
