package com.jiushi.auth.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiushi.auth.api.constant.DeviceType;
import com.jiushi.auth.api.qo.PhoneAndPasswordQO;
import com.jiushi.auth.dao.BaseAuthUserDO;
import com.jiushi.auth.dao.mapper.BaseAuthUserMapper;
import com.jiushi.auth.service.IBaseAuthUserService;
import com.jiushi.core.common.config.utils.AesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

import static com.jiushi.auth.api.constant.LoginConstant.clientType;
import static com.jiushi.auth.api.constant.LoginConstant.device;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author denmgmingyang
 * @since 2023-12-01
 */
@Service
@Slf4j
public class BaseAuthUserServiceImpl extends ServiceImpl<BaseAuthUserMapper, BaseAuthUserDO> implements IBaseAuthUserService {

    @Override
    public SaResult loginByPhoneAndPassword(PhoneAndPasswordQO phoneAndPasswordQO) {
        BaseAuthUserDO baseAuthUserDO = this.getOne(new LambdaQueryWrapper<BaseAuthUserDO>().eq(BaseAuthUserDO::getPhone, phoneAndPasswordQO.getPhone())
                .eq(BaseAuthUserDO::getPassword, AesUtil.encryptBase64(phoneAndPasswordQO.getPassword()))
                .eq(BaseAuthUserDO::getClientType, phoneAndPasswordQO.getClientType()));
        Assert.notNull(baseAuthUserDO,"账户或密码错误");
        SaLoginModel model = new SaLoginModel();
        model.setExtra("currTime", LocalDateTime.now());
        model.setDevice(DeviceType.MINI_PRO.name());
        model.setExtra(device, DeviceType.MINI_PRO.name());
        model.setExtra(clientType, phoneAndPasswordQO.getClientType());

        log.info("用户信息{}",baseAuthUserDO);

        StpUtil.login(baseAuthUserDO.getId(),model);

        return SaResult.data(StpUtil.getTokenInfo().getTokenValue());
    }

    public static void main(String[] args) {
        System.out.println(IdWorker.getId());
        System.out.println(AesUtil.encryptBase64("mylovezq"));
    }
}
