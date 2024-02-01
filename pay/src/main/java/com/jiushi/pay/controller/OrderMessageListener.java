package com.jiushi.pay.controller;

import cn.hutool.json.JSONUtil;
import com.jiushi.pay.api.dto.PayInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RocketMQMessageListener(topic = "jiushiPayTopic", consumerGroup = "jiushiPayGroup")
public class OrderMessageListener implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            PayInfoDTO data = JSONUtil.toBean(new String(messageExt.getBody()), PayInfoDTO.class);
            log.info("onMessage:{}", JSONUtil.toJsonStr(messageExt));
            log.info("tags:{}", messageExt.getTags());
            log.info("data:{}", JSONUtil.toJsonStr(data));
        } catch (Exception e) {
            log.error("事件调用异常", e);
        }
    }

}
