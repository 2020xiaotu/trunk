package com.example.red_pakege.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;

import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;
import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * created  by ganzhe on 2019/11/16.
 */
public class JsonUtil {

    public static <T extends BaseEntity> RequestBody Entity2JsonRequestBody(T entity){
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonStr = gson.toJson(entity,entity.getClass());
        return  RequestBody.create(MediaType.parse("application/json"),jsonStr);
    }

    public static  RequestBody Map2JsonRequestBody(Map map){
        Gson gson = new GsonBuilder().serializeNulls().create();
        String jsonStr = gson.toJson(map);
        return  RequestBody.create(MediaType.parse("application/json"),jsonStr);
    }

    public static String getJson(Context context, String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    public static List<?> analysisJson(Context context,String fileName) {
        //得到本地json文本内容
        //String fileNames = "car_code.json";
        String myjson = getJson(context, fileName);
        //json转换为集合
        return new Gson().fromJson(myjson, new TypeToken<List<?>>(){}.getType());
    }


    public static UserEntity Str2UserEntity(String userinfo){
        if (TextUtils.isEmpty(userinfo)){
            return null;
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        UserEntity userEntity = gson.fromJson(userinfo,UserEntity.class);
        return  userEntity;
    }

    public static String UserEntity2Str(UserEntity userEntity){
        if (userEntity==null){
            return null;
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        String str = gson.toJson(userEntity);
        return  str;
    }

}
