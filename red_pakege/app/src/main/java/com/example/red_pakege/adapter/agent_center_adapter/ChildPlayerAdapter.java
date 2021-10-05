package com.example.red_pakege.adapter.agent_center_adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.red_pakege.R;
import com.example.red_pakege.model.ChildPlayerModel;
import com.example.red_pakege.widget.CommonAdapter;
import com.example.red_pakege.widget.CommonHolder;

import java.util.ArrayList;

public class ChildPlayerAdapter extends CommonAdapter<ChildPlayerAdapter.MyHolder, ChildPlayerModel> {
    Context context;

    public ChildPlayerAdapter(ArrayList<ChildPlayerModel> list, Context context) {
        super(list);
        this.context = context;
    }

    @Override
    public void handleViewHolder(MyHolder commonHolder, int position) {
        ChildPlayerModel itemModel = getItemModel(position);
        if(position==0){
            commonHolder.backgrundTv.setVisibility(View.VISIBLE);
        }else {
            commonHolder.backgrundTv.setVisibility(View.GONE);
        }
        commonHolder.agentNumTv.setText(itemModel.getAgentNum());
        commonHolder.playerNumTv.setText(itemModel.getPlayerNum());
        commonHolder.yongjinTv.setText(itemModel.getYongjinAmount());
        commonHolder.nameTv.setText(itemModel.getName());
        commonHolder.idtv.setText("ID:"+itemModel.getId());
        commonHolder.showMoreTv.setTag(position);
        commonHolder.showMoreTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(view, (Integer) view.getTag());
                }
            }
        });
    }

    @Override
    public int getLayOutRes() {
        return R.layout.child_player_recycke_item;
    }

    public static class MyHolder extends CommonHolder {
        ImageView titleIv;
        TextView nameTv;
        TextView idtv;
        ImageView sexIconIv;
        TextView showMoreTv;
        TextView agentNumTv;
        TextView playerNumTv;
        TextView yongjinTv;
        TextView backgrundTv;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            titleIv=itemView.findViewById(R.id.child_title_iv);
            nameTv=itemView.findViewById(R.id.child_name_tv);
            idtv=itemView.findViewById(R.id.child_id_tv);
            sexIconIv=itemView.findViewById(R.id.child_sex_icon_iv);
            showMoreTv=itemView.findViewById(R.id.child_recycle_item_showMore_tv);
            agentNumTv=itemView.findViewById(R.id.child_agent_num_tv);
            playerNumTv=itemView.findViewById(R.id.child_player_num_tv);
            yongjinTv=itemView.findViewById(R.id.child_yongjin_amount_tv);
            backgrundTv=itemView.findViewById(R.id.child_backgroup_linear);
        }
    }
}