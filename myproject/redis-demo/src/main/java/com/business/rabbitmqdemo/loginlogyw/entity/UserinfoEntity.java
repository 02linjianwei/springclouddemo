package com.business.rabbitmqdemo.loginlogyw.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-18
 */
@KeySequence("SEQ_USERINFO")
@TableName("USERINFO")
public class UserinfoEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.INPUT)
    private Long id;

    @TableField("USER_NAME")
    private String userName;

    @TableField("PASSWORD")
    private String password;

    @TableField("CREATE_TIME")
    private LocalDateTime createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserinfoEntity{" +
        "id=" + id +
        ", userName=" + userName +
        ", password=" + password +
        ", createTime=" + createTime +
        "}";
    }
}
