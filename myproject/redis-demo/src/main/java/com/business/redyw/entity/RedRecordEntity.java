package com.business.redyw.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * �������
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-14
 */
@KeySequence("seq_RED_RECORD")
@TableName("RED_RECORD")
public class RedRecordEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID",type= IdType.INPUT)
    private Long id;

    /**
     * ���id
     */
    @TableField("USER_ID")
    private Long userId;

    /**
     * ��ȫ���һ����
     */
    @TableField("RED_PACKET")
    private String redPacket;

    /**
     * ��
     */
    @TableField("TOTAL")
    private Long total;

    @TableField("AMOUT")
    private BigDecimal amout;

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

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public BigDecimal getAmout() {
        return amout;
    }

    public void setAmout(BigDecimal amout) {
        this.amout = amout;
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
        return "RedRecordEntity{" +
        "id=" + id +
        ", userId=" + userId +
        ", redPacket=" + redPacket +
        ", total=" + total +
        ", amout=" + amout +
        ", isActive=" + isActive +
        ", createTime=" + createTime +
        "}";
    }
}
