package com.jiushi.auth.api.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DeviceType {

    MINI_PRO(0,"小程序"),
    PC(1,"客户端"),
    ;
    private Integer code;

    private String desc;
}
