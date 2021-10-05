package com.example.red_pakege.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;
import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.mvp.contract.UserInfoContract;
import com.example.red_pakege.mvp.presenter.UserInfoPresenter;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.JsonUtil;
import com.example.red_pakege.util.ToastUtils;
import com.example.red_pakege.widget.ConfirmStrPop;
import com.example.red_pakege.widget.SexSelPop;

import butterknife.BindView;

import static com.example.red_pakege.activity.mine.ModifypwdActivity.MODIFY_TYPE;
import static com.example.red_pakege.activity.mine.ModifypwdActivity.RESULTCODE;
import static com.example.red_pakege.activity.mine.ModifypwdActivity.TYPE.MODIFYLOGINPWD;
import static com.example.red_pakege.activity.mine.ModifypwdActivity.TYPE.MODIFYPAYPWD;
import static com.example.red_pakege.util.JsonUtil.UserEntity2Str;

public class UserInfoActivity extends BaseActivity<UserInfoPresenter> implements UserInfoContract.IUserInfoView {


    @BindView(R.id.toolbar_common)
    Toolbar mToolbar;
    @BindView(R.id.iv_commonbar_back)
    ImageView iv_commonbar_back;
    @BindView(R.id.tv_commonbar_title)
    TextView tv_commonbar_title;
    @BindView(R.id.rl_userinfo_nickname)
    RelativeLayout rl_userinfo_nickname;
    @BindView(R.id.rl_userinfo_sex)
    RelativeLayout rl_userinfo_sex;
    @BindView(R.id.tv_userinfo_sex)
    TextView tv_userinfo_sex;
    @BindView(R.id.rl_userinfo_accountId)
    RelativeLayout rl_userinfo_accountId;
    @BindView(R.id.tv_userinfo_accountId)
    TextView tv_userinfo_accountId;
    @BindView(R.id.tv_userinfo_phone)
    TextView tv_userinfo_phone;
    @BindView(R.id.tv_userinfo_nickname)
    TextView tv_userinfo_nickname;
    @BindView(R.id.rl_userinfo_usersign)
    RelativeLayout rl_userinfo_usersign;
    @BindView(R.id.tv_userinfo_usersign)
    TextView tv_userinfo_usersign;
    @BindView(R.id.userinfo_parent)
    LinearLayout userinfo_parent;
    @BindView(R.id.rl_userinfo_modifyloginpwd)
    RelativeLayout rl_userinfo_modifyloginpwd;
    @BindView(R.id.rl_userinfo_modifypaypwd)
    RelativeLayout rl_userinfo_modifypaypwd;
    @BindView(R.id.tv_userinfo_bindbankcard)
    TextView tv_userinfo_bindbankcard;
    @BindView(R.id.rl_userinfo_bindbankcard)
    RelativeLayout rl_userinfo_bindbankcard;

    private UserEntity userEntity;
    private SharedPreferenceHelperImpl mSharedPreferenceHelperImpl;
    public static final int REQUESTCODE = 1001;

