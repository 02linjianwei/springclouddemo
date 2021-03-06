package com.business.redyw.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.business.redyw.entity.ItemEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 商品信息表 Mapper 接口
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-12
 */
@Mapper
public interface ItemMapper extends BaseMapper<ItemEntity> {
   ItemEntity selectByCode(String code);
}
