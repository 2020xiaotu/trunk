package com.example.red_pakege.mvp.contract;

import com.cambodia.zhanbang.rxhttp.net.model.BaseEntity;
import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.base.IBaseView;
import com.example.red_pakege.net.entity.MemberMoneyEntity;

public interface DrawingCenterContract extends IBaseView {

    interface IDrawingCenterView extends IBaseView {

        void showTixianSuccess(BaseEntity entity);
        void showMemberMoneySuccess(MemberMoneyEntity entity);
        void showUserInfoSuccess(UserEntity entity);
    }

    interface IDrawingCenterPresenter extends IBasePresenter<DrawingCenterContract.IDrawingCenterView> {

        void ReqTixian(String price,String paypasswd);
        void ReqMemberMoney();
        void ReqUserInfo();
        void ReqUserInfoAndMemberMoney();
    }

}
