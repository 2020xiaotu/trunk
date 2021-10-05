package com.example.red_pakege.util;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.red_pakege.R;
import com.example.red_pakege.widget.GlideCircleTransformWithBorder;
import com.example.red_pakege.widget.GlideRetcTransformWithBorder;

/**
 * created  by ganzhe on 2019/11/18.
 */
public class GlideLoadViewUtil {

    /**
     *glide 加载带边框的圆形图
     * @param context
     * @param imgUrl
     * @param borderWidth 边框大小
     * @param color  边框颜色
     * @param imageView
     */
    public static void LoadCircleBorderView(Context context, String imgUrl,int borderWidth, int color,ImageView imageView){
        Glide.with(context)
                .load(imgUrl)
                .circleCrop()
                .transform(new GlideCircleTransformWithBorder(context,borderWidth,color))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void LoadRetcCirBorderView(Context context,String imgUrl,int borderWidth,int Corner,int color,ImageView imageView){
        Glide.with(context)
                .load(imgUrl)
                .transform(new GlideRetcTransformWithBorder(Corner, 0))
                .into(imageView);

    }
    //Glide加载圆角矩形图片
    public static void LoadRetcCirView(Context context,String imgUrl,int Corner,ImageView imageView){
        //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(Corner);
        //通过RequestOptions扩展功能
        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners)/*.placeholder(R.drawable.ucrop_ic_next)*/;
        Glide.with(context)
                .load(imgUrl)
                .apply(options)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void LoadRetcCirView(Context context,int drawable,int Corner,ImageView imageView){
        //设置图片圆角角度
        RoundedCorners roundedCorners= new RoundedCorners(Corner);
        //通过RequestOptions扩展功能
        RequestOptions options=RequestOptions.bitmapTransform(roundedCorners)/*.placeholder(R.drawable.ucrop_ic_next)*/;
        Glide.with(context)
                .load(drawable)
                .apply(options)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    //glide加载圆形图
    public static void LoadCircleView(Context context,String imgUrl,ImageView imageView){
        Glide.with(context)
                .load(imgUrl)
                .circleCrop()
                .error(R.drawable.title_image)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }


//    ----------------------------------------------------------------------------------------------------------------------------------------------



    public static void LoadCornersView(Context context,String imgUrl,int corners,ImageView imageView){
        Glide.with(context)
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(corners)))
                .error(R.drawable.error_image)
                .into(imageView);
    }


    public static void FLoadCornersView(Fragment fragment, String imgUrl, int corners, ImageView imageView){
        Glide.with(fragment)
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(corners)))
                .error(R.drawable.ic_bank_fail)
                .into(imageView);
    }

    public static void LoadNormalView(Context context,String imgUrl,ImageView imageView){
        Glide.with(context)
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
    public static void FLoadNormalView(Context context,String imgUrl,ImageView imageView){
        Glide.with(context)
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
