package com.business.rabbitmqdemo.loginlogyw.controller;


import com.business.rabbitmqdemo.loginlogyw.dto.UserLoginDto;
import com.business.rabbitmqdemo.loginlogyw.service.IUserinfoService;
import com.common.BaseResponse;
import com.common.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-18
 */
@RestController
public class UserinfoController {
    private static final Logger log = LoggerFactory.getLogger(UserinfoController.class);
    @Autowired
    private IUserinfoService userinfoService;

    public BaseResponse login(@Validated UserLoginDto userLoginDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new BaseResponse(StatusCode.InvaildParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            Boolean res = userinfoService.login(userLoginDto);
            if (res) {
                response = new BaseResponse(StatusCode.Success.getCode(), "登录成功");
            } else {
                response = new BaseResponse(StatusCode.Fail.getCode(),"账号或密码不匹配");
            }
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}

