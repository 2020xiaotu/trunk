package com.example.new_application.ui.activity.mine_fragment_activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.example.new_application.R;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.GuaranteeOrderEvenEntity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class GuaranteeOrderActivity extends BaseActivity implements XTabLayout.OnTabSelectedListener {
    @BindView(R.id.guarantee_back_iv)
    ImageView guarantee_back_iv;
    @BindView(R.id.mine_release_tv)
    TextView mine_release_tv;
    @BindView(R.id.user_commission_tv)
    TextView user_commission_tv;
    @BindView(R.id.guarantee_order_tab)
    XTabLayout guarantee_order_tab;
    @BindView(R.id.guarantee_order_viewPager)
    ViewPager guarantee_order_viewPager;

    ArrayList<String>titleList = new ArrayList<>();

    GuaranteeTabAdapter guaranteeTabAdapter;

    int flag =0;//我发布的担保;1用户受委托的担保

    @Override
    public int getLayoutId() {
        return R.layout.activity_guarantee_order;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTab();
    }

    private void initTab() {
        titleList.add("全部");
        titleList.add("用户委托中");
        titleList.add("用户取消订单");
        titleList.add("平台审核中");
        titleList.add("平台拒绝");
        titleList.add("用户取消订单");
        titleList.add("担保成功");
        titleList.add("担保失败");
        guaranteeTabAdapter = new GuaranteeTabAdapter(getSupportFragmentManager());
        guarantee_order_viewPager.setAdapter(guaranteeTabAdapter);
        guarantee_order_tab.setupWithViewPager(guarantee_order_viewPager);
        for (int i = 0; i < titleList.size(); i++) {
            XTabLayout.Tab tabAt = guarantee_order_tab.getTabAt(i);
            tabAt.setCustomView(guaranteeTabAdapter.getTabView(i));
            if(i==0){
                TextView textView= tabAt.getCustomView().findViewById(R.id.guarantee_tab_title_tv);
                textView.setTextColor(Color.parseColor("#FA6400"));
            }
        }
        guarantee_order_tab.addOnTabSelectedListener(this);
    }

    @OnClick({R.id.mine_release_tv,R.id.user_commission_tv,R.id.guarantee_back_iv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.guarantee_back_iv:
                finish();
                break;
            case R.id.mine_release_tv:
                mine_release_tv.setTextColor(Color.parseColor("#FA6400"));
                user_commission_tv.setTextColor(Color.parseColor("#222222"));
                flag = 0;
                EventBus.getDefault().post(new GuaranteeOrderEvenEntity());
                break;
            case R.id.user_commission_tv:
                mine_release_tv.setTextColor(Color.parseColor("#222222"));
                user_commission_tv.setTextColor(Color.parseColor("#FA6400"));
                flag = 1;
                EventBus.getDefault().post(new GuaranteeOrderEvenEntity());
                break;
            default:
                break;
        }
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
        TextView textView=  customView.findViewById(R.id.guarantee_tab_title_tv);
        textView.setTextColor(Color.parseColor("#FA6400"));
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
        TextView textView=  customView.findViewById(R.id.guarantee_tab_title_tv);
        textView.setTextColor(Color.parseColor("#999999"));
    }

    @Override
    public void onTabReselected(XTabLayout.Tab tab) {

    }



    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public ArrayList<String> getTitleList() {
        return titleList;
    }

    class GuaranteeTabAdapter extends FragmentPagerAdapter {
        public GuaranteeTabAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return GuaranteeOrderFragment.newInstance(position);
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
        //自定义tab item布局
        public View getTabView(int position) {
            View v = LayoutInflater.from(GuaranteeOrderActivity.this).inflate(R.layout.guarantee_tab_view_layout, null);
            TextView tv = v.findViewById(R.id.guarantee_tab_title_tv);
            tv.setText(titleList.get(position));
            return v;
        }
    }
}