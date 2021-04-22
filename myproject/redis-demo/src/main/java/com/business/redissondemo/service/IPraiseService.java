package com.business.redissondemo.service;

import com.business.redissondemo.dto.PraiseDto;
import com.business.redissondemo.entity.PraiseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-22
 */
public interface IPraiseService {
    void addPraise(PraiseDto praiseDto) throws InterruptedException;
    void addPraiseLock(PraiseDto praiseDto) throws InterruptedException;
    void cancelPraise(PraiseDto praiseDto) throws InterruptedException;
    Long getBlogPraiseTotal(Long blogId);
    Collection<PraiseDto> getRankNoRedisson();
    Collection<PraiseDto> getRankWithRedisson();


}
