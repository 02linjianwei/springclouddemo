package com.business.rabbitmqdemo.orderyw.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-19
 */
@KeySequence("SEQ_MQ_ORDER")
@TableName("MQ_ORDER")
public class MqOrderEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.INPUT)
    private Long id;

    @TableField("ORDER_ID")
    private Long orderId;

    @TableField("BUSINESS_TIME")
    private Date businessTime;

    @TableField("MEMO")
    private String memo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getBusinessTime() {
        return businessTime;
    }

    public void setBusinessTime(Date businessTime) {
        this.businessTime = businessTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Override
    public String toString() {
        return "UserOrderEntity{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", businessTime=" + businessTime +
        ", memo=" + memo +
        "}";
    }
}
