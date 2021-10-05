package com.cambodia.zhanbang.rxhttp.net.interceptor;

import android.annotation.SuppressLint;
import android.text.TextUtils;


import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.google.gson.Gson;



import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.annotations.EverythingIsNonNull;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Call;
import retrofit2.Retrofit;

import static com.cambodia.zhanbang.rxhttp.net.common.RxLibConstants.SP_TOKEN;


/**
 * created  by ganzhe on 2019/9/12.
 */
public class HttpHeaderInterceptor implements Interceptor {

    SharedPreferenceHelperImpl mSharedPreferenceHelperImpl = new SharedPreferenceHelperImpl();
    String token;;

    @Override
    @EverythingIsNonNull
    public Response intercept(Chain chain) throws IOException {

        token = mSharedPreferenceHelperImpl.getToken();
        //添加Token请求头 这里的token应当是从本地数据库中读取的 **********
        Request request = chain.request().newBuilder()
                .addHeader(SP_TOKEN, token)
                .build();
        Response response = chain.proceed(request);
        LogUtils.e("请求头token:" + token);
        return response;
    }



}
