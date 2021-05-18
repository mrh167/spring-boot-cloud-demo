package com.xiaoma.study.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: ext.maruihua1
 * Date: 2021/5/18
 * Time: 18:42
 * Description: No Description
 */

/**
 * 这里使用的alibaba的fastjson,
 * 使用时需要将字符串放进map里
 * 然后才能
 *
 *
 *
 *
 *
 *
 *
 */
public class JsonUtils {

    public static void main(String[] args) {
        String json = "{\n" +
                "  \"paramz\": {\n" +
                "    \"feeds\": [\n" +
                "      {\n" +
                "        \"id\": 299076,\n" +
                "        \"oid\": 288340,\n" +
                "        \"category\": \"article\",\n" +
                "        \"data\": {\n" +
                "          \"subject\": \"荔枝新闻3.0：不止是阅读\",\n" +
                "          \"summary\": \"江苏广电旗下资讯类手机应用“荔枝新闻”于近期推出全新升级换代的3.0版。\",\n" +
                "          \"cover\": \"/Attachs/Article/288340/3e8e2c397c70469f8845fad73aa38165_padmini.JPG\",\n" +
                "          \"pic\": \"\",\n" +
                "          \"format\": \"txt\",\n" +
                "          \"changed\": \"2015-09-22 16:01:41\"\n" +
                "        }\n" +
                "      }\n" +
                "    ],\n" +
                "    \"PageIndex\": 1,\n" +
                "    \"PageSize\": 20,\n" +
                "    \"TotalCount\": 53521,\n" +
                "    \"TotalPage\": 2677\n" +
                "  }\n" +
                "}";
        //将json串转成map集合
        Map<String, Object> jsonToMap = parseJsonToMap(json);

        List<HashMap<String, Object>> test = parseToJsonObject(jsonToMap);
        test.forEach(key ->{
            for (String s : key.keySet()) {
                System.out.println(s+ "===="+key.get(s));
            }
        });

        System.out.println("===========================================");

        for (Map<String, Object> map : test) {
            for (String key: map.keySet()) {
                System.out.println(key+ "===="+map.get(key));
            }
        }
        System.out.println(test.toString());
    }

    /**
     * 由于jsonObject底层入参是map，所以在使用jsonObject转换时先要将入参转换成map集合
     * public JSONObject(Map<String, Object> map) {
     *         if (map == null) {
     *             throw new IllegalArgumentException("map is null.");
     *         } else {
     *             this.map = map;
     *         }
     *     }
     *
     *
     *     parseJsonToMap将json串转换成map集合
     *
     * @param json
     * @return
     */
    private static Map<String, Object> parseJsonToMap(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        Map<String,Object> jsonMap = new HashMap<String,Object>();
        jsonMap.putAll(jsonObject);
        return jsonMap;
    }

    /**
     *{
     *     "paramz": {
     *         "feeds": [
     *             {
     *                 "id": ,
     *                 "oid": ,
     *                 "category": "",
     *                 "data": {
     *                     "subject": "",
     *                     "summary": "",
     *                     "cover": "",
     *                     "pic": "",
     *                     "format": "",
     *                     "changed": ""
     *                 }
     *             }
     *         ],
     *     }
     * }
     *
     *
     * {}一般就是对象  getJSONObject(String val)获取格式为{}的json 根 对象
     * [] 一般就是数组   getJSONArray() 通过根对象获取json数组
     * getJSONObject(Integer v)   获取数组里的每一个属性
     * @param jsonToMap
     * @return
     */
    static List<HashMap<String, Object>> parseToJsonObject(Map<String, Object> jsonToMap){
        List<HashMap<String,Object>> dataList;
        dataList = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(jsonToMap);
            JSONObject paraMzObject = root.getJSONObject("paramz");
            JSONArray feedsArray = paraMzObject.getJSONArray("feeds");
            for (int i = 0;i< feedsArray.size();i++) {
                JSONObject sonObject = feedsArray.getJSONObject(i);
                JSONObject dataObject = sonObject.getJSONObject("data");
                String subject = dataObject.getString("subject");
                String summary = dataObject.getString("summary");
                String cover = dataObject.getString("cover");
                HashMap<String, Object> map = new HashMap<>();
                map.put("subject",subject);
                map.put("summary",summary);
                map.put("cover",cover);
                dataList.add(map);
            }
            return dataList;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
