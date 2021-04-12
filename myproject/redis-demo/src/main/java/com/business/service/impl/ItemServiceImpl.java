package com.business.service.impl;

import com.business.entity.ItemEntity;
import com.business.mapper.ItemMapper;
import com.business.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品信息表 服务实现类
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-12
 */
@Service
public class ItemServiceImpl  implements ItemService {
    @Autowired
    private ItemMapper itemMapper;
    @Override
    public ItemEntity getItemInfo() {
        return null;
    }
}
