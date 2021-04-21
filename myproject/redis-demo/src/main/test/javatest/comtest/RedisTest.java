package javatest.comtest;

import com.business.rabbitmqdemo.loginlogyw.dto.UserLoginDto;
import com.business.redissondemo.common.RedissionLoginPublisher;
import com.business.redissondemo.dto.BloomDto;
import com.business.redissondemo.dto.RMapDto;
import com.entity.Person;
import com.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RMap;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class RedisTest  extends BaseAppManager {
    private static final Logger log = LoggerFactory.getLogger(RedisTest.class);
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedissonClient  redissonClient;
    @Autowired
    private RedissionLoginPublisher redissionLoginPublisher;
    @Test
    public void one() {
        final String content = "实战";
        final  String key = "redis:template:one:string";
    ValueOperations valueOperations = redisTemplate.opsForValue();
    valueOperations.set(key,content);
    Object result = valueOperations.get(key);
    log.info("============"+result);
    }
    @Test
    public void two() {
        final String content = "stringRedisTemplate实战";
        final  String key = "stringRedisTemplate:two";
        redisTemplate.delete(key);
        ValueOperations valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key,content);
        Object result = valueOperations.get(key);
        log.info("============"+result);
    }

    @Test
    public void three() throws IOException {
        User user = new User(2l,"qqq","sss");
         String content = "stringRedisTemplate实战";
         String key = "stringRedisTemplate:two";
        redisTemplate.delete(key);
         content = objectMapper.writeValueAsString(user);
        ValueOperations valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set(key,content);
        Object result = valueOperations.get(key);
        if (result != null) {
            User user1 = objectMapper.readValue(result.toString(),User.class);
            log.info("====toString()========"+user1.getName());
        }
        log.info("============"+result);
    }
    /**
     * list
     * @throws IOException
     */
    @Test
    public void four() throws IOException {

        List<Person> userList = new ArrayList<>();
        User user = new User(2l,"qqq","sss");
        userList.add(new Person(2l,"q1","sss"));
        userList.add(new Person(3l,"q2","sss"));
        userList.add(new Person(4l,"q3","sss"));
        userList.add(new Person(5l,"q4","sss"));
        String content = "stringRedisTemplate实战";
        String key = "stringRedisTemplate:two";
        //content = objectMapper.writeValueAsString(user);
        //redisTemplate.delete(key);
        ListOperations valueOperations = redisTemplate.opsForList();
        if (!redisTemplate.hasKey(key)) {
            for (Person person:userList) {
                //从队尾添加
                valueOperations.leftPush(key,objectMapper.writeValueAsString(person));
            }
        }
        Object result = valueOperations.rightPop(key);
        log.info("====size========"+valueOperations.size(key));
        Person person;
        if (result != null) {
            person = objectMapper.readValue(result.toString(), Person.class);
            log.info("====toString()========"+person.getName());
        }
        //Object result = valueOperations.range(key,0,userList.size()-1);

//        if (result != null) {
//            List<String> personList = (List<String>) result;
//            if (!CollectionUtils.isEmpty(personList)) {
//                for (String person1 : personList) {
//                    person = objectMapper.readValue(person1.toString(), Person.class);
//                    log.info("====toString()========"+person.getName());
//                }
//            }
//        }
        log.info("============"+result);
    }
    /**
     * set
     * @throws IOException
     */
    @Test
    public void five() throws IOException {
        List<Person> userList = new ArrayList<>();
        User user = new User(2l,"qqq","sss");
        userList.add(new Person(2l,"q1","sss"));
        userList.add(new Person(3l,"q2","sss"));
        userList.add(new Person(4l,"q3","sss"));
        userList.add(new Person(5l,"q4","sss"));
        String content = "stringRedisTemplate实战";
        String key = "stringRedisTemplate:set";
        //content = objectMapper.writeValueAsString(user);
        SetOperations valueOperations = redisTemplate.opsForSet();
        redisTemplate.delete(key);
        for (Person person:userList) {
            valueOperations.add(key,objectMapper.writeValueAsString(person));
        }
        Object result = valueOperations.members(key);
        Person person;
        if (result != null) {
            Set<String> personList = (Set<String>) result;
            if (!CollectionUtils.isEmpty(personList)) {
                for (String person1 : personList) {
                    person = objectMapper.readValue(person1.toString(), Person.class);
                    log.info("====toString()========"+person.getName());
                }
            }
        }
        log.info("============"+result);
    }

    /**
     * hash
     * @throws IOException
     */
    @Test
    public void six() throws IOException {
        List<Person> userList = new ArrayList<>();
        User user = new User(2l,"qqq","sss");
        userList.add(new Person(2l,"q1","sss"));
        userList.add(new Person(3l,"q2","sss"));
        userList.add(new Person(4l,"q3","sss"));
        userList.add(new Person(5l,"q4","sss"));
        String content = "stringRedisTemplate实战";
        String key = "stringRedisTemplate:hash";
        //content = objectMapper.writeValueAsString(user);
        HashOperations valueOperations = redisTemplate.opsForHash();
        redisTemplate.delete(key);
        for (Person person:userList) {
            valueOperations.put(key,person.getId(),objectMapper.writeValueAsString(person));
        }
        Map<Long,String> result = valueOperations.entries(key);
        Person person;
        if (result != null) {
            if (!CollectionUtils.isEmpty(result)) {
                for (long id : result.keySet()) {
                    String personStr = result.get(id);
                    person = objectMapper.readValue(personStr, Person.class);
                    log.info("====toString()========"+person.getName());
                }
            }
        }
        log.info("============"+result);
    }

    /**
     * reddisson测试
     * @throws IOException
     */
    @Test
    public void test7() throws IOException {
        log.info("reddisson的配置信息:{}",redissonClient.getConfig().toJSON());
    }
    /**
     * reddisson测试
     * @throws IOException
     */
    @Test
    public void test8() throws IOException {
        final  String key = "myBloomFilterDataV2";
        Long total = 100000L;
        //创建布隆过滤器组件
        RBloomFilter<Integer> bloomFilter = redissonClient.getBloomFilter(key);
        //初始化布隆过滤器,预计统计元素数量为100000,期望误差为0.01
        bloomFilter.tryInit(total,0.01);
        for (int i = 1; i <= total; i++) {
            bloomFilter.add(i);
        }
        log.info("该布隆过滤器是否包含数据1:{}",bloomFilter.contains(1));
        log.info("该布隆过滤器是否包含数据-1:{}",bloomFilter.contains(-1));
        log.info("该布隆过滤器是否包含数据1000:{}",bloomFilter.contains(1000));
        log.info("该布隆过滤器是否包含数据100000000:{}",bloomFilter.contains(100000000));
    }
    /**
     * reddisson测试
     * @throws IOException
     */
    @Test
    public void test9() throws IOException {
        final  String key = "myBloomFilterDataV3";
        //创建布隆过滤器组件
        RBloomFilter<BloomDto> bloomFilter = redissonClient.getBloomFilter(key);
        //初始化布隆过滤器,预计统计元素数量为100000,期望误差为0.01
        bloomFilter.tryInit(1000,0.01);
        for (int i = 1; i <= 1000; i++) {
            bloomFilter.add(new BloomDto(i,i+""));
        }
        log.info("该布隆过滤器是否包含数据1:{}",bloomFilter.contains(new BloomDto(1,"1")));
        log.info("该布隆过滤器是否包含数据-1:{}",bloomFilter.contains(new BloomDto(100,"2")));
        log.info("该布隆过滤器是否包含数据1000:{}",bloomFilter.contains(new BloomDto(1000,"3")));
        log.info("该布隆过滤器是否包含数据1000:{}",bloomFilter.contains(new BloomDto(1000,"1000")));
    }
    /**
     * reddisson测试
     * @throws IOException
     */
    @Test
    public void test10() throws IOException {
        UserLoginDto userLoginDto = new UserLoginDto();
        userLoginDto.setUserId(90001);
        userLoginDto.setUserName("wwwwwwwwwwwww");
        userLoginDto.setPassword("123456");
        redissionLoginPublisher.sendAutoMsg(userLoginDto);

    }
    /**
     * reddisson测试
     * @throws IOException
     */
    @Test
    public void test11() throws IOException {
        final String key = "myRedissonRMap";
        RMapDto rMapDto1 = new RMapDto(1,"map1");
        RMapDto rMapDto2 = new RMapDto(2,"map2");
        RMapDto rMapDto3 = new RMapDto(3,"map3");
        RMapDto rMapDto4 = new RMapDto(4,"map4");
        RMapDto rMapDto5 = new RMapDto(5,"map5");
        RMapDto rMapDto6 = new RMapDto(6,"map6");
        RMapDto rMapDto7 = new RMapDto(7,"map7");
        RMapDto rMapDto8 = new RMapDto(8,"map8");
        RMap<Integer,RMapDto> rMap = redissonClient.getMap(key);
        //正常的添加元素
        rMap.put(rMapDto1.getId(),rMapDto1);
        //异步的方式添加元素
        rMap.putAsync(rMapDto2.getId(),rMapDto2);
        //添加元素之前判断是否存在,如果不存在才添加元素;否则不添加
        rMap.putIfAbsent(rMapDto3.getId(),rMapDto3);
        //添加元素之前判断是否存在,如果不存在才添加元素;否则不添加 --异步
        rMap.putIfAbsentAsync(rMapDto4.getId(),rMapDto4);
        //正常的添加元素 --快速方式
        rMap.fastPut(rMapDto5.getId(),rMapDto5);
        //正常的添加元素 --快速方式 异步
        rMap.fastPutAsync(rMapDto6.getId(),rMapDto6);
    }
    /**
     * reddisson测试
     * @throws IOException
     */
    @Test
    public void test12() throws IOException {
        final String key = "myRedissonRMap";
        RMap<Integer,RMapDto> rMap = redissonClient.getMap(key);
        Set<Integer> ids = rMap.keySet();
        Map<Integer,RMapDto> map = rMap.getAll(ids);
        log.info("元素列表:{}",map);
        final Integer removeId=6;
        rMap.remove(removeId);
        map = rMap.getAll(rMap.keySet());
        log.info("移除元素{}后的数据列表:{}",removeId,map);
        //待移除的元素id列表
        final Integer[] removeIds = new Integer[]{1,2,3};
        rMap.fastRemove(removeIds);
        map = rMap.getAll(rMap.keySet());
        log.info("移除元素{}后的数据列表:{}",removeId,map);
    }
    /**
     * 元素淘汰机制
     * reddisson测试
     * @throws IOException
     */
    @Test
    public void test13() throws IOException, InterruptedException {
        final String key = "myRedissonRMapCache";
        RMapCache<Integer,RMapDto>  rMap = redissonClient.getMapCache(key);
        RMapDto rMapDto1 = new RMapDto(1,"map1");
        RMapDto rMapDto2 = new RMapDto(2,"map2");
        RMapDto rMapDto3 = new RMapDto(3,"map3");
        RMapDto rMapDto4 = new RMapDto(4,"map4");
        rMap.putIfAbsent(rMapDto1.getId(),rMapDto1);
        //将对象元素添加进RMapCache组件中-有效时间TTL设置为10秒钟，即该元素存活时间为10秒
        rMap.putIfAbsent(rMapDto2.getId(),rMapDto2,10, TimeUnit.SECONDS);
        rMap.putIfAbsent(rMapDto3.getId(),rMapDto3);
        rMap.putIfAbsent(rMapDto4.getId(),rMapDto4,5, TimeUnit.SECONDS);
        //首次获取MapCache组件的所有KEY
        Set<Integer> set = rMap.keySet();
        Map<Integer,RMapDto> resMap = rMap.getAll(set);
        log.info("元素列表:{}",resMap);
        //等待5秒
        Thread.sleep(5000);
        resMap = rMap.getAll(rMap.keySet());

    }
}
