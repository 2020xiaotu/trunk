package com.example.red_pakege.activity.red_game_activity;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.red_pakege.R;
import com.example.red_pakege.activity.message_fragment_activity.OnLineKefuActivity;
import com.example.red_pakege.adapter.game_group_adapter.RedPakegeListTabAdapter;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.MemoryLeakUtil;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;

public class RedPakegeGameActivity extends BaseActivity implements View.OnClickListener, TabLayout.BaseOnTabSelectedListener {
    private Toolbar toolbar;
    private TextView actionTitletv;
    private ImageView actionLeftIv;
    private ImageView actionRightIv;

    private TabLayout mTab;
    private ViewPager mViewPager;
    private RedPakegeListTabAdapter redPakegeListTabAdapter;
    private ArrayList<String> titleList ;
    private ArrayList<Integer>typeIdList ;
    //TODO 后台有给图片的时候,将icons 换成 intent中传递的tabImageList
    private ArrayList<Integer>icons =new ArrayList<>();
    //需要默认选中的tabId
    private  int selectorTabId;

    private int parentId;

    private ArrayList<String> tabImageList = new ArrayList<>();


    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_red_pakege_game;
    }

    @Override
    protected void initView() {
        super.initView();
        getintentData();
        bindView();
        ActionBarUtil.initMainActionbar(this,toolbar);
        initTab();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //华为inputMethodManager内存泄漏
        MemoryLeakUtil.fixLeak(this);
    }
    public static void startAty(Context context,ArrayList<String>gameNameList,ArrayList<Integer>gameIdList,int childId,ArrayList<String>tabImageList,int parentId){
        Intent intent = new Intent(context, RedPakegeGameActivity.class);
        intent.putStringArrayListExtra("gameNameList",gameNameList);
        intent.putIntegerArrayListExtra("gameIdList",gameIdList);
        intent.putExtra("clickId",childId);
        intent.putExtra("tabImageList",tabImageList);
        intent.putExtra("parentId",parentId);
        context.startActivity(intent);
    };


    private void getintentData() {
        titleList=getIntent().getStringArrayListExtra("gameNameList");
        typeIdList=getIntent().getIntegerArrayListExtra("gameIdList");
        selectorTabId=getIntent().getIntExtra("clickId",1);
        //TODO  更换tab图片数据源
        tabImageList=getIntent().getStringArrayListExtra("tabImageList");
        parentId =getIntent().getIntExtra("parentId",1);

    }
    private void initTab() {
        for (int i = 0; i <typeIdList.size(); i++) {
            icons.add(R.drawable.game_tab_0);
        }

    redPakegeListTabAdapter=new RedPakegeListTabAdapter(getSupportFragmentManager(),titleList,icons,typeIdList,selectorTabId,parentId);
    mViewPager.setAdapter(redPakegeListTabAdapter);
    mTab.setupWithViewPager(mViewPager);
        for (int i = 0; i < typeIdList.size(); i++) {
        /*    if(i==0){
                mTab.addTab(mTab.newTab(),false);//设置第一个tab不默认选中(因为下面需要设默认选中,这里不取消选中的话会连续选中两个tab)
            }*/
            if(typeIdList.get(i)==selectorTabId){
                mTab.getTabAt(i).select();
            }
            mTab.getTabAt(i).setCustomView(redPakegeListTabAdapter.getTabView(i,typeIdList.get(i)));
        }
     mTab.addOnTabSelectedListener(this);
    }

    private void bindView() {
        toolbar=findViewById(R.id.message_fragent_toolbar);
        actionLeftIv=findViewById(R.id.action_left_iv);
        actionRightIv =findViewById(R.id.action_right_iv);
        actionTitletv=findViewById(R.id.actionBar_title);
        actionTitletv.setText("红包游戏");
        actionLeftIv.setImageResource(R.drawable.icon_back);
        actionRightIv.setImageResource(R.drawable.kefu);
        actionLeftIv.setOnClickListener(this);
        mTab=findViewById(R.id.red_pakege_list_tab);
        mViewPager=findViewById(R.id.redpakege_game_list_viewpager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_left_iv:
                finish();
                break;
            case R.id.action_right_iv:
                startActivity(new Intent(RedPakegeGameActivity.this, OnLineKefuActivity.class));
                break;
                default:
                    break;

        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        TextView textView = tab.getCustomView().findViewById(R.id.game_group_tab_tv);
        textView.setTextColor(getResources().getColor(R.color.tabSelecterColor));
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        TextView textView =tab.getCustomView().findViewById(R.id.game_group_tab_tv);
        textView.setTextColor(getResources().getColor(R.color.defaultColor));
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
