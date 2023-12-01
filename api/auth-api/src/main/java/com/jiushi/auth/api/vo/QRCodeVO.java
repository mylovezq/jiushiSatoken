package com.jiushi.auth.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dengmingyang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QRCodeVO {

    //二维码图片
    private String qrCodeUuidUrl;

    //接入方名字
    private String clientName;

    //提示扫描的小程序的logo的url  即本平台的logo 用于页面回显
    private String platFormIcon;

    //客户端的logo 用于页面回显
    private String clientIcon;

    //提示扫描的小程序的名称  用于页面回显
    private String platFormName;
}
