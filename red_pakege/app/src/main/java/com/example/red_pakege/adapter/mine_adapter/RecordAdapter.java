package com.example.red_pakege.adapter.mine_adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.red_pakege.R;
import com.example.red_pakege.model.RecordModel;

import java.util.List;

import static com.example.red_pakege.activity.mine.RecordActivity.TYPE.DrawRecord;
import static com.example.red_pakege.activity.mine.RecordActivity.TYPE.RechargeRecord;

public class RecordAdapter extends BaseQuickAdapter<RecordModel, BaseViewHolder> {

    public RecordAdapter(int layoutResId, @Nullable List data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, RecordModel item) {

        helper.setText(R.id.tv_itemrecord_time, item.getTime());
        helper.setText(R.id.tv_itemrecord_money, item.getMoney());
        if (item.getMtype()==RechargeRecord){
            helper.setText(R.id.tv_itemrecord_type, item.getType());
            switch (item.getStatus()){
                case "0":
                    helper.setText(R.id.tv_itemrecord_status, "充值中" );
                    break;
                case "1":
                    helper.setText(R.id.tv_itemrecord_status, "已充值" );
                    break;
                case "2":
                    helper.setText(R.id.tv_itemrecord_status, "充值失败" );
                    break;
            }
        }
        if (item.getMtype()==DrawRecord){
            helper.setText(R.id.tv_itemrecord_type, "余额提现");
            switch (item.getStatus()){
                case "0":
                    helper.setText(R.id.tv_itemrecord_status, "提现中" );
                    break;
                case "1":
                    helper.setText(R.id.tv_itemrecord_status, "提现成功" );
                    break;
                case "-1":
                    helper.setText(R.id.tv_itemrecord_status, "提现失败" );
                    break;
            }
        }


    }
}
