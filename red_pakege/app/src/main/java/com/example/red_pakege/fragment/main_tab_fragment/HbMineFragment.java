package com.example.red_pakege.fragment.main_tab_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.red_pakege.R;
import com.example.red_pakege.activity.add_activity.HbAgentCenterActivity;
import com.example.red_pakege.activity.mine.AwardActivity;
import com.example.red_pakege.activity.mine.CapitalDetailActivity;
import com.example.red_pakege.activity.mine.DrawingCenterActivity;
import com.example.red_pakege.activity.mine.SettingActivity;
import com.example.red_pakege.activity.mine.UserInfoActivity;
import com.example.red_pakege.activity.mine.YueBaoActivity;
import com.example.red_pakege.adapter.mine_adapter.MineTabAdapter;
import com.example.red_pakege.base.BaseFragment;
import com.example.red_pakege.mvp.contract.MineFragmentContract;
import com.example.red_pakege.mvp.presenter.MineFragmentPresenter;
import com.example.red_pakege.net.entity.MemberMoneyEntity;
import com.example.red_pakege.util.GlideLoadViewUtil;
import com.example.red_pakege.util.JsonUtil;
import com.example.red_pakege.util.RouteUtil;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import butterknife.BindView;

import static com.example.red_pakege.util.JsonUtil.Str2UserEntity;
import static com.example.red_pakege.util.MoneyUtil.parMoney2;


