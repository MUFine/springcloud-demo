package com.m.demo.service;

import com.m.demo.Entity.Product;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * author:M
 * describe:
 * date:2020/9/27 19:58
 */
public interface ElasticSearchService {
    String createIndex(String index) throws IOException;
    String putData(Product product) throws IOException;
    String updateData(Product product,String id) throws IOException;
    String deleteDataById(String id) throws IOException;
    List<Map> getDataByTitle(String title, int page, int size) throws IOException;
    List<Map> getDataByBrand(String brand, int page, int size) throws IOException;
}
