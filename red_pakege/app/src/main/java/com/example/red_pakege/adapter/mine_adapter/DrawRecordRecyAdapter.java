package com.example.red_pakege.adapter.mine_adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.red_pakege.R;

import java.util.List;

public class DrawRecordRecyAdapter extends BaseQuickAdapter<String,BaseViewHolder> {


    public DrawRecordRecyAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tv_item_recy_drawrecord,item);
    }


}
