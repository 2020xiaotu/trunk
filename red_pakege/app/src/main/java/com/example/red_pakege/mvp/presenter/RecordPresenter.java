package com.example.red_pakege.mvp.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.cambodia.zhanbang.rxhttp.net.common.BaseEntityObserver;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.example.red_pakege.base.BasePresenter;
import com.example.red_pakege.mvp.contract.RecordContract;
import com.example.red_pakege.net.api.HttpApiImpl;
import com.example.red_pakege.net.entity.DrawRecordEntity;
import com.example.red_pakege.net.entity.RechargeRecordEntity;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.Map;

public class RecordPresenter extends BasePresenter<RecordContract.IRecordView> implements RecordContract.IRecordPresenter {

    @Override
    public void ReqChargeRecord(Map map,boolean isRestart) {
        HttpApiImpl.getInstance()
                .PostRechargeRecord(map)
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) getView())))
                .subscribe(new BaseEntityObserver<RechargeRecordEntity>(){
                    @Override
                    public void onSuccess(RechargeRecordEntity entity) {
                        getView().showChargeRecord(entity,isRestart);
                    }
                    @Override
                    public void onFail(String msg) {
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
    public void ReqDrawRecord(Map map,boolean isRestart) {
        HttpApiImpl.getInstance()
                .PostDrawRecord(map)
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) getView())))
                .subscribe(new BaseEntityObserver<DrawRecordEntity>(){
                    @Override
                    public void onSuccess(DrawRecordEntity entity) {
                        getView().showDrawRecord(entity,isRestart);
                    }
                    @Override
                    public void onFail(String msg) {

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
