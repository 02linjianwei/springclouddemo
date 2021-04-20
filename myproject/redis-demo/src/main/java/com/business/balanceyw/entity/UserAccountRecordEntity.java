package com.business.balanceyw.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-20
 */
@KeySequence("SEQ_USER_ACCOUNT_RECORD")
@TableName("USER_ACCOUNT_RECORD")
public class UserAccountRecordEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.INPUT)
    private Long id;

    @TableField("ACCOUNT_ID")
    private Long accountId;

    @TableField("MONEY")
    private BigDecimal money;

    @TableField("CREATE_TIME")
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserAccountRecordEntity{" +
        "id=" + id +
        ", accountId=" + accountId +
        ", money=" + money +
        ", createTime=" + createTime +
        "}";
    }
}
