package com.m.demo.Entity;

import com.m.demo.common.ESConstant;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

/**
 * author:M
 * describe:
 * date:2020/9/28 16:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String id;
    private String title; //标题
    private String category;// 分类
    private String brand; // 品牌
    private Double price; // 价格
}
