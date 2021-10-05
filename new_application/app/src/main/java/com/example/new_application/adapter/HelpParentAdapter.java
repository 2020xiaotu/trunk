package com.example.new_application.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.HelpParentEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HelpParentAdapter extends BaseQuickAdapter<HelpParentEntity, BaseViewHolder> {
    public HelpParentAdapter(int layoutResId, @Nullable List<HelpParentEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HelpParentEntity helpParentEntity) {
        baseViewHolder.setText(R.id.help_center_parent_name_tv,helpParentEntity.getName());
        baseViewHolder.setImageResource(R.id.help_parent_iv,helpParentEntity.getDrawableId());
        ImageView tip_iv = baseViewHolder.getView(R.id.tip_iv);
        RecyclerView help_center_child_recycler =baseViewHolder.getView(R.id.help_center_child_recycler);
        if(helpParentEntity.isOpen()){
            help_center_child_recycler.setVisibility(View.VISIBLE);
            tip_iv.setImageResource(R.drawable.cjwt_shang_fwsrz);
        }else {
            help_center_child_recycler.setVisibility(View.GONE);
            tip_iv.setImageResource(R.drawable.cjwt_xia_fwsrz);
        }
        initChildRecycler(help_center_child_recycler,helpParentEntity);
    }

    private void initChildRecycler(RecyclerView help_center_child_recycler, HelpParentEntity helpParentEntity) {
        HelpChildAdapter helpChildAdapter = new HelpChildAdapter(R.layout.help_child_item,helpParentEntity.getHelpChildEntityArrayList());
        help_center_child_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        help_center_child_recycler.setAdapter(helpChildAdapter);
    }
}
