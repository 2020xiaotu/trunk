package com.example.red_pakege.mvp.presenter;

import androidx.lifecycle.LifecycleOwner;

import com.cambodia.zhanbang.rxhttp.net.common.BaseEntityObserver;

import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.red_pakege.base.BasePresenter;
import com.example.red_pakege.config.ModifyType;
import com.example.red_pakege.mvp.contract.UserInfoContract;
import com.example.red_pakege.net.api.HttpApiImpl;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.HashMap;
import java.util.Map;

import static com.example.red_pakege.config.Constants.ModyTYPE;

public class UserInfoPresenter extends BasePresenter<UserInfoContract.IUserInfoView> implements UserInfoContract.IUserInfoPresenter {

    private SharedPreferenceHelperImpl mSharedPreferenceHelperImpl;

    public UserInfoPresenter() {
        mSharedPreferenceHelperImpl = new SharedPreferenceHelperImpl();
    }

    @Override
    public void ModifyAvatar() {

    }

    /**
     * 修改昵称
     */
    @Override
    public void ModifyUserNickname(String userNickName) {
        Map map = new HashMap();
        map.put(ModyTYPE, ModifyType.NICKNAME.getIndex());
        map.put("userNickName",userNickName);

        HttpApiImpl.getInstance()
                .PostModifyUserInfo(map)
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mViewRef.get())))
                .subscribe(new BaseEntityObserver<BaseEntity>(){
                    @Override
                    public void onSuccess(BaseEntity entity) {
                        LogUtils.e(entity.getErrorMsg());
                        getView().showModifyUserNickname(userNickName);
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

    /**
     *修改用户签名
     * @param userSign
     */
    @Override
    public void ModifyUserSign(String userSign) {
        Map map = new HashMap();
        map.put(ModyTYPE, ModifyType.USERSIGN.getIndex());
        map.put("userSign",userSign);

        HttpApiImpl.getInstance()
                .PostModifyUserInfo(map)
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mViewRef.get())))
                .subscribe(new BaseEntityObserver<BaseEntity>(){
                    @Override
                    public void onSuccess(BaseEntity entity) {
                        LogUtils.e(entity.getErrorMsg());
                        getView().showModifyUserSign(userSign);
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

    //性别0女1男2保密
    @Override
    public void ModifySex(int sex) {
        Map map = new HashMap();
        map.put(ModyTYPE, ModifyType.SEX.getIndex());
        map.put("sex",sex);

        HttpApiImpl.getInstance()
                .PostModifyUserInfo(map)
                .compose(RxTransformerUtils.io_main())
                .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner) mViewRef.get())))
                .subscribe(new BaseEntityObserver<BaseEntity>(){
                    @Override
                    public void onSuccess(BaseEntity entity) {
                        LogUtils.e(entity.getErrorMsg());
                        getView().showModifySex(sex);
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
