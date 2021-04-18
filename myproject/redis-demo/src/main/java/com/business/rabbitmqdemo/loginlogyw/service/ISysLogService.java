package com.business.rabbitmqdemo.loginlogyw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.business.rabbitmqdemo.loginlogyw.dto.UserLoginDto;
import com.business.rabbitmqdemo.loginlogyw.entity.SysLogEntity;

/**
 * <p>
 * 日志记录表 服务类
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-18
 */
public interface ISysLogService  {
void  recordLog(UserLoginDto userLoginDto);
}
