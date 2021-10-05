package com.example.red_pakege.activity.mine;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;

import com.cambodia.zhanbang.rxhttp.net.common.BaseEntityObserver;
import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;
import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.example.red_pakege.BuildConfig;
import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.model.BankInfoModel;
import com.example.red_pakege.mvp.contract.DrawingCenterContract;
import com.example.red_pakege.mvp.presenter.DrawingCenterPresenter;
import com.example.red_pakege.net.api.HttpApiImpl;
import com.example.red_pakege.net.entity.MemberMoneyEntity;
import com.example.red_pakege.util.JsonUtil;
import com.example.red_pakege.util.MoneyUtil;
import com.example.red_pakege.util.RouteUtil;
import com.example.red_pakege.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.BindView;

import static com.example.red_pakege.activity.mine.RecordActivity.TYPE.DrawRecord;
import static com.example.red_pakege.activity.mine.RecordActivity.TYPE.RechargeRecord;
import static com.example.red_pakege.util.ActionBarUtil.initMainActionbar;

public class DrawingCenterActivity extends BaseActivity<DrawingCenterPresenter> implements DrawingCenterContract.IDrawingCenterView {

    @BindView(R.id.tv_commondrawing_title)
    TextView mTitle;
    @BindView(R.id.common_drawing_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_commondrawing_back)
    ImageView mBack;
    @BindView(R.id.tv_commondrawing_right)
    TextView tvRight;
    @BindView(R.id.tv_drawcenter_commit)
    TextView tv_drawcenter_commit;
    @BindView(R.id.tv_drawcenter_allin)
    TextView tv_drawcenter_allin;
    @BindView(R.id.ll_drawcenter_gobank)
    LinearLayout ll_drawcenter_gobank;
    @BindView(R.id.et_drawcenter_drawmoney)
    EditText et_drawcenter_drawmoney;
    @BindView(R.id.et_drawcenter_drawpwd)
    EditText et_drawcenter_drawpwd;
    @BindView(R.id.tv_drawcenter_max)
    TextView tv_drawcenter_max;
    @BindView(R.id.tv_drawcenter_chbankcard)
    TextView tv_drawcenter_chbankcard;
    @BindView(R.id.tv_drawcenter_range)
    TextView tv_drawcenter_range;

    private String max_drawmoney;

    private List<BankInfoModel> bankInfoModelList = new ArrayList<>();

    @Override
    protected DrawingCenterPresenter createPresenter() {
        return new DrawingCenterPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_drawing_center;
    }

    @Override
    protected void initToolbar() {
        initMainActionbar(this, mToolbar);
        mTitle.setText("提款中心");
    }

    @Override
    protected void initEventAndData() {
        mPresenter.ReqUserInfoAndMemberMoney();


        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        String str = JsonUtil.getJson(this,"banks.json");
        LogUtils.e(str);

        TreeMap<String, String> treeMap = gson.fromJson(str, TreeMap.class);

        List<String> list_key = new ArrayList(treeMap.keySet());
        List<String> list_value = new ArrayList(treeMap.values());
        for (int i=0;i<list_key.size();i++){
            bankInfoModelList.add(new BankInfoModel(list_key.get(i),list_value.get(i)));
        }

        mBack.setOnClickListener(v -> onBackPressedSupport());
        tvRight.setOnClickListener(v -> {
            RouteUtil.start2RecordActivity(this, DrawRecord);
        });
        tv_drawcenter_commit.setOnClickListener(v -> {
            if ( !EditCheck()){
                return;
            }
            String mPrice = et_drawcenter_drawmoney.getText().toString();
            String mPaypwd = et_drawcenter_drawpwd.getText().toString();
            mPresenter.ReqTixian(mPrice,mPaypwd);
        });
        tv_drawcenter_allin.setOnClickListener(v -> {
            et_drawcenter_drawmoney.setText(max_drawmoney);
        });
        ll_drawcenter_gobank.setOnClickListener(v -> {
            startActivity(new Intent(this,AddBankCardActivity.class));
        });

    }

    @Override
    public void showTixianSuccess(BaseEntity entity) {
        ToastUtils.showToast(entity.getErrorMsg());
        onBackPressedSupport();
    }

    @Override
    public void showMemberMoneySuccess(MemberMoneyEntity entity) {
        max_drawmoney = ""+entity.getData().getAmount();
        tv_drawcenter_max.setText(max_drawmoney);
     //   tv_drawcenter_range.setText("提现范围:"+MoneyUtil.parMoney2());

    }

    @Override
    public void showUserInfoSuccess(UserEntity entity) {

        UserEntity.DataBean.MemberInfoBean memberInfoBean = entity.getData().getMemberInfo();
        //显示银行卡
        if (memberInfoBean.getBankName()!=null&&memberInfoBean.getBankCard()!=null){
            for (int i=0;i<bankInfoModelList.size();i++){
                if (memberInfoBean.getBankName().equals(bankInfoModelList.get(i).getName())){
                    tv_drawcenter_chbankcard.setText(bankInfoModelList.get(i).getName());
                    int ResId = getResources().getIdentifier(bankInfoModelList.get(i).getResId(),"drawable", BuildConfig.APPLICATION_ID);
                    Drawable drawableLeft = getResources().getDrawable(ResId);
                    tv_drawcenter_chbankcard.setCompoundDrawablesWithIntrinsicBounds(drawableLeft,null,null,null);
                    break;
                }
            }
        }
    }


    private boolean EditCheck(){
            String mPrice = et_drawcenter_drawmoney.getText().toString();
            String mPaypwd = et_drawcenter_drawpwd.getText().toString();
        if (TextUtils.isEmpty(mPrice)){
            ToastUtils.showToast("请输入提款金额");
            return false;
        }
        if (TextUtils.isEmpty(mPaypwd)){
            ToastUtils.showToast("请输入提款密码");
            return false;
        }
        if (Integer.parseInt(mPrice)>Integer.parseInt(max_drawmoney)){
            ToastUtils.showToast("超过最大提款数目");
            return false;
        }
        if (Integer.parseInt(mPrice)<=0){
            ToastUtils.showToast("提款数目不符合");
            return false;
        }

        return true;

    }
}
