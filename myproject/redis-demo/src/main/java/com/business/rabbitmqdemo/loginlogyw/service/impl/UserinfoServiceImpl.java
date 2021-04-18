package com.business.rabbitmqdemo.loginlogyw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.business.rabbitmqdemo.common.LoginPublisher;
import com.business.rabbitmqdemo.loginlogyw.controller.UserinfoController;
import com.business.rabbitmqdemo.loginlogyw.dto.UserLoginDto;
import com.business.rabbitmqdemo.loginlogyw.entity.UserinfoEntity;
import com.business.rabbitmqdemo.loginlogyw.mapper.UserinfoMapper;
import com.business.rabbitmqdemo.loginlogyw.service.IUserinfoService;
import com.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-18
 */
@Service
public class UserinfoServiceImpl  implements IUserinfoService {
    private static final Logger log = LoggerFactory.getLogger(UserinfoServiceImpl.class);
    @Autowired
    private UserinfoMapper userinfoMapper;
    @Autowired
    private LoginPublisher loginPublisher;

    @Override
    public Boolean login(UserLoginDto userLoginDto) {
        QueryWrapper<UserinfoEntity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("USER_NAME",userLoginDto.getUserName());
        queryWrapper.eq("PASSWORD",userLoginDto.getPassword());
        UserinfoEntity userinfoEntity = (UserinfoEntity) userinfoMapper.selectObjs(queryWrapper);
        if (userinfoEntity != null) {
            userLoginDto.setUserId(userinfoEntity.getId());
            loginPublisher.sendAutoMsg(userLoginDto);
            return true;
        }
        return false;
    }
}
