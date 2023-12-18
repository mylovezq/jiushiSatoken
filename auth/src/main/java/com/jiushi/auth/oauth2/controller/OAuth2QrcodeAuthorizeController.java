package com.jiushi.auth.oauth2.controller;

import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.jiushi.auth.api.constant.QRLoginStatusEnum;
import com.jiushi.auth.api.dto.QRLoginDTO;
import com.jiushi.auth.api.vo.QRLoginVO;
import com.jiushi.auth.oauth2.service.impl.SaOAuth2TemplateImpl;
import com.jiushi.core.common.config.utils.redis.RedissonLockService;
import com.jiushi.core.common.config.utils.redis.RedissonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.jiushi.auth.oauth2.controller.Oauth2OpenController.QR_UUID;

/**
 * 对接开放平台  医生端操作  需要网关鉴权且PSO用户已登录
 * <p>
 * 二维码扫码登录PSO已登录的用户扫码操作
 *
 * @author dengmingyang
 */
@RestController
@RequestMapping("/oAuth2/qrcodeLogin")
@Slf4j
public class OAuth2QrcodeAuthorizeController {

    @Resource
    private SaOAuth2TemplateImpl saOAuth2Template;

    @Resource
    private RedissonLockService redissonLockService;

    /**
     * 封装接口【/oauth2/authorize】 用户扫码  要PSO用户已登录
     *
     * @param uuid
     * @return
     */
    @GetMapping("/scanQRCode")
    public SaResult scanQRCode(@RequestParam("uuid") String uuid) {

        QRLoginDTO qrLoginDTO = RedissonUtils.getCacheObject(QR_UUID + uuid);
        if (qrLoginDTO == null || !Objects.equals(qrLoginDTO.getQrLoginStatus(), QRLoginStatusEnum.NOT_SCAN_QRCODE)) {
            throw new SaTokenException("二维码已使用或已过期");
        }
        qrLoginDTO.setQrLoginStatus(QRLoginStatusEnum.HAD_SCAN_QRCODE);
        qrLoginDTO.setUserId(Long.parseLong(StpUtil.getLoginIdAsLong() + ""));

        RedissonUtils.setCacheObject(QR_UUID + uuid, qrLoginDTO, true);
        //过期掉上次授权 目的是让小程序只弹出授权信息
        saOAuth2Template.deleteGrantScope(qrLoginDTO.getClientId(), StpUtil.getLoginIdAsLong());
        //重新授权
        try {
            //oauth2的授权接口
            return (SaResult) saOAuth2Template.forwardOAuth2AuthorizeRequest(qrLoginDTO);
        } catch (Exception e) {
            log.error("oauth2的授权接口异常", e);
            throw new SaTokenException("oauth2的授权接口异常");
        }
    }


    /**
     * 封装接口【/oauth2/doConfirm】确认授权  要PSO用户已登录
     *
     * @param
     * @return
     */
    @GetMapping("/confirmAuthorizeQRCodeLogin")
    public SaResult confirmAuthorizeQRCodeLogin(@RequestParam String uuid, @RequestParam("client_id") String clientId, @RequestParam String scope) {
        return SaResult.data(redissonLockService.runRedisExclusiveLock.apply(QR_UUID + uuid, () -> {
            log.info("进入确认授权==》" + uuid);
            QRLoginDTO qrLoginDTO = RedissonUtils.getCacheObject(QR_UUID + uuid);
            if (qrLoginDTO == null || !Objects.equals(qrLoginDTO.getQrLoginStatus(), QRLoginStatusEnum.HAD_SCAN_QRCODE)) {
                throw new SaTokenException("二维码已过期");
            }
            if (!Objects.equals(qrLoginDTO.getUserId(), StpUtil.getLoginIdAsLong())) {
                throw new SaTokenException("请用扫码用户授权");
            }
            return saOAuth2Template.forwardConfirmAuthorizeRequest();
        }));
    }

    /**
     * 取消授权
     *
     * @param uuid
     * @return
     */
    @GetMapping("/cancelAuthorizeQRCodeLogin")
    public SaResult cancelAuthorizeQRCodeLogin(@RequestParam("uuid") String uuid) {
        QRLoginDTO qrLoginDTO = RedissonUtils.getCacheObject(QR_UUID + uuid);
        if (qrLoginDTO == null || !Objects.equals(qrLoginDTO.getQrLoginStatus(), QRLoginStatusEnum.HAD_SCAN_QRCODE)) {
            throw new SaTokenException("二维码已过期");
        }
        if (!Objects.equals(qrLoginDTO.getUserId(), StpUtil.getLoginIdAsLong())) {
            throw new SaTokenException("请用扫码用户取消");
        }
        qrLoginDTO.setQrLoginStatus(QRLoginStatusEnum.CANCEL_AUTHORIZE_QRCODE);
        RedissonUtils.setCacheObject(QR_UUID + uuid, qrLoginDTO, true);
        //过期掉这次授权
        saOAuth2Template.deleteGrantScope(qrLoginDTO.getClientId(), StpUtil.getLoginIdAsLong());
        new Thread(() -> {//停顿一下再删除 让前端页面感知到 但授权信息上步已删除
            try {
                TimeUnit.SECONDS.sleep(5L);
                //删除这次key
                RedissonUtils.deleteObject(QR_UUID + uuid);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
        return SaResult.data(new QRLoginVO(QRLoginStatusEnum.CANCEL_AUTHORIZE_QRCODE));
    }

}
