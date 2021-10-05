package com.example.red_pakege.mvp.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.cambodia.zhanbang.rxhttp.net.common.BaseEntityObserver;
import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.example.red_pakege.base.BasePresenter;
import com.example.red_pakege.mvp.contract.YuebaoContract;
import com.example.red_pakege.net.api.HttpApiImpl;
import com.example.red_pakege.net.entity.YuebaoInfoEntity;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

public class YuebaoPresenter extends BasePresenter<YuebaoContract.IYuebaoView> implements YuebaoContract.IYuebaoPresenter {


    @Override
    public void PostYuebaoInfo() {
        HttpApiImpl.getInstance()
                .PostYuebaoInfo()
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mViewRef.get())))
                .subscribe(new BaseEntityObserver<YuebaoInfoEntity>(){
                    @Override
                    public void onSuccess(YuebaoInfoEntity entity) {
                        getView().showYuebaoInfo(entity);
                    }
                    @Override
                    public void onFail(String msg) {
                        LogUtils.e(msg);
                        getView().showErrorMsg(msg);
                    }
                    @Override
                    protected void onRequestStart() {
                        getView().showLoading();
                    }

                    @Override
                    protected void onRequestEnd() {
                        getView().closeLoading();
                    }
                });
    }


}
