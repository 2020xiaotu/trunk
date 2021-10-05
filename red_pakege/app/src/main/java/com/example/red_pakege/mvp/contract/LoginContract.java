package com.example.red_pakege.mvp.contract;

import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.base.IBaseView;
import com.example.red_pakege.net.entity.ParamLogin;

/**
 * created  by ganzhe on 2019/11/19.
 */
public interface LoginContract extends IBaseView {

    interface ILoginView extends IBaseView {

        void LoginSuccess(UserEntity entity);
    }

    interface ILoginPresenter extends IBasePresenter<ILoginView> {

        void RequestLogin(String phone,String passwd);
    }

}
