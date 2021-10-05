package com.example.new_application.ui.fragment.main_tab_fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.example.new_application.MainActivity;
import com.example.new_application.R;
import com.example.new_application.base.BaseFragment;
import com.example.new_application.bean.AllMessageUnReadEvenEntity;
import com.example.new_application.bean.MessageTabEntity;
import com.example.new_application.bean.UnReadMessageEntity;
import com.example.new_application.ui.fragment.message_fragment.MessageFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

public class TabMessageFragment extends BaseFragment implements XTabLayout.OnTabSelectedListener {
    @BindView(R.id.message_tab)
    XTabLayout message_tab;
    @BindView(R.id.message_viewpager)
    ViewPager message_viewpager;
    private ArrayList<MessageTabEntity> titleList=new ArrayList<>();
    MessageTabAdapter messageTabAdapter;
    MainActivity mainActivity;
    public static TabMessageFragment newInstance() {
        return  new TabMessageFragment();
    }
    @Override
    protected void init(Bundle savedInstanceState) {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        initTab();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_share;
    }


    private void initTab() {
        if(getActivity() instanceof MainActivity){
            mainActivity = (MainActivity) getActivity();
        }
        titleList.add(new MessageTabEntity("系统消息",1000,"0"));
        titleList.add(new MessageTabEntity("交易消息",1,mainActivity.unReadMessageEntity.getType1()));
        titleList.add(new MessageTabEntity("私信",0,mainActivity.unReadMessageEntity.getType0()));
        messageTabAdapter = new MessageTabAdapter(getChildFragmentManager(),titleList,getContext());
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initUnreadMessageCount(AllMessageUnReadEvenEntity allMessageUnReadEvenEntity){
        UnReadMessageEntity unReadMessageEntity = allMessageUnReadEvenEntity.getUnReadMessageEntity();
//        (type0:系统消息数,type1:交易消息数,type2:合作消息数,typeAll:总消息数)
        for (int i = 0; i < titleList.size(); i++) {
            MessageTabEntity messageTabEntity = titleList.get(i);
            if((messageTabEntity.getType()+"").equals("1000")){
                messageTabEntity.setMessageCount("0");
            }else  if((messageTabEntity.getType()+"").equals("1")){
                messageTabEntity.setMessageCount(unReadMessageEntity.getType1());
            }else if((messageTabEntity.getType()+"").equals("0")){
                messageTabEntity.setMessageCount(unReadMessageEntity.getType0());
            }
        }
        for (int i = 0; i < titleList.size(); i++) {
            XTabLayout.Tab tabAt = message_tab.getTabAt(i);
//            tabAt.setCustomView(messageTabAdapter.getTabView(i));
            View customView = tabAt.getCustomView();
            if(customView==null){
                continue;
            }
            TextView message_count_tv = customView.findViewById(R.id.un_read_message_count_tv);
            initUnreadMessageCount(message_count_tv,titleList.get(i));
        }
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
            View v = LayoutInflater.from(context).inflate(R.layout.message_tab_count_view_layout, null);
            TextView tv = v.findViewById(R.id.message_tab_title_tv);
            TextView un_read_message_count_tv = v.findViewById(R.id.un_read_message_count_tv);
            MessageTabEntity messageTabEntity = titleList.get(position);
            tv.setText(messageTabEntity.getContent());
            if (position == 0) {
                tv.setTextSize(16);
            }
            initUnreadMessageCount(un_read_message_count_tv, messageTabEntity);
            return v;
        }
    }

    private void initUnreadMessageCount(TextView un_read_message_count_tv, MessageTabEntity messageTabEntity) {
        String messageCount = messageTabEntity.getMessageCount();
        if(StringMyUtil.isEmptyString(messageCount) || messageCount.equals("0")){
            un_read_message_count_tv.setVisibility(View.GONE);
        }else {
            un_read_message_count_tv.setVisibility(View.VISIBLE);
            un_read_message_count_tv.setText(messageCount);
        }
    }
}