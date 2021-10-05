package com.example.red_pakege.adapter.mine_adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.red_pakege.R;
import com.example.red_pakege.util.GlideLoadViewUtil;

import java.util.List;

public class AwardRecyAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public AwardRecyAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
       // Glide.with(mContext).load(item).into((ImageView) helper.getView(R.id.iv_item_recy_award));
        GlideLoadViewUtil.LoadRetcCirView(mContext,item,8,(ImageView) helper.getView(R.id.iv_item_recy_award));
    }
}
