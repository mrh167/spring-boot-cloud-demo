package org.es.study.service.impl;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.es.study.config.Response;
import org.es.study.domain.Book;
import org.es.study.service.BasicMatchQueryService;
import org.es.study.utils.CommonQueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/11/11
 * Time: 15:10
 * Description: No Description
 */
@Service
public class BasicMatchQueryServiceImpl implements BasicMatchQueryService {

    @Autowired
    private Client client;

    @Value("${es.bookIndex}")
    private String bookIndex;

    @Value("${es.bookType}")
    private String bookType;

    /**
     *进行ES查询结果
     */
    private SearchResponse requestGet(String queryName, SearchRequestBuilder requestBuilder){
        System.out.println(queryName + " 构建的查询: " + requestBuilder.toString());
        SearchResponse searchResponse = requestBuilder.get();
        System.out.println(queryName + "搜索结果: " + searchResponse.toString());
        return searchResponse;
    }

    /**
     *  1.1 对 "guide" 执行全文检索
     * @param query
     * @return
     */
    @Override
    public Response<List<Book>> multiMatch(String query) {
        /**
         * 匹配查询并分析索引文本返回最终构造查询结果
         */
        MultiMatchQueryBuilder queryBuilder = new MultiMatchQueryBuilder(query);
        // 构建搜索请求
        SearchRequestBuilder requestBuilder = client.prepareSearch(bookIndex).setTypes(bookType).setQuery(queryBuilder);
        // 返回搜索响应的结果
        SearchResponse searchResponse = requestGet("multiBatch", requestBuilder);
        return CommonQueryUtils.buildResponse(searchResponse);
    }
}
