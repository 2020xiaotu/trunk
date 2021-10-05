package com.example.red_pakege.mvp.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.cambodia.zhanbang.rxhttp.net.common.BaseEntityObserver;
import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.example.red_pakege.base.BasePresenter;
import com.example.red_pakege.mvp.contract.MineFragmentContract;
import com.example.red_pakege.net.api.HttpApiImpl;
import com.example.red_pakege.net.entity.MemberMoneyEntity;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

public class MineFragmentPresenter extends BasePresenter<MineFragmentContract.IMineView>
        implements MineFragmentContract.ILoginPresenter  {



    @Override
    public void RequestUserInfo() {
        HttpApiImpl.getInstance()
                .PostUserInfo()
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mViewRef.get())))
                .subscribe(new BaseEntityObserver<UserEntity>(){
                    @Override
                    public void onSuccess(UserEntity userEntity) {
                        LogUtils.e(userEntity.toString());
                        getView().showUserInfo(userEntity);
                    }

                    @Override
                    public void onFail(String msg) {
                        LogUtils.e(msg);
                        getView().showErrorMsg(msg);
                    }

                    @Override
                    protected void onRequestStart() {
               //         getView().showLoading();
                    }

                    @Override
                    protected void onRequestEnd() {
             //           getView().closeLoading();
                    }
                });

    }

    @Override
    public void RequestMemberMoney() {
        HttpApiImpl.getInstance()
                .PostMemberMoney()
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mViewRef.get())))
                .subscribe(new BaseEntityObserver<MemberMoneyEntity>() {
                    @Override
                    public void onSuccess(MemberMoneyEntity entity) {
                        LogUtils.e(entity.toString());
                        getView().showMemberMoney(entity);
                    }

                    @Override
                    public void onFail(String msg) {
                        LogUtils.e(msg);
                        getView().showErrorMsg(msg);
                    }

                    @Override
                    protected void onRequestStart() {
                  //                  getView().showLoading();
                    }

                    @Override
                    protected void onRequestEnd() {
                //        getView().closeLoading();
                    }
                });
    }

    @Override
    public void ReqTodayChongzhi() {

    }

    @Override
    public void ReqTodayTixian() {

    }

    @Override
    public void ReqTodayYingkui() {

    }

    @Override
    public void ReqTabList() {

    }
}
