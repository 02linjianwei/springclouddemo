package com.business.redyw.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-14
 */
@KeySequence("seq_RED_ROB_RECORD")
@TableName("RED_ROB_RECORD")
public class RedRobRecordEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID",type= IdType.INPUT)
    private Long id;

    @TableField("USER_ID")
    private Long userId;

    /**
     * ������
     */
    @TableField("RED_PACKET")
    private String redPacket;

    /**
     * ����
     */
    @TableField("AMOUNT")
    private BigDecimal amount;

    @TableField("ROB_TIME")
    private Date robTime;

    @TableField("IS_ACTIVE")
    private String isActive;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRedPacket() {
        return redPacket;
    }

    public void setRedPacket(String redPacket) {
        this.redPacket = redPacket;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getRobTime() {
        return robTime;
    }

    public void setRobTime(Date robTime) {
        this.robTime = robTime;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "RedRobRecordEntity{" +
        "id=" + id +
        ", userId=" + userId +
        ", redPacket=" + redPacket +
        ", amount=" + amount +
        ", robTime=" + robTime +
        ", isActive=" + isActive +
        "}";
    }
}
