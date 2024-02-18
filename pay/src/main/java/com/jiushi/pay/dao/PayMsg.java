package com.jiushi.pay.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Map;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;

@Data
public class PayMsg  {

    @TableId(value = "id")
    private Long id;

    @TableField("msg_body")
    private String msgBody;

    @TableField("msg_id")
    private String msgId;

    @TableField("status")
    private String status;


    @TableField("err_msg")
    private String errMsg;

}
