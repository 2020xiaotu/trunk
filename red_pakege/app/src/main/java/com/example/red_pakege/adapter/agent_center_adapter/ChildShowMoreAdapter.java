package com.example.red_pakege.adapter.agent_center_adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.red_pakege.R;
import com.example.red_pakege.model.ChildShowMoreModel;
import com.example.red_pakege.widget.CommonAdapter;
import com.example.red_pakege.widget.CommonHolder;

import java.util.ArrayList;

public class ChildShowMoreAdapter extends CommonAdapter<ChildShowMoreAdapter.MyHolder, ChildShowMoreModel> {
    @Override
    public void handleViewHolder(MyHolder commonHolder, int position) {
        ChildShowMoreModel itemModel = getItemModel(position);
        String title = itemModel.getTitle();
        commonHolder.titieTv.setText(title);
        commonHolder.amountTv.setText(itemModel.getAmount());
        commonHolder.contentTv.setText(itemModel.getContent());
        commonHolder.amountRightTv.setText(itemModel.getAmountRight());
        commonHolder.contentRightTv.setText(itemModel.getContentRight());
        if(title.equals("充值奖励")|| title.equals("邀请会员完成充值")){
            commonHolder.imageView.setImageResource(R.drawable.ic_first);
            commonHolder.imageViewRight.setImageResource(R.drawable.ic_second);
        }else if(title.equals("发包与抢包满额奖励")){
            commonHolder.imageView.setImageResource(R.drawable.ic_send_count);
            commonHolder.imageViewRight.setImageResource(R.drawable.ic_grab_count);
        }else{
            commonHolder.imageView.setImageResource(R.drawable.ic_reward_work);
            commonHolder.imageViewRight.setImageResource(R.drawable.ic_second_charge);
        }
    }

    public ChildShowMoreAdapter(ArrayList<ChildShowMoreModel> list) {
        super(list);
    }

    @Override
    public int getLayOutRes() {
        return R.layout.child_show_more_recycle_item;
    }

    public static class MyHolder extends CommonHolder {
        TextView titieTv;
        ImageView imageView;
        TextView amountTv;
        TextView contentTv;
        ImageView imageViewRight;
        TextView amountRightTv;
        TextView contentRightTv;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            titieTv=itemView.findViewById(R.id.show_more_title_tv);
            imageView=itemView.findViewById(R.id.showmore_imageview);
            amountTv=itemView.findViewById(R.id.showmore_amount_tv);
            contentTv=itemView.findViewById(R.id.showmore_content_tv);
            imageViewRight=itemView.findViewById(R.id.showmore_right_imageview);
            amountRightTv=itemView.findViewById(R.id.showmore_amount_right_tv);
            contentRightTv=itemView.findViewById(R.id.showmore_right_content_tv);
        }
    }
}
