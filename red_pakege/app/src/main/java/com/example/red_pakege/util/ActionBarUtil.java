package com.example.red_pakege.util;

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.view.View;

import com.example.red_pakege.R;

public class ActionBarUtil {

    public static void initMainActionbar(Activity activity,View view){
        int[] colors = { activity.getResources().getColor(R.color.mStartColor),     activity.getResources().getColor(R.color.mEndColor)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
        view .setBackground(gradientDrawable);
        StatusBarUtil.setGradientColor(activity, view);
    };
}
