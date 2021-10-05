package com.example.new_application.adapter;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.ChooseServerParentEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChooseServerChildAdapter extends BaseQuickAdapter<ChooseServerParentEntity.ChildListBean, BaseViewHolder> {
    public ChooseServerChildAdapter(int layoutResId, @Nullable List<ChooseServerParentEntity.ChildListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ChooseServerParentEntity.ChildListBean childListBean) {
        ImageView choose_server_child_iv =baseViewHolder.getView(R.id.choose_server_child_iv);
        TextView choose_server_child_tv =baseViewHolder.getView(R.id.choose_server_child_tv);
        choose_server_child_tv.setText(childListBean.getName());
        if(childListBean.isCheck()){
            choose_server_child_iv.setImageResource(R.drawable.sxfw_check_y);
            choose_server_child_tv.setTextColor(Color.parseColor("#FF641B"));
        }else {
            choose_server_child_iv.setImageResource(R.drawable.sxfw_check_n);
            choose_server_child_tv.setTextColor(Color.parseColor("#999999"));
        }
    }
}
