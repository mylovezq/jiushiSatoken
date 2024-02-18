package com.jiushi.core.common.config.rocketMq;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.json.JSONUtil;
import com.jiushi.pay.api.dto.PayInfoDTO;
import com.jiushi.pay.api.dto.PayMsgDTO;

import com.jiushi.pay.api.fegin.PayMsgFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;

import java.util.Arrays;
import java.util.stream.Collectors;


@Slf4j
public class PayRocketmqSendCallback implements SendCallback {

    private final PayMsgFeignClient payMsgFeignClient;
    private final PayInfoDTO payInfoDTO;

    public PayRocketmqSendCallback(PayMsgFeignClient payMsgFeignClient, PayInfoDTO payInfoDTO) {
        this.payMsgFeignClient = payMsgFeignClient;
        this.payInfoDTO = payInfoDTO;
    }

    @Override
    public void onSuccess(SendResult sendResult) {
        try {
            PayMsgDTO payMsgDTO = new PayMsgDTO();
            payMsgDTO.setMsgBody(JSONUtil.toJsonStr(payInfoDTO));
            payMsgDTO.setMsgId(sendResult.getMsgId());
            payMsgDTO.setStatus("已存入");
            payMsgDTO.setId(payInfoDTO.getId());
            payMsgFeignClient.saveOrUpdatePayMsg(payMsgDTO);
        } catch (Exception e) {
           log.error("存入失败", e);
        }

    }

    @Override
    public void onException(Throwable throwable) {

        try {
            String stackTraceDetails= ExceptionUtil.stacktraceToString(throwable);

            log.error("存入失败{}", stackTraceDetails);
            PayMsgDTO payMsgDTO = new PayMsgDTO();
            payMsgDTO.setMsgBody(JSONUtil.toJsonStr(payMsgDTO));
            payMsgDTO.setId(payInfoDTO.getId());
            payMsgDTO.setStatus("存入失败");
            payMsgDTO.setErrMsg(stackTraceDetails);

            payMsgFeignClient.saveOrUpdatePayMsg(payMsgDTO);
        } catch (Exception e){
            log.error("存入失败", e);
        }
    }

    //org.apache.rocketmq.client.exception.MQBrokerException: CODE: 2  DESC: [TIMEOUT_CLEAN_QUEUE]broker busy, start flow control for a while, period in queue: 295ms, size of queue: 1 BROKER: www.jiushi.com:10911
    //For more information, please visit the url, http://rocketmq.apache.org/docs/faq/
    //	at org.apache.rocketmq.client.impl.MQClientAPIImpl.processSendResponse(MQClientAPIImpl.java:779)
    //	at org.apache.rocketmq.client.impl.MQClientAPIImpl$1.operationComplete(MQClientAPIImpl.java:668)
    //	at org.apache.rocketmq.remoting.netty.NettyRemotingClient$InvokeCallbackWrapper.operationComplete(NettyRemotingClient.java:827)
    //	at org.apache.rocketmq.remoting.netty.ResponseFuture.executeInvokeCallback(ResponseFuture.java:62)
    //	at org.apache.rocketmq.remoting.netty.NettyRemotingAbstract.lambda$executeInvokeCallback$1(NettyRemotingAbstract.java:325)
    //	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)
    //	at java.util.concurrent.FutureTask.run$$$capture(FutureTask.java:266)
    //	at java.util.concurrent.FutureTask.run(FutureTask.java)
    //	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
    //	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
    //	at java.lang.Thread.run(Thread.java:750)
}
