package com.wvkity.mybatis.starter.example.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wvkity.mybatis.utils.StringUtil;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class JsonUtil {
    
    private JsonUtil(){}

    public static String readJsonString(String source, String key) throws IOException {
        return readJsonString(new ClassPathResource(source), key);
    }
    
    public static String readJsonString(Resource resource, String key) throws IOException {
        String jsonStr = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
        JSONObject json = JSON.parseObject(jsonStr);
        return json.getString(key);
    }

    public static <T> List<T> read(String source, String key, Class<T> klass) throws IOException {
        String jsonArray = readJsonString(source, key);
        return StringUtil.hasText(jsonArray) ? JSON.parseArray(jsonArray, klass) : new ArrayList<>(0);
    }

    public static <T> List<T> read(Resource resource, String key, Class<T> klass) throws IOException {
        String jsonArray = readJsonString(resource, key);
        return StringUtil.hasText(jsonArray) ? JSON.parseArray(jsonArray, klass) : new ArrayList<>(0); 
    }
}
