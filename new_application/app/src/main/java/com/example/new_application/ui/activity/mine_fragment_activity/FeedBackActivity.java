package com.example.new_application.ui.activity.mine_fragment_activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.new_application.R;
import com.example.new_application.adapter.FragmentAdapter;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.ui.fragment.feedback_fragment.MineFeedbackFragment;
import com.example.new_application.ui.fragment.feedback_fragment.WantToFeedbackFragment;
import com.example.new_application.utils.CommonToolbarUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import butterknife.BindView;

public class FeedBackActivity extends BaseActivity {

    @BindView(R.id.feedback_fragment_content)
    ViewPager mViewPager;
    private ArrayList<Fragment> fragmentArrayList;
    private ArrayList<String> titles=new ArrayList<>();
    public int movieId;
    @BindView(R.id.feedback_tablayout)
    TabLayout tabLayout;
    @Override
    public int getLayoutId() {
        return R.layout.activity_face_back;

    }


    @Override
    protected void init(Bundle savedInstanceState) {
        CommonToolbarUtil.initToolbar(this,"意见反馈");
        getIntentData();
        initTabLayout();
    }


    public void getIntentData() {
        movieId=getIntent().getIntExtra("movieId",0);
    }

    private void initTabLayout() {
        titles.add("我要反馈");
        titles.add("我的反馈");
        for (int i = 0; i < titles.size(); i++) {
            tabLayout.addTab(tabLayout.newTab());
            tabLayout.getTabAt(i).setText(titles.get(i));
        }
        fragmentArrayList=new ArrayList<>();
        fragmentArrayList.add(new WantToFeedbackFragment());
        fragmentArrayList.add(new MineFeedbackFragment());
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), titles, fragmentArrayList);
        mViewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(mViewPager);

        if(movieId!=0){
            mViewPager.setCurrentItem(1);
        }else {
            mViewPager.setCurrentItem(0);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragmentArrayList.clear();
        //华为inputMethodManager内存泄漏
    }
}
