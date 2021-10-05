package com.example.new_application.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.MineRecyclerEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MineRecyclerAdapter extends BaseQuickAdapter<MineRecyclerEntity, BaseViewHolder> {
    public MineRecyclerAdapter(int layoutResId, @Nullable List<MineRecyclerEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MineRecyclerEntity mineRecyclerEntity) {
        baseViewHolder.setImageResource(R.id.mine_recycler_iv,mineRecyclerEntity.getDrawableId()) ;
        baseViewHolder.setText(R.id.mine_recycler_tv,mineRecyclerEntity.getTitle());
    }
}
