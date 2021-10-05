package com.example.new_application.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.ClassificationDetailsEntity;
import com.example.new_application.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;

public class ClassificationDetailsAdapter extends BaseQuickAdapter<ClassificationDetailsEntity, BaseViewHolder> {
    public ClassificationDetailsAdapter(int layoutResId, @Nullable List<ClassificationDetailsEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ClassificationDetailsEntity classificationDetailsEntity) {
        ImageView jingxuan_iv = baseViewHolder.getView(R.id.jingxuan_iv);
        TextView details_title_tv = baseViewHolder.getView(R.id.classification_details_title_tv);
        TextView date_tv = baseViewHolder.getView(R.id.date_tv);
        TextView two_level_name_tv = baseViewHolder.getView(R.id.two_level_name_tv);
        TextView price_tv = baseViewHolder.getView(R.id.price_tv);
        TextView ding_tv = baseViewHolder.getView(R.id.ding_tv);
        TextView details_remark_tv = baseViewHolder.getView(R.id.classification_details_remark_tv);
        String dateStr = Utils.getDatePoor2(new Date().getTime(), Long.parseLong(classificationDetailsEntity.getCreatedDate()));
        date_tv.setText(dateStr);
        details_remark_tv.setText(classificationDetailsEntity.getContent());
        two_level_name_tv.setText("【 "+classificationDetailsEntity.getTwoLevelClassificationName()+" 】");
        RecyclerView classification_details_child_recycler = baseViewHolder.getView(R.id.classification_details_child_recycler);
        details_title_tv.setText(classificationDetailsEntity.getTitle());
        String userType = classificationDetailsEntity.getUserType();
        if(userType.equals("1")){
          //需求
            jingxuan_iv.setImageResource(R.drawable.x_icon);
        }else {
            jingxuan_iv.setImageResource(R.drawable.g_icon);
        }
        String priceType = classificationDetailsEntity.getPriceType();
        if(priceType.equals("3")){
            price_tv.setText("可议价");
        }else {
            price_tv.setText("¥"+classificationDetailsEntity.getPrice());
        }
        initChildRecycler(classification_details_child_recycler,classificationDetailsEntity);
    }

    private void initChildRecycler(RecyclerView classification_details_child_recycler, ClassificationDetailsEntity classificationDetailsEntity) {
        ClassificationDetailsChildAdapter classificationDetailsChildAdapter = new ClassificationDetailsChildAdapter(R.layout.classification_details_child_item,classificationDetailsEntity.getClassificationDetailsChildEntityArrayList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        classification_details_child_recycler.setLayoutManager(linearLayoutManager);
        classification_details_child_recycler.setAdapter(classificationDetailsChildAdapter);
    }
}
