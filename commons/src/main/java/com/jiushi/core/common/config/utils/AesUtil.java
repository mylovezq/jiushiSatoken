package com.jiushi.core.common.config.utils;


import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;

public class AesUtil {
//    长度只能是16位，24位，32位
    private static final String DEFAULT_KEY = "jiushiyunimylove";

    /**
     * 加密
     * @param content
     * @return
     */
    public static String encryptBase64(String content){
        try{
            byte[] byteKey = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue(),
                    DEFAULT_KEY.getBytes()).getEncoded();
            SymmetricCrypto aes = SecureUtil.aes(byteKey);
            // 加密
            return aes.encryptBase64(content);
        }catch (Exception e){
           throw e;
        }
    }

    /**
     * 解密
     * @param encryptString
     * @return
     */
    public static String decryptStr(String encryptString){
        try{
            byte[] byteKey = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue(),
                    DEFAULT_KEY.getBytes()).getEncoded();
            SymmetricCrypto aes = SecureUtil.aes(byteKey);
            //解密
            return aes.decryptStr(encryptString);
        }catch (Exception e){
            throw e;
        }
        }
    }