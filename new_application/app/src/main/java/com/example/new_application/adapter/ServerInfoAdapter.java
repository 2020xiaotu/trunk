package com.example.new_application.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.bean.ServerInfoEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ServerInfoAdapter extends BaseQuickAdapter<ServerInfoEntity, BaseViewHolder> {
    public ServerInfoAdapter(int layoutResId, @Nullable List<ServerInfoEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ServerInfoEntity serverInfoEntity) {

    }
}
