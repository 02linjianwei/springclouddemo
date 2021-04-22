package com.business.redissondemo.entity;

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
 * @since 2021-04-22
 */
@KeySequence("SEQ_PRAISE")
@TableName("PRAISE")
public class PraiseEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.INPUT)
    private Long id;

    @TableField("BLOG_ID")
    private Long blogId;

    @TableField("USER_ID")
    private Long userId;

    @TableField("PRAISE_TIME")
    private Date praiseTime;

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

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getPraiseTime() {
        return praiseTime;
    }

    public void setPraiseTime(Date praiseTime) {
        this.praiseTime = praiseTime;
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
        return "PraiseEntity{" +
        "id=" + id +
        ", blogId=" + blogId +
        ", userId=" + userId +
        ", praiseTime=" + praiseTime +
        ", status=" + status +
        ", isActive=" + isActive +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
