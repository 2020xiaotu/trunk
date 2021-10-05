package com.example.new_application.adapter;

import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;

import java.util.List;

public class HomeCenterZSQAdapter extends BaseQuickAdapter<Boolean, BaseViewHolder> {


    public HomeCenterZSQAdapter(int layoutResId, @Nullable List<Boolean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Boolean isSelector) {
        TextView zsq_tv = helper.getView(R.id.zsq_tv);
            if (isSelector){
                zsq_tv.setBackgroundColor(Color.parseColor("#FED700"));
            }else {
                zsq_tv.setBackgroundColor(Color.parseColor("#ECECEC"));
            }
        }


}
