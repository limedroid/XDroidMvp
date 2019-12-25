package com.lennon.cn.utill.utill;


import android.text.TextUtils;
import cn.droidlover.xdroidmvp.log.XLog;
import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtils {
    private static Gson mGson;

    static {
        if (mGson == null) {
            mGson = new Gson();
        }
    }

    public static String toJson(Object object) {
        return mGson.toJson(object);
    }

    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> str2ListMap(String jsonArrStr) {
        System.out.println(jsonArrStr);
        List<Map<String, Object>> jsonObjList = new ArrayList<Map<String, Object>>();
        List<?> jsonList = jsonToList(jsonArrStr);
        for (Object object : jsonList) {
            String jsonStr = mGson.toJson(object);
            Map<?, ?> json = jsonToMap(jsonStr);
            System.out.println(json.toString());
            jsonObjList.add((Map<String, Object>) json);
        }
        return jsonObjList;
    }

    /**
     * 将传入的json字符串解析为List集合
     *
     * @param jsonStr
     * @return
     */
    public static List<?> jsonToList(String jsonStr) {
        List<?> ObjectList = null;
        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<?>>() {
        }.getType();
        ObjectList = mGson.fromJson(jsonStr, type);
        return ObjectList;
    }

    /**
     * 将传入的json字符串解析为Map集合
     *
     * @param jsonStr
     * @return
     */
    public static Map<?, ?> jsonToMap(String jsonStr) {
        Map<?, ?> ObjectMap = null;
        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<Map<?, ?>>() {
        }.getType();
        ObjectMap = mGson.fromJson(jsonStr, type);
        return ObjectMap;
    }

    /**
     * map转json字符串
     *
     * @param m
     * @return
     */
    public static String map2json(Map<?, ?> m) {
        mGson.toJson(m).toString();
        return mGson.toJson(m).toString();
    }

    /**
     * JsonObject 转换为对象
     */
    public static Object jsonToObject(final Class<?> cls, final JsonDeserializer deserializer, final JsonObject json) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(cls, deserializer);
        Gson gson = builder.create();
        gson.fromJson(json, cls);
        return gson.fromJson(json, cls);
    }

    public static <T> T jsonToObject(String json, Class<T> tClass) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }
        T t;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(json, tClass);
        } catch (Exception e) {
            XLog.e(e.getMessage());
            return null;
        }
        return t;
    }

    /**
     * JsonObject 转换为对象数组
     */
    public static <T> List<T> jsonToList(final Class<T> cls, final JsonDeserializer deserializer, final JsonArray json) {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(cls, deserializer);
        Gson gson = builder.create();
        List<T> items = new ArrayList<>();
        for (JsonElement element : json) {
            items.add(gson.fromJson(element, cls));
        }
        return items;
    }

    /**
     * string to json array
     *
     * @param datas
     * @param <T>
     * @return
     */
    public static <T> JsonArray listToJson(List<T> datas) {
        JsonParser parser = new JsonParser();
        Gson gson = new Gson();
        String str = gson.toJson(datas);
        JsonArray aJson = parser.parse(str).getAsJsonArray();
        return aJson;
    }

    public static String listToJsonString(List list) {
        return new Gson().toJson(list);
    }
}
