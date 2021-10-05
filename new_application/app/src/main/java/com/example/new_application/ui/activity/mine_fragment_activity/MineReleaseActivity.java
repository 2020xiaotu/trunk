package com.example.new_application.ui.activity.mine_fragment_activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.example.new_application.R;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.base.BasePopupWindow;
import com.example.new_application.bean.MineReleaseEntity;
import com.example.new_application.bean.MineReleaseEvenEntity;
import com.example.new_application.bean.MineServerEntity;
import com.example.new_application.ui.pop.ChooseServerPop;
import com.example.new_application.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MineReleaseActivity extends BaseActivity implements XTabLayout.OnTabSelectedListener {

    @BindView(R.id.mine_release_toolbar_back_iv)
    ImageView mine_release_toolbar_back_iv;
    @BindView(R.id.mine_release_toolbar_title_tv)
    TextView mine_release_toolbar_title_tv;
    @BindView(R.id.choose_type_tv)
    TextView choose_type_tv;
    @BindView(R.id.mine_release_toolbar_right_iv)
    ImageView mine_release_toolbar_right_iv;
    @BindView(R.id.mine_release_tab)
    XTabLayout mine_release_tab;
    @BindView(R.id.mine_release_viewPager)
    ViewPager mine_release_viewPager;
    ArrayList<String> titleList = new ArrayList<>();
    private MineReleaseTabAdapter mineReleaseTabAdapter;
    String oneLevelClassification;
    String twoLevelClassification;
    ChooseServerPop chooseServerPop;

    @Override
    public int getLayoutId() {
        return R.layout.activity_m_ine_release;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mine_release_toolbar_title_tv.setText("我的发布");
        initTab();
    }

    private void initTab() {
        titleList.add("全部");
        titleList.add("审核中");
        titleList.add("未通过");
        titleList.add("已发布");
        mineReleaseTabAdapter = new MineReleaseTabAdapter(getSupportFragmentManager());
        mine_release_viewPager.setAdapter(mineReleaseTabAdapter);
        mine_release_tab.setupWithViewPager(mine_release_viewPager);
        for (int i = 0; i < titleList.size(); i++) {
            XTabLayout.Tab tabAt = mine_release_tab.getTabAt(i);
            tabAt.setCustomView(mineReleaseTabAdapter.getTabView(i));
            if(i==0){
                TextView textView= tabAt.getCustomView().findViewById(R.id.guarantee_tab_title_tv);
                textView.setTextColor(Color.parseColor("#FA6400"));
            }
        }
        mine_release_tab.addOnTabSelectedListener(this);
    }

    @OnClick({R.id.mine_release_toolbar_back_iv,R.id.choose_type_tv,R.id.mine_release_toolbar_right_iv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.mine_release_toolbar_back_iv:
                finish();
                break;
            case R.id.choose_type_tv:
            case R.id.mine_release_toolbar_right_iv:
                initChooseTypePop();
                chooseServerPop.showAsDropDown(mine_release_tab, 0,0,Gravity.BOTTOM);
                Utils.darkenBackground(MineReleaseActivity.this,0.7f);
                break;
            default:
                break;
        }
    }

    private void initChooseTypePop() {
        if(chooseServerPop==null){
            chooseServerPop = new ChooseServerPop(MineReleaseActivity.this);
            chooseServerPop.setOnPopItemClick(new BasePopupWindow.OnRecycleItemClick() {
                @Override
                public void onPopItemClick(View view, int position) {
                     MineServerEntity checkedMineServerEntity = chooseServerPop.getMineServerEntityArrayList().get(position);
                     twoLevelClassification=checkedMineServerEntity.getTwoLevelClassificationId();
                     choose_type_tv.setText(checkedMineServerEntity.getTwoLevelClassificationName());
                     EventBus.getDefault().post(new MineReleaseEvenEntity());
                }
            });
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

    public ArrayList<String> getTitleList() {
        return titleList;
    }

    public String getOneLevelClassification() {
        return oneLevelClassification;
    }

    public String getTwoLevelClassification() {
        return twoLevelClassification;
    }

    class MineReleaseTabAdapter extends FragmentPagerAdapter {
        public MineReleaseTabAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return MineReleaseFragment.newInstance(position);
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
            View v = LayoutInflater.from(MineReleaseActivity.this).inflate(R.layout.guarantee_tab_view_layout, null);
            TextView tv = v.findViewById(R.id.guarantee_tab_title_tv);
            tv.setText(titleList.get(position));
            return v;
        }
    }
}