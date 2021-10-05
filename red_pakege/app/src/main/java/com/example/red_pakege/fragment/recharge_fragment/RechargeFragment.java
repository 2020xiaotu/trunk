package com.example.red_pakege.fragment.recharge_fragment;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cambodia.zhanbang.rxhttp.net.common.BaseEntityObserver;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.example.red_pakege.R;
import com.example.red_pakege.adapter.recharge_adapter.RechargeTabAdapter;
import com.example.red_pakege.base.BaseFragment;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.model.RechargeEntryViewModel;
import com.example.red_pakege.net.api.HttpApiImpl;
import com.example.red_pakege.net.entity.RechargeEntreyEntity;
import com.example.red_pakege.util.RouteUtil;
import com.example.red_pakege.util.ToastUtils;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.example.red_pakege.activity.mine.RecordActivity.TYPE.RechargeRecord;

public class RechargeFragment extends BaseFragment implements TabLayout.BaseOnTabSelectedListener {

    @BindView(R.id.iv_commonbar2_kefu)
    ImageView ivCommonbar2Kefu;
    @BindView(R.id.tv_commonbar2_title)
    TextView tvCommonbar2Title;
    @BindView(R.id.ll_commbar2_recharge)
    LinearLayout llCommbar2Recharge;
    @BindView(R.id.toolbar_commonbar2)
    Toolbar toolbarCommonbar2;
    @BindView(R.id.recharge_tab)
    TabLayout rechargeTab;
    @BindView(R.id.recharge_viewpager)
    ViewPager rechargeViewpager;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;


    private RechargeTabAdapter rechargeTabAdapter;
    //   private String[] titles = {"官方充值", "微信支付", "支付宝", "网银支付"};
    //   private int[] icons = {R.drawable.tab_gw_nor, R.drawable.tab_wx_nor, R.drawable.tab_ali_nor, R.drawable.tab_yl_nor};
    List<RechargeEntreyEntity.DataBean.BankAllListBean> mList = new ArrayList<>();

    private RechargeEntryViewModel mViewModel;

    public static RechargeFragment newInstance() {
        Bundle args = new Bundle();
        RechargeFragment fragment = new RechargeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_recharge;
    }

    @Override
    protected void initView() {
        tvCommonbar2Title.setText("充值中心");
        mSmartRefreshLayout.setEnableLoadMore(false);
        initTab();
        mViewModel = ViewModelProviders.of(_mActivity).get(RechargeEntryViewModel.class);

    }

    @Override
    protected void initEventAndData() {
        ReqRechargeEntryList();

        ivCommonbar2Kefu.setOnClickListener(v -> {
        });
        llCommbar2Recharge.setOnClickListener(v -> {
            RouteUtil.start2RecordActivity(_mActivity, RechargeRecord);
        });
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                ReqRechargeEntryList();
            }
        });

    }

    private void initTab() {
        rechargeTabAdapter = new RechargeTabAdapter(_mActivity, getChildFragmentManager(), mList);
        rechargeViewpager.setAdapter(rechargeTabAdapter);
      //  rechargeViewpager.setOffscreenPageLimit(3);
        rechargeTab.setupWithViewPager(rechargeViewpager);
      //  rechargeTab.removeAllTabs();
        for (int i = 0; i < mList.size(); i++) {
            //自定义tab item布局
            rechargeTab.getTabAt(i).setCustomView(rechargeTabAdapter.getTabView(i));
        }
        rechargeTab.addOnTabSelectedListener(this);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getCustomView() != null) {
            TextView textView = tab.getCustomView().findViewById(R.id.tv_recharge_tabitem);
            textView.setTextColor(getResources().getColor(R.color.tabSelecterColor));
        }

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        if (tab.getCustomView() != null) {
            TextView textView = tab.getCustomView().findViewById(R.id.tv_recharge_tabitem);
            textView.setTextColor(getResources().getColor(R.color.defaultColor));
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void ReqRechargeEntryList() {
        HttpApiImpl.getInstance()
                .PostRechargeEntry()
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) this)))
                .subscribe(new BaseEntityObserver<RechargeEntreyEntity>() {
                    @Override
                    public void onSuccess(RechargeEntreyEntity entity) {
                        LogUtils.e(entity.toString());

                        closeLoading();
                        mSmartRefreshLayout.finishRefresh();
                        mList.clear();
                        mList.addAll(entity.getData().getBankAllList());

                        mViewModel.setBankAllList(entity.getData().getBankAllList());
                        rechargeTabAdapter.notifyDataSetChanged();
                   //     initTab();
                    }

                    @Override
                    public void onFail(String msg) {
                        LogUtils.e(msg);
                        ToastUtils.showToast(msg);
                        mSmartRefreshLayout.finishRefresh();

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





}
