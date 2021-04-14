package com.business.service;

import com.business.dto.RedPacketDto;

import java.util.List;

public interface IRedService {

    void recordRedPacket(RedPacketDto redPacketDto, String redId, List<Integer> list);

    void reCordfRobPacket(Integer userId, String redId, Integer amount);
}
