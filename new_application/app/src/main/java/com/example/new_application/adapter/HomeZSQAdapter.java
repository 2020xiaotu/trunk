package com.example.new_application.adapter;

import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;

import java.util.List;

public class HomeZSQAdapter extends BaseQuickAdapter<Boolean, BaseViewHolder> {
    public enum  SelectorColor{
        BULE,
        WHITE;
    };
    SelectorColor selectorColor;
    public HomeZSQAdapter(int layoutResId, @Nullable List<Boolean> data, SelectorColor selectorColor) {
        super(layoutResId, data);
        this.selectorColor = selectorColor;
    }

    @Override
    protected void convert(BaseViewHolder helper, Boolean item) {
        TextView tv = helper.getView(R.id.home_aty_zsq_tv);
        if(selectorColor == SelectorColor.WHITE){
            if (item){
                tv.setBackground(getContext().getResources().getDrawable(R.drawable.seleter_check));
            }else {
                tv.setBackground(getContext().getResources().getDrawable(R.drawable.seleter_uncheck));
            }
        }else {
            if (item){
                tv.setBackground(getContext().getResources().getDrawable(R.drawable.seleter_bule_check));
            }else {
                tv.setBackground(getContext().getResources().getDrawable(R.drawable.seleter_uncheck));
            }
        }

    }
}
