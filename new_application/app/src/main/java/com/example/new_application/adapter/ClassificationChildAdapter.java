package com.example.new_application.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.ClassificationParentEntity;
import com.example.new_application.utils.GlideLoadViewUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ClassificationChildAdapter extends BaseQuickAdapter<ClassificationParentEntity.ChildListBean, BaseViewHolder> {
    public ClassificationChildAdapter(int layoutResId, @Nullable List<ClassificationParentEntity.ChildListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, ClassificationParentEntity.ChildListBean childListBean) {
        LinearLayout classification_child_wrap_linear = baseViewHolder.getView(R.id.classification_child_wrap_linear);
        ImageView classification_child_iv = baseViewHolder.getView(R.id.classification_child_iv);
        baseViewHolder.setText(R.id.classification_child_name_tv,childListBean.getName());
        baseViewHolder.setText(R.id.classification_child_num_tv,childListBean.getPostNumber());
        GlideLoadViewUtil.LoadTitleView(getContext(),childListBean.getPicture(),classification_child_iv);
        classification_child_wrap_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(view,getItemPosition(childListBean));
                }
            }
        });

    }

    public interface OnItemClickListener {
        void onItemClick(View view,int position);
    }
    OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
