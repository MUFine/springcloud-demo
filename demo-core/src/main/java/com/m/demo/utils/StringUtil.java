package com.m.demo.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * author:M
 * describe:字符串工具类
 * date:2020/11/2 15:26
 */
public class StringUtil {
    //根据正则表达式去除某些字符
    public static String resetString(String str,String regex){
        if(StringUtils.isBlank(str) || StringUtils.isBlank(regex)){
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if(String.valueOf(charAt).matches(regex)){
                stringBuilder.append(charAt);
            }
        }
        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        System.out.println(resetString("12  57?","^[0-9a-zA-Z]$"));
    }
}
