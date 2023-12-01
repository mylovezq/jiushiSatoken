package com.jiushi.auth.api.vo;


import com.jiushi.auth.api.constant.QRLoginStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QRLoginVO {

    //状态
    private QRLoginStatusEnum qrLoginStatus;
}
