package javatest.comtest;

import com.entity.Person;
import com.entity.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
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


public class RedisTest  extends BaseAppManager {
    private static final Logger log = LoggerFactory.getLogger(RedisTest.class);
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
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
        redisTemplate.delete(key);
        ListOperations valueOperations = redisTemplate.opsForList();
        for (Person person:userList) {
            //从队尾添加
            valueOperations.leftPush(key,objectMapper.writeValueAsString(person));
        }
        Object result = valueOperations.range(key,0,userList.size()-1);
        Person person;
        if (result != null) {
            List<String> personList = (List<String>) result;
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
}
