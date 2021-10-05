package com.example.red_pakege.net.api;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;

import com.cambodia.zhanbang.rxhttp.net.common.BaseStringObserver;
import com.cambodia.zhanbang.rxhttp.net.common.RetrofitFactory;
import com.cambodia.zhanbang.rxhttp.net.utils.RxUtil;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.config.Constants;
import com.example.red_pakege.util.ErrorUtil;
import com.example.red_pakege.util.HttpUtil;
import com.example.red_pakege.util.ToastUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import io.reactivex.Observable;
import okhttp3.Headers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class HttpApiUtils {

    /**
     * (显示加载中,没有errorLayout)
     * @param context 上下文
     * @param url 接口后缀,用于跟httpApi中的注解url做对比,决定调用哪个方法
     * @param data 数据源
     * @param onRequestLintener  请求结果回调
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static void request(Context context,String url, HashMap<String, Object> data,  OnRequestLintener onRequestLintener) {
        BaseStringObserver<ResponseBody> observer = new BaseStringObserver<ResponseBody>() {
            @Override
            public void onSuccess(String result, Headers headers) {

                try {
                    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
                    long oldTime = sp.getShiJianCha();
                    System.out.println("shijiancha oldTime  ===== "+oldTime );
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
                    Date  date = dateFormat.parse(headers.get("Date"));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
                    date = calendar.getTime();
                    long time = date.getTime();
                    long nowTime = System.currentTimeMillis() - time;
                    if (oldTime == 0) {//第一次存入(后面有时间差为0的一并忽略,存新值)
                        sp.setShiJianCha(nowTime);
                    } else if (Math.abs(oldTime) > Math.abs(nowTime)) {
                        sp.setShiJianCha(nowTime);
                    } else {
                        sp.setShiJianCha(oldTime);
                    }
                    System.out.println("shijiancha nowTime ===== "+sp.getShiJianCha() );
                } catch (ParseException e) {
                    e.printStackTrace();
                }




                JSONObject jsonObject = JSONObject.parseObject(result);
                if(jsonObject.getString("status").equals("200")){
                    if(onRequestLintener!=null){
                        onRequestLintener.onSuccess(result,headers);
                    }
                }else {
                    if(onRequestLintener!=null){
                        String message = jsonObject.getString("message");
                        ToastUtils.showToast(message);
                        onRequestLintener.onFaild(message);
                    }
                }
            }

            @Override
            public void onFail(String msg) {
                if(onRequestLintener!=null){
                    ToastUtils.showToast(msg);
                    onRequestLintener.onFaild(msg);
                }
            }

            @Override
            protected void onRequestStart() {
                super.onRequestStart();
                if((Activity)context instanceof BaseActivity){
                    ((BaseActivity) context).showLoading();
                }
            }

            @Override
            protected void onRequestEnd() {
                super.onRequestEnd();
                if((Activity)context instanceof BaseActivity){
                    ((BaseActivity) context).closeLoading();
                }
            }
        };
        String methodName = getMethodName(url);
        String[] split = methodName.split(",");
        RequestBody requestBody =null;
        HashMap<String ,Object>map =null;
        String getOrPost = split[0];
        SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
        if(getOrPost.equalsIgnoreCase("get")){
            map= (HashMap<String, Object>) HttpUtil.getRequest(data);
            if(!map.containsKey("userId")){
                map.put("userId",sp.getUserId());
            }
            if(!map.containsKey("sign")){
                map.put("sign","123456789");
            }
            if(!map.containsKey("source")){
                map.put("source",0);
            }
            if(!map.containsKey("token")){
                map.put("token",sp.getToken());
            }
            if(!map.containsKey("user_id")){
                map.put("user_id",sp.getUserId());
            }
        }else {
            requestBody=HttpUtil.postRequest(data);
        }
        IHttpApi iHttpApiT = HttpApiImpl.getInstance().iHttpApiT;
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
                    responseObservable
                            .compose(RxUtil.rxSchedulerHelper())
//                            .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) context)))
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

    /**
     *  普通请求 只请求数据  没有ui操作
     * @param context
     * @param url
     * @param data
     * @param onRequestLintener
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static void normalRequest(Context context,String url, HashMap<String, Object> data,  OnRequestLintener onRequestLintener) {
        BaseStringObserver<ResponseBody> observer = new BaseStringObserver<ResponseBody>() {
            @Override
            public void onSuccess(String result, Headers headers) {
                try {
                    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
                    long oldTime = sp.getShiJianCha();
                    System.out.println("shijiancha oldTime  ===== "+oldTime );
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
                    Date  date = dateFormat.parse(headers.get("Date"));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
                    date = calendar.getTime();
                    long time = date.getTime();
                    long nowTime = System.currentTimeMillis() - time;
                    if (oldTime == 0) {//第一次存入(后面有时间差为0的一并忽略,存新值)
                        sp.setShiJianCha(nowTime);
                    } else if (Math.abs(oldTime) > Math.abs(nowTime)) {
                        sp.setShiJianCha(nowTime);
                    } else {
                        sp.setShiJianCha(oldTime);
                    }
                    System.out.println("shijiancha nowTime ===== "+sp.getShiJianCha() );
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                JSONObject jsonObject = JSONObject.parseObject(result);
                if(jsonObject.getString("status").equals("200")){
                    if(onRequestLintener!=null){
                        onRequestLintener.onSuccess(result,headers);
                    }
                }else {
                    if(onRequestLintener!=null){
                        String message = jsonObject.getString("message");
                        ToastUtils.showToast(message);
                        onRequestLintener.onFaild(message);
                    }
                }
            }

            @Override
            public void onFail(String msg) {
                if(onRequestLintener!=null){
                   ToastUtils.showToast(msg);
                   onRequestLintener.onFaild(msg);
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
        RequestBody requestBody =null;
        HashMap<String ,Object>map =null;
        String getOrPost = split[0];
        SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
        if(getOrPost.equalsIgnoreCase("get")){
            map= (HashMap<String, Object>) HttpUtil.getRequest(data);
            if(!map.containsKey("userId")){
                map.put("userId",sp.getUserId());
            }
            if(!map.containsKey("sign")){
                map.put("sign","123456789");
            }
            if(!map.containsKey("source")){
                map.put("source",0);
            }
            if(!map.containsKey("token")){
                map.put("token",sp.getToken());
            }
            if(!map.containsKey("user_id")){
                map.put("user_id",sp.getUserId());
            }
        }else {
            requestBody=HttpUtil.postRequest(data);
        }
        IHttpApi iHttpApiT = HttpApiImpl.getInstance().iHttpApiT;
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
                    responseObservable
                            .compose(RxUtil.rxSchedulerHelper())
//                            .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) context)))
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

    /**
     *  包含加载中, 上拉刷新 下拉加载 失败视图  空视图的请求
     * @param context  上下文
     * @param fragment 当前所在的fragment  (如果在activity中请求,传null)
     * @param url 接口路径
     * @param data 数据源
     * @param loadingLinear  加载中的视图
     * @param errorLinear 加载失败的视图
     * @param reloadTv   点击刷新的按钮(具体看错误页面的布局,如果没有刷新按钮可以不传)
     * @param view  加载中 加载失败  空视图需要隐藏的view (主要是rececleView或者refreshLayout)
     * @param isLoadmore  是否是加载更多时调用
     * @param isrefresh  是否是下拉刷新时调用
     * @param onRequestLintener  请求结果回调
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static void showLoadRequest(Context context, Fragment fragment, String url, HashMap<String, Object> data, ConstraintLayout loadingLinear, LinearLayout errorLinear, TextView reloadTv, View view, boolean isLoadmore, boolean isrefresh, OnRequestLintener onRequestLintener) {
            BaseStringObserver<ResponseBody> observer = new BaseStringObserver<ResponseBody>() {
            @Override
            public void onSuccess(String result, Headers headers) {
                try {
                    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
                    long oldTime = sp.getShiJianCha();
                    System.out.println("shijiancha oldTime  ===== "+oldTime );
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
                    Date  date = dateFormat.parse(headers.get("Date"));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
                    date = calendar.getTime();
                    long time = date.getTime();
                    long nowTime = System.currentTimeMillis() - time;
                    if (oldTime == 0) {//第一次存入(后面有时间差为0的一并忽略,存新值)
                        sp.setShiJianCha(nowTime);
                    } else if (Math.abs(oldTime) > Math.abs(nowTime)) {
                        sp.setShiJianCha(nowTime);
                    } else {
                        sp.setShiJianCha(oldTime);
                    }
                    System.out.println("shijiancha nowTime ===== "+sp.getShiJianCha() );
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                JSONObject jsonObject = JSONObject.parseObject(result);
                if(jsonObject.getString("status").equals("200")){
                    if(onRequestLintener!=null){
                        onRequestLintener.onSuccess(result,headers);
                    }
                }else {
                    if(onRequestLintener!=null){
                        String message = jsonObject.getString("message");
                        ToastUtils.showToast(message);
                        onRequestLintener.onFaild(message);
                    }
                }
            }
            @Override
            public void onFail(String msg) {
                ToastUtils.showToast(msg);
                if(onRequestLintener!=null){
                    onRequestLintener.onFaild(msg);
                }
                if(errorLinear!=null){
                    if(fragment!=null){
                        ErrorUtil.showErrorLayout(fragment,view,errorLinear,reloadTv);
                    }else {
                        ErrorUtil.showErrorLayout((Activity)context,view,errorLinear,reloadTv);
                    }
                }
            }
            @Override
            protected void onRequestStart() {
                super.onRequestStart();
                if(!isLoadmore&&!isrefresh){
                    if(loadingLinear!=null){
                        loadingLinear.setVisibility(View.VISIBLE);
                    }
                    if(errorLinear!=null){
                        if(errorLinear!=null){
                            ErrorUtil.hideErrorLayout(view,errorLinear);
                        }
                    }
                }
            }

            @Override
            protected void onRequestEnd() {
                super.onRequestEnd();
                if(loadingLinear!=null){
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
            if(!map.containsKey("userId")){
                map.put("userId",sp.getUserId());
            }
            if(!map.containsKey("sign")){
                map.put("sign","123456789");
            }
            if(!map.containsKey("source")){
                map.put("source",0);
            }
            if(!map.containsKey("token")){
                map.put("token",sp.getToken());
            }
            if(!map.containsKey("user_id")){
                map.put("user_id",sp.getUserId());
            }
        }else {
            requestBody= HttpUtil.postRequest(data);
        }

        IHttpApi  iHttpApiT = RetrofitFactory.getInstance().create(Constants.BASE_URL,IHttpApi.class);

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
                    responseObservable
                            .compose(RxUtil.rxSchedulerHelper())
                            .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) context)))
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

    /**
     *  有头部的列表 (包含加载中, 上拉刷新 下拉加载 失败视图  空视图的请求,将各种状态的视图添加到尾部,这样视图出现的时候不会将头部也一起覆盖)
     * @param context  上下文
     * @param fragment 当前所在的fragment  (如果在activity中请求,传null)
     * @param url 接口路径
     * @param data 数据源
     * @param loadingLinear  加载中的视图
     * @param errorLinear 加载失败的视图
     * @param reloadTv   点击刷新的按钮(具体看错误页面的布局,如果没有刷新按钮可以不传)
     * @param view  加载中 加载失败  空视图需要隐藏的view (主要是rececleView或者refreshLayout)
     * @param isLoadmore  是否是加载更多时调用
     * @param isrefresh  是否是下拉刷新时调用
     * @param onRequestLintener  请求结果回调
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static void footShowLoadRequest(Context context, Fragment fragment, String url, HashMap<String, Object> data, ConstraintLayout loadingLinear, LinearLayout errorLinear, TextView reloadTv, View view, boolean isLoadmore, boolean isrefresh, OnRequestLintener onRequestLintener) {
        BaseStringObserver<ResponseBody> observer = new BaseStringObserver<ResponseBody>() {
            @Override
            public void onSuccess(String result, Headers headers) {
                try {
                    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
                    long oldTime = sp.getShiJianCha();
                    System.out.println("shijiancha oldTime  ===== "+oldTime );
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
                    Date  date = dateFormat.parse(headers.get("Date"));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
                    date = calendar.getTime();
                    long time = date.getTime();
                    long nowTime = System.currentTimeMillis() - time;
                    if (oldTime == 0) {//第一次存入(后面有时间差为0的一并忽略,存新值)
                        sp.setShiJianCha(nowTime);
                    } else if (Math.abs(oldTime) > Math.abs(nowTime)) {
                        sp.setShiJianCha(nowTime);
                    } else {
                        sp.setShiJianCha(oldTime);
                    }
                    System.out.println("shijiancha nowTime ===== "+sp.getShiJianCha() );
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                JSONObject jsonObject = JSONObject.parseObject(result);
                if(jsonObject.getString("status").equals("200")){
                    if(onRequestLintener!=null){
                        onRequestLintener.onSuccess(result,headers);
                    }
                }else {
                    if(onRequestLintener!=null){
                        String message = jsonObject.getString("message");
                        ToastUtils.showToast(message);
                        onRequestLintener.onFaild(message);
                    }
                }
            }
            @Override
            public void onFail(String msg) {
                ToastUtils.showToast(msg);
                if(onRequestLintener!=null){
                    onRequestLintener.onFaild(msg);
                }
                if(errorLinear!=null){
                    if(fragment!=null){
                        ErrorUtil.showErrorLayout(fragment,view,errorLinear,reloadTv);
                    }else {
                        ErrorUtil.showErrorLayout((Activity)context,view,errorLinear,reloadTv);
                    }
                }
            }
            @Override
            protected void onRequestStart() {
                super.onRequestStart();
                if(!isLoadmore&&!isrefresh){
                    if(loadingLinear!=null){
                        loadingLinear.setVisibility(View.VISIBLE);
                    }
                    if(errorLinear!=null){
                        if(errorLinear!=null){
                            ErrorUtil.hideErrorLayout(view,errorLinear);
                        }
                    }
                }
            }

            @Override
            protected void onRequestEnd() {
                super.onRequestEnd();
                if(loadingLinear!=null){
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
            if(!map.containsKey("userId")){
                map.put("userId",sp.getUserId());
            }
            if(!map.containsKey("sign")){
                map.put("sign","123456789");
            }
            if(!map.containsKey("source")){
                map.put("source",0);
            }
            if(!map.containsKey("token")){
                map.put("token",sp.getToken());
            }
            if(!map.containsKey("user_id")){
                map.put("user_id",sp.getUserId());
            }
        }else {
            requestBody= HttpUtil.postRequest(data);
        }

        IHttpApi  iHttpApiT = RetrofitFactory.getInstance().create(Constants.BASE_URL,IHttpApi.class);

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
                    responseObservable
                            .compose(RxUtil.rxSchedulerHelper())
                            .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) context)))
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


    /**
     * 上传文件(图片)
     * @param context  上下文
     * @param url  接口路径
     * @param filePath 图片路径
     * @param onRequestLintener  请求结果回调
     */
    @TargetApi(Build.VERSION_CODES.O)
    public static void upload(Context context,String url, String filePath,  OnRequestLintener onRequestLintener) {
        BaseStringObserver<ResponseBody> observer = new BaseStringObserver<ResponseBody>() {
            @Override
            public void onSuccess(String result, Headers headers) {
                try {
                    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
                    long oldTime = sp.getShiJianCha();
                    System.out.println("shijiancha oldTime  ===== "+oldTime );
                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
                    Date  date = dateFormat.parse(headers.get("Date"));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(date);
                    calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR) + 8);
                    date = calendar.getTime();
                    long time = date.getTime();
                    long nowTime = System.currentTimeMillis() - time;
                    if (oldTime == 0) {//第一次存入(后面有时间差为0的一并忽略,存新值)
                        sp.setShiJianCha(nowTime);
                    } else if (Math.abs(oldTime) > Math.abs(nowTime)) {
                        sp.setShiJianCha(nowTime);
                    } else {
                        sp.setShiJianCha(oldTime);
                    }
                    System.out.println("shijiancha nowTime ===== "+sp.getShiJianCha() );
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObject = JSONObject.parseObject(result);
                if(jsonObject.getString("status").equals("200")){
                    if(onRequestLintener!=null){
                        onRequestLintener.onSuccess(result,headers);
                    }
                }else {
                    if(onRequestLintener!=null){
                        String message = jsonObject.getString("message");
                        ToastUtils.showToast(message);
                        onRequestLintener.onFaild(message);
                    }
                }
            }
            @Override
            public void onFail(String msg) {
                if(onRequestLintener!=null){
                    ToastUtils.showToast(msg);
                    onRequestLintener.onFaild(msg);
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
        String methodName = getUploadMethodName(url);
        String[] split = methodName.split(",");
        RequestBody requestBody =HttpUtil.uploadRequest(filePath);
        IHttpApi iHttpApiT = HttpApiImpl.getInstance().iHttpApiT;
        Method[] declaredMethods = iHttpApiT.getClass().getDeclaredMethods();
        for (Method method:declaredMethods) {
            if(method.getName().equalsIgnoreCase(split[1])){
                try {
//                    int parameterCount = method.getParameterCount();
                    int parameterCount = method.getParameterTypes().length;
                    Object invoke;
                        invoke =parameterCount==0?method.invoke(iHttpApiT): method.invoke(iHttpApiT,  requestBody);
                    Observable<Response<ResponseBody>> responseObservable = (Observable<Response<ResponseBody>>) invoke;
                    responseObservable
                            .compose(RxUtil.rxSchedulerHelper())
//                            .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) context)))
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

    /**
     * 筛选IHttpApi中上传图片的方法
     * @param url  接口路径
     * @return
     */
    private static String getUploadMethodName(String url) {
        Method[] declaredMethods1 = IHttpApi.class.getDeclaredMethods();
        for (Method method:declaredMethods1) {
            GET getAnnotation = method.getAnnotation(GET.class);
            POST postAnnotation = method.getAnnotation(POST.class);
            if(getAnnotation==null && postAnnotation==null){
                continue;
            }
            String value =getAnnotation!=null? getAnnotation.value():postAnnotation.value();
            if(url.equals(value)){
                if(method.getName().equals("uploadImg")||method.getName().equals("modifyTitle")){
                    return (getAnnotation!=null?"get":"post")+","+method.getName();
                }
            }
        }
        return null;
    }

    /**
     * 通过url筛选IHttpApi中对应的方法
     * @param url  接口路径(url必须和IHttpApi注释中的路径一致)
     * @return
     */
    private static String getMethodName(String url) {
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
    public  static interface OnRequestLintener{
        void onSuccess(String result, Headers headers);
        void onFaild(String msg);
    }
}
