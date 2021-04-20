package javatest.comtest;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.rabbitmqdemo.common.*;
import com.business.redyw.mapper.RedRecordMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
    @Autowired
    private KnowIedgePublisher knowIedgePublisher;
    @Autowired
    private KnowIedgeManualPublisher knowIedgeManualPublisher;
    @Autowired
    private DeadPublisher deadPublisher;
    @Autowired
    private RedRecordMapper redRecordMapper;
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
    @Test
    public void seven() {
    KnowledgeInfo knowledgeInfo = new KnowledgeInfo();
    knowledgeInfo.setId(10010);
    knowledgeInfo.setCode("auto");
    knowledgeInfo.setMode("基于AUTO的消息确认消费模式");
    knowIedgePublisher.sendAutoMsg(knowledgeInfo);
    }
    @Test
    public void test8() {
        KnowledgeInfo knowledgeInfo = new KnowledgeInfo();
        knowledgeInfo.setId(10011);
        knowledgeInfo.setCode("manual");
        knowledgeInfo.setMode("基于manual的消息确认消费模式");
        knowIedgeManualPublisher.sendManualMsg(knowledgeInfo);
    }
    @Test
    public void test9() throws InterruptedException {
       DeadInfo deadInfo = new DeadInfo(1,"~~~~~我是第1则消息~~~~~");
       deadPublisher.sendAutoMsg(deadInfo);
       deadInfo = new DeadInfo(1,"~~~~~我是第二则消息~~~~~");
       deadPublisher.sendAutoMsg(deadInfo);
       Thread.sleep(30000);
    }
    @Test
    public void test10() throws InterruptedException {
        QueryWrapper queryWrapper = new QueryWrapper();
        PageInfo pageInfo = PageHelper.startPage(1,5).doSelectPageInfo(()->redRecordMapper.selectList(queryWrapper));
        System.out.println("======================="+pageInfo);

    }
}
