package com.example.red_pakege.activity.splash_activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import me.yokeyword.fragmentation.SupportActivity;

import android.content.Intent;
import android.os.Bundle;

import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.red_pakege.activity.HbMainActivity;
import com.example.red_pakege.R;
import com.example.red_pakege.activity.login.LoginActivity;
import com.example.red_pakege.activity.login.LoginAndRegisterActivity;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.util.JsonUtil;

import java.util.Timer;
import java.util.TimerTask;

public class HbSplashActivity extends BaseActivity {

    SharedPreferenceHelperImpl mSharedPreferenceHelperImpl = new SharedPreferenceHelperImpl();

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initEventAndData() {
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                startActivity(new Intent(HbSplashActivity.this, HbMainActivity.class));
//            }
//        },1000);
        UserEntity userEntity = JsonUtil.Str2UserEntity(mSharedPreferenceHelperImpl.getUserInfo());
        if (mSharedPreferenceHelperImpl.getLoginStatus()&&null!=userEntity) {
            startActivity(new Intent(HbSplashActivity.this, HbMainActivity.class));
        } else if (!mSharedPreferenceHelperImpl.getLoginStatus()&&null==userEntity){
            startActivity(new Intent(this, LoginAndRegisterActivity.class));
        }else {
            startActivity(new Intent(this, LoginActivity.class));
        }

    }

}
