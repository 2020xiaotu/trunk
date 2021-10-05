package com.example.red_pakege.adapter.game_group_adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.red_pakege.MyApplication;
import com.example.red_pakege.R;
import com.example.red_pakege.fragment.game_group_fragment.OtherGameFragment;
import com.example.red_pakege.model.OtherGameModel;
import com.example.red_pakege.util.GlideLoadViewUtil;
import com.example.red_pakege.widget.CommonAdapter;
import com.example.red_pakege.widget.CommonHolder;

import java.util.ArrayList;

public class OtherGameAdapter extends CommonAdapter<OtherGameAdapter.MyHolder, OtherGameModel> {
    OtherGameFragment otherGameFragment;

    public OtherGameAdapter(ArrayList<OtherGameModel> list, OtherGameFragment otherGameFragment) {
        super(list);
        this.otherGameFragment = otherGameFragment;
    }

    @Override
    public void handleViewHolder(MyHolder commonHolder, int position) {
        OtherGameModel itemModel = getItemModel(position);
        commonHolder.imageView.setImageResource(itemModel.getImageId());
//        GlideLoadViewUtil.FLoadCornersView(otherGameFragment,"http://pic27.nipic.com/20130313/9252150_092049419327_2.jpg",12,commonHolder.imageView);



    }

    @Override
    public int getLayOutRes() {
        return R.layout.other_game_recycle_item_layout;
    }

    public static class MyHolder extends CommonHolder {
        private ImageView imageView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.other_game_iv);
        }
    }
}
