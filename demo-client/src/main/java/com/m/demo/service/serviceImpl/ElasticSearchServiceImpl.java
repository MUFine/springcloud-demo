package com.m.demo.service.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.m.demo.Entity.Product;
import com.m.demo.common.ESConstant;
import com.m.demo.common.Message;
import com.m.demo.entity.WorkerId;
import com.m.demo.service.ElasticSearchService;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * author:M
 * describe:
 * date:2020/9/27 19:59
 */
@Service
public class ElasticSearchServiceImpl implements ElasticSearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private WorkerId workerId;
    @Override
    public String createIndex(String index) throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
        return Message.SUCCESS;
    }

    @Override
    public String putData(Product product) throws IOException {
        String id = String.valueOf(workerId.nextId());
        product.setId(id);
        IndexRequest indexRequest = new IndexRequest(ESConstant.TEST_INDEX);
        indexRequest.id(id);
        indexRequest.timeout(new TimeValue(1,TimeUnit.SECONDS));
        indexRequest.source(JSON.toJSONString(product), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse.toString());
        System.out.println(indexResponse.status());
        return Message.SUCCESS;
    }

    @Override
    public String updateData(Product product,String id) throws IOException {
        UpdateRequest updateRequest = new UpdateRequest(ESConstant.TEST_INDEX,id);
        updateRequest.timeout("1s");
        updateRequest.doc(JSON.toJSONString(product), XContentType.JSON);
        restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        return Message.SUCCESS;
    }

    @Override
    public String deleteDataById(String id) throws IOException {
        DeleteRequest request = new DeleteRequest(ESConstant.TEST_INDEX, id);
        request.timeout("1s");
        restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        return Message.SUCCESS;
    }

    @Override
    public List<Map> getDataByTitle(String title,int page,int size) throws IOException {
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", title);
        return getSearchResult("title",false,matchQueryBuilder,page,size,null,null);
    }

    @Override
    public List<Map> getDataByBrand(String brand, int page,int size) throws IOException {
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("brand", brand);
        String preTags = "<span style='color:red'>";
        String postTags = "</span>";
        return getSearchResult("brand",true,matchQueryBuilder,page,size,preTags,postTags);
    }

    /*
    * name : 字段名
    * isHighlight : 是否是高亮查询
    * QueryBuilder : 查询构造器
    */
    private List<Map> getSearchResult(String name,Boolean isHighlight,QueryBuilder queryBuilder,int page,int size,String preTags,String postTags) throws IOException {
        page = page <= 0 ?1 : page;
        List<Map> list = new ArrayList<>();
        SearchRequest searchRequest = new SearchRequest(ESConstant.TEST_INDEX);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from((page - 1) * size);
        searchSourceBuilder.size(size);
        searchSourceBuilder.sort("price", SortOrder.DESC);
        searchSourceBuilder.timeout(new TimeValue(10, TimeUnit.SECONDS));
        //高亮设置
        if(isHighlight){
            setHighlight(searchSourceBuilder,name,preTags,postTags);
        }
        searchRequest.source(searchSourceBuilder);
        //执行查询
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        SearchHits searchHits = searchResponse.getHits();
        //将结果放进list里
        for (SearchHit searchHit:searchHits.getHits()) {
            if (searchHit != null){
                Map<String,Object> map = searchHit.getSourceAsMap();
                if (isHighlight){
                    Map<String, HighlightField> highlightFields = searchHit.getHighlightFields();
                    HighlightField highlightField = highlightFields.get(name);
                    if(null != highlightField){
                        Text[] fragments = highlightField.fragments();
                        StringBuilder value = new StringBuilder();
                        for (Text text:fragments) {
                            value.append(text);
                        }
                        map.put(name, value.toString());
                    }
                }
                list.add(map);
            }
        }
//        System.out.println(JSON.toJSONString(searchHits));
        return list;
    }

    private void setHighlight(SearchSourceBuilder sourceBuilder,String field,String preTags,String postTags){
        //高亮设置
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field(field);
        highlightBuilder.requireFieldMatch(false);//多个高亮显示
        highlightBuilder.preTags(preTags);
        highlightBuilder.postTags(postTags);
        sourceBuilder.highlighter(highlightBuilder);
    }
}
