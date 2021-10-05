package com.example.new_application.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.DetailsPhotoEntity;
import com.example.new_application.utils.GlideLoadViewUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DetailsPhotoAdapter extends BaseQuickAdapter<DetailsPhotoEntity, BaseViewHolder> {
    public DetailsPhotoAdapter(int layoutResId, @Nullable List<DetailsPhotoEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, DetailsPhotoEntity detailsPhotoEntity) {
        ImageView photo_iv = baseViewHolder.getView(R.id.photo_iv);
        GlideLoadViewUtil.LoadNormalView(getContext(), detailsPhotoEntity.getUrl(),photo_iv);
    }
}
