package com.business.redissondemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.redissondemo.dto.PraiseDto;
import com.business.redissondemo.entity.PraiseEntity;
import com.business.redissondemo.mapper.PraiseMapper;
import com.business.redissondemo.service.IPraiseService;
import com.business.redissondemo.service.IRedisPraiseService;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-22
 */
@Service
public class RedisPraiseServiceImpl implements IRedisPraiseService {
    private static final Logger log = LoggerFactory.getLogger(RedisPraiseServiceImpl.class);
    private static final String keyBlog = "RedisBlogPraiseMap";
    @Autowired
    private Environment environment;
    @Autowired
    private PraiseMapper praiseMapper;
    @Autowired
    private RedissonClient redissonClient;

    /**
     * 缓存当前用户点赞博客的记录
     * @param blogId
     * @param uId
     * @param status
     */
    @Override
    public void cachePraiseBlog(Long blogId, Long uId, String status) throws InterruptedException {
        final String lockName = new StringBuffer("blogRedissonPraiseLock").append(blogId).append(uId).append(status).toString();
        RLock rLock = redissonClient.getLock(lockName);
        //可重入锁
        Boolean res = rLock.tryLock(100,10, TimeUnit.SECONDS);
        try {
            if (res) {
                if (blogId != null && uId != null && !StringUtils.isEmpty(status)) {
                    final String key = blogId +":"+ uId;
                    RMap<String,Long> praiseMap = redissonClient.getMap(keyBlog);
                    if ("1".equals(status)) {
                        praiseMap.putIfAbsent(key,uId);
                    } else if ("0".equals(status)) {
                        praiseMap.remove(key);
                    }
                }
            }

        } catch (Exception e) {
            throw e;
        }finally {
            if (rLock != null) {
                rLock.forceUnlock();
            }

        }
    }

    @Override
    public Long getCacheTotalBlog(Long blogId) {
        Long result =0l;
        if (blogId != null) {
            RMap<String,Long> praiseMap = redissonClient.getMap(keyBlog);
            Map<String,Long> dataMap = praiseMap.readAllMap();
            if (dataMap != null && dataMap.keySet() != null) {
                Set<String> set =dataMap.keySet();
                Long bId;
                for (String key : set) {
                    if (!StringUtils.isEmpty(key)) {
                        String[] arr = key.split(":");
                        if (arr != null && arr.length > 0) {
                            bId = Long.valueOf(arr[0]);
                            if (blogId.equals(bId)) {
                                result+=1;
                            }
                        }
                    }
                }
            }
        }

        return result;
    }

    @Override
    public void rankBlogPraise() {

    }

    @Override
    public List<PraiseDto> getBlogPraiseRank() {
        return null;
    }
}
