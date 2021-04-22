package com.business.redissondemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.redissondemo.controller.PraiseController;
import com.business.redissondemo.dto.PraiseDto;
import com.business.redissondemo.entity.PraiseEntity;
import com.business.redissondemo.mapper.PraiseMapper;
import com.business.redissondemo.service.IPraiseService;
import com.business.redissondemo.service.IRedisPraiseService;
import lombok.ToString;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
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
public class PraiseServiceImpl  implements IPraiseService {
    private static final Logger log = LoggerFactory.getLogger(PraiseServiceImpl.class);
    private static final String keyAddBlogLock = "RedisBlogPraiseAddLock";
    @Autowired
    private Environment environment;
    @Autowired
    private PraiseMapper praiseMapper;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IRedisPraiseService redisPraiseService;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addPraise(PraiseDto praiseDto) throws InterruptedException {
        QueryWrapper<PraiseEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("BLOG_ID",praiseDto.getBlogId());
        queryWrapper.eq("USER_ID",praiseDto.getUserId());
        PraiseEntity praiseEntity = praiseMapper.selectOne(queryWrapper);
        if (praiseEntity == null) {
            praiseEntity = new PraiseEntity();
            BeanUtils.copyProperties(praiseDto,praiseEntity);
            Date praiseTime = new Date();
            praiseEntity.setPraiseTime(praiseTime);
            praiseEntity.setStatus("1");
            int total = praiseMapper.insert(praiseEntity);
            if (total > 0) {
                log.info("------点赞博客-{}-无锁-插入点赞记录成功",praiseDto.getBlogId());
                redisPraiseService.cachePraiseBlog(praiseDto.getBlogId(),praiseDto.getUserId(),"1");
            }
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addPraiseLock(PraiseDto praiseDto) throws InterruptedException {
        final String lockName = keyAddBlogLock+praiseDto.getBlogId()+"-"+praiseDto.getUserId();
        RLock lock = redissonClient.getLock(lockName);
        lock.lock(10, TimeUnit.SECONDS);
        try {
            QueryWrapper<PraiseEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("BLOG_ID", praiseDto.getBlogId());
            queryWrapper.eq("USER_ID", praiseDto.getUserId());
            PraiseEntity praiseEntity = praiseMapper.selectOne(queryWrapper);
            if (praiseEntity == null) {
                praiseEntity = new PraiseEntity();
            }
            BeanUtils.copyProperties(praiseDto, praiseEntity);
            Date praiseTime = new Date();
            praiseEntity.setPraiseTime(praiseTime);
            praiseEntity.setStatus("1");
            int total = praiseMapper.insert(praiseEntity);
            if (total > 0) {
                log.info("---------点赞博客-{}-加分布式锁-插入点赞记录成功---");
                redisPraiseService.cachePraiseBlog(praiseDto.getBlogId(), praiseDto.getUserId(), "1");
            }

        } catch (Exception e) {
            throw e;
        }finally {
            if (lock != null) {
                lock.unlock();
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelPraise(PraiseDto praiseDto) throws InterruptedException {
        if (praiseDto.getBlogId() != null && praiseDto.getUserId() != null) {
            QueryWrapper<PraiseEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("BLOG_ID", praiseDto.getBlogId());
            queryWrapper.eq("USER_ID", praiseDto.getUserId());
            PraiseEntity praiseEntity = praiseMapper.selectOne(queryWrapper);
            if (praiseEntity != null) {
                praiseEntity.setStatus("0");
                int result = praiseMapper.updateById(praiseEntity);
                if (result > 0) {
                    log.info("----取消点赞博客--{}-更新占赞记录成功---",praiseDto.getBlogId());
                    redisPraiseService.cachePraiseBlog(praiseDto.getBlogId(),praiseDto.getUserId(),"0");
                }
            }
        }

    }

    @Override
    public Long getBlogPraiseTotal(Long blogId) {
        return redisPraiseService.getCacheTotalBlog(blogId);
    }

    @Override
    public Collection<PraiseDto> getRankNoRedisson() {
        return null;
    }

    @Override
    public Collection<PraiseDto> getRankWithRedisson() {
        return null;
    }
}
