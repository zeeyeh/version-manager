package com.zeeyeh.versionmanager.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;

import java.util.List;

public class JsonUtil {
    public static JSONObject toJsonObject(String json) {
        return JSONObject.parseObject(json);
    }

    public static JSONArray toJsonArray(String json) {
        return JSONArray.parseArray(json);
    }

    public static JSONArray toJsonArray(String json, JSONReader.Feature features) {
        return JSONArray.parseArray(json, features);
    }

    public static String toJson(JSONObject jsonObject) {
        return JSONObject.toJSONString(jsonObject);
    }

    public static String toJson(JSONObject jsonObject, JSONWriter.Feature features) {
        return JSONObject.toJSONString(jsonObject, features);
    }

    public static String toJson(JSONArray jsonArray) {
        return JSONArray.toJSONString(jsonArray);
    }

    public static String toJson(JSONArray jsonArray, JSONWriter.Feature features) {
        return JSONArray.toJSONString(jsonArray, features);
    }

    public static List<String> toStringList(JSONArray jsonArray) {
        return jsonArray.toJavaList(String.class);
    }
}
