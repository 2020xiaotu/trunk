package com.example.red_pakege.adapter.mine_adapter;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.red_pakege.R;
import com.example.red_pakege.net.entity.RechargeEntreyEntity;

import java.util.List;

public class GfChargeAdapter extends BaseQuickAdapter<RechargeEntreyEntity.DataBean.BankAllListBean.RechargeBankListBean, BaseViewHolder> {


    private int selectedIndex;        //记录当前选中的条目索引

    public GfChargeAdapter(int layoutResId, @Nullable List<RechargeEntreyEntity.DataBean.BankAllListBean.RechargeBankListBean> dataList) {
        super(layoutResId, dataList);
    }

    @Override
    protected void convert(BaseViewHolder helper, RechargeEntreyEntity.DataBean.BankAllListBean.RechargeBankListBean item) {

        int position = helper.getLayoutPosition();
        helper.setText(R.id.tv_item_gfcharge,"通道"+(position+1));

        LinearLayout ll = helper.getView(R.id.ll_item_gfcharge);
        TextView tv = helper.getView(R.id.tv_item_gfcharge);
        if (selectedIndex == position) {
            helper.setChecked(R.id.tv_item_gfcharge,true);           //选中状态
            ll.setBackground(mContext.getResources().getDrawable(R.drawable.bkg_red_corner_shape));
            tv.setTextColor(mContext.getResources().getColor(R.color.white));

        } else {                                                            //非选中状态
            helper.setChecked(R.id.tv_item_gfcharge,false);
            ll.setBackground(mContext.getResources().getDrawable(R.drawable.bkg_gray_selector));
            tv.setTextColor(mContext.getResources().getColor(R.color.defaultColor));
        }

    }

    public void setSelectedIndex(int position) {
        this.selectedIndex = position;
        notifyDataSetChanged();
    }
}
