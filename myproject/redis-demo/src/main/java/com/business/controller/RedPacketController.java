package com.business.controller;


import com.business.dto.RedPacketDto;
import com.business.service.IRedPacketService;
import com.common.BaseResponse;
import com.common.StatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * ������� 前端控制器
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-14
 */
@RestController
public class RedPacketController {
    private static final Logger log = LoggerFactory.getLogger(RedPacketController.class);
    private static final String prefix = "red/packet/";
    @Autowired
    private IRedPacketService redPacketService;

    /**
     * 发红包
     * @param dto
     * @param result
     * @return
     */
    @RequestMapping(value = prefix + "hand/out")
    public BaseResponse handOut(@Validated @RequestBody RedPacketDto dto, BindingResult result) {
        if (result.hasErrors()) {
            return new BaseResponse(StatusCode.InvaildParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            String redId = redPacketService.handOut(dto);
            response.setData(redId);
        } catch (Exception e) {

            log.error("发红包异常:dto={}", dto, e.fillInStackTrace());
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

    /**
     * 抢红包
     * @param userId
     * @param redId
     * @return
     */
    @RequestMapping(value = prefix+"/rob/{userId}/{redId}")
    public BaseResponse rob(@PathVariable Integer userId, @PathVariable String redId) {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {

            Integer result = redPacketService.rob(userId, redId);
            if (result != null) {
                response.setData(result);
            } else {
                response = new BaseResponse(StatusCode.Fail.getCode(), "红包已被抢光");
            }
        } catch (Exception e) {
            log.error("抢红包发生异常:userId={} redId={}",userId,redId,e.fillInStackTrace());
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }
}

