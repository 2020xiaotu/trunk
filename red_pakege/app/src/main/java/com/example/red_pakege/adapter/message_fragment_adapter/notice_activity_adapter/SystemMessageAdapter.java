package com.example.red_pakege.adapter.message_fragment_adapter.notice_activity_adapter;

import android.app.Activity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.red_pakege.R;
import com.example.red_pakege.model.SystemMessageModel;
import com.example.red_pakege.widget.CommonAdapter;
import com.example.red_pakege.widget.CommonHolder;

import java.util.ArrayList;

public class SystemMessageAdapter extends CommonAdapter<SystemMessageAdapter.MyHolder, SystemMessageModel> {
    Activity activity;
    int openPositon=0;
    /**
     * 标记展开的item
     */
//    private int opened = -1;
    public SystemMessageAdapter(ArrayList<SystemMessageModel> list, Activity activity) {
        super(list);
        this.activity = activity;
    }

    @Override
    public void handleViewHolder(MyHolder commonHolder, int position) {
        SystemMessageModel itemModel = getItemModel(position);
        commonHolder.titleTv.setText(itemModel.getTitle());
        commonHolder.contentTv.setText(itemModel.getContent());
        //TODO  DataFormat (后台可能给时间戳)
        commonHolder.timeTv.setText(itemModel.getTime());
        commonHolder.titleLinear.setTag(position);
        ImageView titleIv = commonHolder.titleIv;
        if(itemModel.getStatus()==1){
            //status 为1时,将图标旋转
            Animation animation = AnimationUtils.loadAnimation(activity, R.anim.img_rotate_start_anim);
            titleIv.startAnimation(animation);
            animation.setFillAfter(true);//动画结束后,保持结束时的角度
            commonHolder.spliteTv.setVisibility(View.VISIBLE);
            commonHolder.contentTv.setVisibility(View.VISIBLE);
        }else {
            Animation animation = AnimationUtils.loadAnimation(activity, R.anim.img_rotate_end_anim);
            titleIv.startAnimation(animation);
            animation.setFillAfter(true);//动画结束后,保持结束时的角度
            commonHolder.spliteTv.setVisibility(View.GONE);
            commonHolder.contentTv.setVisibility(View.GONE);
        }
        commonHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //没有展开详情时,展开当前点击的item详情 并收起之前展开的item
                if(itemModel.getStatus()==0){
                    for (int i = 0; i < list.size(); i++) {
                        SystemMessageModel systemMessageModel = list.get(i);
                        if(i==commonHolder.getAdapterPosition()){
                            systemMessageModel.setStatus(1);
                            notifyItemChanged(i);
                        }else {
                            if(systemMessageModel.getStatus()==1){
                                systemMessageModel.setStatus(0);
                                notifyItemChanged(i);
                            }
                        }
                    }
                }else {
                    //当前点击的item详情已经展开时,收起当前item的详情
                    for (int i = 0; i < list.size(); i++) {
                        SystemMessageModel systemMessageModel = list.get(i);
                        if(systemMessageModel.getStatus()==1){
                            systemMessageModel.setStatus(0);
                            notifyItemChanged(i);
                        }
                    }
                }
//            notifyDataSetChanged();
            }
        });
    }



    @Override
    public int getLayOutRes() {
        return R.layout.system_message_recycle_item;
    }

    public static class MyHolder extends CommonHolder {
        LinearLayout titleLinear;
        TextView titleTv;
        ImageView titleIv;
        TextView contentTv;
        TextView timeTv;
        TextView spliteTv;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            titleLinear=itemView.findViewById(R.id.system_message_title_linear);
            titleTv=itemView.findViewById(R.id.system_message_item_title_tv);
            titleIv=itemView.findViewById(R.id.system_message_title_iv);
            contentTv=itemView.findViewById(R.id.system_message_content_tv);
            timeTv=itemView.findViewById(R.id.system_message_time_tv);
            spliteTv=itemView.findViewById(R.id.system_content_spite_tv);
        }
    }
}
