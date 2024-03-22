package com.jiushi.auth.service;

import com.jiushi.user.api.vo.PhoneAndPasswordQO;
import com.jiushi.auth.api.vo.UserAuthLoginInfoVO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author denmgmingyang
 * @since 2023-12-01
 */
public interface IAuthUserService  {


    UserAuthLoginInfoVO loginByPhoneAndPassword(PhoneAndPasswordQO phoneAndPasswordQO);

}
