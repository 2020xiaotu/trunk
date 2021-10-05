package com.example.new_application.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.ChooseServerParentEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChooseServerParentAdapter extends BaseQuickAdapter<ChooseServerParentEntity, BaseViewHolder> {
    public ChooseServerParentAdapter(int layoutResId, @Nullable List<ChooseServerParentEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ChooseServerParentEntity chooseServerParentEntity) {
        TextView choose_server_count_tv = baseViewHolder.getView(R.id.choose_server_count_tv);
        ImageView choose_server_iv = baseViewHolder.getView(R.id.choose_server_iv);
        RecyclerView choose_server_child_recycler = baseViewHolder.getView(R.id.choose_server_child_recycler);
        baseViewHolder.setText(R.id.classification_title_tv,chooseServerParentEntity.getName());
        if(chooseServerParentEntity.isOpen()){
            choose_server_iv.setImageResource(R.drawable.sxfw_arrow_down);
            choose_server_child_recycler.setVisibility(View.VISIBLE);
        }else {
            choose_server_iv.setImageResource(R.drawable.sxfw_arrow_right);
            choose_server_child_recycler.setVisibility(View.GONE);
        }
        List<ChooseServerParentEntity.ChildListBean> childList = chooseServerParentEntity.getChildList();
        int count = 0;
        for (int i = 0; i < childList.size(); i++) {
            boolean check = childList.get(i).isCheck();
            if(check){
                count++;
            }
        }
        if(count==0){
            choose_server_count_tv.setVisibility(View.GONE);
        }else {
            choose_server_count_tv.setVisibility(View.VISIBLE);
            choose_server_count_tv.setText(count+"");
        }
        initCHilcRecycler(choose_server_child_recycler,childList,chooseServerParentEntity);
    }

    private void initCHilcRecycler(RecyclerView choose_server_child_recycler, List<ChooseServerParentEntity.ChildListBean> childList,ChooseServerParentEntity chooseServerParentEntity) {
        ChooseServerChildAdapter chooseServerChildAdapter = new ChooseServerChildAdapter(R.layout.choose_server_child_item, childList);
        choose_server_child_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        choose_server_child_recycler.setAdapter(chooseServerChildAdapter);
        chooseServerChildAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ChooseServerParentEntity.ChildListBean childListBean = childList.get(position);
                childListBean.setCheck(!childListBean.isCheck());
                chooseServerChildAdapter.notifyItemChanged(position);
                notifyItemChanged(getItemPosition(chooseServerParentEntity));
            }
        });
    }
}
