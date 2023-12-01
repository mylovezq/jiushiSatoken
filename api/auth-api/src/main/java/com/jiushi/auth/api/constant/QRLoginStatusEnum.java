package com.jiushi.auth.api.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author dengmingyang
 * @since 2023/11/25 20:58
 */
@Getter
@AllArgsConstructor
public enum QRLoginStatusEnum {


    TIME_OUT_QRCODE(0,"已过期"),
    NOT_SCAN_QRCODE(1,"未扫码"),
    HAD_SCAN_QRCODE(2,"已扫码"),
    HAD_AUTHORIZE_QRCODE(3,"已授权"),
    CANCEL_AUTHORIZE_QRCODE(4,"取消授权"),
    ;

    private int code;

    private String desc;
}
