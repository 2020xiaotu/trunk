package com.example.red_pakege.fragment.main_tab_fragment;

import android.os.Bundle;

import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseFragment;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.fragment.recharge_fragment.RechargeFragment;

public class RechargeRootFragment extends BaseFragment {


    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }


    public static RechargeRootFragment newInstance() {

        Bundle args = new Bundle();
        RechargeRootFragment fragment = new RechargeRootFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_rechargeroot;
    }

    @Override
    protected void initEventAndData() {
        if (findChildFragment(RechargeFragment.class) == null) {
            loadRootFragment(R.id.fl_rechargeroot_container, RechargeFragment.newInstance());
        }
    }
}
