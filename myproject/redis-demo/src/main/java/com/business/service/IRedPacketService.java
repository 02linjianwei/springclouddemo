package com.business.service;

import com.business.dto.RedPacketDto;

import java.util.List;

public interface IRedPacketService {
    String handOut(RedPacketDto redPacketDto) throws Exception;

    Integer rob(Integer userId, String redId);

    void recordRedPacket(RedPacketDto redPacketDto, String redId, List<Integer> list);

    void reCordfRobPacket(Integer userId, String redId, Integer amount);
}
