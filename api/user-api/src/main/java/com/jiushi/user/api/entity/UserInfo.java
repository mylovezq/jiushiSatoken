package com.jiushi.user.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author denmgmingyang
 * @since 2024-03-22
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_info")
public class UserInfo {

    @TableId("id")
    private Long id;

    @TableField("phone")
    private String phone;

    @TableField("password")
    private String password;

    @TableField("nickname")
    private String nickname;

    @TableField("head")
    private String head;

    @TableField("register_ip")
    private String registerIp;

    @TableField("register_time")
    private LocalDateTime registerTime;

    @TableField("birthDay")
    private String birthDay;

    @TableField("info")
    private String info;
}