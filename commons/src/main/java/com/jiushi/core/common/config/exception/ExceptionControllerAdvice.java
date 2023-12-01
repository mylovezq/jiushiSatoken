package com.jiushi.core.common.config.exception;

import cn.dev33.satoken.util.SaResult;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(99999999)
@RestControllerAdvice(basePackages = "com.jiushi")
public class ExceptionControllerAdvice {


    /**
     * 用来拦截valid的校验
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public SaResult handleException(Exception e) {

        return SaResult.error(e.getMessage());
    }
}

