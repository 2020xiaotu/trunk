package com.example.new_application.adapter;

import android.graphics.Color;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.ClassificationDetailsChildEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class ClassificationDetailsChildAdapter extends BaseQuickAdapter<ClassificationDetailsChildEntity, BaseViewHolder> {
    public ClassificationDetailsChildAdapter(int layoutResId, @Nullable List<ClassificationDetailsChildEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ClassificationDetailsChildEntity classificationDetailsChildEntity) {
       TextView classification_details_child_name_tv =  baseViewHolder.getView(R.id.classification_details_child_name_tv);
        String name = classificationDetailsChildEntity.getName();
        classification_details_child_name_tv.setText(name);
        if(name.equals("官方认证")){
            classification_details_child_name_tv.setBackgroundResource(R.drawable.details_official_shape);
            classification_details_child_name_tv.setTextColor(Color.parseColor("#94412B"));
        }else if(name.equals("自行交易")||name.equals("只走担保")){
            classification_details_child_name_tv.setBackgroundResource(R.drawable.details_guarantee_shape);
            classification_details_child_name_tv.setTextColor(Color.parseColor("#B76947"));
        }else {
            classification_details_child_name_tv.setBackgroundResource(R.drawable.details_normal_shape);
            classification_details_child_name_tv.setTextColor(Color.parseColor("#999999"));
        }
    }
}
