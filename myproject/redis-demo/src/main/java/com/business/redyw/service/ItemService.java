package com.business.redyw.service;

import com.business.redyw.entity.ItemEntity;

import java.io.IOException;

/**
 * <p>
 * 商品信息表 服务类
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-12
 */
public interface ItemService   {
ItemEntity getItemInfo(String itemcode) throws IOException;
}
