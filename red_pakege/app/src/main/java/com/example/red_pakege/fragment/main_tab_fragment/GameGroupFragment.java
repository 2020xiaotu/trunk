package com.example.red_pakege.fragment.main_tab_fragment;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.red_pakege.R;
import com.example.red_pakege.activity.message_fragment_activity.NoticeActivity;
import com.example.red_pakege.adapter.game_group_adapter.GameGroupTabAdapter;
import com.example.red_pakege.adapter.message_fragment_adapter.BannerViewHolder;
import com.example.red_pakege.base.BaseFragment;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.model.BannerData;
import com.example.red_pakege.net.api.HttpApiUtils;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.AddPopUtil;
import com.example.red_pakege.util.BannerLoadViewUtil;
import com.example.red_pakege.util.RequestUtil;
import com.google.android.material.tabs.TabLayout;
import com.zhpan.bannerview.BannerViewPager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Headers;

public class GameGroupFragment extends BaseFragment implements View.OnClickListener, TabLayout.BaseOnTabSelectedListener {
    private Toolbar toolbar;
    private ImageView actionLeftIv;
    private ImageView actionRightIv;
    private TextView actionTitleTv;
    //右侧addPop
    private PopupWindow addPop;
    /*
    轮播banner
     */
    private BannerViewPager<BannerData, BannerViewHolder> mBannerViewPager;
    //轮播图linear
    private LinearLayout bannerLinear;
    //轮播图数据
    private List<BannerData> bannerDataList=new ArrayList<>();

    //跑马灯
    private TextView noticeTv;

    //tab viewPager相关
    private TabLayout mTab;
    private ViewPager mViewPager;
    private GameGroupTabAdapter gameGroupTabAdapter;
//    private String[]titles={};/*={"红包游戏","棋牌游戏","电子游戏","休闲游戏"}*/;
    private ArrayList<String>titles = new ArrayList<>();
    private ArrayList<Integer> idList = new ArrayList<>();
    private int[]icons ={R.drawable.game_tab_0,R.drawable.game_tab_1,R.drawable.game_tab_2,R.drawable.game_tab_3};

    public static GameGroupFragment newInstance() {
        Bundle args = new Bundle();
        GameGroupFragment fragment = new GameGroupFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_group, container, false);
        bindView(view);
        ActionBarUtil.initMainActionbar(getActivity(),toolbar);
        initBanner();
        getTabTitleAndId();
//        initTab();
        return view;
    }
    //首页先请求title id 和 图片 在初始化tab
    private void getTabTitleAndId() {
        HttpApiUtils.normalRequest(getContext(), RequestUtil.GAME_CLASSFY_LIST, new HashMap<String, Object>(), new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result, Headers headers) {
                JSONObject data = JSONObject.parseObject(result).getJSONObject("data");
                JSONArray game = data.getJSONArray("game");
                for (int i = 0; i < game.size(); i++) {
                    JSONObject gameJSONObject = game.getJSONObject(i);
                    String name = gameJSONObject.getString("name");
                    Integer id = gameJSONObject.getInteger("id");
                    titles.add(name+"游戏");
                    idList.add(i+1);
                }
                initTab();
            }

            @Override
            public void onFaild(String msg) {

            }
        });
    }

    private void initTab() {
/*        idList.add(1);
        idList.add(2);
        idList.add(3);
        idList.add(4);*/
        gameGroupTabAdapter=new GameGroupTabAdapter(getChildFragmentManager()/*,getContext()*/,titles,icons,idList);
        mViewPager.setAdapter(gameGroupTabAdapter);
        mTab.setupWithViewPager(mViewPager);
        for (int i = 0; i < titles.size(); i++) {
            //自定义tab item布局
            mTab.getTabAt(i).setCustomView(gameGroupTabAdapter.getTabView(i));
        }
        mTab.addOnTabSelectedListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        gameGroupTabAdapter=null;
    }

    /*
             为节省性能,在onStop中停止轮播
              */
    @Override
    public void onStop() {
        super.onStop();
        if (mBannerViewPager != null){
            mBannerViewPager.stopLoop();
        }
    }
    /*
     onResume中开启轮播
     */
    @Override
    public void onResume() {
        super.onResume();
        if (mBannerViewPager != null){
            mBannerViewPager.startLoop();
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //开启器自动轮播后,在destroy中销毁,避免内存泄漏
        if(mBannerViewPager!=null) {
            mBannerViewPager.stopLoop();
        }
    }
    /*
    轮播图
     */
    private void initBanner() {
        /*
        模拟数据
         */
        bannerDataList.add(new BannerData("http://b-ssl.duitang.com/uploads/item/201208/30/20120830173930_PBfJE.jpeg"));
        bannerDataList.add(new BannerData("http://img01.3dmgame.com/uploads/allimg/130417/153_130417091610_1.jpg"));
        bannerDataList.add(new BannerData("http://img3.imgtn.bdimg.com/it/u=3157206841,1245339684&fm=26&gp=0.jpg"));
        bannerDataList.add(new BannerData("http://pic1.nipic.com/2008-10-22/200810221215723_2.jpg"));
        //是否显示圆点指示器
        BannerLoadViewUtil.createBannerView(_mActivity,mBannerViewPager,bannerDataList,true,3000,true,true);
    }

    private void bindView(View view) {
        toolbar=view.findViewById(R.id.message_fragent_toolbar);
        actionLeftIv=view.findViewById(R.id.action_left_iv);
        actionRightIv=view.findViewById(R.id.action_right_iv);
        actionTitleTv=view.findViewById(R.id.actionBar_title);
        noticeTv=view.findViewById(R.id.game_group_notice_tv);
        noticeTv.setSelected(true);
        actionTitleTv.setText("游戏大厅");
        mBannerViewPager=view.findViewById(R.id.game_group_banner);
        mTab=view.findViewById(R.id.game_group_tab);
        mViewPager=view.findViewById(R.id.game_group_viewPager);
        initClick();
    }

    @Override
    public void initClick() {
        super.initClick();
        actionLeftIv.setOnClickListener(this);
        actionRightIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_left_iv:
                startActivity(new Intent(getContext(), NoticeActivity.class));
                break;
            case R.id.action_right_iv:
                if(addPop==null){
                    addPop= AddPopUtil.initAddPop(getContext());
                    AddPopUtil.clickAgentLinear(getContext());
                    AddPopUtil.clickNewPersonLinear(getContext());
                    AddPopUtil.clickPlayRuleLinear(getContext());
                    AddPopUtil.clickCreateGroupLinear(getContext());
                }
                AddPopUtil.showMenuPop(getContext(),actionRightIv);
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
