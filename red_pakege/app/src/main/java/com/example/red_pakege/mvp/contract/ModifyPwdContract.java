package com.example.red_pakege.mvp.contract;

import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;
import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.base.IBaseView;

/**
 * created  by ganzhe on 2019/11/19.
 */
public interface ModifyPwdContract extends IBaseView {

    interface IModifyPwdView extends IBaseView {
        void showModifyLoginPwd(BaseEntity entity);
        void showModifyPayPwd(BaseEntity entity);

    }

    interface IModifyPwdPresenter extends IBasePresenter<IModifyPwdView> {

        //修改登录密码
        void ModifyLoginPwd(String password,String prePassword);
        //修改支付密码
        void ModifyPayPwd(String paypassword,String prePaypassword);
    }

}
