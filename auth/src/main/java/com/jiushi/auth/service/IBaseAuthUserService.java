package com.jiushi.auth.service;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jiushi.auth.api.qo.PhoneAndPasswordQO;
import com.jiushi.auth.dao.BaseAuthUserDO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author denmgmingyang
 * @since 2023-12-01
 */
public interface IBaseAuthUserService extends IService<BaseAuthUserDO> {


    SaResult loginByPhoneAndPassword(PhoneAndPasswordQO phoneAndPasswordQO);

}
