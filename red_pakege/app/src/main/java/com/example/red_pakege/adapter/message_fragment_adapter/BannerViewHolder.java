package com.example.red_pakege.adapter.message_fragment_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.red_pakege.model.BannerData;
import com.example.red_pakege.R;
import com.example.red_pakege.model.MessageFragmentMedel;
import com.example.red_pakege.widget.CommonAdapter;
import com.example.red_pakege.widget.CommonHolder;
import com.zhpan.bannerview.holder.ViewHolder;

import java.util.ArrayList;

/*
首页banner的视图viewHolder
*/
public class BannerViewHolder implements ViewHolder<BannerData> {
private ImageView mImageView ;
@Override
public View createView(ViewGroup viewGroup, Context context, int position) {
    View view = LayoutInflater.from(context).inflate(R.layout.banner_layout, viewGroup, false);
    mImageView = view.findViewById(R.id.banner_imageView);
    return view;
}

@Override
public void onBind(Context context, BannerData data, int position, int size) {
    Glide.with(context)
            .load(data.getUrl())
            .apply(RequestOptions.bitmapTransform(new RoundedCorners(20)))
            .skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(mImageView);
}

    public static class MessageFragmentAdapter extends CommonAdapter<MessageFragmentAdapter.MyHolder, MessageFragmentMedel> {
        Fragment fragment;

        public MessageFragmentAdapter(ArrayList<MessageFragmentMedel> list, Fragment fragment) {
            super(list);
            this.fragment = fragment;
        }

        @Override
        public void handleViewHolder(MyHolder commonHolder, int position) {
            MessageFragmentMedel itemModel = getItemModel(position);
            commonHolder.imageView.setImageResource(R.drawable.online_kefu);
            commonHolder.contentTv.setText(itemModel.getContent());
            commonHolder.titleTv.setText(itemModel.getTitle());
            View itemView = commonHolder.itemView;
            itemView.setTag(position);
            itemView.setOnClickListener(new View.OnClickListener() {
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
            return R.layout.message_fragment_recycle_item;
        }

        public static class MyHolder extends CommonHolder {
            ImageView imageView;
            TextView titleTv;
            TextView contentTv;
            public MyHolder(@NonNull View itemView) {
                super(itemView);
                imageView=itemView.findViewById(R.id.message_item_image);
                titleTv=itemView.findViewById(R.id.message_item_title);
                contentTv=itemView.findViewById(R.id.message_item_content);
            }
        }
    }
}
