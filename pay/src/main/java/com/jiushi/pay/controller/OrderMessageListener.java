package com.jiushi.pay.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jiushi.pay.api.dto.PayInfoDTO;
import com.jiushi.pay.api.dto.PayMsgDTO;
import com.jiushi.pay.dao.PayMsg;
import com.jiushi.pay.service.PayMsgService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@RocketMQMessageListener(topic = "jiushiPayTopic", consumerGroup = "jiushiPayGroup")
public class OrderMessageListener implements RocketMQListener<MessageExt> {
    @Resource
    private PayMsgService payMsgService;

    @Override
    @Transactional(rollbackFor = Exception.class)
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
                PayMsg payMsg = payMsgService.getOne(new LambdaQueryWrapper<PayMsg>().eq(PayMsg::getId, data.getId()).last(" for update"));
                if (payMsg == null) {
                    log.info("等待"+i+"秒后，没有找到对应的消息，id:{}", data.getId());
                    TimeUnit.SECONDS.sleep(1);
                } else if (payMsg.getStatus().equals("已存入")){
                    payMsgService.updateById(BeanUtil.copyProperties(payMsgDTO, PayMsg.class));
                    break;
                }else {
                    log.info("等待"+i+"秒后，找到对应的消息,但是状态不对，id:{}", data.getId());
                    log.error("消息状态为：{},消息为{}",payMsg.getStatus(),payMsg);
                }
                if (i == 9){
                    throw new RuntimeException("没有找到对应的消息");
                }
            }

        } catch (Exception e) {
            String stackTraceDetails= ExceptionUtil.stacktraceToString(e);
            PayMsgDTO payMsgDTO = new PayMsgDTO();
            payMsgDTO.setMsgBody(new String(messageExt.getBody()));
            payMsgDTO.setStatus("消费失败");
            payMsgDTO.setErrMsg(stackTraceDetails);
            payMsgDTO.setId(data.getId());
            payMsgService.updateById(BeanUtil.copyProperties(payMsgDTO, PayMsg.class));
            log.error("事件调用异常", e);
            throw new RuntimeException("消费失败");
        }
    }

}
