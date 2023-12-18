package com.jiushi.auth.oauth2.controller;

import cn.dev33.satoken.exception.SaTokenException;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.jiushi.auth.api.constant.QRLoginStatusEnum;
import com.jiushi.auth.api.dto.QRLoginDTO;
import com.jiushi.auth.api.qo.QO.QRLoginQO;
import com.jiushi.auth.api.vo.QRCodeVO;
import com.jiushi.auth.oauth2.service.impl.SaOAuth2TemplateImpl;
import com.jiushi.core.common.config.utils.redis.RedissonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.Objects;

@RestController
@RequestMapping("/auth2Open")
@Slf4j
public class Oauth2OpenController {

    public final static   String QR_UUID = "auth2:login:uuid:";

    @Resource
    private SaOAuth2TemplateImpl saOAuth2Template;

    /**
     * 生成授权登录的二维码给接入方展示 后续可以考虑限流
     * @return
     */
    @PostMapping("/generateQRCode")
    public SaResult generateQRCode(@RequestBody @Validated QRLoginQO qrLoginQO) {
        String uuid = IdUtil.fastSimpleUUID();
        QRLoginDTO qrLoginDTO = BeanUtil.copyProperties(qrLoginQO, QRLoginDTO.class);
        qrLoginDTO.setQrLoginStatus(QRLoginStatusEnum.NOT_SCAN_QRCODE);
        RedissonUtils.setCacheObject(QR_UUID + uuid,JSONUtil.toJsonStr(qrLoginDTO), Duration.ofMinutes(20L));
        return SaResult.data(uuid);
    }

    /**
     *  根据登录二维码图片的uuid轮询获取二维码的状态，
     *
     * 封装接口【/oauth2/authorize】 如果用户授权会自动重定向
     *
     * @param uuid
     * @return
     */
    @GetMapping("/getQRStatusByUuid")
    public SaResult getQRStatusByUuid(@RequestParam("uuid") String uuid) {
        String qrUuid = RedissonUtils.getCacheObject(QR_UUID+uuid) + "";
        if (StrUtil.isBlank(qrUuid)){
            return SaResult.data(QRLoginStatusEnum.TIME_OUT_QRCODE);
        }
        QRLoginDTO qrLoginDTO = JSONUtil.toBean(qrUuid, QRLoginDTO.class);
        return SaResult.data(qrLoginDTO.getQrLoginStatus());
    }

    /**
     *  如果用户授权会自动重定向到redirectUri的地址并携带code
     *
     * @param uuid
     * @return
     */
    @GetMapping("/redirectUri")
    public SaResult redirectUri(@RequestParam("uuid") String uuid) {
        String qrUuid = RedissonUtils.getCacheObject(QR_UUID + uuid) + "";
        if (StrUtil.isBlank(qrUuid)) {
            return SaResult.data(QRLoginStatusEnum.TIME_OUT_QRCODE);
        }
        QRLoginDTO qrLoginDTO = JSONUtil.toBean(qrUuid, QRLoginDTO.class);
        if (Objects.equals(qrLoginDTO.getQrLoginStatus(), QRLoginStatusEnum.HAD_AUTHORIZE_QRCODE)) {
            Long userId = qrLoginDTO.getUserId();
            Assert.notNull(userId, "登录用户不能为空");
            //服务器授权临时登录
            // 将当前会话[身份临时切换]为其它账号（本次请求内有效）
            StpUtil.switchTo(userId);
            try {
                return SaResult.data(saOAuth2Template.forwardOAuth2AuthorizeRequest(qrLoginDTO)) ;
            } catch (Exception e) {
                log.error("oauth2的授权接口转发异常", e);
            } finally {
                // 结束 [身份临时切换]
                StpUtil.endSwitch();
                //删除这次key
                RedissonUtils.deleteObject(QR_UUID + uuid);
                //过期掉这次授权
                saOAuth2Template.deleteGrantScope(qrLoginDTO.getClientId(), StpUtil.getLoginIdAsLong());
            }
        }


        return  SaResult.data(QRLoginStatusEnum.NOT_SCAN_QRCODE);
    }


    /**
     *
     * 封装接口【/oauth2/token】接入方根据code来换取access_token
     *
     * {"code":200,"msg":"ok",
     * "data":{"access_token":"v8AMP9U21zvqZHnP50S8CThRSbdvD38c1mDTrvdFb4zAzfuGauRuBgsI54ZV",
     * "refresh_token":"BFDZqb1IezIInUIgvdX2suEBIgs5UZ5d9AngxUItcv97S3q9Z4DjjZzaX8Td",
     * "expires_in":7199,"refresh_expires_in":7199,"client_id":"1001","scope":"UD_INFO","openid":"PSO_eb2a79e6169f489ea7f20bf88aaba352"}}
     */
    @GetMapping("/getAccessToken")
    public SaResult getAccessToken(@RequestParam("grant_type") String grantType,
                                           @RequestParam("client_id") String clientId,
                                           @RequestParam("client_secret") String clientSecret,
                                           @RequestParam("code") String code){
        if (!Objects.equals("authorization_code",grantType)){
            throw new SaTokenException("获取token需要grantType为【authorization_code】");
        }
        return SaResult.data(saOAuth2Template.forwardGetTokenRequest());
    }



}
