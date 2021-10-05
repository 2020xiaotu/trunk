package com.example.red_pakege.mvp.contract;

import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.base.IBaseView;

public interface YuebaoRecordContract {


    interface IYuebaoRecordView extends IBaseView {

        void ReqYuebaoRecordSuccess(int type);
        void ReqMycountSuccess();
    }

    interface IYuebaoRecordPresenter extends IBasePresenter<IYuebaoRecordView> {

        void ReqYuebaoRecord(int type);
        void ReqMycount();
    }

}
