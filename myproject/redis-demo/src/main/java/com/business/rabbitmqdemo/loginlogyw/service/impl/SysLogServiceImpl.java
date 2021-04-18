package com.business.rabbitmqdemo.loginlogyw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.business.rabbitmqdemo.loginlogyw.dto.UserLoginDto;
import com.business.rabbitmqdemo.loginlogyw.entity.SysLogEntity;
import com.business.rabbitmqdemo.loginlogyw.mapper.SysLogMapper;
import com.business.rabbitmqdemo.loginlogyw.service.ISysLogService;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 日志记录表 服务实现类
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-18
 */
@Service
public class SysLogServiceImpl  implements ISysLogService {
    private static final Logger log = LoggerFactory.getLogger(SysLogServiceImpl.class);
    @Autowired
    private SysLogMapper sysLogMapper;
    @Override
    @Async
    public void recordLog(UserLoginDto userLoginDto) {
        SysLogEntity sysLogEntity = new SysLogEntity();
        BeanUtils.copyProperties(userLoginDto,sysLogEntity);
        sysLogEntity.setCreateTime(new Date());
        sysLogMapper.insert(sysLogEntity);
    }
}
