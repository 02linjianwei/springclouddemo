package com.business.balanceyw.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-20
 */
@KeySequence("SEQ_USER_ACCOUNT")
@TableName("USER_ACCOUNT")
public class UserAccountEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.INPUT)
    private Long id;

    @TableField("USER_ID")
    private Long userId;

    @TableField("AMOUNT")
    private BigDecimal amount;

    @TableField("VERSION")
    private Long version;

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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "UserAccountEntity{" +
        "id=" + id +
        ", userId=" + userId +
        ", amount=" + amount +
        ", version=" + version +
        ", isActive=" + isActive +
        "}";
    }
}
