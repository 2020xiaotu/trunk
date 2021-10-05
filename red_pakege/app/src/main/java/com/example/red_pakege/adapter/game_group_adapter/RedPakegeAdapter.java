package com.example.red_pakege.adapter.game_group_adapter;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.red_pakege.R;
import com.example.red_pakege.activity.message_fragment_activity.OnLineKefuActivity;
import com.example.red_pakege.fragment.game_group_fragment.RedPakegeGameFragment;
import com.example.red_pakege.model.RedPakegeGameModel;
import com.example.red_pakege.widget.CommonAdapter;
import com.example.red_pakege.widget.CommonHolder;


import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class RedPakegeAdapter extends CommonAdapter<RedPakegeAdapter.MyHolder, RedPakegeGameModel> {
    RedPakegeGameFragment fragment;

    public RedPakegeAdapter(ArrayList<RedPakegeGameModel> list, RedPakegeGameFragment fragment) {
        super(list);
        this.fragment = fragment;
    }

    @Override
    public void handleViewHolder(MyHolder commonHolder, int position) {
        RedPakegeGameModel itemModel = getItemModel(position);
        //TODO 加载图片
        commonHolder.nametv.setText(itemModel.getGameName());
        commonHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 跳转游戏规则
                fragment.getContext().startActivity(new Intent(fragment.getContext(), OnLineKefuActivity.class));
            }
        });
        TextView intoGametv = commonHolder.intoGametv;
        intoGametv.setTag(position);
        intoGametv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(v, (Integer) v.getTag(

                    ));
                }
            }
        });
    }

    @Override
    public int getLayOutRes() {
        return R.layout.red_pakege_recycle_item_layout;
    }

    public static class MyHolder extends CommonHolder {
        GifImageView imageView;
        TextView nametv;
        TextView intoGametv;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView =itemView.findViewById(R.id.red_pakege_item_gif_iv);
            nametv=itemView.findViewById(R.id.red_pakege_game_name_tv);
            intoGametv=itemView.findViewById(R.id.red_pakege_into_game_tv);
        }
    }
}
