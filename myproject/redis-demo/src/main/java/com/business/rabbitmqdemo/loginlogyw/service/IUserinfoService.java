package com.business.rabbitmqdemo.loginlogyw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.business.rabbitmqdemo.loginlogyw.dto.UserLoginDto;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-18
 */
public interface IUserinfoService  {
Boolean login(UserLoginDto userLoginDto);
}
