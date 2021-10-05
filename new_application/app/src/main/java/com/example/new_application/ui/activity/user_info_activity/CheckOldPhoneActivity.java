package com.example.new_application.ui.activity.user_info_activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.example.new_application.R;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.ContactEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.home_activity.ServerDetailsActivity;
import com.example.new_application.ui.pop.ContactPop;
import com.example.new_application.utils.CommonToolbarUtil;
import com.example.new_application.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class CheckOldPhoneActivity extends BaseActivity {

    @BindView(R.id.check_old_phone_etv)
    EditText check_old_phone_etv;
    @BindView(R.id.phone_code_etv)
    EditText phone_code_etv;
    @BindView(R.id.get_phone_code_btn)
    Button get_phone_code_btn;
    @BindView(R.id.check_old_phone_btn)
    Button check_old_phone_btn;
    @BindView(R.id.contact_kefu_tv)
    TextView contact_kefu_tv;
    String phone;
    static String SEND_PHONE_CODE="获取验证码";
    int countDownTime = 61;
    static  int REQUEST_CHOOSE_AREA_CODE=1;
    private ContactPop contactPop;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cheack_old_phone;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void getIntentData() {
        phone = getIntent().getStringExtra("phone");
        check_old_phone_etv.setText(phone);
        check_old_phone_etv.setEnabled(false);
    }
    public static void startAty(Context context,String phone){
        Intent intent = new Intent(context, CheckOldPhoneActivity.class);
        intent.putExtra("phone",phone);
        context.startActivity(intent);
    }
    @Override
    protected void init(Bundle savedInstanceState) {
        CommonToolbarUtil.initToolbar(this,"验证手机号");
    }
    private void initContactPop(String contactString) {
        ArrayList<ContactEntity> contactEntityArrayList = new ArrayList<>();
        contactEntityArrayList.add(new ContactEntity("telegram",contactString));
            contactPop = new ContactPop(this,contactEntityArrayList);
    }
    @OnClick({R.id.contact_kefu_tv,R.id.get_phone_code_btn,R.id.check_old_phone_btn})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.contact_kefu_tv:
                /*startActivity(new Intent(CheckOldPhoneActivity.this,OnLineKeFuActivity.class));*/
                initContactPop(Utils.getSystemParamsEntity().getOnlineCustomer());
                if(contactPop!=null){
                    contactPop.showAtLocation(get_phone_code_btn, Gravity.BOTTOM,0,0);
                    Utils.darkenBackground(CheckOldPhoneActivity.this,0.7f);
                }
                break;
            case R.id.get_phone_code_btn:
                resSendPhoneCode(phone);
                break;
            case R.id.check_old_phone_btn:
                if(StringMyUtil.isEmptyString(phone_code_etv.getText().toString())){
                    showtoast("请输入手机验证码");
                    return;
                }
                requestBIndPhone();
                break;
            default:
                break;
        }
    }
    Handler handler = new Handler();
    private void resSendPhoneCode(String phone) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("phone", phone);
        data.put("type", 2);
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
        data.put("phone",phone);
        data.put("type",2);
        data.put("smsCode",phone_code_etv.getText().toString());
        HttpApiUtils.wwwRequest(this, null, RequestUtils.BIND_PHINE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                String type3VerifyKey = jsonObject.getString("type3VerifyKey");
                SetNewPhoneActivity.startAty(CheckOldPhoneActivity.this,type3VerifyKey);
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

}