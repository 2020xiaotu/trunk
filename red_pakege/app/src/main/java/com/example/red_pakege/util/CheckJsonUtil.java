package com.example.red_pakege.util;

import com.alibaba.fastjson.JSONObject;

public class CheckJsonUtil {
    public static Boolean checkBooleanJson(JSONObject jsonObject, String key){
        Boolean objectBoolean = jsonObject.getBoolean(key);
        return  null== objectBoolean ?false: objectBoolean;
    }
    public static String checkStringJson(JSONObject jsonObject,String key){
        String objectString = jsonObject.getString(key);
        return StringMyUtil.isEmptyString(objectString)?"": objectString;
    }
}
