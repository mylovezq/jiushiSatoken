package com.jiushi.pay.api.dto;


import lombok.Data;

import java.util.Map;

@Data
public class PayMsgDTO {


    private Long id;

    private String msgBody; // 假设msg_body是JSON格式存储，用Map解析

    private String msgId;

    private String status;

}
