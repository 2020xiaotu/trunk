package com.example.red_pakege.rong_yun.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.red_pakege.R;
import com.example.red_pakege.model.SaoLeiModel;
import com.example.red_pakege.rong_yun.model.PakegeDetailModel;
import com.example.red_pakege.util.BigDecimalUtil;
import com.example.red_pakege.util.GlideLoadViewUtil;
import com.example.red_pakege.widget.CommonAdapter;
import com.example.red_pakege.widget.CommonHolder;

import java.util.ArrayList;

public class SaoLeiPakegeDetailAdapter extends CommonAdapter<SaoLeiPakegeDetailAdapter.MyHolder, SaoLeiModel> {
        Context context;

    public SaoLeiPakegeDetailAdapter(ArrayList<SaoLeiModel> list, Context context) {
        super(list);
        this.context = context;
    }



    @Override
    public void handleViewHolder(MyHolder commonHolder, int position) {
        SaoLeiModel itemModel = getItemModel(position);
        GlideLoadViewUtil.LoadCircleView(context,itemModel.getQbImage(),commonHolder.titleIv);
        commonHolder.dateTv.setText(itemModel.getCreatedDate());
        commonHolder.userNameTv.setText(itemModel.getQbName());
        commonHolder.amountTv.setText(itemModel.getAmount());
        GlideLoadViewUtil.LoadCircleView(context,itemModel.getQbImage(),commonHolder.titleIv);
        if(itemModel.isBoom()){
            commonHolder.boomIv.setVisibility(View.VISIBLE);
        }else {
            commonHolder.boomIv.setVisibility(View.GONE);
        }
        if(itemModel.isMySelf()){
            //TODO 设置mySelf的标识
        }

        if(itemModel.isBestLuck()){
            commonHolder.bestLuckIv.setVisibility(View.VISIBLE);
        }else {
            commonHolder.bestLuckIv.setVisibility(View.GONE);
        }
    }


    @Override
    public int getLayOutRes() {
        return R.layout.saolei_red_pakege_detail_item_layout;
    }

    public static class MyHolder extends CommonHolder {
        ImageView titleIv;
        TextView userNameTv;
        TextView amountTv;
        TextView dateTv;
        ImageView boomIv;
        ImageView bestLuckIv;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            titleIv=itemView.findViewById(R.id.saolei_pakege_detail_item_title_iv);
            userNameTv=itemView.findViewById(R.id.saolei_pakege_detail_item_username_tv);
            amountTv=itemView.findViewById(R.id.pakege_detail_item_amount_tv);
            dateTv=itemView.findViewById(R.id.sao_lei_pakege_detail_item_time_tv);
            boomIv=itemView.findViewById(R.id.boom_iv);
            bestLuckIv=itemView.findViewById(R.id.best_luck_iv);
        }
    }
}
