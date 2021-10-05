package com.example.red_pakege.activity.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.RouteUtil;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created  by ganzhe on 2019/11/19.
 */
public class LoginAndRegisterActivity extends BaseActivity {

    @BindView(R.id.iv_commonbar_back)
    ImageView ivCommonbarBack;
    @BindView(R.id.tv_commonbar_title)
    TextView tvCommonbarTitle;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.btn_go_login)
    Button btnGoLogin;
    @BindView(R.id.btn_go_register)
    Button btnGoRegister;


    SharedPreferenceHelperImpl mSharedPreferenceHelperImpl;

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }


    @Override
    protected void initView() {
        ActionBarUtil.initMainActionbar(this, toolbarCommon);
        tvCommonbarTitle.setText("注册登录");
        mSharedPreferenceHelperImpl = new SharedPreferenceHelperImpl();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_loginandregister;
    }

    @Override
    protected void initEventAndData() {
        ivCommonbarBack.setOnClickListener(v ->onBackPressedSupport());
        btnGoLogin.setOnClickListener(v -> RouteUtil.start2LoginActivity(this));
        btnGoRegister.setOnClickListener(v -> RouteUtil.start2RegisterActivity(this));
    }

    @Override
    public void onBackPressedSupport() {
        if (!mSharedPreferenceHelperImpl.getLoginStatus()) {
            QuickExitBkg();
        }
    }
}
