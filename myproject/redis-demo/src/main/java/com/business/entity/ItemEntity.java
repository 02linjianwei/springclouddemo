package com.business.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 商品信息表
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-12
 */
@KeySequence("seq_ITEM")
@TableName("ITEM")
public class ItemEntity implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.INPUT)
    private Long id;

    @TableField("CODE")
    private String code;

    @TableField("NAME")
    private String name;

    @TableField("CREATE_TIME")
    private Date createtime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "ItemEntity{" +
        "id=" + id +
        ", code=" + code +
        ", name=" + name +
        ", createtime=" + createtime +
        "}";
    }
}
