package com.example.red_pakege.base;

import android.os.Bundle;
import android.util.Log;


import com.kaopiz.kprogresshud.KProgressHUD;
import com.example.red_pakege.util.ToastUtils;


import androidx.annotation.Nullable;

/**
 * created  by ganzhe on 2019/9/14.
 */
public abstract class BaseActivity<T extends IBasePresenter> extends AbstractRootActivity implements IBaseView{

    public static final String TAG = BaseActivity.class.getSimpleName();
    protected KProgressHUD mKProgressHUD;

    protected T mPresenter;
    protected  abstract T  createPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onViewCreated() {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.onAttachView(this);
            Log.e(TAG,"---mPresenter 不为空" + mPresenter.getClass());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mPresenter != null){
            mPresenter.onDetachView();
            mPresenter = null;
        }

    }

    @Override
    public void showLoading() {
        mKProgressHUD = KProgressHUD.create(this);
        mKProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    @Override
    public void closeLoading() {
        if (mKProgressHUD != null) {
            mKProgressHUD.dismiss();
        }
    }

    @Override
    public void showNormal() { }

    @Override
    public void showError() { }

    @Override
    protected int getLayout() {
        return 0;
    }

    @Override
    protected void initToolbar() { }

    @Override
    protected void initEventAndData() { }



    @Override
    public void reload() { }

    @Override
    public void showErrorMsg(String errorMsg) {
        ToastUtils.showToast(errorMsg);
    }



}
