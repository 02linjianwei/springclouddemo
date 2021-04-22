package com.business.balanceyw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.balanceyw.dto.UserAccountDto;
import com.business.balanceyw.entity.UserAccountEntity;
import com.business.balanceyw.entity.UserAccountRecordEntity;
import com.business.balanceyw.mapper.UserAccountMapper;
import com.business.balanceyw.mapper.UserAccountRecordMapper;
import com.business.balanceyw.service.IDataBaseLockService;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class DataBaseLockServiceImpl implements IDataBaseLockService {
    private static final Logger log = LoggerFactory.getLogger(DataBaseLockServiceImpl.class);
    @Autowired
    private UserAccountMapper userAccountMapper;
    @Autowired
    private UserAccountRecordMapper userAccountRecordMapper;
    @Autowired
    private CuratorFramework curatorFramework;
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public void takeMoneyByVersion(UserAccountDto userAccountDto) throws Exception {
        QueryWrapper<UserAccountEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("USER_ID", userAccountDto.getUserId());
        UserAccountEntity userAccountEntity = userAccountMapper.selectOne(queryWrapper);
        if (userAccountEntity != null && (userAccountEntity.getAmount().doubleValue() - userAccountDto.getAmount()) >= 0) {
            Map map = new HashMap();
            map.put("money", BigDecimal.valueOf(userAccountDto.getAmount()));
            map.put("id", userAccountEntity.getId());
            map.put("version", userAccountEntity.getVersion());
            int res = userAccountMapper.updateByPKVersion(map);
            if (res > 0) {
                UserAccountRecordEntity userAccountRecordEntity = new UserAccountRecordEntity();
                userAccountRecordEntity.setCreateTime(new Date());
                userAccountRecordEntity.setAccountId(userAccountEntity.getId());
                userAccountRecordEntity.setMoney(BigDecimal.valueOf(userAccountDto.getAmount()));
                userAccountRecordMapper.insert(userAccountRecordEntity);
                log.info("当前待提现的金额为:{} 用户账户余额为:{}", userAccountDto.getAmount(), userAccountEntity.getAmount());
            } else {

            }

        } else {
            throw new Exception("账户不存在或账户余额不足");
        }
    }

    @Override
    public void takeMoneyByZk(UserAccountDto userAccountDto) throws Exception {
        InterProcessMutex mutex = new InterProcessMutex(curatorFramework, "/redisdemo/zklock/balance-lock");
        try {
            //采用互斥锁组件尝试获取分布式锁,其中尝试最大时间在这里设置10秒
            if (mutex.acquire(10L, TimeUnit.SECONDS)) {
                QueryWrapper<UserAccountEntity> queryWrapper = new QueryWrapper();
                queryWrapper.eq("USER_ID", userAccountDto.getUserId());
                UserAccountEntity userAccountEntity = userAccountMapper.selectOne(queryWrapper);
                if (userAccountEntity != null && (userAccountEntity.getAmount().doubleValue() - userAccountDto.getAmount()) >= 0) {
                    userAccountEntity.setAmount(BigDecimal.valueOf(userAccountEntity.getAmount().doubleValue() - userAccountDto.getAmount()));
                    userAccountMapper.updateById(userAccountEntity);
                    UserAccountRecordEntity userAccountRecordEntity = new UserAccountRecordEntity();
                    userAccountRecordEntity.setCreateTime(new Date());
                    userAccountRecordEntity.setAccountId(userAccountEntity.getId());
                    userAccountRecordEntity.setMoney(BigDecimal.valueOf(userAccountDto.getAmount()));
                    userAccountRecordMapper.insert(userAccountRecordEntity);
                    log.info("当前待提现的金额为:{} 用户账户余额为:{}", userAccountDto.getAmount(), userAccountEntity.getAmount());

                } else {
                    throw new Exception("账户不存在或账户余额不足");
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            //释放锁
            mutex.release();

        }

    }

    @Override
    public void takeMoneyByRedisson(UserAccountDto userAccountDto) throws Exception {
        final String lockName = "redissonOneLock-"+userAccountDto.getUserId();
        //获取分布式锁
        RLock rLock = redissonClient.getLock(lockName);
        try {
            //不是常用的
            //rLock.lock();
            //常用：即上锁后不管何种状况,10秒后自动释放
            rLock.lock(10L,TimeUnit.SECONDS);
            //可重入锁设置,未获取到锁就一直尝试,最多到100秒,上锁成功则10秒后自动解锁
            rLock.tryLock(100L,10L,TimeUnit.SECONDS);
            QueryWrapper<UserAccountEntity> queryWrapper = new QueryWrapper();
            queryWrapper.eq("USER_ID", userAccountDto.getUserId());
            UserAccountEntity userAccountEntity = userAccountMapper.selectOne(queryWrapper);
            if (userAccountEntity != null && (userAccountEntity.getAmount().doubleValue() - userAccountDto.getAmount()) >= 0) {
                userAccountEntity.setAmount(BigDecimal.valueOf(userAccountEntity.getAmount().doubleValue() - userAccountDto.getAmount()));
                userAccountMapper.updateById(userAccountEntity);
                UserAccountRecordEntity userAccountRecordEntity = new UserAccountRecordEntity();
                userAccountRecordEntity.setCreateTime(new Date());
                userAccountRecordEntity.setAccountId(userAccountEntity.getId());
                userAccountRecordEntity.setMoney(BigDecimal.valueOf(userAccountDto.getAmount()));
                userAccountRecordMapper.insert(userAccountRecordEntity);
                log.info("当前待提现的金额为:{} 用户账户余额为:{}", userAccountDto.getAmount(), userAccountEntity.getAmount());

            } else {
                throw new Exception("账户不存在或账户余额不足");
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (rLock != null) {
                rLock.unlock();
                //在某些严格业务场景下,可以调用强制释放分布式锁的方法
                //rLock.forceUnlock();
            }

        }
    }
}
