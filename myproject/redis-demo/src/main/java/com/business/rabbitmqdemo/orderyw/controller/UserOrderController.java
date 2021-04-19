package com.business.rabbitmqdemo.orderyw.controller;


import com.business.rabbitmqdemo.loginlogyw.controller.UserinfoController;
import com.business.rabbitmqdemo.orderyw.dto.UserOrderDto;
import com.business.rabbitmqdemo.orderyw.service.IUserOrderService;
import com.common.BaseResponse;
import com.common.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-19
 */
@RestController
public class UserOrderController {
    private static final Logger log = LoggerFactory.getLogger(UserOrderController.class);
    private static final String prefix="user/order";
    @Autowired
    private IUserOrderService userOrderService;
    @RequestMapping(value = prefix+"/push")
    public BaseResponse login(@Validated @RequestBody UserOrderDto userOrderDto, BindingResult result) {
        if (result.hasErrors()) {
            return new BaseResponse(StatusCode.InvaildParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            userOrderService.pushUserOrder(userOrderDto);
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }
}

