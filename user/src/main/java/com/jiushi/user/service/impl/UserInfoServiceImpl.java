package com.jiushi.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiushi.user.api.entity.UserInfo;
import com.jiushi.user.mapper.UserInfoMapper;
import com.jiushi.user.service.IUserInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author denmgmingyang
 * @since 2024-03-22
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

}
