package com.example.new_application.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.HomeLabelEntity;
import com.example.new_application.utils.GlideLoadViewUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

//
public class HonestyMerchantChildAdapter extends BaseQuickAdapter<HomeLabelEntity, BaseViewHolder> {
    boolean formStore = false;
    public HonestyMerchantChildAdapter(int layoutResId, @Nullable List<HomeLabelEntity> data,boolean formStore) {
        super(layoutResId, data);
        this.formStore = formStore;
    }
    public HonestyMerchantChildAdapter(int layoutResId, @Nullable List<HomeLabelEntity> data) {
        super(layoutResId, data);

    }
    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeLabelEntity homeLabelEntity) {
        TextView classification_details_child_name_tv =  baseViewHolder.getView(R.id.classification_details_child_name_tv);
        ImageView classification_details_child_name_iv =  baseViewHolder.getView(R.id.classification_details_child_name_iv);
        String image = homeLabelEntity.getImage();
        if(StringMyUtil.isEmptyString(image)){
            classification_details_child_name_iv.setVisibility(View.GONE);
            classification_details_child_name_tv.setVisibility(View.VISIBLE);
            classification_details_child_name_tv.setText(homeLabelEntity.getName());
        }else {
            classification_details_child_name_iv.setVisibility(View.VISIBLE);
            classification_details_child_name_tv.setVisibility(View.GONE);
            GlideLoadViewUtil.LoadNormalView(getContext(),image,classification_details_child_name_iv);
        }
        if(formStore){
            classification_details_child_name_tv.setTextColor(Color.parseColor("#FFFFFF"));
            classification_details_child_name_tv.setBackgroundResource(R.drawable.white_2_store_shape);
        }else {
            classification_details_child_name_tv.setTextColor(Color.parseColor("#FF6801"));
            classification_details_child_name_tv.setBackgroundResource(R.drawable.honesty_label_shape);
        }
    }
}
