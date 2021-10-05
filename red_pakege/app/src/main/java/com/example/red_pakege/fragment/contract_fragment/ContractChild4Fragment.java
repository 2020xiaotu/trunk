package com.example.red_pakege.fragment.contract_fragment;

import android.os.Bundle;

import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseFragment;
import com.example.red_pakege.base.IBasePresenter;

/**
 * created  by ganzhe on 2019/11/22.
 */
public class ContractChild4Fragment extends BaseFragment {

    public static ContractChild4Fragment newInstance() {
        ContractChild4Fragment fragment = new ContractChild4Fragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contractchild4;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
    }

    @Override
    protected void initView() {
        super.initView();
    }
}
