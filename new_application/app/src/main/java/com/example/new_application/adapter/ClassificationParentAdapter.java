package com.example.new_application.adapter;
import android.view.View;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.ClassificationParentEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class ClassificationParentAdapter extends BaseQuickAdapter<ClassificationParentEntity, BaseViewHolder> {
    public ClassificationParentAdapter(int layoutResId, @Nullable List<ClassificationParentEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ClassificationParentEntity classificationParentEntity) {
        baseViewHolder.setText(R.id.classification_parent_name_tv,classificationParentEntity.getName());
        RecyclerView classification_child_recycler= baseViewHolder.getView(R.id.classification_child_recycler);
        initChildRecycler(classification_child_recycler,classificationParentEntity);
    }

    private void initChildRecycler(RecyclerView classification_child_recycler,ClassificationParentEntity classificationParentEntity) {
        ClassificationChildAdapter classificationChildAdapter = new ClassificationChildAdapter(R.layout.classification_child_recycler_item,classificationParentEntity.getChildList());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        classification_child_recycler.setLayoutManager(gridLayoutManager);
        classification_child_recycler.setAdapter(classificationChildAdapter);
        classificationChildAdapter.setOnItemClickListener(new ClassificationChildAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
               if(onItemClickListener !=null){
                   onItemClickListener.onItemClick(view,getItemPosition(classificationParentEntity),position);
               }
            }
        });
    }
    public interface OnItemClickListener {
        void onItemClick(View view,int parentPosition,int childPosition);
    }
    OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
