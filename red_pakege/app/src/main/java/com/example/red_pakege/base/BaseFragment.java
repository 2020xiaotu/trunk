package com.example.red_pakege.base;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.example.red_pakege.util.ToastUtils;

import androidx.annotation.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * MVP Fragment 基类
 * created  by ganzhe on 2019/9/20.
 */
public abstract class BaseFragment<T extends IBasePresenter> extends AbstractRootFragment implements IBaseView {

    private static final String TAG = BaseFragment.class.getSimpleName();

    protected T mPresenter;
    protected  abstract T  createPresenter();
    protected KProgressHUD mKProgressHUD;
    @Override
    public void onAttach(Context onAttach) {
        super.onAttach(onAttach);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter = createPresenter();
        if(mPresenter != null){
            Log.e(TAG,"=== mPresenter 不为空" + mPresenter.getClass());
            mPresenter.onAttachView(this);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(mPresenter != null){
            mPresenter.onDetachView();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if(mPresenter != null){
            mPresenter = null;
        }
    }

    @Override
    public void showLoading() {
        mKProgressHUD = KProgressHUD.create(_mActivity);
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
    public void showErrorMsg(String errorMsg) {
        ToastUtils.showToast(errorMsg);
    }

    @Override
    protected int getLayoutId() { return 0; }

    @Override
    protected void initEventAndData() { }

    @Override
    public void reload() {
        ToastUtils.showToast("点击重新加载");
    }
}
