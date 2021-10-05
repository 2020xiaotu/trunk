package com.example.new_application.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.OfficialGroupEntity;
import com.example.new_application.utils.GlideLoadViewUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OfficialGroupAdapter extends BaseQuickAdapter<OfficialGroupEntity, BaseViewHolder> {
    public OfficialGroupAdapter(int layoutResId, @Nullable List<OfficialGroupEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, OfficialGroupEntity officialGroupEntity) {
        ImageView cooperation_iv = baseViewHolder.getView(R.id.cooperation_iv);
        GlideLoadViewUtil.LoadTitleView(getContext(),officialGroupEntity.getImage(),cooperation_iv);
        baseViewHolder.setText(R.id.cooperation_top_tv,officialGroupEntity.getTitle());
        baseViewHolder.setText(R.id.cooperation_bottom_tv,officialGroupEntity.getRemark());
    }
}
