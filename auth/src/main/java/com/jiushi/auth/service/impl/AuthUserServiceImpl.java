package com.jiushi.auth.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.jiushi.auth.api.constant.DeviceType;
import com.jiushi.user.api.vo.PhoneAndPasswordQO;
import com.jiushi.auth.api.vo.UserAuthLoginInfoVO;
import com.jiushi.auth.api.vo.UserBaseInfoVO;
import com.jiushi.auth.service.IAuthUserService;
import com.jiushi.user.api.fegin.UserInfoFeign;
import com.jiushi.user.api.vo.UserInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
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
public class AuthUserServiceImpl implements IAuthUserService {

    @Resource
    private UserInfoFeign userInfoFeign;
    @Override
    public UserAuthLoginInfoVO loginByPhoneAndPassword(PhoneAndPasswordQO phoneAndPasswordQO) {

        UserInfoVO userPhoneAndPassword = userInfoFeign.getUserPhoneAndPassword(phoneAndPasswordQO);
        Assert.notNull(userPhoneAndPassword,"账户或密码错误");
        SaLoginModel model = new SaLoginModel();
        model.setExtra("currTime", LocalDateTime.now());
        model.setDevice(DeviceType.MINI_PRO.name());
        model.setExtra(device, DeviceType.PC.name());
        log.info("用户信息{}",userPhoneAndPassword);
        StpUtil.login(userPhoneAndPassword.getId(),model);
        return new UserAuthLoginInfoVO(StpUtil.getTokenInfo().getTokenValue(), BeanUtil.copyProperties(userPhoneAndPassword, UserBaseInfoVO.class));
    }

    public static void main(String[] args) {

    }
}
