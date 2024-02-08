package com.jiushi.pay.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Map;

import static com.baomidou.mybatisplus.annotation.IdType.ASSIGN_ID;

@Data
public class PayMsg  {

    @TableId(value = "id", type = ASSIGN_ID.AUTO)
    private Long id;

    @TableField("msg_body")
    private Map<String, Object> msgBody; // 假设msg_body是JSON格式存储，用Map解析

    @TableField("msg_id")
    private String msgId;

    @TableField("status")
    private String status;

}
