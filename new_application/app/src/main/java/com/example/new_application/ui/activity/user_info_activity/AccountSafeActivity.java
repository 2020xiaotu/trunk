package com.example.new_application.ui.activity.user_info_activity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.new_application.R;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.UserInfoEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.utils.CommonToolbarUtil;
import com.example.new_application.utils.StatusBarUtils2;
import com.example.new_application.utils.Utils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.new_application.utils.StatusBarUtils2.getStatusBarHeight;

public class AccountSafeActivity extends BaseActivity {
    @BindView(R.id.toolbar_title_tv)
    TextView toolbar_title_tv;
    @BindView(R.id.toolbar_back_iv)
    ImageView toolbar_back_iv;
    @BindView(R.id.safe_level_tv)
    TextView safe_level_tv;
    @BindView(R.id.modify_login_password_constrainLayout)
    ConstraintLayout modify_login_password_constrainLayout;
    @BindView(R.id.last_login_date_tv)
    TextView last_login_date_tv;
    @BindView(R.id.bind_phone_constrainLayout)
    ConstraintLayout bind_phone_constrainLayout;
    @BindView(R.id.is_bind_phone_tv)
    TextView is_bind_phone_tv;
    @BindView(R.id.bind_phone_tip_one_tv)
            TextView bind_phone_tip_one_tv;
    @BindView(R.id.bind_phone_tip_two_tv)
    TextView bind_phone_tip_two_tv;
    SharedPreferenceHelperImpl sp =new SharedPreferenceHelperImpl();
    boolean isBindPhone = false;
    @Override
    public int getLayoutId() {
        return R.layout.activity_account_safe;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        CommonToolbarUtil.initToolbar(this,"账号与安全");
        requestLastLoginDate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestUserInfo();
    }
    @Override
    protected void initStatusBar() {
        super.initStatusBar();
        View viewById = findViewById(R.id.toolbar_linear);
        viewById.setBackgroundColor(ContextCompat.getColor(this,R.color.transparent));//toolbar设置透明
//        toolbar_back_iv.setImageResource(R.drawable.icon_arrow_back);//设置白色返回键
        toolbar_title_tv.setTextColor(Color.WHITE);
        toolbar_back_iv.setImageResource(R.drawable.arrwo_back_white);
        CommonToolbarUtil.initToolbar(this,"账号与安全");//标题

        //rootView第一个子view的图片顶入状态栏
        StatusBarUtils2.with(this)
                .init();
        //设置toolBar父view的marginTop()
        ViewGroup.LayoutParams layoutParams = viewById.getLayoutParams();
        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) layoutParams;
        layoutParams1.setMargins(0, getStatusBarHeight(this),0,0);
        viewById.setLayoutParams(layoutParams1);
    }
    @OnClick({R.id.bind_phone_constrainLayout,R.id.modify_login_password_constrainLayout})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.bind_phone_constrainLayout:
                    if(isBindPhone){
                        /**
                         * 修改手机
                         */
                        CheckOldPhoneActivity.startAty(AccountSafeActivity.this, Utils.getUserInfo().getPhone());
                    }else {
                        /**
                         * 绑定手机
                         */
                        startActivity(new Intent(AccountSafeActivity.this,BindPhoneActivity.class));
                    }
                break;
            case R.id.modify_login_password_constrainLayout:
                startActivity(new Intent(AccountSafeActivity.this,ModifyLoginPasswordActivity.class));
                break;
            default:
                break;
        }
    }
    private void requestLastLoginDate() {
        HttpApiUtils.wwwRequest(this, null, RequestUtils.LAST_LOGIN_DATE, new HashMap<String, Object>(), new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                String loginDate = jsonObject.getString("loginDate");
                last_login_date_tv.setText(loginDate);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
    private void requestUserInfo() {
        HttpApiUtils.wwwNormalRequest(this, null, RequestUtils.USER_INFO, new HashMap<String, Object>(), new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                UserInfoEntity userInfoEntity = JSONObject.parseObject(result, UserInfoEntity.class);
                sp.setUserInfo(result);
                if(StringMyUtil.isEmptyString(userInfoEntity.getPhone())){
                    is_bind_phone_tv.setText("去绑定");
                    isBindPhone = false;
                    bind_phone_tip_one_tv.setText("绑定手机号");
                    safe_level_tv.setText("中");
                }else {
                    is_bind_phone_tv.setText("去修改");
                    isBindPhone = true;
                    bind_phone_tip_one_tv.setText("修改手机号");
                    safe_level_tv.setText("高");
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

}