package com.business.service.impl;

import com.business.dto.RedPacketDto;
import com.business.entity.RedDetailEntity;
import com.business.entity.RedRecordEntity;
import com.business.entity.RedRobRecordEntity;
import com.business.mapper.RedDetailMapper;
import com.business.mapper.RedRecordMapper;
import com.business.mapper.RedRobRecordMapper;
import com.business.service.IRedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Service
//@EnableAsync
public class RedServiceImpl implements IRedService {
    private static final Logger log = LoggerFactory.getLogger(RedServiceImpl.class);
    @Autowired
    private RedRecordMapper redRecordMapper;
    @Autowired
    private RedDetailMapper redDetailMapper;
    @Autowired
    private RedRobRecordMapper redRobRecordMapper;
    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void recordRedPacket(RedPacketDto redPacketDto, String redId, List<Integer> list) {
        RedDetailEntity redDetailEntity;
        RedRecordEntity redRecordEntity = new RedRecordEntity();
        redRecordEntity.setUserId(Long.valueOf(redPacketDto.getUserId()));
        redRecordEntity.setAmout(BigDecimal.valueOf(redPacketDto.getAmount()));
        redRecordEntity.setCreateTime(new Date());
        redRecordEntity.setRedPacket(redId);
        redRecordEntity.setTotal(Long.valueOf(redPacketDto.getTotal()));
        redRecordEntity.setIsActive(1);
        redRecordMapper.insert(redRecordEntity);
        for (Integer i : list) {
            redDetailEntity = new RedDetailEntity();
            redDetailEntity.setRecordId(redRecordEntity.getId());
            redDetailEntity.setAmount(BigDecimal.valueOf(i));
            redDetailEntity.setCreateTime(new Date());
            redDetailMapper.insert(redDetailEntity);
        }
    }

    @Override
    @Async
    @Transactional(rollbackFor = Exception.class)
    public void reCordfRobPacket(Integer userId, String redId, Integer amount) {
        RedRobRecordEntity redRobRecordEntity  = new RedRobRecordEntity();
        redRobRecordEntity.setUserId(Long.valueOf(userId));
        redRobRecordEntity.setRedPacket(redId);
        redRobRecordEntity.setAmount(new BigDecimal(amount));
        redRobRecordEntity.setRobTime(new Date());
        redRobRecordMapper.insert(redRobRecordEntity);
    }
}
