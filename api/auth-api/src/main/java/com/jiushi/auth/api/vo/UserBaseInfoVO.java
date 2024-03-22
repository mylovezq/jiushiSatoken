package com.jiushi.auth.api.vo;

import lombok.Data;

@Data
public class UserBaseInfoVO {
    private Long  phone;
    private String nickname;
    private String head;
    private String birthDay;
    private String info;
    private String loginIp;
}