package com.example.new_application.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.MeetProblemEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class MeetProblemAdapter extends BaseQuickAdapter<MeetProblemEntity, BaseViewHolder>  {


    public MeetProblemAdapter(int layoutResId, @Nullable List<MeetProblemEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MeetProblemEntity dataBean) {

        TextView problemTv = baseViewHolder.getView(R.id.meet_problem_tv);
        if(dataBean.getStatus()==1){
            problemTv.setSelected(true);
        }else {
            problemTv.setSelected(false);
        }
        problemTv.setText(dataBean.getExplain());
    }


}
