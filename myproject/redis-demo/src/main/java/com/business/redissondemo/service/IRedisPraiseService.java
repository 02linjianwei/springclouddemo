package com.business.redissondemo.service;

import com.business.redissondemo.dto.PraiseDto;

import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-22
 */
public interface IRedisPraiseService {
    void cachePraiseBlog(Long blogId, Long uId, String status) throws InterruptedException;

    Long getCacheTotalBlog(Long blogId);

    void rankBlogPraise();

    List<PraiseDto> getBlogPraiseRank();
}
