package com.example.new_application.adapter;

import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.AllOneLevelEntity;
import com.example.new_application.bean.AllTwoLevelEntity;
import com.example.new_application.bean.ClassificationParentEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AllClassificationParentAdapter extends BaseQuickAdapter<AllOneLevelEntity, BaseViewHolder> {
    public AllClassificationParentAdapter(int layoutResId, @Nullable List<AllOneLevelEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, AllOneLevelEntity allOneLevelEntity) {
        baseViewHolder.setText(R.id.all_classification_parent_name_tv,allOneLevelEntity.getName());
        RecyclerView classification_child_recycler = baseViewHolder.getView(R.id.all_classification_child_recycler);
        boolean open = allOneLevelEntity.isOpen();
        if(open){
            classification_child_recycler.setVisibility(View.VISIBLE);
            baseViewHolder.setImageResource(R.id.arrow_down_iv,R.drawable.sxfw_arrow_down);
        }else {
            classification_child_recycler.setVisibility(View.GONE);
            baseViewHolder.setImageResource(R.id.arrow_down_iv,R.drawable.sxfw_arrow_right);
        }

        initChildRecycler(allOneLevelEntity,classification_child_recycler);
    }

    private void initChildRecycler(AllOneLevelEntity allOneLevelEntity,RecyclerView classification_child_recycler) {
        ArrayList<AllTwoLevelEntity> childList = allOneLevelEntity.getChildList();
        AllClassificationChildAdapter allClassificationChildAdapter = new AllClassificationChildAdapter(R.layout.all_classification_child_item, childList);
        classification_child_recycler.setLayoutManager(new GridLayoutManager(getContext(),3));
        classification_child_recycler.setAdapter(allClassificationChildAdapter);
        allClassificationChildAdapter.setOnItemClickListener(new AllClassificationChildAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                for (int i = 0; i < childList.size(); i++) {
                    childList.get(i).setCheck(false);
                }
                childList.get(position).setCheck(true);
                allClassificationChildAdapter.notifyDataSetChanged();

                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(view,getItemPosition(allOneLevelEntity),position);
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(View view,int parentPosition,int childPosition);
    }
    AllClassificationParentAdapter.OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(AllClassificationParentAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
