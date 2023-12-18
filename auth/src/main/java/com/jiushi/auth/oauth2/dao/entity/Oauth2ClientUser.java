package com.jiushi.auth.oauth2.dao.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * <p>
 * OAuth2 应用和用户的关联
 * </p>
 *
 * @author denmgmingyang
 * @since 2023-12-18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("oauth2_client_user")
public class Oauth2ClientUser  {

    /**
     * 自增id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * oauth2_registered_client表id
     */
    @TableField("client_id")
    private String clientId;

    /**
     * 用户ID
     */
    @TableField("user_id")
    private String userId;

    /**
     * 此账号在此Client下的openid
     */
    @TableField("open_id")
    private String openId;

    /**
     * 创建人者id
     */
    @TableField("create_user_id")
    private Long createUserId;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @TableField("create_user_no")
    private String createUserNo;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @TableField("update_user_no")
    private String updateUserNo;

    /**
     * 是否已删除
     */
    @TableField("enable")
    private Boolean enable;

    /**
     * 用户类型
     */
    @TableField("user_type")
    private Integer userType;
}