package com.example.red_pakege.activity.login;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;

import com.cambodia.zhanbang.rxhttp.net.common.BaseEntityObserver;
import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;
import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.net.api.HttpApiImpl;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.StringMyUtil;
import com.example.red_pakege.util.ToastUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * created  by ganzhe on 2019/11/19.
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.iv_commonbar_back)
    ImageView ivCommonbarBack;
    @BindView(R.id.tv_commonbar_title)
    TextView tvCommonbarTitle;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.iv_register_contract)
    ImageView ivRegisterContract;
    @BindView(R.id.btn_getcode)
    Button btnGetCode;
    @BindView(R.id.et_register_phone)
    EditText mPhone;
    @BindView(R.id.et_register_pwd)
    EditText mPwd;
    @BindView(R.id.et_register_pwdconfirm)
    EditText mPwdconfirm;
    @BindView(R.id.register_msgcode)
    EditText mMsgCode;

    @BindView(R.id.et_register_invitecode)
    EditText et_register_invitecode;

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }


    @Override
    protected void initToolbar() {
        ActionBarUtil.initMainActionbar(this, toolbarCommon);
        tvCommonbarTitle.setText("注册");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initEventAndData() {
        ivCommonbarBack.setOnClickListener(v -> onBackPressedSupport());
        btnGetCode.setOnClickListener(v -> {
            mMsgCode.setText("888888");
        });

        btnRegister.setOnClickListener(v -> {

            if (!checkEdit()) {
                return;
            }

            Map map = new HashMap();
            map.put("phone", mPhone.getText().toString());
            map.put("smsCode", mMsgCode.getText().toString());
            map.put("password", mPwd.getText().toString());
            map.put("inviteCode", et_register_invitecode.getText().toString());
            map.put("sign", "123456789");
            map.put("source", "0");

            HttpApiImpl.getInstance()
                    .PostRegister(map)
                    .compose(RxTransformerUtils.io_main())
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner)this)))
                    .subscribe(new BaseEntityObserver<BaseEntity>() {
                        @Override
                        public void onSuccess(BaseEntity baseEntity) {
                            LogUtils.e(baseEntity.toString());
                            /**
                             * 注册成功 跳到LoginActivity
                             */
                            closeLoading();
                            ToastUtils.showToast("请登录");
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }

                        @Override
                        public void onFail(String msg) {
                            if(StringMyUtil.isNotEmpty(msg)){
                                LogUtils.e(msg);
                                ToastUtils.showToast(msg);
                            }
                        }

                        @Override
                        protected void onRequestStart() {
                            showLoading();
                        }

                        @Override
                        protected void onRequestEnd() {
                            closeLoading();
                        }
                    });

        });

    }


    private boolean checkEdit() {

        if (TextUtils.isEmpty(mPhone.getText().toString())) {
            ToastUtils.showToast("手机号不能为空");
            return false;
        }
        if (TextUtils.isEmpty(mPwd.getText().toString())) {
            ToastUtils.showToast("密码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(mMsgCode.getText().toString())) {
            ToastUtils.showToast("短信验证码不能为空");
            return false;
        }

        if (!mPwd.getText().toString().equals(mPwdconfirm.getText().toString())) {
            ToastUtils.showToast("两次密码输入不同");
            return false;
        }
        if (TextUtils.isEmpty(et_register_invitecode.getText().toString())){
            ToastUtils.showToast("请输入邀请码");
            return false;
        }
        return true;
    }


}
