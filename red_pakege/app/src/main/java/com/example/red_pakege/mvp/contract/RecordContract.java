package com.example.red_pakege.mvp.contract;

import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.base.IBaseView;
import com.example.red_pakege.net.entity.DrawRecordEntity;
import com.example.red_pakege.net.entity.RechargeRecordEntity;

import java.util.Map;

public interface RecordContract extends IBaseView {


    interface IRecordView extends IBaseView {
        void showChargeRecord(RechargeRecordEntity entity,boolean isRestart);
        void showDrawRecord(DrawRecordEntity entity,boolean isRestart);
    }

    interface IRecordPresenter extends IBasePresenter<IRecordView> {

        void ReqChargeRecord(Map map,boolean isRestart);
        void ReqDrawRecord(Map map,boolean isRestart);
    }

}
