package org.es.study.controller;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/11/8
 * Time: 16:51
 * Description: No Description
 */
@RestController
public class EsAddIndexController {

    @Autowired
    private RestHighLevelClient client;

    /**
     * 添加标准索引库
     */
    @PostMapping("/createIndex")
    public CreateIndexResponse createIndex(){
        CreateIndexRequest indexRequest = new CreateIndexRequest("my_index");
        CreateIndexResponse indexResponse = null;
        try {
            indexResponse = client.indices().create(indexRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexResponse;
    }

    @PostMapping("/createResponseIndexAndAlias")
    public CreateIndexResponse createResponseIndexAndAlias(){
//        CreateIndexRequest indexRequest = new CreateIndexRequest("my_alias_index");
//        client.indices().create( )
        String index = "xAddIndex";
        IndexRequest indexRequest = new IndexRequest();
//        indexRequest.
//        client.index()
        return null;
    }

}
