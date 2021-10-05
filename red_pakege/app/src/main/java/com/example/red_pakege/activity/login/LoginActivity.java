package com.example.red_pakege.activity.login;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.mvp.contract.LoginContract;
import com.example.red_pakege.mvp.presenter.LoginPresenter;
import com.example.red_pakege.net.entity.ParamLogin;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.ActivityUtil;
import com.example.red_pakege.util.JsonUtil;
import com.example.red_pakege.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;

import static com.example.red_pakege.util.RouteUtil.start2ForgetPwdActivity;
import static com.example.red_pakege.util.RouteUtil.start2MainActivity;

/**
 * created  by ganzhe on 2019/11/19.
 */
public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.ILoginView {

    SharedPreferenceHelperImpl mSharedPreferenceHelperImpl = new SharedPreferenceHelperImpl();
    @BindView(R.id.iv_commonbar_back)
    ImageView ivCommonbarBack;
    @BindView(R.id.tv_commonbar_title)
    TextView tvCommonbarTitle;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.et_login_phone)
    EditText etLoginPhone;
    @BindView(R.id.et_login_passwd)
    EditText etLoginPasswd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_forgot_pwd)
    TextView tvForgotPwd;
    @BindView(R.id.iv_login_contract)
    ImageView ivLoginContract;


    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void initActionBar() {
        ActionBarUtil.initMainActionbar(this, toolbarCommon);
        tvCommonbarTitle.setText("登录");
        //   UserEntity userEntity = JsonUtil.getUserEntity(mSharedPreferenceHelperImpl.getUserInfo());

        etLoginPhone.setText(mSharedPreferenceHelperImpl.getPhoneNum());


    }

    @Override
    protected void initView() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initEventAndData() {
        ivCommonbarBack.setOnClickListener(v -> onBackPressedSupport());
        tvForgotPwd.setOnClickListener(v -> start2ForgetPwdActivity(this));
        btnLogin.setOnClickListener(v -> {

            if (!checkEdit()) {
                return;
            }

            mPresenter.RequestLogin(etLoginPhone.getText().toString(), etLoginPasswd.getText().toString());
        });
    }

    @Override
    public void LoginSuccess(UserEntity entity) {
    //    ToastUtils.showToast("登录成功");
        /**
         *   保存信息  》 页面跳转
         */

        mSharedPreferenceHelperImpl.setLoginStatus(true);
        mSharedPreferenceHelperImpl.setToken(entity.getData().getToken());
        mSharedPreferenceHelperImpl.setPhoneNum(etLoginPhone.getText().toString());
        Gson gson = new GsonBuilder().serializeNulls().create();
        String strEntity = gson.toJson(entity);
        mSharedPreferenceHelperImpl.setUserInfo(strEntity);
        int user_id = entity.getData().getMemberInfo().getId();
        mSharedPreferenceHelperImpl.setUserId(user_id);
        start2MainActivity(this);
    }

    private boolean checkEdit() {
        if (TextUtils.isEmpty(etLoginPhone.getText().toString())) {
            ToastUtils.showToast("请输入手机号");
            return false;
        }
        if (TextUtils.isEmpty(etLoginPasswd.getText().toString())) {
            ToastUtils.showToast("请输入密码");
            return false;
        }
        return true;
    }


    @Override
    public void onBackPressedSupport() {
        if (ActivityUtil.getInstance().getActivitySize()>2){
            super.onBackPressedSupport();
        }else {
            QuickExitBkg();
        }
    }
}
