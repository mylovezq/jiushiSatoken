package com.jiushi.user.api.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserInfoVO {

    private Long id;

    private Long phone;

    private String password;

    private String nickname;

    private String head;

    private String registerIp;

    private LocalDateTime registerTime;

    private String birthDay;

    private String info;
}
