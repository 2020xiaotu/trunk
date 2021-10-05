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
import com.example.new_application.bean.HistoryEntity;
import com.example.new_application.utils.Utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;

public class ResourceHistoryAdapter extends BaseQuickAdapter<ClassificationDetailsEntity, BaseViewHolder> {
    public ResourceHistoryAdapter(int layoutResId, @Nullable List<ClassificationDetailsEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ClassificationDetailsEntity historyEntity) {
        TextView details_title_tv = baseViewHolder.getView(R.id.history_title_tv);
        TextView two_level_name_tv = baseViewHolder.getView(R.id.two_level_name_tv);
        TextView date_tv = baseViewHolder.getView(R.id.date_tv);
        ImageView jingxuan_iv = baseViewHolder.getView(R.id.jingxuan_iv);
        TextView price_tv = baseViewHolder.getView(R.id.price_tv);
        TextView details_remark_tv = baseViewHolder.getView(R.id.history_remark_tv);
        details_remark_tv.setText(historyEntity.getContent());
        details_title_tv.setText(historyEntity.getTitle());
        String dateStr = Utils.getDatePoor2(new Date().getTime(), Long.parseLong(historyEntity.getCreatedDate()));
        date_tv.setText(dateStr);
        two_level_name_tv.setText("【 "+historyEntity.getTwoLevelClassificationName()+" 】");
        RecyclerView classification_details_child_recycler = baseViewHolder.getView(R.id.history_child_recycler);
        initChildRecycler(classification_details_child_recycler,historyEntity);
        ImageView delete_selector_iv =   baseViewHolder.getView(R.id.delete_selector_iv);
        if(historyEntity.isShow()){
            delete_selector_iv.setVisibility(View.VISIBLE);
        }else {
            delete_selector_iv.setVisibility(View.GONE);
        }
        if(historyEntity.isCheck()){
            delete_selector_iv.setImageResource(R.drawable.history_check_s);
        }else {
            delete_selector_iv.setImageResource(R.drawable.history_check_n);
        }
        String userType = historyEntity.getUserType();
        if(userType.equals("1")){
            //需求
            jingxuan_iv.setImageResource(R.drawable.x_icon);
        }else {
            jingxuan_iv.setImageResource(R.drawable.g_icon);
        }
        String priceType = historyEntity.getPriceType();
        if(priceType.equals("3")){
            price_tv.setText("可议价");
        }else {
            price_tv.setText("¥"+historyEntity.getPrice());
        }


    }

    private void initChildRecycler(RecyclerView classification_details_child_recycler, ClassificationDetailsEntity historyEntity) {
        ClassificationDetailsChildAdapter classificationDetailsChildAdapter = new ClassificationDetailsChildAdapter(R.layout.classification_details_child_item,historyEntity.getClassificationDetailsChildEntityArrayList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        classification_details_child_recycler.setLayoutManager(linearLayoutManager);
        classification_details_child_recycler.setAdapter(classificationDetailsChildAdapter);
    }

}
