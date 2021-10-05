package com.example.red_pakege.fragment.recharge_fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.red_pakege.R;
import com.example.red_pakege.adapter.mine_adapter.GfChargeAdapter;
import com.example.red_pakege.base.BaseFragment;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.model.RechargeEntryViewModel;
import com.example.red_pakege.net.entity.RechargeEntreyEntity;
import com.example.red_pakege.util.ToastUtils;
import com.example.red_pakege.widget.AbSpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
//import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

import static com.example.red_pakege.config.Constants.POSITION;
import static com.example.red_pakege.util.ToolsUtils.hideKeyboard;

/**
 * created  by ganzhe on 2019/11/20.
 */
public class GfchargeFragment extends BaseFragment {


    @BindView(R.id.et_gfcharge_name)
    EditText etGfchargeName;
    @BindView(R.id.et_gfcharge_jine)
    EditText etGfchargeJine;
    @BindView(R.id.btn_gfcharge_next)
    Button btnGfchargeNext;
    @BindView(R.id.recy_gfcharge)
    RecyclerView mRecy;
    @BindView(R.id.tv_gfcharge_bankname)
    TextView tv_gfcharge_bankname;


    private int mPosition;
    private int mSelecttion = 0;
  //  private String bank_id;
    private String name;
    private String price;

    private RechargeEntryViewModel mViewModel;
    private GfChargeAdapter gfChargeAdapter;
    private LinearLayoutManager layoutManager;
    private List<RechargeEntreyEntity.DataBean.BankAllListBean.RechargeBankListBean> mList = new ArrayList<>();

    private static final int REQ_GF_COMMIT = 100;
    public static final String KEY_RESULT_RESPONSE = "result_response";

    public static GfchargeFragment newInstance(int positon) {
        GfchargeFragment fragment = new GfchargeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(POSITION, positon);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gfcharge;
    }

    @Override
    protected void initView() {

        Bundle bundle = getArguments();
        mPosition = bundle.getInt(POSITION);
        mViewModel = ViewModelProviders.of(_mActivity).get(RechargeEntryViewModel.class);
        initRecy();
    }

    private void initRecy(){
        layoutManager = new LinearLayoutManager(_mActivity);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mRecy.setLayoutManager(layoutManager);
        mRecy.addItemDecoration(new AbSpacesItemDecoration(8));
        gfChargeAdapter  = new GfChargeAdapter(R.layout.item_gfcharge,mList);
        mRecy.setAdapter(gfChargeAdapter);
    }


    @Override
    protected void initEventAndData() {


        mViewModel.getBankAllList().observe(this,mBankAllList ->{
            //update to UI
            LogUtils.e("gf显示item:"+  "  , "  + mBankAllList.size());
            mList.clear();
            mList.addAll(mBankAllList.get(mPosition).getRechargeBankList());
            etGfchargeJine.setHint(""+mList.get(mSelecttion).getDown()+"-"+mList.get(mSelecttion).getUp());
            gfChargeAdapter.replaceData(mBankAllList.get(mPosition).getRechargeBankList());
            if (mList.size()>0){
                gfChargeAdapter.setSelectedIndex(0);
                tv_gfcharge_bankname.setText("官方代理充值("+mList.get(0).getBankName()+")");
            }
        });

        hideKeyboard(_mActivity);


        gfChargeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                gfChargeAdapter.setSelectedIndex(position);
                LogUtils.e("通道列表点击："+position);
                mSelecttion = position;
                etGfchargeJine.setHint(""+mList.get(mSelecttion).getDown()+"-"+mList.get(mSelecttion).getUp());
                tv_gfcharge_bankname.setText("官方代理充值("+mList.get(mSelecttion).getBankName()+")");
            }
        });


        btnGfchargeNext.setOnClickListener(v -> {
            LogUtils.e("通道列表跳转："+mPosition+" ， "+mSelecttion);

            if (!CheckEdit()){
                return;
            }
            name = etGfchargeName.getText().toString();
            price = etGfchargeJine.getText().toString();

            ((SupportFragment) getParentFragment()).
                    start(GfchargeNextFragment.newInstance(mPosition,mSelecttion,name,price));
        });


    }

    private boolean CheckEdit(){
        if (TextUtils.isEmpty(etGfchargeName.getText().toString())){
            ToastUtils.showToast("请输入姓名");
            return false;
        }
        if (TextUtils.isEmpty(etGfchargeJine.getText().toString())){
            ToastUtils.showToast("请输入金额");
            return false;
        }
        if (Integer.parseInt(etGfchargeJine.getText().toString())<mList.get(mSelecttion).getDown()
                ||Integer.parseInt(etGfchargeJine.getText().toString())>mList.get(mSelecttion).getUp()){
            ToastUtils.showToast("资金超出范围");
            return false;
        }

        return true;

    }

    /*@Override
    public void onFragmentResult(int requestCode, int resultCode, Bundle data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == REQ_GF_COMMIT && resultCode == RESULT_OK && data != null) {
            String msg = data.getString(KEY_RESULT_RESPONSE);
            ToastUtils.showToast(msg);
            etGfchargeName.setText("");
            etGfchargeJine.setText("");

        }
    }*/



}
