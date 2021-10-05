package com.example.red_pakege.mvp.contract;

import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.base.IBaseView;

public interface SettingContract extends IBaseView {

    interface ISettingView extends IBaseView {
        void showSafeQuit();
        void showgetVersion();

    }

    interface ISettingPresenter extends IBasePresenter<ISettingView> {
        void SafeQuit();
        void getVersion();

    }
}
