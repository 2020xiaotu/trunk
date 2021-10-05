package com.example.new_application.ui.activity.mine_fragment_activity.mine_foolow_activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;

import com.androidkun.xtablayout.XTabLayout;
import com.example.new_application.R;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.utils.CommonToolbarUtil;
import com.example.new_application.utils.NoScrollViewPager;

import java.util.ArrayList;

import butterknife.BindView;

public class MineFollowActivity extends BaseActivity {

    @BindView(R.id.mine_follow_tab)
    XTabLayout mine_follow_tab;
    @BindView(R.id.mine_follow_viewPager)
    NoScrollViewPager mine_follow_viewPager;
    ArrayList<String>titleList = new ArrayList<>();
    MineFollowTabAdapter mineFollowTabAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_mine_follow;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        CommonToolbarUtil.initToolbar(this,"我的关注");
        initTab();
    }

    private void initTab() {
        titleList.add("收藏的帖子");
        titleList.add("收藏的店铺");
        mineFollowTabAdapter = new MineFollowTabAdapter(getSupportFragmentManager());
        mine_follow_viewPager.setAdapter(mineFollowTabAdapter);
        mine_follow_tab.setupWithViewPager(mine_follow_viewPager);
    }

    class MineFollowTabAdapter extends FragmentPagerAdapter {
        public MineFollowTabAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return MineFollowFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return titleList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }


}