package com.example.red_pakege.mvp.contract;

import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.base.IBaseView;
import com.example.red_pakege.net.entity.YuebaoInfoEntity;

/**
 * created  by ganzhe on 2019/11/19.
 */
public interface YuebaoContract extends IBaseView {

    interface IYuebaoView extends IBaseView {
        void showYuebaoInfo(YuebaoInfoEntity entity);

    }


    interface IYuebaoPresenter extends IBasePresenter<IYuebaoView> {

        void PostYuebaoInfo();

    }

}
