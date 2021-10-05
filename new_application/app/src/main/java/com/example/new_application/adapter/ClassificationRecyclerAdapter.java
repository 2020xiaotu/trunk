package com.example.new_application.adapter;

import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.ClassificationEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ClassificationRecyclerAdapter extends BaseQuickAdapter<ClassificationEntity, BaseViewHolder> {
    public ClassificationRecyclerAdapter(int layoutResId, @Nullable List<ClassificationEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ClassificationEntity classificationEntity) {
        boolean check = classificationEntity.isCheck();
        LinearLayout classification_item_linear = baseViewHolder.getView(R.id.classification_item_linear);
        TextView classification_item_name_tv = baseViewHolder.getView(R.id.classification_item_name_tv);
        TextView classification_item_num_tv = baseViewHolder.getView(R.id.classification_item_num_tv);
        classification_item_name_tv.setText(classificationEntity.getName());
        classification_item_num_tv.setText(classificationEntity.getNum());
        if(check){
            /**
             * 选中
             */
            classification_item_name_tv.setTextColor(Color.parseColor("#FFB300"));
            classification_item_num_tv.setTextColor(Color.parseColor("#FFB300"));
            classification_item_num_tv.setBackgroundResource(R.drawable.classification_num_check_shape);
            classification_item_linear.setBackgroundResource(R.drawable.classification_linear_check_shape);
        }else {
            /**
             * 未选中
             */
            classification_item_name_tv.setTextColor(Color.parseColor("#222222"));
            classification_item_num_tv.setTextColor(Color.parseColor("#999999"));
            classification_item_num_tv.setBackgroundResource(R.drawable.classification_num_uncheck_shape);
            classification_item_linear.setBackgroundResource(R.drawable.classification_linear_uncheck_shape);
        }

    }
}
