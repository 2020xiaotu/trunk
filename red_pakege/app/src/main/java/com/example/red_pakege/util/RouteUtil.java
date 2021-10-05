package com.example.red_pakege.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.red_pakege.activity.HbMainActivity;
import com.example.red_pakege.activity.common_activity.WebActivity;
import com.example.red_pakege.activity.login.ForgetPwdActivity;
import com.example.red_pakege.activity.login.LoginActivity;
import com.example.red_pakege.activity.login.LoginAndRegisterActivity;
import com.example.red_pakege.activity.login.RegisterActivity;
import com.example.red_pakege.activity.mine.RecordActivity;

import static com.example.red_pakege.config.Constants.POSITION;
import static com.example.red_pakege.config.Constants.ROUTE_URL;
import static com.example.red_pakege.config.Constants.WEB_URL;

/**
 * created  by ganzhe on 2019/11/20.
 */
public class RouteUtil {
/*
    public static void start2BannerWebViewActivity(Context context,String url){
        Intent intent = new Intent();
        intent.setClass(context, BannerWebviewActivity.class);
        intent.putExtra(ROUTE_URL,url);
        context.startActivity(intent);
    }*/
    public static void start2LoginAndRegisActivity(Context context){
        Intent intent = new Intent();
        intent.setClass(context, LoginAndRegisterActivity.class);
        context.startActivity(intent);
    }
    public static void start2LoginActivity(Context context){
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        context.startActivity(intent);
    }
    public static void start2RegisterActivity(Context context){
        Intent intent = new Intent();
        intent.setClass(context, RegisterActivity.class);
        context.startActivity(intent);
    }
    public static void start2MainActivity(Context context){
        Intent intent = new Intent();
        intent.setClass(context, HbMainActivity.class);
        context.startActivity(intent);
    }
    public static void start2ForgetPwdActivity(Context context){
        Intent intent = new Intent();
        intent.setClass(context, ForgetPwdActivity.class);
        context.startActivity(intent);
    }

    public static void start2WebActivity(Context context,String web_url){
        Intent intent = new Intent();
        intent.putExtra(WEB_URL,web_url);
        intent.setClass(context, WebActivity.class);
        context.startActivity(intent);
    }

    public static void start2RecordActivity(Context context, RecordActivity.TYPE type){
        Intent intent = new Intent();
        intent.setClass(context,RecordActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(POSITION,type);
        intent.putExtras(bundle);    //将bundle传入intent中
        context.startActivity(intent);
    }

}
