package com.example.new_application.net.api;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.common.ApiConfig;
import com.cambodia.zhanbang.rxhttp.net.common.BaseStringObserver;
import com.cambodia.zhanbang.rxhttp.net.model.SingleLoginEvent;
import com.cambodia.zhanbang.rxhttp.net.utils.AppContextUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.CommonModule;
import com.cambodia.zhanbang.rxhttp.net.utils.RxUtil;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.cambodia.zhanbang.rxhttp.net.utils.SystemUtil;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.new_application.BuildConfig;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.utils.ErrorUtil;
import com.example.new_application.utils.MyApplication;
import com.example.new_application.utils.Utils;
import com.hjq.toast.ToastUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;

import static android.widget.Toast.LENGTH_SHORT;
import static com.cambodia.zhanbang.rxhttp.net.common.BaseStringObserver.RESPONSE_RETURN_ERROR;

public class HttpApiUtils {
    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
    private static final String REQUEST_404 = "?????????????????????";
    private static final String REQUEST_400 = "????????????";
    /**
     * (???????????????,??????errorLayout)
     * @param activity ?????????
     * @param url ????????????,?????????httpApi????????????url?????????,????????????????????????
     * @param data ?????????
     * @param onRequestLintener  ??????????????????
     */

    public static void request(final Activity activity,Fragment fragment, final String url, HashMap<String, Object> data, final OnRequestLintener onRequestLintener) {
        BaseStringObserver<ResponseBody> observer = new BaseStringObserver<ResponseBody>() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if(jsonObject.getString("code").equals("0")){
                    if(onRequestLintener!=null){
                        onRequestLintener.onSuccess(jsonObject.getString("data"));
                    }
                }else {
                    if(onRequestLintener!=null){
                        String message = jsonObject.getString("msg");
                        ToastUtils.show(message);
                        onRequestLintener.onFail(message);
                    }
                }
            }
            @Override
            public void onFail(String msg) {
                if(onRequestLintener!=null){
                    ToastUtils.show(msg);
                    onRequestLintener.onFail(msg);
                }
            }

            @Override
            protected void onRequestStart() {
                super.onRequestStart();
                if((Activity)activity instanceof BaseActivity){
                        ((BaseActivity) activity).showLoading();

                }
            }

