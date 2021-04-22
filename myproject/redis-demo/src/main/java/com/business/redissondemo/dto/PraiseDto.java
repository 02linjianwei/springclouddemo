package com.business.redissondemo.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
@Data
@ToString
public class PraiseDto implements Serializable {
    @NotNull
    private Long blogId;//博客id
    @NotNull
    private Long userId;//

    public PraiseDto() {

    }

    public PraiseDto(Long blogId, Long userId) {
        this.blogId = blogId;
        this.userId = userId;
    }
}
