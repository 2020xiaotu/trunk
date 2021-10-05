package com.example.new_application.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MineFeedbackRecycleAdapter extends BaseQuickAdapter<MineFeedbackModel, BaseViewHolder> {

    public MineFeedbackRecycleAdapter(int layoutResId, @Nullable List<MineFeedbackModel> data) {
        super(layoutResId, data);
    }



    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MineFeedbackModel recordsBean) {

        String time = recordsBean.getCreateTime();
        baseViewHolder.setText(R.id.feedback_time_tv,"反馈时间:"+ time);
        baseViewHolder.setText(R.id.feedback_type,"反馈类型:"+ recordsBean.getOpinionTypeExplain());
        baseViewHolder.setText(R.id.feedback_content,"反馈内容:"+ recordsBean.getOpinionContent());
        String reply = recordsBean.getReplyContent();
        if(StringMyUtil.isEmptyString(reply)){
            baseViewHolder.setText(R.id.gf_Reply_tv,"官方回复:暂无回复");
        }else {
            baseViewHolder.setText(R.id.gf_Reply_tv,"官方回复:"+reply);
        }
    }

}
