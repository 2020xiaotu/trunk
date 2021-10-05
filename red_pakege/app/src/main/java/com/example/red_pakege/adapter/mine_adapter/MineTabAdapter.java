package com.example.red_pakege.adapter.mine_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.red_pakege.R;
import com.example.red_pakege.fragment.mine_fragment.MineTabChild1Fragment;
import com.example.red_pakege.fragment.mine_fragment.MineTabChild2Fragment;
import com.example.red_pakege.fragment.mine_fragment.MineTabChild3Fragment;
import com.example.red_pakege.fragment.mine_fragment.MineTabChild4Fragment;

public class MineTabAdapter extends FragmentPagerAdapter {


    Context mContext;
    String[] titles;
    int icons[];
    int icons_n[];

    public MineTabAdapter(@NonNull FragmentManager fm, Context context, String[] titles, int[] icons,int[] icon_n) {
        super(fm);
        this.mContext = context;
        this.titles = titles;
        this.icons = icons;
        this.icons_n = icon_n;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position==0){
            fragment = MineTabChild1Fragment.newInstance();
        }else if (position==1){
            fragment = MineTabChild2Fragment.newInstance();
        }else if (position==2){
            fragment =MineTabChild3Fragment.newInstance();
        }else if (position==3){
            fragment = MineTabChild4Fragment.newInstance();
        }else {
            fragment = MineTabChild1Fragment.newInstance();
        }
        return fragment;

    }

    @Override
    public int getCount() {
        return null == titles ? 0: titles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    public View getTabView(int position){
        View v = LayoutInflater.from(mContext).inflate(R.layout.mine_tab_item_view, null);
        ImageView iv = v.findViewById(R.id.iv_mine_tabitem);
        TextView tv = v.findViewById(R.id.tv_mine_tabitem);
        iv.setImageResource(icons[position]);
        tv.setText(titles[position]);
        if (position == 0) {
            tv.setTextColor(mContext.getResources().getColor(R.color.white));
            iv.setImageResource(icons_n[0]);
            v.setBackground(mContext.getDrawable(R.drawable.bkg_pink_leftup_shape));
        }
        return v;
    }
}
