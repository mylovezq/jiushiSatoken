package com.jiushi.auth.oauth2.controller;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Consts;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Handle;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Util;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.jiushi.auth.api.constant.QRLoginStatusEnum;
import com.jiushi.auth.api.dto.QRLoginDTO;
import com.jiushi.auth.api.vo.QRLoginVO;
import com.jiushi.core.common.config.utils.redis.RedissonUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static com.jiushi.auth.oauth2.controller.Oauth2OpenController.QR_UUID;


/**
 * Sa-OAuth2 Server端 控制器
 *
 * @author dengmingyang
 */
@RestController
public class OAuth2ServerController {
    

    /**
     * 处理所有OAuth相关请求 不暴露对外，根据功能需求提取接口单独出路
     *
     * @return 结果
     */
    @RequestMapping("/oauth2/*")
    public Object request() {
        // 获取变量
        SaRequest req = SaHolder.getRequest();
        Object serverRequest = SaOAuth2Handle.serverRequest();
        //如果是确认授权接口 并且授权成功  进行pso扫码登录的业务的授权操作
        if (serverRequest instanceof SaResult && req.isPath(SaOAuth2Consts.Api.doConfirm) && ((SaResult) serverRequest).getCode() == 200) {
            return checkIsDoConfirm(req);
        }

        return serverRequest;
    }


    private Object checkIsDoConfirm(SaRequest req) {

        String clientId = req.getParamNotNull("client_id");
        String scope = req.getParamNotNull("scope");
        Long userId = StpUtil.getLoginIdAsLong();
        //判断是否已经授权了
        if (SaOAuth2Util.isGrant(userId,clientId,scope)) {

            String uuid = req.getParamNotNull("uuid");
            QRLoginDTO qrLoginDTO = RedissonUtils.getCacheObject(QR_UUID + uuid);

            if (qrLoginDTO == null || !Objects.equals(qrLoginDTO.getQrLoginStatus(), QRLoginStatusEnum.HAD_SCAN_QRCODE)) {
                return SaResult.data(new QRLoginVO(QRLoginStatusEnum.TIME_OUT_QRCODE));
            }
            if (!Objects.equals(qrLoginDTO.getUserId(), userId)) {
                throw new SaTokenException("请用扫码用户授权");
            }
            qrLoginDTO.setQrLoginStatus(QRLoginStatusEnum.HAD_AUTHORIZE_QRCODE);
            RedissonUtils.setCacheObject(QR_UUID + uuid, qrLoginDTO,true);
            return SaResult.data(new QRLoginVO(QRLoginStatusEnum.HAD_AUTHORIZE_QRCODE));
        }else {
            throw new SaTokenException("请先签约scope");
        }
    }

}