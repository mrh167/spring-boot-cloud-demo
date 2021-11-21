//package org.es.study.config;
//
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//
///**
// * Created with IntelliJ IDEA.
// * User: ext.maruihua1
// * Date: 2021/11/8
// * Time: 16:17
// * Description: No Description
// */
//@Configuration
//public class EsConfig {
//
//    // 集群地址
//    private static final String hosts = "127.0.0.1";
//    // 端口号
//    private static final int port = 9200;
//    // 使用协议
//    private static final String schema = "http";
//
//    private static ArrayList<HttpHost> hostsLists = null;
//
//    // 连接超时时间
//    private static final int connectTimeOut = 1000;
//    // 长连接超时时间
//    private static final int socketTimeOut = 30000;
//    // 获取请求连接的超时时间
//    private static final int connectionRequestTimeOut = 500;
//    //最大连接数
//    private static final int maxConnectNum = 100;
//    //最大路由连接数
//    private static final int maxConnectPerRoute = 100;
//
//    static {
//        hostsLists = new ArrayList<>();
//        String[] hostSplit = hosts.split(",");
//        for (String host : hostSplit) {
//            hostsLists.add(new HttpHost(host,port,schema));
//        }
//    }
//
//
//
//    /**
//     * RestClient实例可以通过相应的RestClientBuilder类构建，
//     * 该类是通过RestClient#builder(HttpHost…)静态方法创建的。
//     * 唯一需要的参数是客户机将与之通信的一个或多个主机，
//     * 它作为HttpHost的实例提供，如下所示
//     * @return
//     */
//    @Bean
//    public RestHighLevelClient restClient(){
//        RestClientBuilder builder = RestClient.builder(hostsLists.toArray(new HttpHost[0]));
//        // 自定义请求配置的回调
//        builder.setRequestConfigCallback(requestConfigBuilder -> {
//            requestConfigBuilder.setConnectTimeout(connectTimeOut);
//            requestConfigBuilder.setSocketTimeout(socketTimeOut);
//            requestConfigBuilder.setConnectionRequestTimeout(connectionRequestTimeOut);
//            return requestConfigBuilder;
//        });
//        // 异步httpclient连接数配置
//        builder.setHttpClientConfigCallback(httpClientBuilder -> {
//            // 自定义httpclient的配置回调
//            httpClientBuilder.setMaxConnTotal(maxConnectNum);
//            httpClientBuilder.setMaxConnPerRoute(maxConnectPerRoute);
//            return httpClientBuilder;
//        });
//        return new RestHighLevelClient(builder);
//    }
//
//
//
//
//
//}
