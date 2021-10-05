package com.example.red_pakege.mvp.presenter;

import com.cambodia.zhanbang.rxhttp.net.common.BaseEntityObserver;
import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.example.red_pakege.base.BasePresenter;
import com.example.red_pakege.mvp.contract.LoginContract;
import com.example.red_pakege.net.api.HttpApiImpl;
import com.example.red_pakege.net.entity.ParamLogin;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import androidx.lifecycle.LifecycleOwner;

/**
 * created  by ganzhe on 2019/11/19.
 */
public class LoginPresenter extends BasePresenter<LoginContract.ILoginView> implements LoginContract.ILoginPresenter {



    @Override
    public void RequestLogin(String phone,String passwd) {
        HttpApiImpl.getInstance()
                .PostLogin(phone,passwd)
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mViewRef.get())))
                .subscribe(new BaseEntityObserver<UserEntity>(){
                    @Override
                    public void onSuccess(UserEntity userEntity) {
                        LogUtils.e(userEntity.toString());
                        getView().LoginSuccess(userEntity);
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
