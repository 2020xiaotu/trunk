package com.example.new_application.ui.fragment.message_fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.cambodia.zhanbang.rxhttp.net.common.BaseStringObserver;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.example.new_application.R;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.base.BaseWebActivity;
import com.uber.autodispose.AutoDispose;

import okhttp3.ResponseBody;

public class MessageDetailsActivity extends BaseWebActivity {


    @Override
    public String getToolBarTitle() {
        return  getIntent().getStringExtra("title");
    }

    @Override
    public String getUrl() {
        return  getIntent().getStringExtra("url");
    }

    @Override
    public void doSomething() {
        super.doSomething();
        String id = getIntent().getStringExtra("id");
        if(getIntent().getBooleanExtra("isSystemMessage",true)){

        }else {
            /*
            私信消息跳转而来
             */

        }

    }

    /**
     *
     * @param fragment
     * @param url 消息内容
     * @param title toolBat标题
     * @param id 消息id
     * @param requestCode 跳转请求码
     */
    public static void startAty(Fragment fragment, String url, String title, String id,int requestCode){
        Intent intent = new Intent(fragment.getContext(), MessageDetailsActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("title",title);
        intent.putExtra("id",id);
        fragment.startActivityForResult(intent, requestCode);

    }
}