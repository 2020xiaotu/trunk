package com.example.new_application.ui.activity.main_tab_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.utils.AESUtil;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.new_application.MainActivity;
import com.example.new_application.R;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.LoginEntity;
import com.example.new_application.bean.UserInfoEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.user_info_activity.ForgetPasswordActivity;
import com.example.new_application.ui.activity.user_info_activity.ModifyLoginPasswordActivity;
import com.example.new_application.utils.CommonToolbarUtil;
import com.example.new_application.utils.Utils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_account_etv)
    EditText login_account_etv;
    @BindView(R.id.login_password_etv)
    EditText login_password_etv;
    @BindView(R.id.login_switch_psw_iv)
    ImageView login_switch_psw_iv;
    @BindView(R.id.login_forget_password_tv)
    TextView login_forget_password_tv;
    @BindView(R.id.login_btn)
    Button login_btn;
    @BindView(R.id.login_to_register_tv)
    TextView login_to_register_tv;
    @BindView(R.id.toolbar_right_tv)
    TextView toolbar_right_tv;
    private boolean mPasswordVisible = false;
    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void getIntentData() {

    }



    @Override
    protected void init(Bundle savedInstanceState) {
        CommonToolbarUtil.initToolbar(this,"登录");
    }
    @OnClick({R.id.login_to_register_tv,R.id.login_btn,R.id.login_switch_psw_iv,R.id.login_forget_password_tv,R.id.toolbar_right_tv})
    public void  onClick(View view){
        switch (view.getId()){
            case R.id.login_to_register_tv:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
            case R.id.login_forget_password_tv:
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                break;
            case R.id.login_btn:
                if(checkEtv() && Utils.isNotFastClick()){
                    requestLogin();
                }
                break;
            case R.id.toolbar_right_tv:
                showtoast("点击帮助");
                break;
            case R.id.login_switch_psw_iv:
                /**
                 * 点击更换密码可见状态
                 */
                if(mPasswordVisible){
                    login_switch_psw_iv.setImageResource(R.drawable.eye_open);
                    login_password_etv.setTransformationMethod(PasswordTransformationMethod.getInstance());//密码隐藏
                    login_password_etv.setSelection(login_password_etv.getText().length());//光标移到最后
                }else {
                    login_switch_psw_iv.setImageResource(R.drawable.eye_close);
                    login_password_etv.setTransformationMethod(HideReturnsTransformationMethod.getInstance());//密码显示
                    login_password_etv.setSelection(login_password_etv.getText().length());//光标移到最后
                }
                mPasswordVisible= !mPasswordVisible;
                break;
            default:
                break;
        }
    }

    private boolean checkEtv() {
        if(StringMyUtil.isEmptyString(login_account_etv.getText().toString())){
            showtoast("请输入账号");
            return false;
        }
        String password = login_password_etv.getText().toString();
        if(StringMyUtil.isEmptyString(password)){
            showtoast("请输入密码");
            return false;
        }
        if(password.length()<8 || password.length()>20){
            showtoast("密码应为8-20位");
            return false;
        }
        return true;
    }

    private void requestLogin() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("username",login_account_etv.getText().toString());
        String encrypt = AESUtil.encrypt(login_password_etv.getText().toString());
        data.put("password", encrypt.replace("\n",""));
        HttpApiUtils.wwwRequest(LoginActivity.this, null, RequestUtils.LOGIN, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                Utils. LoginSuccess(LoginActivity.this,result,"登录成功");
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
