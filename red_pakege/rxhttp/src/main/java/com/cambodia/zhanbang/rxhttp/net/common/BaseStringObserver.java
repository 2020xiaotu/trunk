package com.cambodia.zhanbang.rxhttp.net.common;

import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;
import com.cambodia.zhanbang.rxhttp.net.utils.RxExceptionUtils;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Response;

/**
 * created  by ganzhe on 2019/11/16.
 */
public abstract class BaseStringObserver <T extends ResponseBody> implements Observer<Response<T>>  {

    private static final String RESPONSE_RETURN_ERROR = "返回数据为空";

    public BaseStringObserver() {
    }

    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();
    }

    @Override
    public void onNext(Response<T> tResponse) {
        if (tResponse.body()==null){
            onFail(RESPONSE_RETURN_ERROR);
        }else {
            try {
                String result = tResponse.body().string();
                Headers headers = tResponse.raw().headers();
                onSuccess(result,headers);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        String errorMsg = RxExceptionUtils.exceptionHandler(e);
        onFail(errorMsg);
        onRequestEnd();
    }

    @Override
    public void onComplete() {
        onRequestEnd();
    }

    public abstract void onSuccess(String result,Headers headers);
    abstract public void onFail(String msg);
    /**
     * 网络请求开始
     */
    protected void onRequestStart() {
    }
    /**
     * 网络请求结束
     */
    protected void onRequestEnd() {
    }
}
