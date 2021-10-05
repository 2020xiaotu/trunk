package com.example.red_pakege.model;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cambodia.zhanbang.rxhttp.net.common.BaseEntityObserver;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.example.red_pakege.net.api.HttpApiImpl;
import com.example.red_pakege.net.entity.RechargeEntreyEntity;
import com.example.red_pakege.util.ToastUtils;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

public class RechargeEntryViewModel extends ViewModel {

    private MutableLiveData<List<RechargeEntreyEntity.DataBean.BankAllListBean>> mBankAllList;
  //  private Context mContext;

    public LiveData<List<RechargeEntreyEntity.DataBean.BankAllListBean>> getBankAllList(){
//        this.mContext = context;
        if (mBankAllList==null){
            mBankAllList = new MutableLiveData<>();
  //          loadBankAllList();
        }

        return mBankAllList;
    }

    public void setBankAllList(List<RechargeEntreyEntity.DataBean.BankAllListBean> mList){
        if (mBankAllList==null){
            mBankAllList = new MutableLiveData<>();
        }
        mBankAllList.setValue(mList);
    }


    /*private void loadBankAllList(){
        // Do an asynchronous operation to fetch users.
        HttpApiImpl.getInstance()
                .PostRechargeEntry()
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mContext)))
                .subscribe(new BaseEntityObserver<RechargeEntreyEntity>() {
                    @Override
                    public void onSuccess(RechargeEntreyEntity entity) {
                        LogUtils.e(entity.toString());

                  //      closeLoading();
                 //       mSmartRefreshLayout.finishRefresh();
                        mBankAllList.setValue(entity.getData().getBankAllList());
                    }

                    @Override
                    public void onFail(String msg) {
                        LogUtils.e(msg);
                        ToastUtils.showToast(msg);
                //        mSmartRefreshLayout.finishRefresh();

                    }

                    @Override
                    protected void onRequestStart() {
                  //      showLoading();
                    }

                    @Override
                    protected void onRequestEnd() {
                 //       closeLoading();
                    }
                });

    }*/
}
