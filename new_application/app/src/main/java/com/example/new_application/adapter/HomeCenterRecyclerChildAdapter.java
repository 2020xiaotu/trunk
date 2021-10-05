package com.example.new_application.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.HomeCenterEntity;
import com.example.new_application.utils.GlideLoadViewUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HomeCenterRecyclerChildAdapter extends BaseQuickAdapter<HomeCenterEntity, BaseViewHolder> {
    public HomeCenterRecyclerChildAdapter(int layoutResId, @Nullable List<HomeCenterEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeCenterEntity homeCenterEntity) {
        TextView right_split_tv = baseViewHolder.getView(R.id.right_split_tv);
        TextView bottom_split_tv = baseViewHolder.getView(R.id.bottom_split_tv);
        ImageView left_iv = baseViewHolder.getView(R.id.left_iv);
        baseViewHolder.setText(R.id.title_tv,homeCenterEntity.getTheme());
        baseViewHolder.setText(R.id.sub_title_tv,homeCenterEntity.getThemeTwo());
        GlideLoadViewUtil.LoadNormalView(getContext(),homeCenterEntity.getImage(),left_iv);
        int itemPosition = getItemPosition(homeCenterEntity);
        if((itemPosition+1)%2==0){
            right_split_tv.setVisibility(View.INVISIBLE);
        }else {
            right_split_tv.setVisibility(View.VISIBLE);
        }
        if(itemPosition==2 || itemPosition==3 ){
            bottom_split_tv.setVisibility(View.INVISIBLE);
        }else {
            bottom_split_tv.setVisibility(View.VISIBLE);

        }

    }


}
