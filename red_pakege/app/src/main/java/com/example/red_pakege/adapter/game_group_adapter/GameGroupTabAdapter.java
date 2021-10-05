package com.example.red_pakege.adapter.game_group_adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.red_pakege.MyApplication;
import com.example.red_pakege.R;
import com.example.red_pakege.fragment.game_group_fragment.OtherGameFragment;
import com.example.red_pakege.fragment.game_group_fragment.RedPakegeGameFragment;

import java.time.temporal.ValueRange;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class GameGroupTabAdapter extends FragmentPagerAdapter {
    //    Context context;
    ArrayList<String>titles = new ArrayList<>();
    int icons[];
    private ArrayList<Integer>idList = new ArrayList<>();

    public GameGroupTabAdapter(@NonNull FragmentManager fm, /*Context context,*/  ArrayList<String>titles, int[] icons,ArrayList<Integer>idList) {
        super(fm);
//        this.context = context;
        this.titles = titles;
        this.icons = icons;
        this.idList = idList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment ;
        if(position==0){
            fragment=RedPakegeGameFragment.newInstance(position,idList.get(position));
 /*           Bundle bundle = new Bundle();
            bundle.putInt("position",position);
            fragment=new RedPakegeGameFragment();
            fragment.setArguments(bundle);*/
        }else {
            fragment=OtherGameFragment.newInstance(position,idList.get(position));
/*            Bundle bundle = new Bundle();
            bundle.putInt("position",position);
            fragment=new OtherGameFragment();
            fragment.setArguments(bundle);*/

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public View getTabView(int position){
        View v = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.game_group_tab_layout, null);
        GifImageView iv = v.findViewById(R.id.game_group_tab_iv);
        TextView tv = v.findViewById(R.id.game_group_tab_tv);
        iv.setBackgroundResource(icons[position]);
        tv.setText(titles.get(position));
        if (position == 0) {
            tv.setTextColor(v.getResources().getColor(R.color.tabSelecterColor));
        }
        return v;
    }
}
