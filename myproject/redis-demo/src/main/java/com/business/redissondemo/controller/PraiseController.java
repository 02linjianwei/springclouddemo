package com.business.redissondemo.controller;


import com.business.balanceyw.controller.DataBaseLockController;
import com.business.redissondemo.dto.PraiseDto;
import com.business.redissondemo.service.IPraiseService;
import com.common.BaseResponse;
import com.common.StatusCode;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-22
 */
@RestController
public class PraiseController {
    private static final Logger log = LoggerFactory.getLogger(PraiseController.class);
    private static final  String prefix = "/blog/praise/";
    @Autowired
    private IPraiseService praiseService;
    @RequestMapping(prefix+"/add")
    public BaseResponse addPraise(@Validated @RequestBody PraiseDto praiseDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new BaseResponse(StatusCode.InvaildParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap = Maps.newHashMap();
        try {
            //praiseService.addPraise(praiseDto);
            praiseService.addPraiseLock(praiseDto);
            Long total = praiseService.getBlogPraiseTotal(praiseDto.getBlogId());
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        response.setData(resMap);
        return response;
    }
    @RequestMapping(prefix+"/cancel")
    public BaseResponse cancelPraise(@RequestBody @Validated PraiseDto praiseDto,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new BaseResponse(StatusCode.InvaildParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap = Maps.newHashMap();
        try {
            praiseService.cancelPraise(praiseDto);
            Long total = praiseService.getBlogPraiseTotal(praiseDto.getBlogId());
            resMap.put("praiseTotal",total);
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}

