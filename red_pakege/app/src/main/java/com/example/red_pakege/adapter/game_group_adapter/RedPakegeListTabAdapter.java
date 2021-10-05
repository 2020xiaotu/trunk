package com.example.red_pakege.adapter.game_group_adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.example.red_pakege.MyApplication;
import com.example.red_pakege.R;
import com.example.red_pakege.activity.red_game_activity.RedPakegeListFragment;
import java.util.ArrayList;
import pl.droidsonroids.gif.GifImageView;

public class RedPakegeListTabAdapter extends FragmentStatePagerAdapter {
    ArrayList<String> titleList = new ArrayList<>();
    ArrayList<Integer> icons = new ArrayList<>();
    ArrayList<Integer>typeIdlist = new ArrayList<>();
    private int defaultId;
    private int parentId;
    public RedPakegeListTabAdapter(@NonNull FragmentManager fm, ArrayList<String> titleList, ArrayList<Integer> icons,ArrayList<Integer>typeIdlist,int defaultId,int parentId) {
        super(fm);
        this.titleList = titleList;
        this.icons = icons;
        this.typeIdlist = typeIdlist;
        this.defaultId = defaultId;
        this.parentId = parentId;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return RedPakegeListFragment.newInstance(position,typeIdlist.get(position),parentId);
    }

    @Override
    public int getCount() {
        return icons.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position<titleList.size()){
            return titleList.get(position);
        }
        return "";
    }
    public View getTabView(int position,int selectorId){
        View v = LayoutInflater.from(MyApplication.getInstance()).inflate(R.layout.game_group_tab_layout, null);
        GifImageView iv = v.findViewById(R.id.game_group_tab_iv);
        TextView tv = v.findViewById(R.id.game_group_tab_tv);
        iv.setBackgroundResource(icons.get(position));
        tv.setText(titleList.get(position));
        if (selectorId == defaultId) {
            tv.setTextColor(v.getResources().getColor(R.color.tabSelecterColor));
        }
        return v;
    }
}
