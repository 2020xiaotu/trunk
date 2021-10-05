package com.example.new_application.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.RightMenuEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RightMenuAdapter extends BaseQuickAdapter<RightMenuEntity, BaseViewHolder> {
    public RightMenuAdapter(int layoutResId, @Nullable List<RightMenuEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, RightMenuEntity rightMenuEntity) {
        baseViewHolder.setImageResource(R.id.menu_iv,rightMenuEntity.getResourceId());
        baseViewHolder.setText(R.id.menu_tv,rightMenuEntity.getName());
    }
}