public class HbMineFragment extends BaseFragment<MineFragmentPresenter>
        implements MineFragmentContract.IMineView, TabLayout.BaseOnTabSelectedListener{

    @BindView(R.id.tv_mine_name)
    TextView tv_mine_name;
    @BindView(R.id.tv_mine_id)
    TextView tv_mine_id;
    @BindView(R.id.tv_mine_remark)
    TextView tv_mine_remark;
    @BindView(R.id.iv_mine_avatar)
    ImageView iv_mine_avatar;
    @BindView(R.id.iv_mine_sex)
    ImageView iv_mine_sex;
    @BindView(R.id.ll_mine_setting)
    LinearLayout llMineSetting;
    @BindView(R.id.tv_mine_version)
    TextView tvMineVersion;
    @BindView(R.id.tab_mine)
    TabLayout mTab;
    @BindView(R.id.mine_tab_viewpager)
    ViewPager mViewPager;
    @BindView(R.id.ll_mine_drawcenter)
    LinearLayout ll_mine_drawcenter;
    @BindView(R.id.ll_mine_activityaward)
    LinearLayout ll_mine_activityaward;
    @BindView(R.id.ll_mine_capitaldetail)
    LinearLayout ll_mine_capitaldetail;
    @BindView(R.id.ll_mine_proxycenter)
    LinearLayout ll_mine_proxycenter;
    @BindView(R.id.ll_mine_yubao)
    LinearLayout ll_mine_yubao;
    @BindView(R.id.swiperefreshlayout_mine)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.tv_mine_yue)
    TextView tv_mine_yue;
    @BindView(R.id.fl_mine_userinfo)
    FrameLayout fl_mine_userinfo;


    private SharedPreferenceHelperImpl mSharedPreferenceHelperImpl;


    private MineTabAdapter mineTabAdapter;
    private String[]titles={"红包","电子","棋牌","休闲"};
    private int[]icons ={R.drawable.icon_me_hongbao,R.drawable.icon_me_dianzi,R.drawable.icon_me_qipai,R.drawable.icon_me_xiuxian};
    private int[]icons_n ={R.drawable.icon_me_hongbao_n,R.drawable.icon_me_dianzi_n,R.drawable.icon_me_qipai_n,R.drawable.icon_me_xiuxian_n};

    public static HbMineFragment newInstance() {
        Bundle args = new Bundle();
        HbMineFragment fragment = new HbMineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected MineFragmentPresenter createPresenter() {
        return new MineFragmentPresenter();
    }


    @Override
    protected void initView() {

        mSharedPreferenceHelperImpl = new SharedPreferenceHelperImpl();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.red);
        initTab();
        LogUtils.e(""+"initView()");
    }

    @Override
    public void onSupportVisible(){
        LogUtils.e("可见");
        mPresenter.RequestMemberMoney();
        GlideLoadViewUtil.LoadRetcCirView(_mActivity,R.drawable.icon_user_avatar,18,iv_mine_avatar);
        UserEntity userEntity = JsonUtil.Str2UserEntity(mSharedPreferenceHelperImpl.getUserInfo());
        UserEntity.DataBean.MemberInfoBean userInfo = userEntity.getData().getMemberInfo();
        tv_mine_name.setText(userInfo.getUserNickName());
        tv_mine_id.setText("账号ID:"+userInfo.getNickname());
        tv_mine_remark.setText(userInfo.getRemark());
        iv_mine_sex.setImageResource(userInfo.getSex()==1?R.drawable.icon_me_nan:R.drawable.icon_me_nv);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initEventAndData() {


        //事件操作
        llMineSetting.setOnClickListener(v -> startActivity(new Intent(_mActivity, SettingActivity.class)));
        ll_mine_drawcenter.setOnClickListener(v -> startActivity(new Intent(_mActivity, DrawingCenterActivity.class)));
        ll_mine_activityaward.setOnClickListener(v -> startActivity(new Intent(_mActivity, AwardActivity.class)));
        ll_mine_capitaldetail.setOnClickListener(v -> startActivity(new Intent(_mActivity, CapitalDetailActivity.class)));
        ll_mine_proxycenter.setOnClickListener(v -> startActivity(new Intent(_mActivity, HbAgentCenterActivity.class)));
        ll_mine_yubao.setOnClickListener(v -> startActivity(new Intent(_mActivity, YueBaoActivity.class)));
        fl_mine_userinfo.setOnClickListener(v -> startActivity(new Intent(_mActivity, UserInfoActivity.class)));


        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.RequestUserInfo();
                mPresenter.RequestMemberMoney();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });



    }

    private void initTab(){
        mineTabAdapter=new MineTabAdapter(getChildFragmentManager(),_mActivity,titles,icons,icons_n);
        mViewPager.setAdapter(mineTabAdapter);
        mTab.setupWithViewPager(mViewPager);
        for (int i = 0; i < icons.length; i++) {
            //自定义tab item布局
            mTab.getTabAt(i).setCustomView(mineTabAdapter.getTabView(i));
        }
        mTab.addOnTabSelectedListener(this);
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        TextView textView = tab.getCustomView().findViewById(R.id.tv_mine_tabitem);
        textView.setTextColor(getResources().getColor(R.color.white));
        ImageView imageView = tab.getCustomView().findViewById(R.id.iv_mine_tabitem);
        switch (tab.getPosition()){
            case 0:
                imageView.setImageResource(icons_n[0]);
                tab.getCustomView().setBackground(getResources().getDrawable(R.drawable.bkg_pink_leftup_shape));
                break;
            case 1:
                imageView.setImageResource(icons_n[1]);
                tab.getCustomView().setBackground(getResources().getDrawable(R.drawable.bkg_blue_shape));
                break;
            case 2:
                imageView.setImageResource(icons_n[2]);
                tab.getCustomView().setBackground(getResources().getDrawable(R.drawable.bkg_yellow_shape));
                break;
            case 3:
                imageView.setImageResource(icons_n[3]);
                tab.getCustomView().setBackground(getResources().getDrawable(R.drawable.bkg_purple_rightup_shape));
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        TextView textView =tab.getCustomView().findViewById(R.id.tv_mine_tabitem);
        textView.setTextColor(getResources().getColor(R.color.defaultColor));
        ImageView imageView = tab.getCustomView().findViewById(R.id.iv_mine_tabitem);
        tab.getCustomView().setBackground(getResources().getDrawable(R.drawable.bkg_white_shape));
        switch (tab.getPosition()){
            case 0:
                imageView.setImageResource(icons[0]);
                break;
            case 1:
                imageView.setImageResource(icons[1]);

                break;
            case 2:
                imageView.setImageResource(icons[2]);

                break;
            case 3:
                imageView.setImageResource(icons[3]);
                break;
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    //===================请求接口返回数据更新界面========================

    @Override
    public void showUserInfo(UserEntity entity) {
        UserEntity.DataBean.MemberInfoBean userInfo = entity.getData().getMemberInfo();
        tv_mine_name.setText(userInfo.getUserNickName());
        tv_mine_id.setText("账号ID:"+userInfo.getNickname());
        tv_mine_remark.setText(userInfo.getRemark());
        iv_mine_sex.setImageResource(userInfo.getSex()==1?R.drawable.icon_me_nan:R.drawable.icon_me_nv);

    }

    @Override
    public void showMemberMoney(MemberMoneyEntity entity) {
        tv_mine_yue.setText("余额:"+parMoney2(""+entity.getData().getAmount())+"元");
    }

    @Override
    public void showTodayChongzhi() {

    }

    @Override
    public void showTodayTixian() {

    }

    @Override
    public void showTodayYingkui() {

    }

    @Override
    public void showTabList() {

    }

    //////////////test 生命周期//////////////////////


    @Override
    public void onAttach(Context onAttach) {
        super.onAttach(onAttach);
        LogUtils.e("_onAttach");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.e("_onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.e("_onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.e("_onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.e("_onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.e("_onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtils.e("_onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtils.e("_onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtils.e("_onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.e("_onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtils.e("_onDetach");
    }


}
