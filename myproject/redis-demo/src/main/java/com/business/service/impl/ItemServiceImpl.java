package com.business.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import com.business.entity.ItemEntity;
import com.business.mapper.ItemMapper;
import com.business.service.ItemService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.baomidou.mybatisplus.mapper.Wrapper;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 商品信息表 服务实现类
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-12
 */
@Service
public class ItemServiceImpl  implements ItemService {
    private static final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    private static final String keyPrefix = "item";

    @Override
    public ItemEntity getItemInfo(String itemcode) throws IOException {
        ItemEntity itemEntity = null;
        final String key = keyPrefix + itemcode;
        ValueOperations valueOperations = redisTemplate.opsForValue();
        if (redisTemplate.hasKey(key)) {
            Object res = valueOperations.get(key);
            if (res != null && !Strings.isNullOrEmpty(res.toString())) {
                itemEntity = objectMapper.readValue(res.toString(), ItemEntity.class);
            } else {
                Wrapper<ItemEntity> itemEntityWrapper = new Condition();
                itemEntityWrapper.eq("CODE", itemcode);
                List<ItemEntity> itemEntityList = itemMapper.selectList(itemEntityWrapper);
                if (!CollectionUtils.isEmpty(itemEntityList)) {
                    itemEntity = itemEntityList.get(0);
                    valueOperations.set(key, objectMapper.writeValueAsString(itemEntityList.get(0)));
                } else {
                    valueOperations.set(key, "", 30L, TimeUnit.MINUTES);
                }
            }
        }
        return itemEntity;
    }
}

