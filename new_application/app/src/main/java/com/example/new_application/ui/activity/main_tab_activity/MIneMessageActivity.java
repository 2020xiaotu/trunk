package com.example.new_application.ui.activity.main_tab_activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.example.new_application.R;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.MessageTabEntity;
import com.example.new_application.ui.fragment.message_fragment.MessageFragment;
import com.example.new_application.utils.CommonToolbarUtil;

import java.util.ArrayList;

import butterknife.BindView;

public class MIneMessageActivity extends BaseActivity implements XTabLayout.OnTabSelectedListener {
    @BindView(R.id.message_tab)
    XTabLayout message_tab;
    @BindView(R.id.message_viewpager)
    ViewPager message_viewpager;

    private ArrayList<MessageTabEntity> titleList=new ArrayList<>();
    MessageTabAdapter messageTabAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_m_ine_message;
    }

    @Override
    public void getIntentData() {

    }


    @Override
    protected void init(Bundle savedInstanceState) {
        CommonToolbarUtil.initToolbar(this,"我的消息");
        initTab();
    }

    private void initTab() {
        titleList.add(new MessageTabEntity("系统消息",1000));
        titleList.add(new MessageTabEntity("交易消息",1));
        titleList.add(new MessageTabEntity("私信",0));
        messageTabAdapter = new MessageTabAdapter(getSupportFragmentManager(),titleList,this);
        message_viewpager.setAdapter(messageTabAdapter);
        message_tab.setupWithViewPager(message_viewpager);
        for (int i = 0; i < titleList.size(); i++) {
            XTabLayout.Tab tabAt = message_tab.getTabAt(i);
            tabAt.setCustomView(messageTabAdapter.getTabView(i));
            if(i==0){
                TextView textView= tabAt.getCustomView().findViewById(R.id.message_tab_title_tv);
                textView.setTextColor(Color.parseColor("#222222"));
                textView.setTextSize(16);
            }
        }
        message_tab.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(XTabLayout.Tab tab) {
        if(tab==null){
            return;
        }
        View customView = tab.getCustomView();
        if(customView==null){
            return;
        }
        TextView textView=  customView.findViewById(R.id.message_tab_title_tv);
        textView.setTextSize(16);
        textView.setTextColor(Color.parseColor("#222222"));
    }

    @Override
    public void onTabUnselected(XTabLayout.Tab tab) {
        if(tab==null){
            return;
        }
        View customView = tab.getCustomView();
        if(customView==null){
            return;
        }
        TextView textView=  customView.findViewById(R.id.message_tab_title_tv);
        textView.setTextSize(14);
        textView.setTextColor(Color.parseColor("#666666"));
    }

    @Override
    public void onTabReselected(XTabLayout.Tab tab) {

    }

    public class MessageTabAdapter extends FragmentPagerAdapter {
        private ArrayList<MessageTabEntity> titleList;
        private Context context;
        public MessageTabAdapter(@NonNull FragmentManager fm, ArrayList<MessageTabEntity> titleList, Context context) {
            super(fm);
            this.titleList = titleList;
            this.context = context;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return MessageFragment.newInstance(position,titleList.get(position));
        }

        @Override
        public int getCount() {
            return titleList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position).getContent();
        }
        //自定义tab item布局
        public View getTabView(int position) {
            View v = LayoutInflater.from(context).inflate(R.layout.message_tab_view_layout, null);
            TextView tv = v.findViewById(R.id.message_tab_title_tv);
            tv.setText(titleList.get(position).getContent());
            if (position == 0) {
                tv.setTextSize(16);
            }
            return v;
        }
    }


}