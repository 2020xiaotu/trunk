package com.example.red_pakege.util;

import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;

public class ImageUtil {

    static SharedPreferenceHelperImpl mSharedPreferenceHelperImpl = new SharedPreferenceHelperImpl();

    public static String ImageUrlCheck(String img_url){
        if (StringMyUtil.isNotEmpty(img_url)&&(!img_url.startsWith("http")||!img_url.startsWith("https"))){
            img_url = mSharedPreferenceHelperImpl.getImageDomin()+img_url;
        }
        return img_url;
    }
}
