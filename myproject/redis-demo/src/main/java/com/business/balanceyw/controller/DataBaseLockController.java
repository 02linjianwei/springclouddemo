package com.business.balanceyw.controller;


import com.business.balanceyw.dto.UserAccountDto;
import com.business.balanceyw.service.IDataBaseLockService;
import com.business.redyw.controller.ItemController;
import com.common.BaseResponse;
import com.common.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @since 2021-04-20
 */
@RestController
public class DataBaseLockController {
    private static final Logger log = LoggerFactory.getLogger(DataBaseLockController.class);
    private static final  String prefix = "/db";
    @Autowired
    private IDataBaseLockService dataBaseLockService;
    @RequestMapping(prefix+"/money/take")
    public BaseResponse takeMoney(@Validated @RequestBody UserAccountDto userAccountDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new BaseResponse(StatusCode.InvaildParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            //dataBaseLockService.takeMoneyByVersion(userAccountDto);//乐观锁
            dataBaseLockService.takeMoneyByZk(userAccountDto);//zk分布式锁
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }
}

