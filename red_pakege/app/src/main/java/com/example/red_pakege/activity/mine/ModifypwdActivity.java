package com.example.red_pakege.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.mvp.contract.ModifyPwdContract;
import com.example.red_pakege.mvp.presenter.ModifyPwdPresenter;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.ToastUtils;

import butterknife.BindView;


public class ModifypwdActivity extends BaseActivity<ModifyPwdPresenter> implements ModifyPwdContract.IModifyPwdView {

    public static final String MODIFY_TYPE = "modify_type";
    private TYPE mType;
    public static final int RESULTCODE = 1002;
    SharedPreferenceHelperImpl mSharedPreferenceHelperImpl;
    public enum TYPE {
        MODIFYLOGINPWD,
        MODIFYPAYPWD
    }

    @BindView(R.id.toolbar_common)
    Toolbar mToolbar;
    @BindView(R.id.iv_commonbar_back)
    ImageView iv_commonbar_back;
    @BindView(R.id.tv_commonbar_title)
    TextView tv_commonbar_title;

    @BindView(R.id.et_modifypwd_phonenum)
    TextView et_modifypwd_phonenum;
    @BindView(R.id.et_modifypwd_msgcode)
    EditText et_modifypwd_msgcode;
    @BindView(R.id.et_modifypwd_prepasswd)
    EditText et_modifypwd_prepasswd;
    @BindView(R.id.et_modifypwd_passwd)
    EditText et_modifypwd_passwd;
    @BindView(R.id.et_modifypwd_passwd_again)
    EditText et_modifypwd_passwd_again;
    @BindView(R.id.btn_commit)
    Button btn_commit;

    @Override
    protected ModifyPwdPresenter createPresenter() {
        return new ModifyPwdPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_modifypwd;
    }

    @Override
    protected void mGetIntentData() {
        Bundle bundle = getIntent().getExtras();
        mType = (TYPE) bundle.getSerializable(MODIFY_TYPE);
    }

    @Override
    protected void initView() {
        switch (mType) {
            case MODIFYLOGINPWD:
                tv_commonbar_title.setText("修改登录密码");
                et_modifypwd_prepasswd.setHint("请输入原密码");
                et_modifypwd_passwd.setHint("请输入密码");
                et_modifypwd_passwd_again.setHint("请确认密码");
                break;
            case MODIFYPAYPWD:
                tv_commonbar_title.setText("修改支付密码");
                et_modifypwd_prepasswd.setHint("请输入原支付密码");
                et_modifypwd_passwd.setHint("请输入支付密码");
                et_modifypwd_passwd_again.setHint("请确认支付密码");
                break;
        }
        mSharedPreferenceHelperImpl = new SharedPreferenceHelperImpl();
        et_modifypwd_phonenum.setText(mSharedPreferenceHelperImpl.getPhoneNum());
        ActionBarUtil.initMainActionbar(this, mToolbar);
    }

    private boolean EditCheck() {
        if (TextUtils.isEmpty(et_modifypwd_prepasswd.getText().toString())) {
            ToastUtils.showToast("原密码不能为空");
            return false;
        }
        if (TextUtils.isEmpty(et_modifypwd_passwd.getText().toString())) {
            ToastUtils.showToast("密码不能为空");
            return false;
        }
        if (!et_modifypwd_passwd.getText().toString().equals(et_modifypwd_passwd_again.getText().toString())) {
            ToastUtils.showToast("请确认2次密码是否输入正确");
            return false;
        }

        return true;
    }

    @Override
    protected void initEventAndData() {
        iv_commonbar_back.setOnClickListener(v -> onBackPressedSupport());
        btn_commit.setOnClickListener(v -> {
            if (!EditCheck()){
                return;
            }
            switch (mType){
                case MODIFYLOGINPWD:
                    mPresenter.ModifyLoginPwd(et_modifypwd_passwd.getText().toString(),et_modifypwd_prepasswd.getText().toString());
                    break;
                case MODIFYPAYPWD:
                    mPresenter.ModifyPayPwd(et_modifypwd_passwd.getText().toString(),et_modifypwd_prepasswd.getText().toString());
                    break;
            }
        });
    }


    @Override
    public void showModifyLoginPwd(BaseEntity entity) {
        String msg = "修改密码成功";
        Intent intent = new Intent();
        intent.putExtra("msg",msg);
        setResult(RESULTCODE,intent);
        finish();
    }

    @Override
    public void showModifyPayPwd(BaseEntity entity) {
        String msg = "修改支付密码成功";
        Intent intent = new Intent();
        intent.putExtra("msg",msg);
        setResult(RESULTCODE,intent);
        finish();
    }
}
