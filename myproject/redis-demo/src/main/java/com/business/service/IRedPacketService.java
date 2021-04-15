package com.business.service;

import com.business.dto.RedPacketDto;

import java.util.List;
import java.util.Map;

public interface IRedPacketService {
    String handOut(RedPacketDto redPacketDto) throws Exception;

    Map rob(Integer userId, String redId) throws Exception;

}
