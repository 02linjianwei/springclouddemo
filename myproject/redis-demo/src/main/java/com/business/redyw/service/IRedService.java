package com.business.redyw.service;

import com.business.redyw.dto.RedPacketDto;

import java.util.List;

public interface IRedService {

    void recordRedPacket(RedPacketDto redPacketDto, String redId, List<Integer> list);

    void reCordfRobPacket(Integer userId, String redId, Integer amount);
}
