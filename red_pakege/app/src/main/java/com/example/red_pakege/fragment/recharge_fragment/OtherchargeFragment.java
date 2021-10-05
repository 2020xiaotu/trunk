package com.example.red_pakege.fragment.recharge_fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.red_pakege.R;
import com.example.red_pakege.adapter.mine_adapter.GfChargeAdapter;
import com.example.red_pakege.base.AbstractRootFragment;
import com.example.red_pakege.model.RechargeEntryViewModel;
import com.example.red_pakege.net.entity.RechargeEntreyEntity;
import com.example.red_pakege.util.ToastUtils;
import com.example.red_pakege.widget.AbSpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

import static com.example.red_pakege.config.Constants.POSITION;
import static com.example.red_pakege.util.ToolsUtils.hideKeyboard;

/**
 * created  by ganzhe on 2019/11/21.
 */
public class OtherchargeFragment extends AbstractRootFragment {



    @BindView(R.id.recy_othercharge)
    RecyclerView mRecy;
    @BindView(R.id.et_othercharge_nickname)
    TextView et_nickname;
    @BindView(R.id.et_othercharge_jine)
    EditText et_jine;
    @BindView(R.id.btn_othercharge_next)
    Button btn_next;


    private  int mPosition;
    private int mSelecttion = 0;
    private String name;
    private String price;
    private RechargeEntryViewModel mViewModel;
    private GfChargeAdapter gfChargeAdapter;
    private LinearLayoutManager layoutManager;
    private List<RechargeEntreyEntity.DataBean.BankAllListBean.RechargeBankListBean> mList = new ArrayList<>();



    public static OtherchargeFragment newInstance(int positon) {
        OtherchargeFragment fragment = new OtherchargeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, positon);
        fragment.setArguments(bundle);
        return fragment;
    }



    @Override
    protected void initView() {
        mPosition = getArguments().getInt(POSITION);
        mViewModel = ViewModelProviders.of(_mActivity).get(RechargeEntryViewModel.class);
        initRecy();

    }

    private void initRecy(){
        layoutManager = new LinearLayoutManager(_mActivity);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        gfChargeAdapter  = new GfChargeAdapter(R.layout.item_gfcharge,mList);
        mRecy.addItemDecoration(new AbSpacesItemDecoration(4));
        mRecy.setAdapter(gfChargeAdapter);
        mRecy.setLayoutManager(layoutManager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_othercharge;
    }

    @Override
    protected void initEventAndData() {
        hideKeyboard(_mActivity);
        mViewModel.getBankAllList().observe(this,mBankAllList ->{
            //update to UI
            LogUtils.e("other显示item:"+  "  , "  + mBankAllList.size());
            mList.clear();
            mList.addAll(mBankAllList.get(mPosition).getRechargeBankList());
            gfChargeAdapter.replaceData(mBankAllList.get(mPosition).getRechargeBankList());
            if (mList.size()>0){
                gfChargeAdapter.setSelectedIndex(0);
                et_jine.setHint(""+mList.get(mSelecttion).getDown()+"-"+mList.get(mSelecttion).getUp());
            }
        });



        gfChargeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                gfChargeAdapter.setSelectedIndex(position);
                LogUtils.e("通道列表点击："+position);
                mSelecttion = position;
                et_jine.setHint(""+mList.get(mSelecttion).getDown()+"-"+mList.get(mSelecttion).getUp());
            }
        });


        btn_next.setOnClickListener(v -> {
            LogUtils.e("通道列表跳转："+mPosition+" ， "+mSelecttion);

            if (!CheckEdit()){
                return;
            }
            //     bank_id = ""+mList.get(mSelecttion).getId();
            name = et_nickname.getText().toString();
            price = et_jine.getText().toString();

            ((SupportFragment) getParentFragment()).
                    start(OtherChargeNextFragment.newInstance(mPosition,mSelecttion,name,price));
        });


    }


    private boolean CheckEdit(){
        if (TextUtils.isEmpty(et_nickname.getText().toString())){
            ToastUtils.showToast("请输入昵称");
            return false;
        }
        if (TextUtils.isEmpty(et_jine.getText().toString())){
            ToastUtils.showToast("请输入金额");
            return false;
        }
        if (Integer.parseInt(et_jine.getText().toString())<mList.get(mSelecttion).getDown()
                ||Integer.parseInt(et_jine.getText().toString())>mList.get(mSelecttion).getUp()){
            ToastUtils.showToast("资金超出范围");
            return false;
        }

        return true;

    }
}
