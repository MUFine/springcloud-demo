package com.m.demo.utils;

import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * author:M
 * describe:
 * date:2019/11/4 14:50
 */
public class ImageUtil {
    public static String getImgName(MultipartFile img, String imgPath){
        String imgName = getMD5(img) + img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf("."));
        File file = new File(imgPath + imgName);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdir();
        }
        try {
            img.transferTo(file);
        } catch (IOException e) {
            e.getStackTrace();
        }
        return imgName;
    }

    //获取上传图片的md5码
    public static String getMD5(MultipartFile img){
        String str = null;
        try {
            byte[] imgBytes = img.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(imgBytes);
            str = new BigInteger(1, digest).toString(16);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return str;
    }
}
