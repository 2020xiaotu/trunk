package com.example.red_pakege.activity.mine;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.red_pakege.BuildConfig;
import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.model.BankInfoModel;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BindBankCardActivity extends BaseActivity {


    @BindView(R.id.iv_commonbar_back)
    ImageView ivCommonbarBack;
    @BindView(R.id.tv_commonbar_title)
    TextView tvCommonbarTitle;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.ll_bindbankcard_mycard)
    LinearLayout ll_bindbankcard_mycard;
    @BindView(R.id.iv_bankcard_tu)
    ImageView iv_bankcard_tu;
    @BindView(R.id.tv_bankcard_name)
    TextView tv_bankcard_name;
    @BindView(R.id.tv_bankcard_cardnum1)
    TextView tv_bankcard_cardnum1;
    @BindView(R.id.tv_bankcard_cardnum2)
    TextView tv_bankcard_cardnum2;
    @BindView(R.id.tv_bindcard_personname)
    TextView tv_bindcard_personname;
    @BindView(R.id.iv_add_bankcard)
    ImageView iv_add_bankcard;

    SharedPreferenceHelperImpl mSharedPreferenceHelperImpl;
    UserEntity userEntity;

    List<BankInfoModel> bankInfoModelList = new ArrayList<>();

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_bindbankcard;
    }


    @Override
    protected void initView() {
        mSharedPreferenceHelperImpl = new SharedPreferenceHelperImpl();

        ActionBarUtil.initMainActionbar(this, toolbarCommon);
        tvCommonbarTitle.setText("绑定银行卡");

    }


    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.e("Resume");
        showBancardInfo();
    }

    @Override
    protected void initEventAndData() {
        ivCommonbarBack.setOnClickListener(v -> onBackPressedSupport());
        iv_add_bankcard.setOnClickListener(v -> {
            startActivity(new Intent(this,AddBankCardActivity.class));
        });
    }

    private void showBancardInfo(){
        userEntity = JsonUtil.Str2UserEntity(mSharedPreferenceHelperImpl.getUserInfo());
        UserEntity.DataBean.MemberInfoBean userInfo = userEntity.getData().getMemberInfo();

        if ( TextUtils.isEmpty(userEntity.getData().getMemberInfo().getBankName())) {
            ll_bindbankcard_mycard.setVisibility(View.GONE);
        } else {
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
            String str = JsonUtil.getJson(this, "banks.json");
            LogUtils.e(str);

            TreeMap<String, String> treeMap = gson.fromJson(str, TreeMap.class);
            LogUtils.e("treeMap:" + treeMap);

            List<String> list_key = new ArrayList(treeMap.keySet());
            List<String> list_value = new ArrayList(treeMap.values());
            for (int i = 0; i < list_key.size(); i++) {
                bankInfoModelList.add(new BankInfoModel(list_key.get(i), list_value.get(i)));
            }
            for (int i = 0; i < bankInfoModelList.size(); i++) {
                if (bankInfoModelList.get(i).getName().equals(userEntity.getData().getMemberInfo().getBankName())) {
                    int ResId = getResources().getIdentifier(bankInfoModelList.get(i).getResId(), "drawable", BuildConfig.APPLICATION_ID);
                    iv_bankcard_tu.setImageResource(ResId);
                    break;
                }
            }

            tv_bankcard_name.setText(userInfo.getBankName());
            tv_bindcard_personname.setText("" + userInfo.getBankAccountName());
            if (userInfo.getBankCard().length() >= 7) {
                tv_bankcard_cardnum1.setText(userInfo.getBankCard().substring(0, 4));
                tv_bankcard_cardnum2.setText(userInfo.getBankCard().substring(userInfo.getBankCard().length() - 3));
            }
        }
    }


}
