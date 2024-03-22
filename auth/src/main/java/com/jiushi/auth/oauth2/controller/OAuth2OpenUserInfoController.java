package com.jiushi.auth.oauth2.controller;


import cn.dev33.satoken.oauth2.logic.SaOAuth2Util;
import cn.dev33.satoken.oauth2.model.AccessTokenModel;
import cn.dev33.satoken.util.SaFoxUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.StrUtil;
import com.jiushi.auth.api.vo.SaTokenUserBaseVO;
import com.jiushi.auth.oauth2.service.impl.SaOAuth2TemplateImpl;
import com.jiushi.core.common.utils.redis.RedissonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * sa对接开放平台 医生端
 * <p>
 * /psoAuth/open 实际是对 sa-token /oauth2/* 接口的提取封装  由开发控制接口调用
 * <p>
 * 开放平台接口  不需要网关鉴权 实际使用是接入方
 * <p>
 * 此接口和OAuth2PSOOpenController路径前缀（/psoAuth/open）一样的
 * <p>
 * 这里是专门处理接入方通过access_token获取用户信息，医生信息等等信息
 */
@RestController
@RequestMapping("/auth2Open")
@Slf4j
public class OAuth2OpenUserInfoController {

    @Resource
    private SaOAuth2TemplateImpl saOAuth2Template;

    /**
     * 根据access_token 获取医生信息  UD_REAL_NAME（医生基础信息+医生实名信息） 权限
     * 目前结构按照专病需要的字段
     *
     * @param accessToken
     * @return 加密字符串
     */
    @GetMapping("/getUserInfo")
    public SaResult getUserInfo(@RequestParam("access_token") String accessToken) {
        AccessTokenModel accessTokenModel = SaOAuth2Util.getAccessToken(accessToken);
        String clientId = accessTokenModel.clientId;
        String currScope = accessTokenModel.scope;
        Long loginId = Long.parseLong(accessTokenModel.loginId + "");
        //当前access_token所有拥有的权限
        SaOAuth2Util.checkContract(clientId, currScope);
        String openid = saOAuth2Template.getOpenid(clientId, loginId);
        List<String> scopelist = SaFoxUtil.convertStringToList(currScope);

        String scopeSortStr = scopelist.stream().sorted().collect(Collectors.joining(StrPool.COMMA));

        String oauthUserStr = RedissonUtils.getCacheObject(clientId+openid+scopeSortStr);
        if (StrUtil.isNotBlank(oauthUserStr)) {
            return SaResult.data(oauthUserStr);
        }
        SaTokenUserBaseVO saTokenUserBaseVO = new SaTokenUserBaseVO();
        saTokenUserBaseVO.setOpenId(openid);
        saTokenUserBaseVO.setName("jiushi");
        saTokenUserBaseVO.setGender(1);
        return SaResult.data(saTokenUserBaseVO);


    }

}