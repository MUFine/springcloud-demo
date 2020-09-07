package com.m.demo.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

/**
 * author:M
 * describe:
 * date:2020/9/7 16:10
 */
public class MD5Util {
    public static String getMd5(String str1, String str2){
        String string = null;
        if(StringUtils.isBlank(str2)){
            string = DigestUtils.md5Hex(DigestUtils.md5Hex(str1));
        }else if(StringUtils.isBlank(str1)){
            string = DigestUtils.md5Hex(DigestUtils.md5Hex(str2));
        }else {
            string = DigestUtils.md5Hex(DigestUtils.md5Hex(str1 + str2));
        }
        return string;
    }
}
