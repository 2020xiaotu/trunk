package com.example.red_pakege.rong_yun.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.red_pakege.R;
import com.example.red_pakege.rong_yun.model.PakegeDetailModel;
import com.example.red_pakege.util.BigDecimalUtil;
import com.example.red_pakege.util.GlideLoadViewUtil;
import com.example.red_pakege.widget.CommonAdapter;
import com.example.red_pakege.widget.CommonHolder;

import java.util.ArrayList;

public class PakegeDetailAdapter extends CommonAdapter<PakegeDetailAdapter.MyHolder, PakegeDetailModel> {
    Activity activity;
    public PakegeDetailAdapter(ArrayList<PakegeDetailModel> list, Activity activity) {
        super(list);
        this.activity = activity;
    }

    @Override
    public void handleViewHolder(MyHolder commonHolder, int position) {
        PakegeDetailModel itemModel = getItemModel(position);
        GlideLoadViewUtil.LoadCornersView(activity,itemModel.getImageUrl(),12,commonHolder.titleIv);//设置头像
        commonHolder.userNameTv.setText(itemModel.getUserName());//用户名
        //是否是庄家
        if(itemModel.isBank()){
            commonHolder.isZhuangJiaIv.setVisibility(View.VISIBLE);
        }else {
            commonHolder.isZhuangJiaIv.setVisibility(View.INVISIBLE);
        }
        String amount = itemModel.getAmount();
        int count = BigDecimalUtil. checkIsDoublePointTwo(amount);
        if(count ==1){//未开奖时,庄家item中的金额只有一个小数,此时在后面拼一个*
            commonHolder.amountTv.setText(amount+"*");//抢包金额
        }else{
            commonHolder.amountTv.setText(amount);//抢包金额
        }
        ImageView niujiIv = commonHolder.niujiIv;//牛几
        initNiuJiIv(itemModel, niujiIv);//牛几的判断和设置
        commonHolder.dateTv.setText(itemModel.getDate());
    }

    private void initNiuJiIv(PakegeDetailModel itemModel, ImageView niujiIv) {
        int niuji = itemModel.getNiuji();
        if(niuji==1){
            niujiIv.setImageResource(R.drawable.ic_cow_1);
        }else if(niuji==2){
            niujiIv.setImageResource(R.drawable.ic_cow_2);
        }else if(niuji==3){
            niujiIv.setImageResource(R.drawable.ic_cow_3);
        }
        else if(niuji==4){
            niujiIv.setImageResource(R.drawable.ic_cow_4);
        }
        else if(niuji==5){
            niujiIv.setImageResource(R.drawable.ic_cow_5);
        }
        else if(niuji==6){
            niujiIv.setImageResource(R.drawable.ic_cow_6);
        }
        else if(niuji==7){
            niujiIv.setImageResource(R.drawable.ic_cow_7);
        }
        else if(niuji==8){
            niujiIv.setImageResource(R.drawable.ic_cow_8);
        }
        else if(niuji==9){
            niujiIv.setImageResource(R.drawable.ic_cow_9);
        }
        else if(niuji==10){
            niujiIv.setImageResource(R.drawable.ic_cow_10);
        }else{//倒计时未结束时,庄家牛几的显示
            niujiIv.setImageResource(R.drawable.ic_cow_0);
        }
    }

    @Override
    public int getLayOutRes() {
        return R.layout.red_pakege_detail_item_layout;
    }

    public static class MyHolder extends CommonHolder {
        ImageView titleIv;
        TextView userNameTv;
        ImageView isZhuangJiaIv;
        TextView amountTv;
        ImageView niujiIv;
        TextView dateTv;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            titleIv=itemView.findViewById(R.id.saolei_pakege_detail_item_title_iv);
            userNameTv=itemView.findViewById(R.id.saolei_pakege_detail_item_username_tv);
            isZhuangJiaIv=itemView.findViewById(R.id.is_zhuangjia_iv);
            amountTv=itemView.findViewById(R.id.pakege_detail_item_amount_tv);
            niujiIv=itemView.findViewById(R.id.pakege_detail_item_niuji_iv);
            dateTv=itemView.findViewById(R.id.sao_lei_pakege_detail_item_time_tv);
        }
    }
}
