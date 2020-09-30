package com.m.demo.utils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
/**
 * author:M
 * describe:
 * date:2020/9/28 18:00
 */
public class PageUtil {
    public static Pageable getPageable(int page){
        Pageable pageable = PageRequest.of(page, 10);
        return pageable;
    }
}
