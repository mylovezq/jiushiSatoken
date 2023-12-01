package com.jiushi.auth.api.dto;


import com.jiushi.auth.api.constant.QRLoginStatusEnum;
import lombok.Data;

/**
 * 记录二维码的状态流转 放入reids 只在内部流转
 *
 * @author dengmingyang
 */
@Data
public class QRLoginDTO {

    private String responseType;
    private String clientId ;
    private String scope ;
    private String redirectUri;
    private String state;


    //登录的用户id
    private String clientUserNo ;
    //状态
    private QRLoginStatusEnum qrLoginStatus;
}
