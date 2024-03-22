package com.jiushi.user.controller;


import cn.hutool.core.bean.BeanUtil;
import com.jiushi.user.api.entity.UserInfo;
import com.jiushi.user.api.fegin.UserInfoFeign;
import com.jiushi.user.api.vo.PhoneAndPasswordQO;
import com.jiushi.user.api.vo.UserInfoVO;
import com.jiushi.user.service.IUserInfoService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author denmgmingyang
 * @since 2024-03-22
 */
@RestController
public class UserInfoController implements UserInfoFeign{

    @Resource
    private IUserInfoService userInfoService;


    private static BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Override
    public UserInfoVO getUserPhoneAndPassword(PhoneAndPasswordQO phoneAndPasswordQO) {
        UserInfo userInfo = userInfoService.lambdaQuery()
                .eq(UserInfo::getPhone, phoneAndPasswordQO.getPhone()).one();
        if (userInfo == null|| !bCryptPasswordEncoder.matches(phoneAndPasswordQO.getPassword(), userInfo.getPassword())){
            return null;
        }
        return BeanUtil.copyProperties(userInfo, UserInfoVO.class);
    }

    public static void main(String[] args) {
        System.out.println(bCryptPasswordEncoder.encode("123456"));
    }
}
