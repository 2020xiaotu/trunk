package com.example.new_application.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.CooperationEntity;
import com.example.new_application.utils.GlideLoadViewUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CooperationAdapter extends BaseQuickAdapter<CooperationEntity, BaseViewHolder> {
    public CooperationAdapter(int layoutResId, @Nullable List<CooperationEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, CooperationEntity cooperationEntity) {
        ImageView  cooperation_iv = baseViewHolder.getView(R.id.cooperation_iv);
        GlideLoadViewUtil.LoadTitleView(getContext(),cooperationEntity.getImage(),cooperation_iv);
        baseViewHolder.setText(R.id.cooperation_top_tv,cooperationEntity.getCustomerServiceName());
        baseViewHolder.setText(R.id.cooperation_bottom_tv,cooperationEntity.getRemark());

    }
}
