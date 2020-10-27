package com.m.demo.entity;

import lombok.Data;

import java.util.List;

/**
 * author:M
 * describe:
 * date:2020/10/27 16:04
 */
@Data
public class ResultPage {
    private int pageNum;

    private int pageSize;

    private long total;

    private List list;

    public ResultPage() {}

    public ResultPage(int pageNum, int pageSize, long total, List list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }
}
