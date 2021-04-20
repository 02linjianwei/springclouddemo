package com.business.balanceyw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.business.balanceyw.controller.DataBaseLockController;
import com.business.balanceyw.dto.UserAccountDto;
import com.business.balanceyw.entity.UserAccountEntity;
import com.business.balanceyw.entity.UserAccountRecordEntity;
import com.business.balanceyw.mapper.UserAccountMapper;
import com.business.balanceyw.mapper.UserAccountRecordMapper;
import com.business.balanceyw.service.IDataBaseLockService;
import com.common.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class DataBaseLockServiceImpl implements IDataBaseLockService {
    private static final Logger log = LoggerFactory.getLogger(DataBaseLockServiceImpl.class);
    @Autowired
    private UserAccountMapper userAccountMapper;
    @Autowired
    private UserAccountRecordMapper userAccountRecordMapper;

    @Override
    public void takeMoney(UserAccountDto userAccountDto) throws Exception {
        QueryWrapper<UserAccountEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("USER_ID",userAccountDto.getUserId());
        UserAccountEntity userAccountEntity = userAccountMapper.selectOne(queryWrapper);
        if (userAccountEntity !=null && (userAccountEntity.getAmount().doubleValue()-userAccountDto.getAmount()) >= 0) {
            Map map = new HashMap();
            map.put("money",BigDecimal.valueOf(userAccountDto.getAmount()));
            map.put("id",userAccountEntity.getId());
            map.put("version",userAccountEntity.getVersion());
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

        }else {
            throw new Exception("账户不存在或账户余额不足");
        }
    }
}
