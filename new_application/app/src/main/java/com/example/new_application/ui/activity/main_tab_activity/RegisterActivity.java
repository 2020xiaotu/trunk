package com.example.new_application.ui.activity.main_tab_activity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.new_application.R;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.LoginEntity;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.utils.CommonToolbarUtil;
import com.example.new_application.utils.MyApplication;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.utils.Utils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.operate_constraint_layout)
    ConstraintLayout operate_constraint_layout;
    @BindView(R.id.operate_top_tv)
    TextView operate_top_tv;
    @BindView(R.id.operate_bottom_tv)
    TextView operate_bottom_tv;

    @BindView(R.id.server_constraint_layout)
    ConstraintLayout server_constraint_layout;
    @BindView(R.id.server_top_tv)
    TextView server_top_tv;
    @BindView(R.id.server_bottom_tv)
    TextView server_bottom_tv;

    @BindView(R.id.switch_password_iv)
    ImageView switch_password_iv;
    @BindView(R.id.register_btn)
    Button register_btn;

    @BindView(R.id.account_etv)
    EditText account_etv;
    @BindView(R.id.password_etv)
    EditText password_etv;
    @BindView(R.id.sure_password_etv)
    EditText sure_password_etv;

    @BindView(R.id.bottom_tip_iv)
    ImageView bottom_tip_iv;
    private boolean mPasswordVisible = false;
    String userType;
    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        CommonToolbarUtil.initToolbar(this,"注册账号");
        operate_constraint_layout.performClick();
    }


    @OnClick({R.id.switch_password_iv,R.id.server_constraint_layout,R.id.operate_constraint_layout,R.id.register_btn})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.register_btn:
                if(  checkEtv() && Utils.isNotFastClick()){
                    requestRegister();
                }

                break;
            case R.id.server_constraint_layout:
                server_bottom_tv.setTextColor(ContextCompat.getColor(MyApplication.getInstance(),R.color.white));
                server_top_tv.setTextColor(ContextCompat.getColor(MyApplication.getInstance(),R.color.white));
                server_constraint_layout.setBackgroundResource(R.drawable.register_btn_selector);
                operate_bottom_tv.setTextColor(ContextCompat.getColor(MyApplication.getInstance(),R.color.default_color));
                operate_top_tv.setTextColor(ContextCompat.getColor(MyApplication.getInstance(),R.color.default_color));
                operate_constraint_layout.setBackgroundResource(R.drawable.white_4_radio_shape);
                bottom_tip_iv.setImageResource(R.drawable.regist_bottom_tip_fws);
                userType = "2";
                break;
            case R.id.operate_constraint_layout:
                operate_bottom_tv.setTextColor(ContextCompat.getColor(MyApplication.getInstance(),R.color.white));
                operate_top_tv.setTextColor(ContextCompat.getColor(MyApplication.getInstance(),R.color.white));
                operate_constraint_layout.setBackgroundResource(R.drawable.register_btn_selector);
                server_bottom_tv.setTextColor(ContextCompat.getColor(MyApplication.getInstance(),R.color.default_color));
                server_top_tv.setTextColor(ContextCompat.getColor(MyApplication.getInstance(),R.color.default_color));
                server_constraint_layout.setBackgroundResource(R.drawable.white_4_radio_shape);
                bottom_tip_iv.setImageResource(R.drawable.regist_bottom_tip_gz);
                userType = "1";
                break;
            case R.id.switch_password_iv:
                /**
                 * 点击更换密码可见状态
                 */
                if(mPasswordVisible){
                    switch_password_iv.setImageResource(R.drawable.eye_open);
                    sure_password_etv.setTransformationMethod(PasswordTransformationMethod.getInstance());//密码隐藏
                    sure_password_etv.setSelection(sure_password_etv.getText().length());//光标移到最后
                }else {
                    switch_password_iv.setImageResource(R.drawable.eye_close);
                    sure_password_etv.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//密码显示
                    sure_password_etv.setSelection(sure_password_etv.getText().length());//光标移到最后
                }
                mPasswordVisible= !mPasswordVisible;
                break;
            default:
                break;
        }
    }

    /**
     * 注册
     */
    private void requestRegister() {
        HashMap<String, Object> data = new HashMap<>();
        String password = password_etv.getText().toString();
        String account = account_etv.getText().toString();
        data.put("password", password);
        data.put("username", account);
        data.put("userType",userType);
        HttpApiUtils.wwwRequest(RegisterActivity.this, null, RequestUtils.REGISTER, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                Utils. LoginSuccess(RegisterActivity.this,result,"注册成功");

            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    private boolean checkEtv() {
        String account = account_etv.getText().toString();
        String password = password_etv.getText().toString();
        String surePassword = sure_password_etv.getText().toString();
        if(StringMyUtil.isEmptyString(account)){
            showtoast("账号不能为空");
            return false;
        }
        if(StringMyUtil.isEmptyString(password)){
            showtoast("密码不能为空");
            return false;
        }
        if(password.length() <8||password.length()>20){
            showtoast("密码应为8-20位");
            return false;
        }
        if(surePassword.length()<8||surePassword.length()>20){
            showtoast("密码应为8-20位");
            return false;
        }
        if(!password.equals(surePassword)){
            showtoast("两次密码输入不一致");
            return false;
        }
        return true;
    }

}