package com.jiushi.pay.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONUtil;
import com.jiushi.pay.api.dto.PayInfoDTO;
import com.jiushi.pay.api.dto.PayMsgDTO;

import com.jiushi.pay.api.fegin.PayMsgFeignClient;
import com.jiushi.pay.dao.PayMsg;
import com.jiushi.pay.service.PayMsgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
@RocketMQMessageListener(topic = "jiushiPayTopic", consumerGroup = "jiushiPayGroup")
public class OrderMessageListener implements RocketMQListener<MessageExt> {
    @Resource
    private PayMsgService payMsgService;

    @Override
    public void onMessage(MessageExt messageExt) {
        PayInfoDTO data = JSONUtil.toBean(new String(messageExt.getBody()), PayInfoDTO.class);

        Assert.notNull(data, "消息体不能为空");
        Assert.notNull(data.getId(), "消息体不能为空");
        try {

            log.info("data的id:{}", data.getId());


            PayMsgDTO payMsgDTO = new PayMsgDTO();
            payMsgDTO.setMsgBody(new String(messageExt.getBody()));
            payMsgDTO.setStatus("消费成功");
            payMsgDTO.setId(data.getId());
            //根据data的Id去更新，如果id不存在，就循环等待5次，每次等待1秒
            for (int i = 0; i < 10; i++) {
                PayMsg payMsg = payMsgService.getById(data.getId());
                if (payMsg == null) {
                    log.info("等待1秒后，没有找到对应的消息，id:{}", data.getId());
                    TimeUnit.SECONDS.sleep(2);
                } else {
                    break;
                }
            }
            payMsgService.updateById(BeanUtil.copyProperties(payMsgDTO, PayMsg.class));
        } catch (Exception e) {
            String stackTraceDetails= ExceptionUtil.stacktraceToString(e);
            PayMsgDTO payMsgDTO = new PayMsgDTO();
            payMsgDTO.setMsgBody(new String(messageExt.getBody()));
            payMsgDTO.setStatus("消费失败");
            payMsgDTO.setErrMsg(stackTraceDetails);
            payMsgDTO.setId(data.getId());
            payMsgService.updateById(BeanUtil.copyProperties(payMsgDTO, PayMsg.class));
            log.error("事件调用异常", e);
        }
    }
//org.apache.rocketmq.client.exception.MQClientException: No route info of this topic: jiushiPayTopic
//See http://rocketmq.apache.org/docs/faq/ for further details.
//	at org.apache.rocketmq.client.impl.producer.DefaultMQProducerImpl.sendDefaultImpl(DefaultMQProducerImpl.java:735)
//	at org.apache.rocketmq.client.impl.producer.DefaultMQProducerImpl.access$300(DefaultMQProducerImpl.java:99)
//	at org.apache.rocketmq.client.impl.producer.DefaultMQProducerImpl$3.run(DefaultMQProducerImpl.java:527)
//	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
//	at java.util.concurrent.FutureTask.run$$$capture(FutureTask.java:266)
//	at java.util.concurrent.FutureTask.run(FutureTask.java)
//	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
//	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
//	at java.lang.Thread.run(Thread.java:750)

}
