
package com.example.new_application.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.new_application.R;
import com.example.new_application.bean.MineBannerEntity;
import com.example.new_application.utils.GlideLoadViewUtil;
import com.zhpan.bannerview.holder.ViewHolder;

public class BannerViewHolder implements ViewHolder<MineBannerEntity> {
    private ImageView mImageView;

    @Override
    public View createView(ViewGroup viewGroup, Context context, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.banner_layout, viewGroup, false);
        mImageView = view.findViewById(R.id.banner_imageView);
        return view;
    }

    @Override
    public void onBind(Context context, MineBannerEntity data, int position, int size) {
        GlideLoadViewUtil.LoadCornersView(context,data.getImage(),8,mImageView);
    }




}
