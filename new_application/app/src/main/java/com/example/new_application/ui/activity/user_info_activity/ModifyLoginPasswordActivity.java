package com.example.new_application.ui.activity.user_info_activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.example.new_application.R;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.main_tab_activity.LoginActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.SettingActivity;
import com.example.new_application.utils.CommonToolbarUtil;
import com.example.new_application.utils.Utils;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class ModifyLoginPasswordActivity extends BaseActivity {
    @BindView(R.id.password_etv)
    EditText password_etv;
    @BindView(R.id.new_password_etv)
    EditText new_password_etv;
    @BindView(R.id.sure_new_password_etv)
    EditText sure_new_password_etv;
    @BindView(R.id.modify_password_btn)
    Button modify_password_btn;

    @Override
    protected void init(Bundle savedInstanceState) {
        CommonToolbarUtil.initToolbar(this,"修改登录密码");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_login_password;
    }

    @Override
    public void getIntentData() {

    }
    @OnClick({R.id.modify_password_btn})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.modify_password_btn:
                if(checkEtvs()){
                    requestModifyPassword();
                }
                break;
            default:
                break;
        }
    }

    private void requestModifyPassword() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("newPassword",new_password_etv.getText().toString());
        data.put("oldPassword",password_etv.getText().toString());
        HttpApiUtils.wwwRequest(this, null, RequestUtils.MODIFY_PASSWORD, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showtoast("修改成功");
                Utils.clearCache();
                startActivity(new Intent(ModifyLoginPasswordActivity.this, LoginActivity.class));
                finish();
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    private boolean checkEtvs() {
        if(StringMyUtil.isEmptyString(password_etv.getText().toString())){
            showtoast("请输入原密码");
            return false;
        }
        String newPassword = new_password_etv.getText().toString();
        if(newPassword.length()<8||newPassword.length()>20){
            showtoast("密码长度为8-20位");
            return false;
        }
        String sureNewPassword = sure_new_password_etv.getText().toString();
        if(sureNewPassword.length()<8||sureNewPassword.length()>20){
            showtoast("请确认密码,密码长度为8-20位");
            return false;
        }
        return true;
    }

}