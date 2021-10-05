package com.example.new_application.ui.activity.user_info_activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.example.new_application.R;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.ContactEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.pop.ContactPop;
import com.example.new_application.utils.CommonToolbarUtil;
import com.example.new_application.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class SetNewPhoneActivity extends BaseActivity {

    @BindView(R.id.set_new_phone_etv)
    EditText set_new_phone_etv;
    @BindView(R.id.phone_code_etv)
    EditText phone_code_etv;
    @BindView(R.id.get_phone_code_btn)
    Button get_phone_code_btn;
    @BindView(R.id.set_new_phone_btn)
    Button set_new_phone_btn;
    @BindView(R.id.contact_kefu_tv)
    TextView contact_kefu_tv;
    @BindView(R.id.area_code_tv)
    TextView area_code_tv;
    @BindView(R.id.area_code_iv)
    ImageView area_code_iv;
    static String SEND_PHONE_CODE="获取验证码";
    int countDownTime = 61;
    static  int REQUEST_CHOOSE_AREA_CODE=1;
    Handler handler = new Handler();
    String type3VerifyKey;
    private ContactPop contactPop;

    @Override
    public int getLayoutId() {
        return R.layout.activity_set_new_phone;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
    @Override
    public void getIntentData() {
        type3VerifyKey=getIntent().getStringExtra("type3VerifyKey");
    }
    @Override
    protected void init(Bundle savedInstanceState) {
        CommonToolbarUtil.initToolbar(this,"修改手机号");
    }
    public static void startAty(Context context, String type3VerifyKey){
        Intent intent = new Intent(context, SetNewPhoneActivity.class);
        intent.putExtra("type3VerifyKey",type3VerifyKey);
        context.startActivity(intent);
    }
    private void initContactPop(String contactString) {
        ArrayList<ContactEntity> contactEntityArrayList = new ArrayList<>();
        contactEntityArrayList.add(new ContactEntity("telegram",contactString));
         contactPop = new ContactPop(this,contactEntityArrayList);
    }
    @OnClick({R.id.contact_kefu_tv,R.id.get_phone_code_btn,R.id.set_new_phone_btn,R.id.area_code_iv,R.id.area_code_tv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.contact_kefu_tv:
                initContactPop(Utils.getSystemParamsEntity().getOnlineCustomer());
                if(contactPop!=null){
                    contactPop.showAtLocation(get_phone_code_btn, Gravity.BOTTOM,0,0);
                    Utils.darkenBackground(SetNewPhoneActivity.this,0.7f);
                }
//                startActivity(new Intent(SetNewPhoneActivity.this,OnLineKeFuActivity.class));
                break;
            case R.id.get_phone_code_btn:
                if(StringMyUtil.isEmptyString(set_new_phone_etv.getText().toString())){
                    showtoast("请输入手机号");
                    return;
                }
                resSendPhoneCode(set_new_phone_etv.getText().toString());
                break;
            case R.id.set_new_phone_btn:
                if(StringMyUtil.isEmptyString(phone_code_etv.getText().toString())){
                    showtoast("请输入手机验证码");
                    return;
                }
                requestBIndPhone();
                break;
            case R.id.area_code_tv:
            case R.id.area_code_iv:
                Intent intent = new Intent(SetNewPhoneActivity.this, AreaSelectActivity.class);
                startActivityForResult(intent,REQUEST_CHOOSE_AREA_CODE);
                break;
            default:
                break;
        }
    }

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

    private void requestBIndPhone() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("phone",area_code_tv.getText().toString()+set_new_phone_etv.getText().toString());
        data.put("type",3);
        data.put("type3VerifyKey",type3VerifyKey);
        data.put("smsCode",phone_code_etv.getText().toString());
        HttpApiUtils.wwwRequest(this, null, RequestUtils.BIND_PHINE, data, new HttpApiUtils.OnRequestLintener() {
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
}