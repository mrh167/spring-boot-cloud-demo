package org.es.study;

import com.carrotsearch.hppc.cursors.ObjectObjectCursor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.es.study.domain.Book;
import org.es.study.utils.DataUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/11/11
 * Time: 11:34
 * Description: No Description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class EsTest {

    @Autowired
    private Client client;

    @Value("${es.bookIndex}")
    private String bookIndex;

    @Value("${es.bookType}")
    private String bookType;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

    /**
     * 创建索引   设置settings    设置mapping
     */
    @Test
    public void createIndex(){
        // 设置分片数
        int settingShards = 1;
        // 设置副本数
        int settingReplicas = 0;

        // 判断索引是否存在,存在则删除
        IndicesExistsResponse existsResponse = client.admin().indices().prepareExists(bookIndex).get();

        if (existsResponse.isExists()) {
            System.out.println(bookIndex + "索引已存在!!!");
            return;
        }

        // 设置 settings
        CreateIndexResponse indexResponse = client.admin().indices().prepareCreate(bookIndex)
                .setSettings(Settings.builder()
                        .put("index.number_of_shards", settingShards)
                        .put("index.number_of_replicas", settingReplicas)).get();

        // 查看结果
        GetSettingsResponse getSettingsResponse = client.admin().indices().prepareGetSettings(bookIndex).get();
        System.out.println("索引设置结果是:");
        ImmutableOpenMap<String, Settings> indexToSettings = getSettingsResponse.getIndexToSettings();
        for (ObjectObjectCursor<String, Settings> indexToSetting : indexToSettings) {
            String index = indexToSetting.key;
            Settings settings = indexToSetting.value;
            // 分片数
            Integer shards = settings.getAsInt("index.number_of_shards", null);

            // 副本数
            Integer replicas = settings.getAsInt("index.number_of_replicas", null);
            System.out.println("index:"+ index + ", shards:" + shards + ", replicas:" + replicas);
            Assert.assertEquals(Optional.of(settingShards),Optional.of(shards));
            Assert.assertEquals(Optional.of(settingReplicas),Optional.of(replicas));
        }
    }

    @Test
    public void bulk(){
        List<Book> list = DataUtil.batchData();
        BulkRequestBuilder bulkRequestBuilder = client.prepareBulk();
        // 添加index操作到bulk中
        list.forEach(book -> {
            // 新版的API中使用setSource时，参数的个数必须是偶数，否则需要加上 setSource(json, XContentType.JSON)
            bulkRequestBuilder.add(client.prepareIndex(bookIndex, bookType, book.getId()).setSource(gson.toJson(book), XContentType.JSON));
        });

        BulkResponse bulkItemResponses = bulkRequestBuilder.get();
        if (bulkItemResponses.hasFailures()) {
            // bulkl有失败
            for (BulkItemResponse res : bulkItemResponses) {
                System.out.println("批量操作失败==========="+res.getFailure());
            }
            Assert.assertTrue(false);
        }
    }



}
