package com.example.red_pakege.test;

import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;
import com.example.red_pakege.net.entity.GameListEntity;
import com.example.red_pakege.net.entity.ParamYuebaoEntity;
import com.example.red_pakege.util.JsonUtil;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;

/**
 * created  by ganzhe on 2019/11/16.
 */
public class Test {
    public static void main(String args[]){
     /*   Map<String,Object> map = new HashMap();
        map.put("xh",1);
        map.put("yg","2");
        RequestBody requestBody = JsonUtil.Map2JsonRequestBody(map);
        System.out.println(requestBody);*/

        A aa = new A();
        aa.setA("111");
        aa.setB("222");
        System.out.println(aa.toString());
        String a = aa.getA();
        System.out.println(a);
        aa.setA("333");
        System.out.println(aa.toString());
        System.out.println(a);

    }
}
