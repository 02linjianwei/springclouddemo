package com.business.rabbitmqdemo.orderyw.service;


import com.business.rabbitmqdemo.orderyw.dto.UserOrderDto;
import com.business.rabbitmqdemo.orderyw.entity.UserOrderEntity;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-19
 */
public interface IUserOrderService  {
void pushUserOrder(UserOrderDto userOrderDto);
void updateUserOrderRecord(UserOrderEntity userOrderEntity);
}
