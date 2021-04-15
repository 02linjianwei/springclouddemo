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
@KeySequence("seq_RED_DETAIL")
@TableName("RED_DETAIL")
public class RedDetailEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID",type= IdType.INPUT)
    private Long id;

    /**
     * �����id
     */
    @TableField("RECORD_ID")
    private Long recordId;

    /**
     * ��
     */
    @TableField("AMOUNT")
    private BigDecimal amount;

    @TableField("IS_ACTIVE")
    private Integer isActive;

    @TableField("CREATE_TIME")
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "RedDetailEntity{" +
        "id=" + id +
        ", recordId=" + recordId +
        ", amount=" + amount +
        ", isActive=" + isActive +
        ", createTime=" + createTime +
        "}";
    }
}
