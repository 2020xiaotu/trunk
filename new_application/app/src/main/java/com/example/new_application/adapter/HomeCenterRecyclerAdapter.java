package com.example.new_application.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.HomeCenterEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HomeCenterRecyclerAdapter extends BaseQuickAdapter<HomeCenterEntity, BaseViewHolder>{

    List<HomeCenterEntity> mDataList;
    int totalPage = 0;

    public HomeCenterRecyclerAdapter(int layoutResId, @Nullable List<HomeCenterEntity> data) {
        super(layoutResId, data);
        mDataList = data;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeCenterEntity homeCenterEntity) {
        RecyclerView home_center_recycler =baseViewHolder.getView(R.id.home_center_recycler);
        HomeCenterRecyclerChildAdapter centerRecyclerChildAdapter = new HomeCenterRecyclerChildAdapter(R.layout.home_center_child_recycler_item,getChildList(getItemPosition(homeCenterEntity)));
        home_center_recycler.setLayoutManager(new GridLayoutManager(getContext(),2));
        home_center_recycler.setAdapter(centerRecyclerChildAdapter);

        centerRecyclerChildAdapter.setOnItemClickListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                int Xposition = baseViewHolder.getAdapterPosition()*4+position;
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(view,Xposition);
                }
            }
        });
    }

    private List<HomeCenterEntity> getChildList(int curPosition){
        List<HomeCenterEntity> mChildList;
        if (totalPage>curPosition+1){
            mChildList = mDataList.subList(curPosition*4,(curPosition+1)*4);
        }else if(totalPage==curPosition+1){
            mChildList = mDataList.subList(curPosition*4,mDataList.size());
        }else {
            mChildList = null;
        }
        return mChildList;

    }
    @Override
    public int getItemCount() {
        if (mDataList.size()%4==0){
            totalPage = mDataList.size()/4;
        }else {
            totalPage = mDataList.size()/4+1;
        }
        return totalPage;
    }
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    HomeCenterRecyclerAdapter.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(HomeCenterRecyclerAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
