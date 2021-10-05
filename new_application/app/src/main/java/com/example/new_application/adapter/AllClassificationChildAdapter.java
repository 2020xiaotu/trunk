package com.example.new_application.adapter;

import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.AllTwoLevelEntity;
import com.example.new_application.bean.ClassificationParentEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AllClassificationChildAdapter extends BaseQuickAdapter<AllTwoLevelEntity, BaseViewHolder> {
    public AllClassificationChildAdapter(int layoutResId, @Nullable List<AllTwoLevelEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AllTwoLevelEntity allTwoLevelEntity) {
        TextView all_classification_child_name_tv = baseViewHolder.getView(R.id.all_classification_child_name_tv);
        all_classification_child_name_tv.setText(allTwoLevelEntity.getName());
        boolean check = allTwoLevelEntity.isCheck();
        if(check){
            all_classification_child_name_tv.setSelected(true);
        }else {
            all_classification_child_name_tv.setSelected(false);
        }
        all_classification_child_name_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(view,getItemPosition(allTwoLevelEntity));
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    AllClassificationChildAdapter.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(AllClassificationChildAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
