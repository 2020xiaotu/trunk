package com.example.red_pakege.adapter.mine_adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.red_pakege.net.entity.YuebaoItemEntity;

import java.util.List;

public class YueBaoRecordAdapter extends BaseQuickAdapter<YuebaoItemEntity, BaseViewHolder> {

    public YueBaoRecordAdapter(int layoutResId, @Nullable List<YuebaoItemEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, YuebaoItemEntity item) {

    }
}
