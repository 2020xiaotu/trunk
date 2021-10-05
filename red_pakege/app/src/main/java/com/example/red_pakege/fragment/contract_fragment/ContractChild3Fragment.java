package com.example.red_pakege.fragment.contract_fragment;

import android.os.Bundle;

import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseFragment;
import com.example.red_pakege.base.IBasePresenter;

/**
 * created  by ganzhe on 2019/11/22.
 */
public class ContractChild3Fragment extends BaseFragment {

    public static ContractChild3Fragment newInstance() {
        ContractChild3Fragment fragment = new ContractChild3Fragment();
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
        return R.layout.fragment_contractchild3;
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
