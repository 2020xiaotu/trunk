package com.example.red_pakege.fragment.recharge_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
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
import me.yokeyword.fragmentation.SupportFragment;

import static com.example.red_pakege.config.Constants.NAME;
import static com.example.red_pakege.config.Constants.POSITION;
import static com.example.red_pakege.config.Constants.PRICE;
import static com.example.red_pakege.config.Constants.SELECTION;
import static com.example.red_pakege.fragment.recharge_fragment.GfchargeFragment.KEY_RESULT_RESPONSE;

public class GfchargeNextFragment extends BaseFragment {

    @BindView(R.id.tv_commonbar_title)
    TextView tv_title;
    @BindView(R.id.iv_commonbar_back)
    ImageView iv_back;
    @BindView(R.id.btn_gfchargenext_commit)
    Button btn_gfchargenext_commit;
    @BindView(R.id.tv_gfchargenext_name)
    TextView tv_name;
    @BindView(R.id.tv_gfchargenext_account)
    TextView tv_account;
    @BindView(R.id.tv_gfchargenext_zh)
    TextView tv_zh;



    private int mPosition;
    private int mSelection;

    private String bank_id;
    private String name;
    private String price;

    private RechargeEntryViewModel mViewModel;
    private RechargeEntreyEntity.DataBean.BankAllListBean.RechargeBankListBean mBankInfo = new RechargeEntreyEntity.DataBean.BankAllListBean.RechargeBankListBean();

    public static GfchargeNextFragment newInstance(int position,int selection,String name,String price) {
        GfchargeNextFragment fragment = new GfchargeNextFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, position);
        bundle.putInt(SELECTION, selection);
        bundle.putString(NAME, name);
        bundle.putString(PRICE, price);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gfchargenext;
    }

    @Override
    protected void initView() {
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

        iv_back.setOnClickListener(v -> onBackPressedSupport());
        btn_gfchargenext_commit.setOnClickListener(v -> {
            btn_gfchargenext_commit.setClickable(false);
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
                        btn_gfchargenext_commit.setClickable(true);
                        closeLoading();
                        LogUtils.e(entity.toString());
                        ToastUtils.showToast(entity.getErrorMsg());
                      /*  Bundle bundle = new Bundle();
                        bundle.putString(KEY_RESULT_RESPONSE, entity.getErrorMsg());
                        setFragmentResult(RESULT_OK,bundle);*/
                        pop();
                    }

                    @Override
                    public void onFail(String msg) {
                        btn_gfchargenext_commit.setClickable(true);
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