    @Override
    protected UserInfoPresenter createPresenter() {
        return new UserInfoPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void initToolbar() {
        ActionBarUtil.initMainActionbar(this, mToolbar);
        tv_commonbar_title.setText("个人信息");
    }

    @Override
    protected void initView() {
        mSharedPreferenceHelperImpl = new SharedPreferenceHelperImpl();
        userEntity = JsonUtil.Str2UserEntity(mSharedPreferenceHelperImpl.getUserInfo());
        UserEntity.DataBean.MemberInfoBean userInfo = userEntity.getData().getMemberInfo();

        tv_userinfo_nickname.setText(userEntity.getData().getMemberInfo().getUserNickName());
        tv_userinfo_usersign.setText(userEntity.getData().getMemberInfo().getRemark());
        tv_userinfo_sex.setText(userEntity.getData().getMemberInfo().getSex()==1?"男":"女");
        tv_userinfo_accountId.setText(userEntity.getData().getMemberInfo().getNickname());
        tv_userinfo_phone.setText(mSharedPreferenceHelperImpl.getPhoneNum());
        tv_userinfo_bindbankcard.setText((userInfo.getBankName()==null|| TextUtils.isEmpty(userInfo.getBankName()))?"未设置":"已设置");

    }

    @Override
    protected void initEventAndData() {
        iv_commonbar_back.setOnClickListener(v -> onBackPressedSupport());
        rl_userinfo_nickname.setOnClickListener(v -> {
            ConfirmStrPop  confirmStrPop = new ConfirmStrPop(this,userEntity.getData().getMemberInfo().getUserNickName());
            confirmStrPop.showPopupWindow(rl_userinfo_nickname);
            confirmStrPop.setOnTvClickListener(new ConfirmStrPop.OnClickListener() {
                @Override
                public void onClick(String str) {
                    LogUtils.e(str);
                    mPresenter.ModifyUserNickname(str);
                }
            });
        });
        rl_userinfo_usersign.setOnClickListener(v -> {
            ConfirmStrPop  confirmStrPop = new ConfirmStrPop(this,userEntity.getData().getMemberInfo().getRemark());
            confirmStrPop.showPopupWindow(rl_userinfo_usersign);
            confirmStrPop.setOnTvClickListener(new ConfirmStrPop.OnClickListener() {
                @Override
                public void onClick(String str) {
                    LogUtils.e(str);
                    mPresenter.ModifyUserSign(str);
                }
            });
        });

        rl_userinfo_sex.setOnClickListener(v -> {
            SexSelPop sexSelPop = new SexSelPop(this,userEntity.getData().getMemberInfo().getSex());
            sexSelPop.showPopupWindow(userinfo_parent);
            sexSelPop.setOnTvClickListener(new SexSelPop.OnClickListener() {
                @Override
                public void onClick(int sex) {
                    LogUtils.e(""+sex);
                    mPresenter.ModifySex(sex);
                }
            });
        });

        rl_userinfo_modifyloginpwd.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(this,ModifypwdActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(MODIFY_TYPE,MODIFYLOGINPWD);
            intent.putExtras(bundle);
            startActivityForResult(intent,REQUESTCODE);
        });
        rl_userinfo_modifypaypwd.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(this,ModifypwdActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(MODIFY_TYPE,MODIFYPAYPWD);
            intent.putExtras(bundle);
            startActivityForResult(intent,REQUESTCODE);
        });

        rl_userinfo_bindbankcard.setOnClickListener(v -> {
            UserEntity.DataBean.MemberInfoBean userInfo = userEntity.getData().getMemberInfo();
            if ((userInfo.getBankName()==null|| TextUtils.isEmpty(userInfo.getBankName()))){
                startActivity(new Intent(this,AddBankCardActivity.class));
            }else {
                startActivity(new Intent(this,BindBankCardActivity.class));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //此处可以根据两个Code进行判断，本页面和结果页面跳过来的值
        if (requestCode == REQUESTCODE && resultCode == RESULTCODE) {
            String msg = data.getStringExtra("msg");
            ToastUtils.showToast(msg);
        }


    }

    @Override
    public void showModifyAvatar() {

    }

    @Override
    public void showModifyUserNickname(String userNickName) {
        ToastUtils.showToast("修改昵称成功");
        tv_userinfo_nickname.setText(userNickName);
        userEntity.getData().getMemberInfo().setUserNickName(userNickName);
        mSharedPreferenceHelperImpl.setUserInfo(UserEntity2Str(userEntity));
    }

    @Override
    public void showModifyUserSign(String userSign) {
        ToastUtils.showToast("修改签名成功");
        tv_userinfo_usersign.setText(userSign);
        userEntity.getData().getMemberInfo().setRemark(userSign);
        mSharedPreferenceHelperImpl.setUserInfo(UserEntity2Str(userEntity));
    }

    @Override
    public void showModifySex(int sex) {
        ToastUtils.showToast("修改性别成功");
        tv_userinfo_sex.setText(sex==1?"男":"女");
        userEntity.getData().getMemberInfo().setSex(sex);
        mSharedPreferenceHelperImpl.setUserInfo(UserEntity2Str(userEntity));
    }


}
