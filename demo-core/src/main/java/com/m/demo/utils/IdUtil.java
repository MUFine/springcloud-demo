package com.m.demo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * author:M
 * describe:
 * date:2020/9/11 14:41
 */
public class IdUtil {
    private static long tmpID = 0;
    private static boolean tmpIDlocked = false;
    //生成16位id
    public static long getId() {
        long ltime = 0;
        while (true)
        {
            if(!tmpIDlocked)
            {
                tmpIDlocked = true;
                //当前：（年、月、日、时、分、秒、毫秒）*10000
                ltime = Long.valueOf(new SimpleDateFormat("yyyyMMddhhmmss").format(new Date()).toString()) * 100000;
                if(tmpID < ltime) {
                    tmpID = ltime;
                }
                else {
                    tmpID = tmpID + 1;
                    ltime = tmpID;
                }
                tmpIDlocked = false;
                return ltime;
            }
        }
    }

    //生成19位long型id
    public static Long createId(){
        return Math.abs(UUID.randomUUID().getLeastSignificantBits());
    }

    public static void main(String[] args) {
        System.out.println("开始时间:" + System.currentTimeMillis());
        for (int i = 0; i < 1000; i++) {
            System.out.println(getId());
        }

//        System.out.println(createId());
        System.out.println("结束时间:" + System.currentTimeMillis());
    }
}
