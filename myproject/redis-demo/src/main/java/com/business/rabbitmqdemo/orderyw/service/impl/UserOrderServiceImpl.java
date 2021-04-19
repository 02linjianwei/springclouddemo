package com.business.rabbitmqdemo.orderyw.service.impl;

import com.business.rabbitmqdemo.common.DeadOrderPublisher;
import com.business.rabbitmqdemo.orderyw.controller.UserOrderController;
import com.business.rabbitmqdemo.orderyw.dto.UserOrderDto;
import com.business.rabbitmqdemo.orderyw.entity.MqOrderEntity;
import com.business.rabbitmqdemo.orderyw.entity.UserOrderEntity;
import com.business.rabbitmqdemo.orderyw.mapper.MqOrderMapper;
import com.business.rabbitmqdemo.orderyw.mapper.UserOrderMapper;
import com.business.rabbitmqdemo.orderyw.service.IUserOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-19
 */
@Service
public class UserOrderServiceImpl implements IUserOrderService {
    private static final Logger log = LoggerFactory.getLogger(UserOrderServiceImpl.class);
    @Autowired
    private UserOrderMapper userOrderMapper;
    @Autowired
    private MqOrderMapper mqOrderMapper;
    @Autowired
    private DeadOrderPublisher deadOrderPublisher;
    @Override
    public void pushUserOrder(UserOrderDto userOrderDto) {
        UserOrderEntity userOrderEntity = new UserOrderEntity();
        BeanUtils.copyProperties(userOrderDto,userOrderEntity);
        userOrderEntity.setStatus("1");
        userOrderEntity.setCreateTime(new Date());
        userOrderMapper.insert(userOrderEntity);
        deadOrderPublisher.sendAutoMsg(userOrderEntity.getId());
    }

    @Override
    public void updateUserOrderRecord(UserOrderEntity userOrderEntity) {
        try {
            if (userOrderEntity != null) {
                userOrderEntity.setIsActive("0");
                userOrderEntity.setUpdateTime(new Date());
                userOrderMapper.updateById(userOrderEntity);
                MqOrderEntity mqOrderEntity = new MqOrderEntity();
                mqOrderEntity.setBusinessTime(new Date());
                mqOrderEntity.setMemo("更新失效当前用户下单记录id,orderId="+userOrderEntity.getId());
                mqOrderEntity.setOrderId(userOrderEntity.getId());
                mqOrderMapper.insert(mqOrderEntity);
            }
        } catch (Exception e) {
            log.error("下单支付超时业务-更新用户下单记录的状态发生异常:",e.fillInStackTrace());
        }
    }
}
