package com.example.red_pakege.mvp.contract;

import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.base.IBaseView;
import com.example.red_pakege.net.entity.MemberMoneyEntity;


public interface MineFragmentContract extends IBaseView {


    interface IMineView extends IBaseView {
        void showUserInfo(UserEntity entity);
        void showMemberMoney(MemberMoneyEntity entity);
        void showTodayChongzhi();
        void showTodayTixian();
        void showTodayYingkui();
        void showTabList();
    }

    interface ILoginPresenter extends IBasePresenter<IMineView> {
        //用户信息
        void RequestUserInfo();
        //用户资金
        void RequestMemberMoney();
        //今日充值金额
        void ReqTodayChongzhi();
        //今日提现
        void ReqTodayTixian();
        //今日盈亏
        void ReqTodayYingkui();
        //下方分类列表 红包 电子 棋牌 休闲
        void ReqTabList();


    }
}
