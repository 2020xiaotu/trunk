package com.example.red_pakege.fragment.mine_fragment;

import android.os.Bundle;

import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseFragment;
import com.example.red_pakege.base.IBasePresenter;

public class MineTabChild2Fragment  extends BaseFragment {

    public static MineTabChild2Fragment newInstance() {
        MineTabChild2Fragment fragment = new MineTabChild2Fragment();
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
        return R.layout.fragment_minetab2;
    }

    @Override
    protected void initEventAndData() {
        LogUtils.e("minetab2 c");
        super.initEventAndData();
    }

    @Override
    protected void initView() {
        super.initView();
    }
}