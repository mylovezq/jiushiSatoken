package com.jiushi.core.common.api;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BusinessException extends RuntimeException {

    private int code;

    public BusinessException(String errMsg) {
        super(errMsg);
        this.code = 500;
    }
}