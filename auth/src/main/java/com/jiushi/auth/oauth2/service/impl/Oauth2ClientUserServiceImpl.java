package com.jiushi.auth.oauth2.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiushi.auth.oauth2.dao.Oauth2ClientUserMapper;
import com.jiushi.auth.oauth2.dao.entity.Oauth2ClientUser;
import com.jiushi.auth.oauth2.service.IOauth2ClientUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * OAuth2 应用和用户的关联 服务实现类
 * </p>
 *
 * @author denmgmingyang
 * @since 2023-12-18
 */
@Service
public class Oauth2ClientUserServiceImpl extends ServiceImpl<Oauth2ClientUserMapper, Oauth2ClientUser> implements IOauth2ClientUserService {

}
