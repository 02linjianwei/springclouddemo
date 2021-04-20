package com.business.balanceyw.service;

import com.business.balanceyw.dto.UserAccountDto;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author AutoGen
 * @since 2021-04-20
 */
public interface IDataBaseLockService {
void takeMoney(UserAccountDto userAccountDto) throws Exception;
}
