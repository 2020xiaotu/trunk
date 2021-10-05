package com.example.red_pakege.mvp.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.cambodia.zhanbang.rxhttp.net.common.BaseEntityObserver;
import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;
import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.example.red_pakege.base.BasePresenter;
import com.example.red_pakege.mvp.contract.DrawingCenterContract;
import com.example.red_pakege.net.api.HttpApiImpl;
import com.example.red_pakege.net.entity.MemberMoneyEntity;
import com.example.red_pakege.util.ToastUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class DrawingCenterPresenter extends BasePresenter<DrawingCenterContract.IDrawingCenterView>
        implements DrawingCenterContract.IDrawingCenterPresenter {


    @Override
    public void ReqTixian(String price, String paypasswd) {
        HttpApiImpl.getInstance()
                .PostBalanceDraw(price, paypasswd)
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mViewRef.get())))
                .subscribe(new BaseEntityObserver<BaseEntity>() {
                    @Override
                    public void onSuccess(BaseEntity entity) {
                        LogUtils.e(entity.toString());
                        getView().showTixianSuccess(entity);
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

    @Override
    public void ReqMemberMoney() {
        HttpApiImpl.getInstance()
                .PostMemberMoney()
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mViewRef.get())))
                .subscribe(new BaseEntityObserver<MemberMoneyEntity>() {
                    @Override
                    public void onSuccess(MemberMoneyEntity entity) {
                        LogUtils.e(entity.toString());
                        getView().closeLoading();
                        getView().showMemberMoneySuccess(entity);
                    }

                    @Override
                    public void onFail(String msg) {
                        LogUtils.e(msg);
                        getView().closeLoading();
                        getView().showErrorMsg(msg);
                    }

                    @Override
                    protected void onRequestStart() {
                        //            getView().showLoading();
                    }

                    @Override
                    protected void onRequestEnd() {
                        getView().closeLoading();
                    }
                });
    }

    @Override
    public void ReqUserInfo() {
        HttpApiImpl.getInstance()
                .PostUserInfo()
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mViewRef.get())))
                .subscribe(new BaseEntityObserver<UserEntity>() {
                    @Override
                    public void onSuccess(UserEntity entity) {
                        LogUtils.e(entity.toString());
                        getView().showUserInfoSuccess(entity);

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


    @Override
    public void ReqUserInfoAndMemberMoney() {
        Observable<UserEntity> observable1 = HttpApiImpl.getInstance().PostUserInfo()
                .compose(RxTransformerUtils.io_main());
        Observable<MemberMoneyEntity> observable2 = HttpApiImpl.getInstance().PostMemberMoney()
                .compose(RxTransformerUtils.io_main());

        Observable.merge(observable1, observable2)
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mViewRef.get())))
                .subscribe(new BaseEntityObserver<BaseEntity>() {
                    @Override
                    protected void onRequestStart() {
                        getView().showLoading();
                    }

                    @Override
                    public void onComplete() {
                        getView().closeLoading();
                    }

                    @Override
                    public void onSuccess(BaseEntity responseEntity) {
                        if (responseEntity instanceof UserEntity) {
                            UserEntity userEntity = (UserEntity) responseEntity;
                            getView().showUserInfoSuccess(userEntity);
                        } else {
                            MemberMoneyEntity memberMoneyEntity = (MemberMoneyEntity) responseEntity;
                            getView().showMemberMoneySuccess(memberMoneyEntity);
                        }
                    }

                    @Override
                    public void onFail(String msg) {
                        getView().showErrorMsg(msg);
                    }

                });


    }
}
