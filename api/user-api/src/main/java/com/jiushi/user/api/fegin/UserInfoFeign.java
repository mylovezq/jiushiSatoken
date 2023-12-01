package com.jiushi.user.api.fegin;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user", path = "/user")
public interface UserInfoFeign {
    // 声明需要调用的接口方法名
    @GetMapping("/currentUser")
    Object currentUser();
}