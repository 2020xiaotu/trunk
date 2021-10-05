package com.example.red_pakege.activity.mine;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.red_pakege.BuildConfig;
import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.mvp.contract.SettingContract;
import com.example.red_pakege.mvp.presenter.SettingPresenter;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.ActivityUtil;
import com.example.red_pakege.util.ToastUtils;
import com.rey.material.widget.CheckBox;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity<SettingPresenter> implements SettingContract.ISettingView {


    @BindView(R.id.iv_commonbar_back)
    ImageView ivCommonbarBack;
    @BindView(R.id.tv_commonbar_title)
    TextView tvCommonbarTitle;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.rl_setting_msg)
    RelativeLayout rlSettingMsg;
    @BindView(R.id.rl_setting_vol)
    RelativeLayout rlSettingVol;
    @BindView(R.id.iv_setting_go3)
    ImageView ivSettingGo3;
    @BindView(R.id.rl_setting_version)
    RelativeLayout rlSettingVersion;
    @BindView(R.id.rl_setting_cache)
    RelativeLayout rlSettingCache;
    @BindView(R.id.checkbox_setting_msg)
    CheckBox CheboxMsg;
    @BindView(R.id.checkbox_setting_vol)
    CheckBox CheboxVol;
    @BindView(R.id.btn_setting_safequit)
    Button btn_setting_safequit;
    @BindView(R.id.tv_setting_version)
    TextView tv_setting_version;



    SharedPreferenceHelperImpl mSharedPreferenceHelperImpl;

    @Override
    protected SettingPresenter createPresenter() {
        return new SettingPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initToolbar() {
        ActionBarUtil.initMainActionbar(this, toolbarCommon);
        tvCommonbarTitle.setText("系统设置");
        mSharedPreferenceHelperImpl = new SharedPreferenceHelperImpl();
        CheboxMsg.setChecked(mSharedPreferenceHelperImpl.getMsgSwitch());
        CheboxVol.setChecked(mSharedPreferenceHelperImpl.getVoiceSwitch());
        tv_setting_version.setText(BuildConfig.DEBUG == true ? "测试版" + BuildConfig.VERSION_NAME : BuildConfig.VERSION_NAME);
    }

    @Override
    protected void initEventAndData() {

        ivCommonbarBack.setOnClickListener(v -> onBackPressedSupport());
        CheboxMsg.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            LogUtils.e("CheboxMsg:" + isChecked);
            mSharedPreferenceHelperImpl.setMsgSwitch(isChecked);
        });
        CheboxVol.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            LogUtils.e("CheboxVol:" + isChecked);
            mSharedPreferenceHelperImpl.setVoiceSwitch(isChecked);
        });
        rlSettingMsg.setOnClickListener(v -> {
            if (mSharedPreferenceHelperImpl.getMsgSwitch()){
                CheboxMsg.setChecked(false);
            }else {
                CheboxMsg.setChecked(true);
            }
        });
        rlSettingVol.setOnClickListener(v -> {
            if (mSharedPreferenceHelperImpl.getVoiceSwitch()){
                CheboxVol.setChecked(false);
            }else {
                CheboxVol.setChecked(true);
            }
        });
        btn_setting_safequit.setOnClickListener(v -> {
            mSharedPreferenceHelperImpl.setLoginStatus(false);
            LogUtils.e("loginstatus:"+mSharedPreferenceHelperImpl.getLoginStatus());
            QuickExitBkg();
        });

    }

    @Override
    public void showSafeQuit() {

    }

    @Override
    public void showgetVersion() {

    }





}
