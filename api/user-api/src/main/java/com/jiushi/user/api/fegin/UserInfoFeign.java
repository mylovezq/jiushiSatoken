package com.jiushi.user.api.fegin;


import com.jiushi.user.api.vo.PhoneAndPasswordQO;
import com.jiushi.user.api.vo.UserInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user")
public interface UserInfoFeign {

    // 声明需要调用的接口方法名
    @PostMapping( "/userInfo/getUserPhoneAndPassword")
    UserInfoVO getUserPhoneAndPassword(@RequestBody PhoneAndPasswordQO phoneAndPasswordQO);
}