package com.example.red_pakege.fragment.recharge_fragment;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;

import com.cambodia.zhanbang.rxhttp.net.common.BaseEntityObserver;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseFragment;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.model.RechargeEntryViewModel;
import com.example.red_pakege.net.api.HttpApiImpl;
import com.example.red_pakege.net.entity.RechargeEntreyEntity;
import com.example.red_pakege.net.entity.RemittanceEntity;
import com.example.red_pakege.util.ToastUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import butterknife.BindView;

import static com.example.red_pakege.config.Constants.NAME;
import static com.example.red_pakege.config.Constants.POSITION;
import static com.example.red_pakege.config.Constants.PRICE;
import static com.example.red_pakege.config.Constants.SELECTION;

public class OtherChargeNextFragment extends BaseFragment {

    @BindView(R.id.iv_commonbar_back)
    ImageView iv_commonbar_back;
    @BindView(R.id.tv_commonbar_title)
    TextView tv_commonbar_title;
    @BindView(R.id.tv_otherchargenext_name)
    TextView tv_name;
    @BindView(R.id.tv_otherchargenext_zh)
    TextView tv_zh;
    @BindView(R.id.tv_otherchargenext_account)
    TextView tv_account;
    @BindView(R.id.iv_otherchargenext)
    ImageView iv_otherchargenext;
    @BindView(R.id.btn_otherchargenext_commit)
    Button btn_commit;


    private int mPosition;
    private int mSelection;

    private String bank_id;
    private String name;
    private String price;

    private RechargeEntryViewModel mViewModel;
    private RechargeEntreyEntity.DataBean.BankAllListBean.RechargeBankListBean mBankInfo = new RechargeEntreyEntity.DataBean.BankAllListBean.RechargeBankListBean();

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }


    public static OtherChargeNextFragment newInstance(int position,int selection,String name,String price) {
        OtherChargeNextFragment fragment = new OtherChargeNextFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        bundle.putInt(SELECTION, selection);
        bundle.putString(NAME, name);
        bundle.putString(PRICE, price);
        fragment.setArguments(bundle);
        return fragment;
    }

    protected  void initView(){
        mViewModel = ViewModelProviders.of(_mActivity).get(RechargeEntryViewModel.class);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mPosition = bundle.getInt(POSITION);
            mSelection = bundle.getInt(SELECTION);
            name = bundle.getString(NAME);
            price = bundle.getString(PRICE);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_otherchargenext;
    }

    @Override
    protected void initEventAndData() {

        mViewModel.getBankAllList().observe(this,mBankAllList ->{
            //update to UI
            LogUtils.e("gf显示item:"+  "  , "  + mBankAllList.size());
            mBankInfo = mBankAllList.get(mPosition).getRechargeBankList().get(mSelection);
            bank_id = ""+mBankInfo.getId();
            tv_name.setText(mBankInfo.getName());
            tv_account.setText(mBankInfo.getAccount());
            tv_zh.setText(mBankInfo.getBankName());
        });

        iv_commonbar_back.setOnClickListener(v -> onBackPressedSupport());
        btn_commit.setOnClickListener(v -> {
            btn_commit.setClickable(false);
            ReqRemittance();

        });
    }


    private void ReqRemittance(){
        HttpApiImpl.getInstance()
                .PostRemittance(bank_id,name,price)
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) this)))
                .subscribe(new BaseEntityObserver<RemittanceEntity>() {
                    @Override
                    public void onSuccess(RemittanceEntity entity) {
                        btn_commit.setClickable(true);
                        LogUtils.e(entity.toString());
                        ToastUtils.showToast(entity.getErrorMsg());
                        closeLoading();
                    }

                    @Override
                    public void onFail(String msg) {
                        btn_commit.setClickable(true);
                        LogUtils.e(msg);
                        ToastUtils.showToast(msg);
                    }

                    @Override
                    protected void onRequestStart() {
                        showLoading();
                    }

                    @Override
                    protected void onRequestEnd() {
                        closeLoading();
                    }
                });
    }

    @Override
    public boolean onBackPressedSupport() {
        pop();
        return true;
    }


}
