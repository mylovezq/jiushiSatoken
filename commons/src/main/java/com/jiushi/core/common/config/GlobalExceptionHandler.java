package com.jiushi.core.common.config;

import com.jiushi.core.common.api.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {



    // 对特定异常进行捕获处理
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResultVO handleIllegalArgumentException(HttpServletRequest request, IllegalArgumentException ex) {

        return ResultVO.failBiz("参数异常："+ex.getMessage());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResultVO handleGeneralException(HttpServletRequest request, Exception ex) {

        return ResultVO.failBiz("系统异常："+ex.getMessage());
    }

    // 可以添加更多针对特定异常类型的处理方法
}

