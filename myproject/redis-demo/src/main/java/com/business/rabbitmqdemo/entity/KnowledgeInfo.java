package com.business.rabbitmqdemo.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 确认消费实体对象信息
 */
@Data
@ToString
public class KnowledgeInfo implements Serializable {
    private Integer id;//消息标识id
    private String mode;//模式名称
    private String code;//模式编码

}
