package com.example.red_pakege.adapter.mine_adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.red_pakege.R;
import com.example.red_pakege.model.CapitalDetailModel;

import java.util.List;

public class CapitalDetailAdapter extends BaseQuickAdapter<CapitalDetailModel, BaseViewHolder> {

    public CapitalDetailAdapter(int layoutResId, @Nullable List<CapitalDetailModel> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CapitalDetailModel item) {
        helper.setText(R.id.tv_item_recy_capitaldetail,item.getTitle());
        Glide.with(mContext).load(item.getIcon()).into((ImageView) helper.getView(R.id.iv_item_recy_capitaldetail));
    }
}
