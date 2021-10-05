package com.example.red_pakege.util;

import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;

import org.json.JSONException;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * created  by ganzhe on 2019/11/16.
 */
public class HttpUtil {

    public static RequestBody postRequest(HashMap<String,Object> data){
        org.json.JSONObject root = new org.json.JSONObject();
        try {
            SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
            if(!data.containsKey("userId")){
                data.put("userId",sp.getUserId());
            }
            if(!data.containsKey("sign")){
                data.put("sign","123456789");
            }
            if(!data.containsKey("source")){
                data.put("source",0);
            }
            if(!data.containsKey("token")){
                data.put("token",sp.getToken());
            }
            if(!data.containsKey("user_id")){
                data.put("user_id",sp.getUserId());
            }
            Iterator<String> iterator = data.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                root.put(key, String.valueOf(data.get(key)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return RequestBody.create(MediaType.parse("application/json"),root.toString());
    }

    public static Map<String,Object> getRequest(HashMap<String,Object>data){
        return data;
    }
    public static String pathRequsest(String path){
        return path;
    }
    public static RequestBody uploadRequest(String filePath){
        File file = new File(filePath);//filePath为图片位置
        RequestBody  fileBody  = RequestBody.create(MediaType.parse("image/jpg"), file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", file.getName(), fileBody)
                .build();
        return requestBody;
    }
}
