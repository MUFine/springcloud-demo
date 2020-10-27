package com.m.demo.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.m.demo.entity.ResultPage;

import java.util.List;
import java.util.function.Supplier;


/**
 * author:M
 * describe:
 * date:2020/9/28 18:00
 */
public class PageUtil {
    public static ResultPage getPageInfo(Supplier<List> supplier, int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List list = supplier.get();
        PageInfo pageInfo = new PageInfo(list);
        return new ResultPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getTotal(),pageInfo.getList());
    }

    public static ResultPage listToPage(List list,int pageNum, int pageSize){
        //创建Page类
        Page page = new Page(pageNum, pageSize);
        //为Page类中的total属性赋值
        int total = list.size();
        page.setTotal(total);
        //计算数据下标起始值
        int startIndex = (pageNum - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, total);
        if (total > startIndex) {
            //截取数据
            page.addAll(list.subList(startIndex, endIndex));
            //创建PageInfo
            PageInfo pageInfo = new PageInfo<>(page);
            return new ResultPage(pageInfo.getPageNum(),pageInfo.getPageSize(),pageInfo.getTotal(),pageInfo.getList());
        }else {
            return new ResultPage(pageNum,pageSize,list.size(),list);
        }
    }
}
