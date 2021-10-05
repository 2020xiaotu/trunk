package com.example.red_pakege.activity.message_fragment_activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.fragment.notice_fragment.HbNoticeFragment;
import com.example.red_pakege.fragment.notice_fragment.SystemMessageFragment;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.R;
import com.example.red_pakege.util.MemoryLeakUtil;
import com.google.android.material.tabs.TabLayout;
public class NoticeActivity extends BaseActivity implements TabLayout.BaseOnTabSelectedListener, View.OnClickListener {
    int[] icons ={R.drawable.system_message,R.drawable.notice};
    String[] titles ={"系统消息","平台公告"};
    private TabLayout tabLayout;
    private NoticeTabAdapter mTabAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private ImageView actionLeftIv;
    private ImageView actionRightIv;


    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_notice;
    }

    @Override
    protected void initView() {
        super.initView();
        bindView();
        initTab();
        ActionBarUtil.initMainActionbar(this,toolbar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //华为inputMethodManager内存泄漏
        MemoryLeakUtil.fixLeak(this);
    }

    private void initTab() {
        mTabAdapter=new NoticeTabAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mTabAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < icons.length; i++) {
            //自定义tab item布局
            tabLayout.getTabAt(i).setCustomView(mTabAdapter.getTabView(i));
        }
        tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    protected void initClick() {
        super.initClick();
        actionLeftIv.setOnClickListener(this);
        actionRightIv.setOnClickListener(this);
    }

    private void bindView() {
        tabLayout=findViewById(R.id.notice_tablayout);
        mViewPager=findViewById(R.id.notice_viewpager);
        toolbar=findViewById(R.id.message_fragent_toolbar);
        actionLeftIv=findViewById(R.id.action_left_iv);
        actionRightIv=findViewById(R.id.action_right_iv);
        actionLeftIv.setImageResource(R.drawable.icon_back);
        actionRightIv.setImageResource(R.drawable.kefu);

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        TextView textView = tab.getCustomView().findViewById(R.id.notice_tab_tv);
        textView.setTextColor(getResources().getColor(R.color.tabSelecterColor));
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        TextView textView =tab.getCustomView().findViewById(R.id.notice_tab_tv);
        textView.setTextColor(getResources().getColor(R.color.defaultColor));
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.action_left_iv:
                finish();
                break;
            case R.id.action_right_iv:
                //TODO 跳转在线客服
               startActivity(new Intent(NoticeActivity.this, OnLineKefuActivity.class));
                break;
        }
    }

    /*--------------------   TabLayout 适配器  ----------------------*/
    public class NoticeTabAdapter extends FragmentPagerAdapter {

        public NoticeTabAdapter(@NonNull FragmentManager fm ) {
            super(fm);
        }
        /*
            getItem()直接new Fragment 不将fragment放入list
         */
        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment;
            if(position==0){
                fragment=new SystemMessageFragment();
            }else {
                fragment=new HbNoticeFragment();
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return titles.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
        //自定义tab item布局
        public View getTabView(int position) {
            View v = LayoutInflater.from(NoticeActivity.this).inflate(R.layout.notice_tab_view, null);
            ImageView iv = v.findViewById(R.id.notice_tab_icon);
            TextView tv = v.findViewById(R.id.notice_tab_tv);
            iv.setBackgroundResource(icons[position]);
            tv.setText(titles[position]);
            if (position == 0) {
                tv.setTextColor(v.getResources().getColor(R.color.tabSelecterColor));
            }
            return v;
        }
    }
}
