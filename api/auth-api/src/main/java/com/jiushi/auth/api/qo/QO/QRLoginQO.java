package com.jiushi.auth.api.qo.QO;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class QRLoginQO {

    //请求授权方的clientId
    @NotBlank(message = "请求授权方的clientId不能为空")
    private String clientId ;

    //2.2.0传UD_REAL_NAME。请求授权方的范围，多个用逗号隔开，目前系统支持,UD_REAL_NAME
    @NotBlank(message = "请求授权方的范围不能为空")
    private String scope;


    //重定向的地址 跳转到授权方的地址，授权成功会携带code跳转
    @NotBlank(message = "重定向的地址不能为空")
    private String redirectUri;

    @Length(max = 1024,message = "state最大1024长度")
    private String state;
}