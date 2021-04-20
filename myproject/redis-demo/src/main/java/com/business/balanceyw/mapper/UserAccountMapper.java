package com.business.balanceyw.mapper;

import com.business.balanceyw.entity.UserAccountEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-20
 */
@Mapper
public interface UserAccountMapper extends BaseMapper<UserAccountEntity> {
  int  updateByPKVersion(Map map);
}
