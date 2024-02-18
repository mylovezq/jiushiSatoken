package com.jiushi.pay.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.jiushi.core.common.config.rocketMq.PayRocketmqSendCallback;
import com.jiushi.pay.api.dto.PayInfoDTO;
import com.jiushi.pay.api.fegin.PayMsgFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class JiushiRocketmqProducer {
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private PayMsgFeignClient payMsgFeignClient;


    public void send() {

        PayInfoDTO payInfoDTO = new PayInfoDTO();
        payInfoDTO.setId(IdWorker.getId());
        payInfoDTO.setPayType("test");
        payInfoDTO.setPayUserId("99999");

        //发送订单变更mq消息
        rocketMQTemplate.asyncSend("jiushiPayTopic:jiushiTag", JSONUtil.toJsonStr(payInfoDTO),
                new PayRocketmqSendCallback(payMsgFeignClient,payInfoDTO));

    }
}
