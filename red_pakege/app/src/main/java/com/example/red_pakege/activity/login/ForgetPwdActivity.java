package com.example.red_pakege.activity.login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.util.ActionBarUtil;

import androidx.appcompat.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * created  by ganzhe on 2019/11/19.
 */
public class ForgetPwdActivity extends BaseActivity {


    @BindView(R.id.iv_commonbar_back)
    ImageView ivCommonbarBack;
    @BindView(R.id.tv_commonbar_title)
    TextView tvCommonbarTitle;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.et_forgetpwd_pwd)
    EditText etForgetpwdPwd;
    @BindView(R.id.btn_forgetpwd)
    Button btnForgetpwd;
    @BindView(R.id.btn_getcode)
    Button btnGetCode;

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initActionBar() {
        ActionBarUtil.initMainActionbar(this, toolbarCommon);
        tvCommonbarTitle.setText("修改密码");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_forgetpwd;
    }

    @Override
    protected void initEventAndData() {
        ivCommonbarBack.setOnClickListener(v -> onBackPressedSupport());
        btnForgetpwd.setOnClickListener(v -> {

        });

        btnGetCode.setOnClickListener(v -> {

        });
    }


}
