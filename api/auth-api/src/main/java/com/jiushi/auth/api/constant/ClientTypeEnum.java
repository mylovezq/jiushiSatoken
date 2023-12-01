package com.jiushi.auth.api.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ClientTypeEnum {

    USER(0,"用户"),;
    private Integer code;

    private String desc;

}
