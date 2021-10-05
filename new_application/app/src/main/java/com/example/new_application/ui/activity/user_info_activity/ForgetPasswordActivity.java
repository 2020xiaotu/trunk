package com.example.new_application.ui.activity.user_info_activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.example.new_application.R;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.utils.CommonToolbarUtil;
import com.example.new_application.utils.Utils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetPasswordActivity extends BaseActivity {
    @BindView(R.id.toolbar_right_tv)
    TextView toolbar_right_tv;
    @BindView(R.id.area_code_tv)
    TextView area_code_tv;
    @BindView(R.id.area_code_iv)
    ImageView area_code_iv;
    @BindView(R.id.phone_etv)
    EditText phone_etv;
    @BindView(R.id.phone_code_etv)
    EditText phone_code_etv;
    @BindView(R.id.get_phone_code_btn)
    Button get_phone_code_btn;
    @BindView(R.id.password_etv)
    EditText password_etv;
    @BindView(R.id.sure_password_etv)
    EditText sure_password_etv;
    @BindView(R.id.modify_password_btn)
    Button modify_password_btn;
    static  int REQUEST_CHOOSE_AREA_CODE=1;
    static String SEND_PHONE_CODE="获取验证码";
    int countDownTime = 61;
    @Override
    public int getLayoutId() {
        return R.layout.activity_forget_password;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initToolbar();
    }

    private void initToolbar() {
        CommonToolbarUtil.initToolbar(this,"忘记密码");
        toolbar_right_tv.setText("登录");
        toolbar_right_tv.setVisibility(View.VISIBLE);
        toolbar_right_tv.setTextColor(Color.parseColor("#048CF7"));
    }
    @OnClick({R.id.area_code_tv,R.id.area_code_iv,R.id.modify_password_btn,R.id.get_phone_code_btn,R.id.toolbar_right_tv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.area_code_tv:
            case R.id.area_code_iv:
                Intent intent = new Intent(ForgetPasswordActivity.this, AreaSelectActivity.class);
                startActivityForResult(intent,REQUEST_CHOOSE_AREA_CODE);
                break;
            case R.id.modify_password_btn:
                if(checkEtv()&& Utils.isNotFastClick()){
                    requestModifyPassword();
                }
                break;
            case R.id.get_phone_code_btn:
               /* countDownTime = 61;
                handler.post(countDownRunnable);*/
                String phone = phone_etv.getText().toString();
                if(StringMyUtil.isEmptyString(phone)){
                    showtoast("请输入手机号");
                    return;
                }
                resSendPhoneCode(area_code_tv.getText().toString()+phone);
                break;
            case R.id.toolbar_right_tv:
                finish();
                break;
            default:
                break;
        }
    }

    private void requestModifyPassword() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("phone",phone_etv.getText().toString());
        data.put("pwd",password_etv.getText().toString());
        data.put("smsCode",area_code_tv.getText().toString()+phone_code_etv.getText().toString());
        HttpApiUtils.wwwRequest(ForgetPasswordActivity.this, null, RequestUtils.FORGET_PASSWORD, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showtoast("修改成功");
                finish();
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    private boolean checkEtv() {
        if(StringMyUtil.isEmptyString(phone_etv.getText().toString())){
            showtoast("请输入手机号");
            return false;
        }
        if(StringMyUtil.isEmptyString(phone_code_etv.getText().toString())){
            showtoast("请输入验证码");
            return false;
        }
        String password = password_etv.getText().toString();
        if(password.length()<8||password.length()>20){
            showtoast("密码长度为8-20位");
            return false;
        }

        String surePassword = sure_password_etv.getText().toString();
        if(surePassword.length()<8|| surePassword.length()>16){
            showtoast("请确认密码,密码长度为8-18位");
            return false;
        }
        if(!surePassword.equals(password)){
            showtoast("两次输入的密码不一致");
            return false;
        }
        return true;
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

        }
    };

    Runnable countDownRunnable = new Runnable() {
        @Override
        public void run() {
            countDownTime--;
            if(countDownTime>=1){
                get_phone_code_btn.setText(countDownTime+"s后重发");
                handler.postDelayed(countDownRunnable,1000);
            }else {
                get_phone_code_btn.setText(SEND_PHONE_CODE);
                countDownTime=61;
                handler.removeCallbacks(countDownRunnable);
            }
        }
    };
    private void resSendPhoneCode(String phone) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("phone", phone);
        data.put("type", 1);
        HttpApiUtils.wwwRequest(this, null, RequestUtils.SEND_PHONE_CODE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showtoast("验证码发送成功");
                countDownTime = 61;
                handler.post(countDownRunnable);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CHOOSE_AREA_CODE && resultCode == RESULT_OK){
            String str = data.getStringExtra("string");
            area_code_tv.setText(str);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}