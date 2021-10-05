package com.example.red_pakege.mvp.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.cambodia.zhanbang.rxhttp.net.common.BaseEntityObserver;
import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.example.red_pakege.base.BasePresenter;
import com.example.red_pakege.config.ModifyType;
import com.example.red_pakege.mvp.contract.ModifyPwdContract;
import com.example.red_pakege.net.api.HttpApiImpl;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.HashMap;
import java.util.Map;

import static com.example.red_pakege.config.Constants.ModyTYPE;

public class ModifyPwdPresenter extends BasePresenter<ModifyPwdContract.IModifyPwdView> implements ModifyPwdContract.IModifyPwdPresenter {
    @Override
    public void ModifyLoginPwd(String password, String prePassword) {
        Map map = new HashMap();
        map.put(ModyTYPE, ModifyType.LOGINPWD.getIndex());
        map.put("password", password);
        map.put("prePassword", prePassword);

        HttpApiImpl.getInstance()
                .PostModifyUserInfo(map)
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mViewRef.get())))
                .subscribe(new BaseEntityObserver<BaseEntity>() {
                    @Override
                    public void onSuccess(BaseEntity entity) {
                        LogUtils.e(entity.getErrorMsg());
                        getView().closeLoading();
                        getView().showModifyLoginPwd(entity);
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
    public void ModifyPayPwd(String paypassword, String prePaypassword) {
        Map map = new HashMap();
        map.put(ModyTYPE, ModifyType.PAYPWD.getIndex());
        map.put("paypassword", paypassword);
        map.put("prePaypassword", prePaypassword);

        HttpApiImpl.getInstance()
                .PostModifyUserInfo(map)
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mViewRef.get())))
                .subscribe(new BaseEntityObserver<BaseEntity>() {
                    @Override
                    public void onSuccess(BaseEntity entity) {
                        LogUtils.e(entity.getErrorMsg());
                        getView().closeLoading();
                        getView().showModifyPayPwd(entity);
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
