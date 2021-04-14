package com.business.service.impl;

import com.business.dto.RedPacketDto;
import com.business.service.IRedPacketService;
import com.business.service.IRedService;
import com.util.RedPacketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedPacketServiceImpl implements IRedPacketService {
    private static final Logger log = LoggerFactory.getLogger(RedPacketServiceImpl.class);
    private static final String keyPrefix="redis:red:packet:";
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IRedService redService;
    @Override
    public String handOut(RedPacketDto redPacketDto) throws Exception {
        if (redPacketDto.getAmount() > 0 && redPacketDto.getTotal() > 0) {
            List<Integer> integerList = RedPacketUtil.divideRedPackage(redPacketDto.getAmount(), redPacketDto.getTotal());
            String timestamp = String.valueOf(System.nanoTime());
            String redId = new StringBuffer(keyPrefix).append(timestamp).toString();
            redisTemplate.opsForList().leftPushAll(redId, integerList);
            String redTotalKey = redId + ":total";
            redisTemplate.opsForValue().set(redTotalKey, redPacketDto.getTotal());
            redService.recordRedPacket(redPacketDto, redId, integerList);
            return redId;
        } else {
            throw new Exception("系统异常-分发红包-参数不合法");
        }
    }

    @Override
    public Integer rob(Integer userId, String redId) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        Object obj = valueOperations.get(redId+userId+":rob");
        if (obj != null) {
            return Integer.getInteger(obj.toString());
        }
        Boolean res = click(redId);
        if (res) {
            Object value = redisTemplate.opsForList().rightPop(redId);
        }
        return null;
    }

    @Override
    public void recordRedPacket(RedPacketDto redPacketDto, String redId, List<Integer> list) {

    }

    @Override
    public void reCordfRobPacket(Integer userId, String redId, Integer amount) {

    }

    private Boolean click(String redId) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String redTotalKey = redId+":total";
        Object total = valueOperations.get(redTotalKey);
        if (total != null && Integer.valueOf(total.toString()) > 0) {
            return true;
        }
        return false;
    }
}
