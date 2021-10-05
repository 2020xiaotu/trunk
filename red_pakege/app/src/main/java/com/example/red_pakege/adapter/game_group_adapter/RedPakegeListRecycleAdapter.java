package com.example.red_pakege.adapter.game_group_adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.red_pakege.R;
import com.example.red_pakege.model.RedPakegeListModel;
import com.example.red_pakege.widget.CommonAdapter;
import com.example.red_pakege.widget.CommonHolder;

import java.util.ArrayList;

public class RedPakegeListRecycleAdapter extends CommonAdapter<RedPakegeListRecycleAdapter.MyHolder, RedPakegeListModel> {
    public RedPakegeListRecycleAdapter(ArrayList<RedPakegeListModel> list) {
        super(list);
    }

    @Override
    public void handleViewHolder(MyHolder commonHolder, int position) {
        RedPakegeListModel itemModel = getItemModel(position);
        commonHolder.tipTv.setText(itemModel.getTip());
        TextView nameTv = commonHolder.nameTv;
        nameTv.setText(itemModel.getName());
        View itemView = commonHolder.itemView;
        itemView.setTag(position);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
                }
            }
        });
    }

    @Override
    public int getLayOutRes() {
        return R.layout.red_pakege_list_recycle_item;
    }

    public static class MyHolder extends CommonHolder {
        ImageView imageView;
        TextView nameTv;
        TextView tipTv;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.red_pakege_list_gif_iv);
            nameTv=itemView.findViewById(R.id.red_pakege_list_item_name_tv);
            tipTv=itemView.findViewById(R.id.red_pakege_list_item_tip_tv);
        }
    }
}
