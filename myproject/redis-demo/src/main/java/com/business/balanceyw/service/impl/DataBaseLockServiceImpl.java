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
                log.info("???????????????????????????:{} ?????????????????????:{}", userAccountDto.getAmount(), userAccountEntity.getAmount());
            } else {

            }

        } else {
            throw new Exception("????????????????????????????????????");
        }
    }

    @Override
    public void takeMoneyByZk(UserAccountDto userAccountDto) throws Exception {
        InterProcessMutex mutex = new InterProcessMutex(curatorFramework, "/redisdemo/zklock/balance-lock");
        try {
            //?????????????????????????????????????????????,???????????????????????????????????????10???
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
                    log.info("???????????????????????????:{} ?????????????????????:{}", userAccountDto.getAmount(), userAccountEntity.getAmount());

                } else {
                    throw new Exception("????????????????????????????????????");
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            //?????????
            mutex.release();

        }

    }

    @Override
    public void takeMoneyByRedisson(UserAccountDto userAccountDto) throws Exception {
        final String lockName = "redissonOneLock-"+userAccountDto.getUserId();
        //??????????????????
        RLock rLock = redissonClient.getLock(lockName);
        try {
            //???????????????
            //rLock.lock();
            //???????????????????????????????????????,10??????????????????
            rLock.lock(10L,TimeUnit.SECONDS);
            //??????????????????,??????????????????????????????,?????????100???,???????????????10??????????????????
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
                log.info("???????????????????????????:{} ?????????????????????:{}", userAccountDto.getAmount(), userAccountEntity.getAmount());

            } else {
                throw new Exception("????????????????????????????????????");
            }

        } catch (Exception e) {
            throw e;
        } finally {
            if (rLock != null) {
                rLock.unlock();
                //??????????????????????????????,?????????????????????????????????????????????
                //rLock.forceUnlock();
            }

        }
    }
}
