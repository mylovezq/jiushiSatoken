package com.jiushi.core.common.api;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class ResultVO<T> implements Serializable {

    public static final int SUCCESS_CODE = 200;//成功码.
    public static final String SUCCESS_MESSAGE = "操作成功";//成功信息.

    public static final int ERROR_CODE = 500000;//错误码.
    public static final String ERROR_MESSAGE = "系统异常";//错误信息.

    private int code;
    private String msg;
    private T data;

    public ResultVO(){}

    private ResultVO(int code, String msg, T data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ResultVO<T> success(T data){
        return new ResultVO<>(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static <T> ResultVO<T> success(String msg, T data){
        return new ResultVO<>(SUCCESS_CODE, msg, data);
    }


    public static <T> ResultVO<T> defaultError(){
        return new ResultVO<>(ERROR_CODE, ERROR_MESSAGE,null);
    }
    public static <T> ResultVO<T> failBiz(String errMsg, T data){
        return new ResultVO<>(500, errMsg,data);
    }
    public static <T> ResultVO<T> failBiz(String errMsg){
        return new ResultVO<>(500, errMsg,null);
    }

    public boolean hasError(){
        // 状态码 !=200 说明有错误.
        return this.code != SUCCESS_CODE;
    }

}
