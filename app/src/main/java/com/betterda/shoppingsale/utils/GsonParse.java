package com.betterda.shoppingsale.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * json解析
 *
 * @author Administrator
 */
public class GsonParse {

    private static Gson gson = null;

    static {
        if (null == gson) {
            gson = new GsonBuilder().create();
        }
    }

    /**
     * 使用Gson解析String
     *
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> T getObject(String jsonString, Class<T> cls) {
        T t = null;
        if (null != jsonString) {
            t = gson.fromJson(jsonString, cls);
        }

        return t;
    }

    /**
     * 使用Gson解析String
     *
     * @param
     * @param cls
     * @return
     */
    public static <T> T getObject(JsonElement jsonElement, Class<T> cls) {
        T t = null;
        t = gson.fromJson(jsonElement, cls);

        return t;
    }

    /**
     * 使用Gson解析list封装String
     *
     * @param jsonString
     * @param
     * @return
     */

    public static List<String> getListString(String jsonString) {
        List<String> list = new ArrayList<String>();
        list = gson.fromJson(jsonString, new TypeToken<List<String>>() {
        }.getType());
        return list;

    }

    /**
     * 使用Gson解析list封装Map
     *
     * @param jsonString
     * @return
     */
    public static List<Map<String, Object>> getListMap(String jsonString) {
        List<Map<String, Object>> list = gson.fromJson(jsonString, new TypeToken<List<Map<String, Object>>>() {
        }.getType());
        return list;
    }




}
