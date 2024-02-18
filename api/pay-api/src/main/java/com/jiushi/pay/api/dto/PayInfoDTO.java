package com.jiushi.pay.api.dto;

import lombok.Data;

/**
 * 记录二维码的状态流转 放入reids 只在内部流转
 *
 * @author dengmingyang
 */
@Data
public class PayInfoDTO {


    private Long id;

    private String payType;

    private String payUserId;

    private String payStatus;

}
