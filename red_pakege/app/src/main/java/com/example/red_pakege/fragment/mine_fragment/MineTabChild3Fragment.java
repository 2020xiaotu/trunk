package com.example.red_pakege.fragment.mine_fragment;

import android.os.Bundle;

import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseFragment;
import com.example.red_pakege.base.IBasePresenter;

public class MineTabChild3Fragment  extends BaseFragment {

    public static MineTabChild3Fragment newInstance() {
        MineTabChild3Fragment fragment = new MineTabChild3Fragment();
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
        return R.layout.fragment_minetab3;
    }

    @Override
    protected void initEventAndData() {
        LogUtils.e("minetab3 c");
        super.initEventAndData();
    }

    @Override
    protected void initView() {
        super.initView();
    }
}