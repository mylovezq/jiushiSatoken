package com.jiushi.core.common.config.exception;

import cn.dev33.satoken.util.SaResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

@Order(99999999)
@Slf4j
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

        log.error("接口异常",e);

        return SaResult.error(e.getMessage());
    }
}

