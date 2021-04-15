package com.business.redyw.service;

import com.business.redyw.dto.RedPacketDto;

import java.util.Map;

public interface IRedPacketService {
    String handOut(RedPacketDto redPacketDto) throws Exception;

    Map rob(Integer userId, String redId) throws Exception;

}
