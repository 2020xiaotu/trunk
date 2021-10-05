package com.example.new_application.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.ServerLabelEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ServerLabelAdapter extends BaseQuickAdapter<ServerLabelEntity, BaseViewHolder> {
    public ServerLabelAdapter(int layoutResId, @Nullable List<ServerLabelEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ServerLabelEntity serverLabelEntity) {
    baseViewHolder.setText(R.id.server_label_name_tv,serverLabelEntity.getName());
    }
}
