package com.business.rabbitmqdemo.loginlogyw.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 日志记录表
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-18
 */
@KeySequence("SEQ_SYS_LOG")
@TableName("SYS_LOG")
public class SysLogEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.INPUT)
    private Long id;

    @TableField("USER_ID")
    private Long userId;

    /**
     * 所属模块
     */
    @TableField("MODULE")
    private String module;

    /**
     * 操作数据
     */
    @TableField("DATA")
    private String data;

    /**
     * 备注
     */
    @TableField("MEMO")
    private String memo;

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

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SysLogEntity{" +
        "id=" + id +
        ", userId=" + userId +
        ", module=" + module +
        ", data=" + data +
        ", memo=" + memo +
        ", createTime=" + createTime +
        "}";
    }
}
