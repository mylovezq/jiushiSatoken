package com.jiushi.pay.controller;

import cn.hutool.core.bean.BeanUtil;
import com.jiushi.pay.api.dto.PayMsgDTO;
import com.jiushi.pay.dao.PayMsg;
import com.jiushi.pay.service.PayMsgService;
import fegin.PayMsgFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pay")
public class PayMsgController implements PayMsgFeignClient {

    @Autowired
    private PayMsgService payMsgService;

    @Override
    public void save(PayMsgDTO payMsg) {
        payMsgService.save(BeanUtil.copyProperties(payMsg, PayMsg.class));
    }
}
