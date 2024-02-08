package com.jiushi.user.rocketMq;

import cn.hutool.json.JSONUtil;
import com.jiushi.pay.api.dto.PayInfoDTO;
import com.jiushi.pay.api.dto.PayMsgDTO;
import fegin.PayMsgFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;

@Slf4j
public class RocketmqSendCallback implements SendCallback {

    private PayMsgFeignClient payMsgFeignClient;
    private PayInfoDTO payInfoDTO;

    public RocketmqSendCallback(PayMsgFeignClient payMsgFeignClient, PayInfoDTO payInfoDTO) {
        this.payMsgFeignClient = payMsgFeignClient;
        this.payInfoDTO = payInfoDTO;
    }

    @Override
    public void onSuccess(SendResult sendResult) {
        PayMsgDTO payMsgDTO = new PayMsgDTO();
        payMsgDTO.setMsgBody(JSONUtil.toJsonStr(payMsgDTO));
        payMsgDTO.setMsgId( sendResult.getMsgId());
        payMsgDTO.setStatus("已存入");
        payMsgFeignClient.save(payMsgDTO);
        log.info("send success, msgId:{}", sendResult.getMsgId());

    }

    @Override
    public void onException(Throwable throwable) {

        PayMsgDTO payMsgDTO = new PayMsgDTO();
        payMsgDTO.setMsgBody(JSONUtil.toJsonStr(payMsgDTO));

        payMsgDTO.setStatus("存入失败");
        payMsgFeignClient.save(payMsgDTO);
        log.error("async onException Throwable", throwable);
    }
}
