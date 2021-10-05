package com.example.red_pakege.mvp.contract;

import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.base.IBaseView;

public interface UserInfoContract extends IBaseView {

    interface IUserInfoView extends IBaseView {
        void showModifyAvatar();
        void showModifyUserNickname(String userNickName);
        void showModifyUserSign(String userSign);
        void showModifySex(int sex);


    }

    interface IUserInfoPresenter extends IBasePresenter<IUserInfoView> {

        //修改头像
        void ModifyAvatar();
        //修改昵称
        void ModifyUserNickname(String userNickName);
        //修改用户签名
        void ModifyUserSign(String userNickName);
        //修改性别
        void ModifySex(int sex);


    }
}
