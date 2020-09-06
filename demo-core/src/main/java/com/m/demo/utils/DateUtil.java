package com.m.demo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author:M
 * describe:
 * date:2020/9/6 16:28
 */
public class DateUtil {
    public static Date strToDate(String str,int option){
        SimpleDateFormat simpleDateFormat = null;
        Date date = null;
        if(option == 1){
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }else {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String dateToStr(Date date,int option){
        SimpleDateFormat simpleDateFormat = null;
        if(option == 1){
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }else {
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
        String str = simpleDateFormat.format(date);
        return str;
    }


    public static void main(String[] args) {
        System.out.println(dateToStr(new Date(), 2));
    }
}
