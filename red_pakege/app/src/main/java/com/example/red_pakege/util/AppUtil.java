package com.example.red_pakege.util;

import android.content.Context;

import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;

import static com.example.red_pakege.util.RouteUtil.start2LoginAndRegisActivity;

/**
 * created  by ganzhe on 2019/11/21.
 */
public class AppUtil {
    static SharedPreferenceHelperImpl mSharedPreferenceHelperImpl = new SharedPreferenceHelperImpl();
    public static boolean CheckLoginStatus(){
        if (mSharedPreferenceHelperImpl.getLoginStatus()){
           return true;
        }
        return false;
    }
}
