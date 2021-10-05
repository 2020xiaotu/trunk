package com.example.red_pakege.adapter.mine_adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.red_pakege.R;
import com.example.red_pakege.net.entity.MineTabChildEntity;

import java.util.List;

public class MineTabChildAdapter extends BaseQuickAdapter<MineTabChildEntity, BaseViewHolder> {

    public MineTabChildAdapter(int layoutResId, @Nullable List<MineTabChildEntity> data) {
        super(layoutResId, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, MineTabChildEntity item) {
        helper.setText(R.id.tv_item_minetabchild,item.getTitle());
        Glide.with(mContext).load(item.getUrl()).into((ImageView) helper.getView(R.id.iv_item_minetabchild));
    }
}
