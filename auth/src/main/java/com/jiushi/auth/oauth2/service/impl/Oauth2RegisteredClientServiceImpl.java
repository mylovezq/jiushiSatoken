package com.jiushi.auth.oauth2.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiushi.auth.oauth2.dao.Oauth2RegisteredClientMapper;
import com.jiushi.auth.oauth2.service.IOauth2RegisteredClientService;
import com.jiushi.auth.oauth2.dao.entity.Oauth2RegisteredClientDO;
import org.springframework.stereotype.Service;

/**
 * <p>
 * OAuth2 客户端 服务实现类
 * </p>
 *
 * @author denmgmingyang
 * @since 2023-12-18
 */
@Service
public class Oauth2RegisteredClientServiceImpl extends ServiceImpl<Oauth2RegisteredClientMapper, Oauth2RegisteredClientDO> implements IOauth2RegisteredClientService {

}
