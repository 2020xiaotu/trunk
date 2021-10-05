package com.example.red_pakege.fragment.main_tab_fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.red_pakege.activity.add_activity.CreateGroupActivity;
import com.example.red_pakege.activity.add_activity.HbAgentCenterActivity;
import com.example.red_pakege.activity.message_fragment_activity.NoticeActivity;
import com.example.red_pakege.adapter.message_fragment_adapter.BannerViewHolder;
import com.example.red_pakege.base.BaseFragment;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.model.BannerData;
import com.example.red_pakege.model.MessageFragmentMedel;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.R;
import com.example.red_pakege.util.BannerLoadViewUtil;
import com.example.red_pakege.util.Utils;
import com.zhpan.bannerview.BannerViewPager;



import java.util.ArrayList;
import java.util.List;

public class HbMessageFragment extends BaseFragment implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView noticeTv;
    private ImageView actionBarLeftIv;
    private ImageView actionBarRightIv;

    //轮播图
    private BannerViewPager<BannerData, BannerViewHolder> mBannerViewPager;
    //轮播图linear
    private LinearLayout bannerLinear;
    //轮播图数据
    private List<BannerData> bannerDataList=new ArrayList<>();

    //recycleView 列表相关
    private RecyclerView mRecy;
    private BannerViewHolder.MessageFragmentAdapter messageFragmentAdapter;
    private ArrayList<MessageFragmentMedel> messageFragmentMedelArrayList=new ArrayList<>();
    private LinearLayout noDataLinear;
    private LinearLayout errorLinear;
    private ConstraintLayout loadingLinear;

    //右侧pop
    private PopupWindow addPop;
    private LinearLayout agentCenterLinear;
    private LinearLayout newPersonLinear;
    private LinearLayout playRuleLinear;
    private LinearLayout createGroupLinear;

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }
    public static HbMessageFragment newInstance() {
        Bundle args = new Bundle();
        HbMessageFragment fragment = new HbMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        bindView(view);
        ActionBarUtil.initMainActionbar(getActivity(),toolbar);
        initBanner();
        initRecycle();
        return view;
    }

    private void initRecycle() {
        messageFragmentAdapter=new BannerViewHolder.MessageFragmentAdapter(messageFragmentMedelArrayList,this);
        mRecy.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecy.setAdapter(messageFragmentAdapter);
        for (int i = 0; i < 5; i++) {
            messageFragmentMedelArrayList.add(new MessageFragmentMedel("在线客服","有问题 找客服",""));
        }
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
    private void bindView(View view) {
        toolbar=view.findViewById(R.id.message_fragent_toolbar);
        actionBarLeftIv=view.findViewById(R.id.action_left_iv);
        actionBarRightIv=view.findViewById(R.id.action_right_iv);
        mBannerViewPager=view.findViewById(R.id.message_banner_view);
        noticeTv=view.findViewById(R.id.notice_tv);
        mRecy=view.findViewById(R.id.message_fragment_recycle);
        noDataLinear=view.findViewById(R.id.nodata_linear);
        loadingLinear=view.findViewById(R.id.loading_linear);
        errorLinear=view.findViewById(R.id.error_linear);
        noticeTv.setSelected(true);
        actionBarLeftIv.setOnClickListener(this);
        actionBarRightIv.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //跳转消息公告activity
            case R.id.action_left_iv:
                startActivity(new Intent(getContext(), NoticeActivity.class));
                break;
                //弹出pop
            case R.id.action_right_iv:
                if(addPop==null){
                    initAddPop();
                }
                addPop.showAsDropDown(actionBarRightIv, 15,-15);
                Utils.darkenBackground(getActivity(),0.7f);
                break;
                //代理中心
            case R.id.add_agent_center_linear:
                startActivity(new Intent(getContext(), HbAgentCenterActivity.class));
                addPop.dismiss();
                break;
                //新手教程
            case R.id.add_new_person_linear:
                addPop.dismiss();
                break;
                //玩法规则
            case R.id.add_play_rule_linear:
                addPop.dismiss();
                break;
                //创建群组
            case R.id.add_create_group_linear:
                startActivity(new Intent(getContext(), CreateGroupActivity.class));
                addPop.dismiss();
                break;
                default:
                    break;
        }
    }
        /*
        右侧添加pop初始化
         */
    private void initAddPop() {
        View popView = LayoutInflater.from(getContext()).inflate(R.layout.add_popupwindow,null);
        agentCenterLinear=popView.findViewById(R.id.add_agent_center_linear);
        newPersonLinear=popView.findViewById(R.id.add_new_person_linear);
        playRuleLinear=popView.findViewById(R.id.add_play_rule_linear);
        createGroupLinear=popView.findViewById(R.id.add_create_group_linear);
        agentCenterLinear.setOnClickListener(this);
        newPersonLinear.setOnClickListener(this);
        playRuleLinear.setOnClickListener(this);
        createGroupLinear.setOnClickListener(this);
        addPop =new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);
        addPop.setAnimationStyle(R.style.popAlphaanim0_3);
        addPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.darkenBackground(getActivity(),1f);
            }
        });
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
        //BannerView
        BannerLoadViewUtil.createBannerView(_mActivity,mBannerViewPager,bannerDataList,true,3000,true,true);

    }
}
