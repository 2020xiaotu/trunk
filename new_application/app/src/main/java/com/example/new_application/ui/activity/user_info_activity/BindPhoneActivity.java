package com.example.new_application.ui.activity.user_info_activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
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

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class BindPhoneActivity extends BaseActivity {
    @BindView(R.id.bind_phone_etv)
    EditText bind_phone_etv;
    @BindView(R.id.phone_code_etv)
    EditText phone_code_etv;
    @BindView(R.id.get_phone_code_btn)
    Button get_phone_code_btn;
    @BindView(R.id.bind_phone_btn)
    Button bind_phone_btn;
    @BindView(R.id.area_code_tv)
    TextView area_code_tv;
    @BindView(R.id.area_code_iv)
    ImageView area_code_iv;
    static String SEND_PHONE_CODE="获取验证码";
    int countDownTime = 61;
    static  int REQUEST_CHOOSE_AREA_CODE=1;
    @Override
    public int getLayoutId() {
        return R.layout.activity_bind_phone;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        CommonToolbarUtil.initToolbar(this,"绑定手机");
    }
    @OnClick({R.id.bind_phone_btn,R.id.get_phone_code_btn,R.id.area_code_iv,R.id.area_code_tv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bind_phone_btn:
                if(checkEtv()){
                    requestBIndPhone();
                }
                break;
            case R.id.get_phone_code_btn:
                String phone = bind_phone_etv.getText().toString();
                if(StringMyUtil.isEmptyString(phone)){
                    showtoast("请输入手机号");
                    return;
                }
                resSendPhoneCode(area_code_tv.getText().toString()+phone);
                break;
            case R.id.area_code_tv:
            case R.id.area_code_iv:
                Intent intent = new Intent(BindPhoneActivity.this, AreaSelectActivity.class);
                startActivityForResult(intent,REQUEST_CHOOSE_AREA_CODE);
                break;
            default:
                break;
        }
    }

    private void requestBIndPhone() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("phone",area_code_tv.getText().toString()+bind_phone_etv.getText().toString());
        data.put("smsCode",phone_code_etv.getText().toString());
        data.put("type",0);
        HttpApiUtils.wwwRequest(this, null, RequestUtils.BIND_PHINE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showtoast("绑定成功");
                finish();
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    private boolean checkEtv() {
        String phone = bind_phone_btn.getText().toString();
        if(StringMyUtil.isEmptyString(phone)){
            showtoast("请输入手机号");
            return false;
        }
        String phoneCode = phone_code_etv.getText().toString();
        if(StringMyUtil.isEmptyString(phoneCode)){
            showtoast("请输入手机验证码");
            return false;
        }
        return true;
    }

    Handler handler = new Handler();
    private void resSendPhoneCode(String phone) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("phone", phone);
        data.put("type", 0);
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