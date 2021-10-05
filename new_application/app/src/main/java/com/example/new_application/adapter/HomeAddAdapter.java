package com.example.new_application.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.HomeAddEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HomeAddAdapter extends BaseQuickAdapter<HomeAddEntity, BaseViewHolder> {
    public HomeAddAdapter(int layoutResId, @Nullable List<HomeAddEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeAddEntity homeAddEntity) {
        baseViewHolder.setText(R.id.home_add_tv,homeAddEntity.getName());
        baseViewHolder.setImageResource(R.id.home_add_iv,homeAddEntity.getResourceId());
    }
}