            @Override
            protected void onRequestEnd() {
                super.onRequestEnd();
                if((Activity)activity instanceof BaseActivity){
                    ((BaseActivity) activity).closeLoading();
                }
            }
        };
        String methodName = getMethodName(url);
        String[] split = methodName.split(",");
        RequestBody requestBody =null;
        HashMap<String ,Object>map =null;
        String getOrPost = split[0];
        if(getOrPost.equalsIgnoreCase("get")){
            map= (HashMap<String, Object>) HttpUtil.getRequest(data);
        }else {
            requestBody=HttpUtil.postRequest(data);
        }
        HttpApiImpl httpApi = HttpApiImpl.getInstance();
        IHttpApi iHttpApiT = httpApi.iHttpApiT;
        Method[] declaredMethods = iHttpApiT.getClass().getDeclaredMethods();
        for (Method method:declaredMethods) {

            if(method.getName().equalsIgnoreCase(split[1])){
                try {
//                    int parameterCount = method.getParameterCount();
                    int parameterCount = method.getParameterTypes().length;
                    Object invoke;
                    if(getOrPost.equalsIgnoreCase("get")){
                        invoke =parameterCount==0?method.invoke(iHttpApiT): method.invoke(iHttpApiT,  map);
                    }else {
                        invoke =parameterCount==0?method.invoke(iHttpApiT): method.invoke(iHttpApiT,  requestBody);
                    }
                    Observable<Response<ResponseBody>> responseObservable = (Observable<Response<ResponseBody>>) invoke;
                    if(null==fragment){
                        responseObservable
                                .compose(RxUtil.rxSchedulerHelper())
                                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) activity)))
                                .subscribe(observer);
                    }else {
                        responseObservable
                                .compose(RxUtil.rxSchedulerHelper())
                                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) fragment)))
                                .subscribe(observer);
                    }

                            //??????java8???????????????subscribe(observer)??????;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    public static void wwwRequest(final Activity activity,Fragment fragment, final String url, HashMap<String, Object> data, final OnRequestLintener onRequestLintener) {
        BaseStringObserver<ResponseBody> observer = new BaseStringObserver<ResponseBody>() {

            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if(jsonObject.getString("code").equals("0")){
                    if(onRequestLintener!=null){
                        onRequestLintener.onSuccess(jsonObject.getString("data"));
                    }
                }else {
                    if(onRequestLintener!=null){
                        String message = jsonObject.getString("msg");
                        ToastUtils.show(message);
                        onRequestLintener.onFail(message);
                    }
                } 
            }

            @Override
            public void onFail(String msg) {
                if(onRequestLintener!=null){
                    ToastUtils.show(msg);
                    onRequestLintener.onFail(msg);
                }
            }

            @Override
            protected void onRequestStart() {
                super.onRequestStart();
                if((Activity)activity instanceof BaseActivity){
                    ((BaseActivity) activity).showLoading();

                }
            }

            @Override
            protected void onRequestEnd() {
                super.onRequestEnd();
                if((Activity)activity instanceof BaseActivity){
                    ((BaseActivity) activity).closeLoading();
                }
            }
        };
        String methodName = getMethodName(url);
        String[] split = methodName.split(",");
        RequestBody requestBody =null;
        HashMap<String ,Object>map =null;
        String getOrPost = split[0];
        if(getOrPost.equalsIgnoreCase("get")){
            map= (HashMap<String, Object>) HttpUtil.getRequest(data);
        }else {
            data=HttpUtil.wwwPostRequestBody(data);
        }
        HttpApiImpl httpApi = HttpApiImpl.getInstance();
        IHttpApi iHttpApiT = httpApi.iHttpApiT;
        Method[] declaredMethods = iHttpApiT.getClass().getDeclaredMethods();
        for (Method method:declaredMethods) {

            if(method.getName().equalsIgnoreCase(split[1])){
                try {
//                    int parameterCount = method.getParameterCount();
                    int parameterCount = method.getParameterTypes().length;
                    Object invoke;
                    if(getOrPost.equalsIgnoreCase("get")){
                        invoke =parameterCount==0?method.invoke(iHttpApiT): method.invoke(iHttpApiT,  map);
                    }else {
                        invoke =parameterCount==0?method.invoke(iHttpApiT): method.invoke(iHttpApiT,  data);
                    }
                    Observable<Response<ResponseBody>> responseObservable = (Observable<Response<ResponseBody>>) invoke;
                    if(null==fragment){
                        responseObservable
                                .compose(RxUtil.rxSchedulerHelper())
                                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) activity)))
                                .subscribe(observer);
                    }else {
                        responseObservable
                                .compose(RxUtil.rxSchedulerHelper())
                                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) fragment)))
                                .subscribe(observer);
                    }

                    //??????java8???????????????subscribe(observer)??????;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    public static void normalRequest(final Activity activity,Fragment fragment, final String url, HashMap<String, Object> data, final OnRequestLintener onRequestLintener) {
        BaseStringObserver<ResponseBody> observer = new BaseStringObserver<ResponseBody>() {
            @Override
            public void onSuccess(String result) {

                JSONObject jsonObject = JSONObject.parseObject(result);
                if(jsonObject.getString("code").equals("0")){
                    if(onRequestLintener!=null){
                        onRequestLintener.onSuccess(jsonObject.getString("data"));
                    }
                }else {
                    if(onRequestLintener!=null){
                        String message = jsonObject.getString("msg");
                        Toast toast = Toast.makeText(MyApplication.getInstance(), null, Toast.LENGTH_SHORT);
                        toast.setText(message);
                        toast.show();
                        onRequestLintener.onFail(message);
                    }
                }
            }
            @Override
            public void onFail(String msg) {
                if(onRequestLintener!=null){
                    ToastUtils.show(msg);
                    onRequestLintener.onFail(msg);
                }
            }
        };
        String methodName = getMethodName(url);
        String[] split = methodName.split(",");
        RequestBody requestBody =null;
        HashMap<String ,Object>map =null;
        String getOrPost = split[0];
        if(getOrPost.equalsIgnoreCase("get")){
            map= (HashMap<String, Object>) HttpUtil.getRequest(data);
        }else {
            requestBody=HttpUtil.postRequest(data);
        }
        HttpApiImpl httpApi = HttpApiImpl.getInstance();
        IHttpApi iHttpApiT = httpApi.iHttpApiT;
        Method[] declaredMethods = iHttpApiT.getClass().getDeclaredMethods();
        for (Method method:declaredMethods) {

            if(method.getName().equalsIgnoreCase(split[1])){
                try {
//                    int parameterCount = method.getParameterCount();
                    int parameterCount = method.getParameterTypes().length;
                    Object invoke;
                    if(getOrPost.equalsIgnoreCase("get")){
                        invoke =parameterCount==0?method.invoke(iHttpApiT): method.invoke(iHttpApiT,  map);
                    }else {
                        invoke =parameterCount==0?method.invoke(iHttpApiT): method.invoke(iHttpApiT,  requestBody);
                    }
                    Observable<Response<ResponseBody>> responseObservable = (Observable<Response<ResponseBody>>) invoke;
                    if(fragment==null){
                        responseObservable
                                .compose(RxUtil.rxSchedulerHelper())
                                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) activity)))
                                .subscribe(observer);
                    }else {
                        responseObservable
                                .compose(RxUtil.rxSchedulerHelper())
                                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) fragment)))
                                .subscribe(observer);
                    }
                    //??????java8???????????????subscribe(observer)??????;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    public static void wwwNormalRequest(final Activity activity,Fragment fragment, final String url, HashMap<String, Object> data, final OnRequestLintener onRequestLintener) {
        BaseStringObserver<ResponseBody> observer = new BaseStringObserver<ResponseBody>() {
            @Override
            public void onSuccess(String result) {

                JSONObject jsonObject = JSONObject.parseObject(result);
                if(jsonObject.getString("code").equals("0")){
                    if(onRequestLintener!=null){
                        onRequestLintener.onSuccess(jsonObject.getString("data"));
                    }
                }else {
                    if(onRequestLintener!=null){
                        String message = jsonObject.getString("msg");
                        Toast toast = Toast.makeText(MyApplication.getInstance(), null, Toast.LENGTH_SHORT);
                        toast.setText(message);
                        toast.show();
                        onRequestLintener.onFail(message);
                    }
                }
            }
            @Override
            public void onFail(String msg) {
                if(onRequestLintener!=null){
                    ToastUtils.show(msg);
                    onRequestLintener.onFail(msg);
                }
            }
        };
        String methodName = getMethodName(url);
        String[] split = methodName.split(",");
        RequestBody requestBody =null;
        HashMap<String ,Object>map =null;
        String getOrPost = split[0];
        if(getOrPost.equalsIgnoreCase("get")){
            map= (HashMap<String, Object>) HttpUtil.getRequest(data);
        }else {
            data=HttpUtil.wwwPostRequestBody(data);
        }
        HttpApiImpl httpApi = HttpApiImpl.getInstance();
        IHttpApi iHttpApiT = httpApi.iHttpApiT;
        Method[] declaredMethods = iHttpApiT.getClass().getDeclaredMethods();
        for (Method method:declaredMethods) {

            if(method.getName().equalsIgnoreCase(split[1])){
                try {
//                    int parameterCount = method.getParameterCount();
                    int parameterCount = method.getParameterTypes().length;
                    Object invoke;
                    if(getOrPost.equalsIgnoreCase("get")){
                        invoke =parameterCount==0?method.invoke(iHttpApiT): method.invoke(iHttpApiT,  map);
                    }else {
                        invoke =parameterCount==0?method.invoke(iHttpApiT): method.invoke(iHttpApiT,  data);
                    }
                    Observable<Response<ResponseBody>> responseObservable = (Observable<Response<ResponseBody>>) invoke;
                    if(fragment==null){
                        responseObservable
                                .compose(RxUtil.rxSchedulerHelper())
                                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) activity)))
                                .subscribe(observer);
                    }else {
                        responseObservable
                                .compose(RxUtil.rxSchedulerHelper())
                                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) fragment)))
                                .subscribe(observer);
                    }
                    //??????java8???????????????subscribe(observer)??????;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    /**
     *  application/json ?????????????????? (???????????????, ???????????? ???????????? ????????????  ??????????????????,???????????????????????????????????????,?????????????????????????????????????????????????????????)
     * @param fragment ???????????????fragment  (?????????activity?????????,???null)
     * @param url ????????????
     * @param data ?????????
     * @param loadingLinear  ??????????????????
     * @param errorLinear ?????????????????????
     * @param reloadTv   ?????????????????????(??????????????????????????????,????????????????????????????????????)
     * @param view  ????????? ????????????  ????????????????????????view (?????????rececleView??????refreshLayout)
     * @param isLoadmore  ??????????????????????????????
     * @param isrefresh  ??????????????????????????????
     * @param onRequestLintener  ??????????????????
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static void showLoadRequest(Activity activity, Fragment fragment, String url, HashMap<String, Object> data, ConstraintLayout loadingLinear, LinearLayout errorLinear, TextView reloadTv, View view, boolean isLoadmore, boolean isrefresh, OnRequestLintener onRequestLintener) {
        BaseStringObserver<ResponseBody> observer = new BaseStringObserver<ResponseBody>() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if(jsonObject.getString("code").equals("0")){
                    if(onRequestLintener!=null){
                        onRequestLintener.onSuccess(jsonObject.getString("data"));
                    }
                }else {
                    if(onRequestLintener!=null){
                        String message = jsonObject.getString("msg");
                        Toast toast = Toast.makeText(MyApplication.getInstance(), null, Toast.LENGTH_SHORT);
                        toast.setText(message);
                        toast.show();
                        onRequestLintener.onFail(message);
                    }
                }
            }
            @Override
            public void onFail(String msg) {
                ToastUtils.show(msg);
                if(null!=onRequestLintener){
                    onRequestLintener.onFail(msg);
                }
                if(null!=errorLinear){
                    if(null!=fragment){
                        ErrorUtil.showErrorLayout(fragment,view,errorLinear,reloadTv);
                    }else {
                        ErrorUtil.showErrorLayout((Activity)activity,view,errorLinear,reloadTv);
                    }
                }
            }
            @Override
            protected void onRequestStart() {
                super.onRequestStart();
                if(!isLoadmore&&!isrefresh){
                    if(null!=loadingLinear){
                        loadingLinear.setVisibility(View.VISIBLE);
                    }
                    if(null!=errorLinear){
                        ErrorUtil.hideErrorLayout(view,errorLinear);
                    }
                }
            }

            @Override
            protected void onRequestEnd() {
                super.onRequestEnd();
                if(null!=loadingLinear){
                    loadingLinear.setVisibility(View.GONE);
                }
            }
        };

        String methodName = getMethodName(url);
        String[] split = methodName.split(",");
        RequestBody requestBody = null;
        HashMap<String,Object> map = null;
        String getOrPost = split[0];
        SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
        if(getOrPost.equalsIgnoreCase("get")){
            map= (HashMap<String, Object>) HttpUtil.getRequest(data);
        }else {
            requestBody= HttpUtil.postRequest(data);
        }

        IHttpApi iHttpApiT = HttpApiImpl.getInstance().iHttpApiT;

        Method[] declaredMethods = iHttpApiT.getClass().getDeclaredMethods();
        for (Method method:declaredMethods) {
            if(method.getName().equalsIgnoreCase(split[1])){
                try {

                    int parameterCount = method.getParameterTypes().length;
                    Object invoke;
                    if(getOrPost.equalsIgnoreCase("get")){
                        invoke =parameterCount==0?method.invoke(iHttpApiT): method.invoke(iHttpApiT,  map);
                    }else {
                        invoke =parameterCount==0?method.invoke(iHttpApiT): method.invoke(iHttpApiT,  requestBody);
                    }
                    Observable<Response<ResponseBody>> responseObservable = (Observable<Response<ResponseBody>>) invoke;
                    if(fragment==null){
                        responseObservable
                                .compose(RxUtil.rxSchedulerHelper())
                                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) activity)))
                                .subscribe(observer);
                    }else {
                        responseObservable
                                .compose(RxUtil.rxSchedulerHelper())
                                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) fragment)))
                                .subscribe(observer);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }




    /**
     *  x-www-form-urlencoded  ?????????????????? (???????????????, ???????????? ???????????? ????????????  ??????????????????,???????????????????????????????????????,?????????????????????????????????????????????????????????)
     * @param fragment ???????????????fragment  (?????????activity?????????,???null)
     * @param url ????????????
     * @param data ?????????
     * @param loadingLinear  ??????????????????
     * @param errorLinear ?????????????????????
     * @param reloadTv   ?????????????????????(??????????????????????????????,????????????????????????????????????)
     * @param view  ????????? ????????????  ????????????????????????view (?????????rececleView??????refreshLayout)
     * @param isLoadmore  ??????????????????????????????
     * @param isrefresh  ??????????????????????????????
     * @param onRequestLintener  ??????????????????
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static void wwwShowLoadRequest(Activity activity, Fragment fragment, String url, HashMap<String, Object> data, ConstraintLayout loadingLinear, LinearLayout errorLinear, TextView reloadTv, View view, boolean isLoadmore, boolean isrefresh, OnRequestLintener onRequestLintener) {
        BaseStringObserver<ResponseBody> observer = new BaseStringObserver<ResponseBody>() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if(jsonObject.getString("code").equals("0")){
                    if(onRequestLintener!=null){
                        onRequestLintener.onSuccess(jsonObject.getString("data"));
                    }
                }else {
                    if(onRequestLintener!=null){
                        String message = jsonObject.getString("msg");
                        Toast toast = Toast.makeText(MyApplication.getInstance(), null, Toast.LENGTH_SHORT);
                        toast.setText(message);
                        toast.show();
                        onRequestLintener.onFail(message);
                    }
                }
            }
            @Override
            public void onFail(String msg) {
                ToastUtils.show(msg);
                if(null!=onRequestLintener){
                    onRequestLintener.onFail(msg);
                }
                if(null!=errorLinear){
                    if(null!=fragment){
                        ErrorUtil.showErrorLayout(fragment,view,errorLinear,reloadTv);
                    }else {
                        ErrorUtil.showErrorLayout((Activity)activity,view,errorLinear,reloadTv);
                    }
                }
            }
            @Override
            protected void onRequestStart() {
                super.onRequestStart();
                if(!isLoadmore&&!isrefresh){
                    if(null!=loadingLinear){
                        loadingLinear.setVisibility(View.VISIBLE);
                    }
                    if(null!=errorLinear){
                        ErrorUtil.hideErrorLayout(view,errorLinear);
                    }
                }
            }

            @Override
            protected void onRequestEnd() {
                super.onRequestEnd();
                if(null!=loadingLinear){
                    loadingLinear.setVisibility(View.GONE);
                }
            }
        };

        String methodName = getMethodName(url);
        String[] split = methodName.split(",");
        RequestBody requestBody = null;
        HashMap<String,Object> map = null;
        String getOrPost = split[0];
        SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
        if(getOrPost.equalsIgnoreCase("get")){
            map= (HashMap<String, Object>) HttpUtil.getRequest(data);
        }else {
            data=HttpUtil.wwwPostRequestBody(data);
        }

        IHttpApi iHttpApiT = HttpApiImpl.getInstance().iHttpApiT;

        Method[] declaredMethods = iHttpApiT.getClass().getDeclaredMethods();
        for (Method method:declaredMethods) {
            if(method.getName().equalsIgnoreCase(split[1])){
                try {
                    int parameterCount = method.getParameterTypes().length;
                    Object invoke;
                    if(getOrPost.equalsIgnoreCase("get")){
                        invoke =parameterCount==0?method.invoke(iHttpApiT): method.invoke(iHttpApiT,  map);
                    }else {
//                        invoke =parameterCount==0?method.invoke(iHttpApiT): method.invoke(iHttpApiT,  requestBody);
                        //x-www-form-urlencoded??????body ?????????map
                        invoke =parameterCount==0?method.invoke(iHttpApiT): method.invoke(iHttpApiT,  data);
                    }
                    Observable<Response<ResponseBody>> responseObservable = (Observable<Response<ResponseBody>>) invoke;
                    if(fragment==null){
                        responseObservable
                                .compose(RxUtil.rxSchedulerHelper())
                                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) activity)))
                                .subscribe(observer);
                    }else {
                        responseObservable
                                .compose(RxUtil.rxSchedulerHelper())
                                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) fragment)))
                                .subscribe(observer);
                    }

                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    /**
     * (???????????????,??????errorLayout)
     * @param activity ?????????
     * @param url ????????????,?????????httpApi????????????url?????????,????????????????????????
     * @param
     * @param onRequestLintener  ??????????????????
     */

    public static void pathRequest(final Activity activity, final  Fragment fragment,final String url, String pathParam, final OnRequestLintener onRequestLintener) {
        if(null==activity && fragment == null){
            return;
        }
        BaseStringObserver<ResponseBody> observer = new BaseStringObserver<ResponseBody>() {
            @Override
            public void onSuccess(String result) {

                JSONObject jsonObject = JSONObject.parseObject(result);
                if(jsonObject.getString("code").equals("0")){
                    if(onRequestLintener!=null){
                        onRequestLintener.onSuccess(jsonObject.getString("data"));
                    }
                }else {
                    if(onRequestLintener!=null){
                        String message = jsonObject.getString("msg");
                        Toast toast = Toast.makeText(MyApplication.getInstance(), null, Toast.LENGTH_SHORT);
                        toast.setText(message);
                        toast.show();
                        onRequestLintener.onFail(message);
                    }
                }
            }
            @Override
            public void onFail(String msg) {
                if(onRequestLintener!=null){
                    ToastUtils.show(msg);
                    onRequestLintener.onFail(msg);
                }
            }

            @Override
            protected void onRequestStart() {
                super.onRequestStart();
                if((Activity)activity instanceof BaseActivity){
                    ((BaseActivity) activity).showLoading();

                }
            }

            @Override
            protected void onRequestEnd() {
                super.onRequestEnd();
                if((Activity)activity instanceof BaseActivity){
                    ((BaseActivity) activity).closeLoading();
                }
            }
        };
        String methodName = getMethodName(url);
        String[] split = methodName.split(",");
        HttpApiImpl httpApi = HttpApiImpl.getInstance();
        IHttpApi iHttpApiT = httpApi.iHttpApiT;
        Method[] declaredMethods = iHttpApiT.getClass().getDeclaredMethods();
        for (Method method:declaredMethods) {
            if(method.getName().equalsIgnoreCase(split[1])){
                try {
//                    int parameterCount = method.getParameterCount();
                    int parameterCount = method.getParameterTypes().length;
                    Object invoke;
                        invoke =parameterCount==0?method.invoke(iHttpApiT): method.invoke(iHttpApiT,  pathParam);
                    Observable<Response<ResponseBody>> responseObservable = (Observable<Response<ResponseBody>>) invoke;
                    if(null==fragment){
                        responseObservable
                                .compose(RxUtil.rxSchedulerHelper())
                                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) activity)))
                                .subscribe(observer);
                    }else {
                        responseObservable
                                .compose(RxUtil.rxSchedulerHelper())
                                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) fragment)))
                                .subscribe(observer);
                    }
                    //??????java8???????????????subscribe(observer)??????;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    public static void pathNormalRequest(final Activity activity, final Fragment fragment ,final String url, String pathParam, final OnRequestLintener onRequestLintener) {
        if(null==activity && fragment == null){
            return;
        }
        BaseStringObserver<ResponseBody> observer = new BaseStringObserver<ResponseBody>() {
            @Override
            public void onSuccess(String result) {

                JSONObject jsonObject = JSONObject.parseObject(result);
                if(jsonObject.getString("code").equals("0")){
                    if(onRequestLintener!=null){
                        onRequestLintener.onSuccess(jsonObject.getString("data"));
                    }
                }else {
                    if(onRequestLintener!=null){
                        String message = jsonObject.getString("msg");
                        Toast toast = Toast.makeText(MyApplication.getInstance(), null, Toast.LENGTH_SHORT);
                        toast.setText(message);
                        toast.show();
                        onRequestLintener.onFail(message);
                    }
                }
            }
            @Override
            public void onFail(String msg) {
                if(onRequestLintener!=null){
                    ToastUtils.show(msg);
                    onRequestLintener.onFail(msg);
                }
            }

            @Override
            protected void onRequestStart() {
                super.onRequestStart();

            }

            @Override
            protected void onRequestEnd() {
                super.onRequestEnd();

            }
        };
        String methodName = getMethodName(url);
        String[] split = methodName.split(",");
        String getOrPost = split[0];
        HttpApiImpl httpApi = HttpApiImpl.getInstance();
        IHttpApi iHttpApiT = httpApi.iHttpApiT;
        Method[] declaredMethods = iHttpApiT.getClass().getDeclaredMethods();
        for (Method method:declaredMethods) {

            if(method.getName().equalsIgnoreCase(split[1])){
                try {
//                    int parameterCount = method.getParameterCount();
                    int parameterCount = method.getParameterTypes().length;
                    Object invoke;
                    invoke =parameterCount==0?method.invoke(iHttpApiT): method.invoke(iHttpApiT,  pathParam);
                    Observable<Response<ResponseBody>> responseObservable = (Observable<Response<ResponseBody>>) invoke;
                    if(null==fragment){
                        responseObservable
                                .compose(RxUtil.rxSchedulerHelper())
                                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) activity)))
                                .subscribe(observer);
                    }else {
                        responseObservable
                                .compose(RxUtil.rxSchedulerHelper())
                                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) fragment)))
                                .subscribe(observer);
                    }
                    //??????java8???????????????subscribe(observer)??????;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    public  static interface OnRequestLintener{
        void onSuccess(String result);
        void onFail(String msg);
    }

    /**
     * ??????url??????IHttpApi??????????????????
     * @param url  ????????????(url?????????IHttpApi????????????????????????)
     * @return
     */
    public static String getMethodName(String url) {
        Method[] declaredMethods1 = IHttpApi.class.getDeclaredMethods();
        for (Method method:declaredMethods1) {
            GET getAnnotation = method.getAnnotation(GET.class);
            POST postAnnotation = method.getAnnotation(POST.class);
            if(getAnnotation==null && postAnnotation==null){
                continue;
            }
            String value =getAnnotation!=null? getAnnotation.value():postAnnotation.value();
            if(url.equals(value)){
                return (getAnnotation!=null?"get":"post")+","+method.getName();
            }
        }
        return null;
    }
    /**
     *  get?????? @phth?????????@quary?????? ?????????????????? (???????????????, ???????????? ???????????? ????????????  ??????????????????,???????????????????????????????????????,?????????????????????????????????????????????????????????)
     * @param fragment ???????????????fragment  (?????????activity?????????,???null)
     * @param url ????????????
     * @param data ?????????
     * @param loadingLinear  ??????????????????
     * @param errorLinear ?????????????????????
     * @param reloadTv   ?????????????????????(??????????????????????????????,????????????????????????????????????)
     * @param view  ????????? ????????????  ????????????????????????view (?????????rececleView??????refreshLayout)
     * @param isLoadmore  ??????????????????????????????
     * @param isrefresh  ??????????????????????????????
     * @param onRequestLintener  ??????????????????
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static void pathShowLoadRequest(Activity activity, Fragment fragment, String url, String pathParameter,HashMap<String, Object> data, ConstraintLayout loadingLinear, LinearLayout errorLinear, TextView reloadTv, View view, boolean isLoadmore, boolean isrefresh, OnRequestLintener onRequestLintener) {
        if(null==activity){
            return;
        }
        BaseStringObserver<ResponseBody> observer = new BaseStringObserver<ResponseBody>() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if(jsonObject.getString("code").equals("0")){
                    if(onRequestLintener!=null){
                        onRequestLintener.onSuccess(jsonObject.getString("data"));
                    }
                }else {
                    if(onRequestLintener!=null){
                        String message = jsonObject.getString("msg");
                        Toast toast = Toast.makeText(MyApplication.getInstance(), null, Toast.LENGTH_SHORT);
                        toast.setText(message);
                        toast.show();
                        onRequestLintener.onFail(message);
                    }
                }
            }
            @Override
            public void onFail(String msg) {
                ToastUtils.show(msg);
                if(null!=onRequestLintener){
                    onRequestLintener.onFail(msg);
                }
                if(null!=errorLinear){
                    if(null!=fragment){
                        ErrorUtil.showErrorLayout(fragment,view,errorLinear,reloadTv);
                    }else {
                        ErrorUtil.showErrorLayout((Activity)activity,view,errorLinear,reloadTv);
                    }
                }
            }
            @Override
            protected void onRequestStart() {
                super.onRequestStart();
                if(!isLoadmore&&!isrefresh){
                    if(null!=loadingLinear){
                        loadingLinear.setVisibility(View.VISIBLE);
                    }
                    if(null!=errorLinear){
                        ErrorUtil.hideErrorLayout(view,errorLinear);
                    }
                }
            }

            @Override
            protected void onRequestEnd() {
                super.onRequestEnd();
                if(null!=loadingLinear){
                    loadingLinear.setVisibility(View.GONE);
                }
            }
        };

        String methodName = getMethodName(url);
        String[] split = methodName.split(",");
        HashMap<String,Object> map = null;
        map= (HashMap<String, Object>) HttpUtil.getRequest(data);
        IHttpApi iHttpApiT = HttpApiImpl.getInstance().iHttpApiT;

        Method[] declaredMethods = iHttpApiT.getClass().getDeclaredMethods();
        for (Method method:declaredMethods) {
            if(method.getName().equalsIgnoreCase(split[1])){
                try {
                    int parameterCount = method.getParameterTypes().length;
                    Object invoke;
                        invoke =method.invoke(iHttpApiT, pathParameter, map);
                    Observable<Response<ResponseBody>> responseObservable = (Observable<Response<ResponseBody>>) invoke;
                    responseObservable
                            .compose(RxUtil.rxSchedulerHelper())
                            .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) activity)))
                            .subscribe(observer);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
    public static void doPostForm(String httpUrl, Map param,FormRequestResult formRequestResult) {
        new Thread(){
            @Override
            public void run() {
                HttpURLConnection connection = null;
                InputStream is = null;
                OutputStream os = null;
                BufferedReader br = null;
                String result = null;
                try {
                    URL url = new URL(httpUrl);
                    // ????????????url????????????????????????
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("Authorization","Basic bW9iaWxlOm1vYmlsZQ==");
                    connection.setRequestProperty("Accept-Language","zh-CN,zh");
                    // ????????????????????????
                    connection.setRequestMethod("POST");
                    // ??????????????????????????????????????????15000??????
                    connection.setConnectTimeout(15000);
                    // ??????????????????????????????????????????????????????60000??????
                    connection.setReadTimeout(60000);

                    // ???????????????false????????????????????????????????????/??????????????????????????????true
                    connection.setDoOutput(true);
                    // ???????????????true???????????????????????????????????????????????????true????????????????????????
                    connection.setDoInput(true);
                    // ???????????????????????????:????????????????????? name1=value1&name2=value2 ????????????
                    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    // ?????????????????????Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
                    //connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
                    // ???????????????????????????????????????
                    os = connection.getOutputStream();
                    // ???????????????????????????????????????/????????????,?????????????????????????????????(form?????????????????????????????????key,value????????????????????????get?????????????????????)
                    os.write(createLinkString(param).getBytes());
                    // ?????????????????????????????????????????????????????????
                    if (connection.getResponseCode() == 200) {

                        is = connection.getInputStream();
                        // ??????????????????????????????:charset???????????????????????????????????????
                        br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                        StringBuffer sbf = new StringBuffer();
                        String temp = null;
                        // ????????????????????????????????????
                        while ((temp = br.readLine()) != null) {
                            sbf.append(temp);
                            sbf.append("\r\n");
                        }
                        result = sbf.toString();
                        JSONObject jsonObject = JSONObject.parseObject(result);
                        if(jsonObject.getString("code").equals("0")){
                            if(formRequestResult!=null){
                                formRequestResult.onSuccess(result);
                            }
                        }else {
                            if(formRequestResult!=null){
                                String message = jsonObject.getString("msg");
                                Toast toast = Toast.makeText(MyApplication.getInstance(), null, Toast.LENGTH_SHORT);
                                toast.setText(message);
                                toast.show();
                                formRequestResult.onFail(message);
                                ToastUtils.show(message);
                            }
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    // ????????????
                    if (null != br) {
                        try {
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (null != os) {
                        try {
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (null != is) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    // ?????????????????????url?????????
                    connection.disconnect();
                }
            }
        }.start();

    }
    public static void show( String text) {
        Toast toast = null;
        try {
                toast= Toast.makeText(AppContextUtils.getContext(), null, Toast.LENGTH_SHORT);
                toast.setText(text);
            toast.show();
        } catch (Exception e) {
            //???????????????????????????Toast?????????????????????
            Looper.prepare();
            toast = Toast.makeText(AppContextUtils.getContext(), null, LENGTH_SHORT);
            toast.setText(text);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            Looper.loop();
        }
    }


    public static String createLinkString(Map<String, String> params) {

        List<String> keys = new ArrayList<>(params.keySet());
        Collections.sort(keys);

        StringBuilder prestr = new StringBuilder();
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (i == keys.size() - 1) {// ?????????????????????????????????&??????
                prestr.append(key).append("=").append(value);
            } else {
                prestr.append(key).append("=").append(value).append("&");
            }
        }

        return prestr.toString();
    }

    public static interface FormRequestResult{
        void onSuccess(String result);
        void onFail(String failStr);
    }
}
