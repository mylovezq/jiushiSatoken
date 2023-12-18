package com.jiushi.auth.oauth2.service.impl;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.oauth2.logic.SaOAuth2Template;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jiushi.auth.api.dto.QRLoginDTO;
import com.jiushi.auth.oauth2.dao.entity.Oauth2ClientUser;
import com.jiushi.auth.oauth2.dao.entity.Oauth2RegisteredClientDO;
import com.jiushi.auth.oauth2.service.IOauth2ClientUserService;
import com.jiushi.auth.oauth2.service.IOauth2RegisteredClientService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Optional;

/**
 * Sa-Token OAuth2.0 整合实现
 *
 * @author Colin Yin
 * @since 2023/7/5
 */
@Component
public class SaOAuth2TemplateImpl extends SaOAuth2Template {


    @Resource
    private IOauth2RegisteredClientService oauth2RegisteredClientService;
    @Resource
    private IOauth2ClientUserService oauth2ClientUserService;

    /**
     * 根据id获取Client信息
     *
     * @param clientId 应用id
     * @return ClientModel
     */
    @Override
    public Oauth2RegisteredClientDO getClientModel(String clientId) {
        return oauth2RegisteredClientService.getOne(new LambdaQueryWrapper<Oauth2RegisteredClientDO>().eq(Oauth2RegisteredClientDO::getClientId, clientId));
    }

    /**
     * 根据ClientId 和 LoginId 获取openid
     *
     * @param clientId 应用id
     * @param userId  账号id
     * @return 此账号在此Client下的openid
     */
    @Override
    public String getOpenid(String clientId, Object userId) {
        return Optional.ofNullable(oauth2ClientUserService.getOne(new LambdaQueryWrapper<Oauth2ClientUser>()
                .eq(Oauth2ClientUser::getClientId, clientId)
                .eq(Oauth2ClientUser::getUserId, userId)))
                .orElse(new Oauth2ClientUser())
                .getOpenId();
    }


    /**
     *
     * oauth2的授权接口/oauth2/authorize?response_type=code&client_id=xxx&redirect_uri=xxx&scope=xxx
     * 这里请求转发授权接口
     *
     * 在这里封装成方法统一转发
     */
    public Object forwardOAuth2AuthorizeRequest(QRLoginDTO qrLoginDTO){
        String path = "/oauth2/authorize"
                +"?response_type=" + qrLoginDTO.getResponseType()
                +"&client_id="+ qrLoginDTO.getClientId()
                +"&redirect_uri="+ qrLoginDTO.getRedirectUri()
                +"&scope="+ qrLoginDTO.getScope()
                +"&state="+ qrLoginDTO.getState();
        SaRequest req = SaHolder.getRequest();
        return req.forward(path);
    }

    /**
     * 确认授权接口
     * @return
     */
    public Object forwardConfirmAuthorizeRequest(){
        String path = "/oauth2/doConfirm";
        SaRequest req = SaHolder.getRequest();
        return req.forward(path);
    }

    /**
     * 通过code获取access_token
     * @return
     */
    public Object forwardGetTokenRequest() {
        String path = "/oauth2/token";
        SaRequest req = SaHolder.getRequest();
        return req.forward(path);
    }
}