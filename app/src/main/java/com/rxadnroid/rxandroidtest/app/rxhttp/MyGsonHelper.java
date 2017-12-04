package com.rxadnroid.rxandroidtest.app.rxhttp;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by shenxq on 2016/10/14.
 */
public class MyGsonHelper {

    public static <T> T jsonToObj(JSONObject jsonObject, Class<T> tClass){
        Gson gson = new Gson();
        return gson.fromJson(jsonObject.toString(),tClass);
    }

    public static <T> List<T> jsonToList(JSONArray jsonArray, Class<T> tClass){
        Gson gson = new Gson();
        Type listType = new ParameterizedTypeImpl(List.class, new Class[]{tClass});
        return gson.fromJson(jsonArray.toString(),listType);
    }

    public static String toJson(Object object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static class ParameterizedTypeImpl implements ParameterizedType {
        private final Class raw;
        private final Type[] args;
        public ParameterizedTypeImpl(Class raw, Type[] args) {
            this.raw = raw;
            this.args = args != null ? args : new Type[0];
        }
        @Override
        public Type[] getActualTypeArguments() {
            return args;
        }
        @Override
        public Type getRawType() {
            return raw;
        }
        @Override
        public Type getOwnerType() {return null;}
    }
}
