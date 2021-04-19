package com.business.rabbitmqdemo.orderyw.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-19
 */
@KeySequence("SEQ_USER_ORDER")
@TableName("USER_ORDER")
public class UserOrderEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.INPUT)
    private Long id;

    @TableField("ORDER_NO")
    private String orderNo;

    @TableField("USER_ID")
    private Long userId;

    /**
     * 1=�����������ȡ�
     */
    @TableField("STATUS")
    private String status;

    @TableField("IS_ACTIVE")
    private String isActive;

    @TableField("CREATE_TIME")
    private Date createTime;

    @TableField("UPDATE_TIME")
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "UserOrderEntity{" +
        "id=" + id +
        ", orderNo=" + orderNo +
        ", userId=" + userId +
        ", status=" + status +
        ", isActive=" + isActive +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
