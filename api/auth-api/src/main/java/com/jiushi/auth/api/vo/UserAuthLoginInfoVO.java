package com.jiushi.auth.api.vo;

import com.jiushi.user.api.vo.UserInfoVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthLoginInfoVO {
    private String token;
    private UserBaseInfoVO userInfo;
}