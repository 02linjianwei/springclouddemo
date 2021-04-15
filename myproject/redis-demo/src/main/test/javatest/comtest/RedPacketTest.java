package javatest.comtest;

import com.business.rabbitmqdemo.entity.Publisher;
import com.util.RedPacketUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.List;

public class RedPacketTest extends BaseAppManager{
    private static final Logger log = LoggerFactory.getLogger(RedPacketTest.class);
    @Autowired
    private Publisher publisher;
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
}
