package com.example.new_application.ui.fragment.main_tab_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.new_application.MainActivity;
import com.example.new_application.R;
import com.example.new_application.adapter.BannerViewHolder;
import com.example.new_application.adapter.MineRecyclerAdapter;
import com.example.new_application.base.BaseFragment;
import com.example.new_application.bean.ContactEntity;
import com.example.new_application.bean.GuaranteeOrderEntity;
import com.example.new_application.bean.MineBannerEntity;
import com.example.new_application.bean.MineRecyclerEntity;
import com.example.new_application.bean.ServerDetailsEntity;
import com.example.new_application.bean.UserInfoEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.home_activity.ServerDetailsActivity;
import com.example.new_application.ui.activity.home_activity.ServerHallActivity;
import com.example.new_application.ui.activity.home_activity.TakeGuaranteeActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.CooperationActivity;
import com.example.new_application.ui.activity.main_tab_activity.MIneMessageActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.CertificationActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.FeedBackActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.GuaranteeOrderActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.HelpCenterActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.mine_foolow_activity.MineFollowActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.mine_history_activity.MineHistoryActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.MineReleaseActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.ReleaseServerActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.SettingActivity;
import com.example.new_application.ui.activity.user_info_activity.BannerWebViewActivity;
import com.example.new_application.ui.pop.ContactPop;
import com.example.new_application.utils.GlideLoadViewUtil;
import com.example.new_application.utils.Utils;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.constants.IndicatorStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class MineFragment extends BaseFragment {
    @BindView(R.id.recent_release_linear)
    ConstraintLayout recent_release_linear;
    @BindView(R.id.no_release_group)
    Group no_release_group;
    @BindView(R.id.mine_banner_linear)
    LinearLayout mine_banner_linear;
    @BindView(R.id.mine_fragment_banner)
    BannerViewPager<MineBannerEntity, BannerViewHolder> mine_fragment_banner;
    @BindView(R.id.mine_server_recycler)
    RecyclerView mine_server_recycler;
    @BindView(R.id.mine_follow_linear)
    LinearLayout mine_follow_linear;
    @BindView(R.id.mine_history_linear)
    LinearLayout mine_history_linear;
    @BindView(R.id.nickname_tv)
    TextView nickname_tv;
    @BindView(R.id.title_iv)
    ImageView title_iv;
    @BindView(R.id.mine_set_iv)
    ImageView mine_set_iv;
    @BindView(R.id.release_tip_top_tv)
    TextView release_tip_top_tv;
    @BindView(R.id.release_tip_bottom_tv)
    TextView release_tip_bottom_tv;
    @BindView(R.id.guarantee_content_constrainLayout)
    ConstraintLayout guarantee_content_constrainLayout;
    @BindView(R.id.guarantee_no_release_group)
    Group guarantee_no_release_group;
    @BindView(R.id.more_recommend_recycler)
    RecyclerView more_recommend_recycler;
    ArrayList<MineBannerEntity>mineBannerEntityArrayList = new ArrayList<>();
    MineRecyclerAdapter serverAdapter;
    ArrayList<MineRecyclerEntity> serverRecyclerEntityList = new ArrayList<>();
    MineRecyclerAdapter moreRecommendAdapter;
    ArrayList<MineRecyclerEntity> moreRecommendEntityList = new ArrayList<>();
    int[] serverImgs ={R.drawable.wd_yjfk,R.drawable.wd_cjwt,R.drawable.wd_kfzx,/*R.drawable.wd_wdxx,R.drawable.wd_tsjb,*/R.drawable.wd_dxjl};
    String[] serverTitles = {"意见反馈","帮助中心","联系客服",/*"消息中心","投诉举报",*/"答谢记录"};
    int[] moreRecommendImgs={R.drawable.wd_sjrz,R.drawable.wd_wyyq,R.drawable.wd_zyzh};
    String[] moreRecommendTitles = {"申请联合运营","我要邀请","资源置换"};
    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
    private ContactPop contactPop;

    public static MineFragment newInstance() {
        return  new MineFragment();
    }
    @Override
    protected void init(Bundle savedInstanceState) {
        initRelease();
        initGuarantee();
        requestBanner();
        initServerRecycler();
    }

    private void initGuarantee() {

        guarantee_content_constrainLayout.setVisibility(View.GONE);
        guarantee_no_release_group.setVisibility(View.VISIBLE);

    }
    private void initRelease() {

        recent_release_linear.setVisibility(View.GONE);
        no_release_group.setVisibility(View.VISIBLE);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        if(StringMyUtil.isNotEmpty(sp.getToken())){
            requestUserInfo();
        }
    }

    private void requestUserInfo() {
        HttpApiUtils.wwwNormalRequest(getActivity(), this, RequestUtils.USER_INFO, new HashMap<String, Object>(), new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                UserInfoEntity userInfoEntity = JSONObject.parseObject(result, UserInfoEntity.class);
                sp.setUserInfo(result);
                GlideLoadViewUtil.fLoadTitleView(MineFragment.this,userInfoEntity.getImage(),title_iv);
                nickname_tv.setText(userInfoEntity.getNickname());
                initMoreRecommendRecycler();
                if(userInfoEntity.getUserType().equals("1")){
                    /**
                     * 运营商
                     */
                    release_tip_top_tv.setText("免费发布需求");
                    release_tip_bottom_tv.setText("获取优质服务商");
                }else {
                    release_tip_top_tv.setText("免费发布资源");
                    release_tip_bottom_tv.setText("获取优质运营商");

                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    private void initServerRecycler() {
        serverAdapter = new MineRecyclerAdapter(R.layout.mine_bottom_recycler_item,serverRecyclerEntityList);
        mine_server_recycler.setLayoutManager(new GridLayoutManager(getContext(),5));
        mine_server_recycler.setAdapter(serverAdapter);
        serverAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                MineRecyclerEntity mineRecyclerEntity = serverRecyclerEntityList.get(position);
                String title = mineRecyclerEntity.getTitle();
                switch (title) {
                    case "意见反馈":
                        startActivity(new Intent(getContext(), FeedBackActivity.class));
                        break;
                    case "帮助中心":
                        startActivity(new Intent(getContext(), HelpCenterActivity.class));
                        break;
                    case "联系客服":
                        startActivity(new Intent(getContext(), CooperationActivity.class));
                        break;
                    case "消息中心":
                        startActivity(new Intent(getContext(), MIneMessageActivity.class));
                        break;
                    case "投诉举报":
                        showToast("暂未开放");
                        break;
                    case "答谢记录":
                        showToast("暂未开放");
                        break;
                    default:
                        break;
                }
            }
        });
        for (int i = 0; i < serverImgs.length; i++) {
            serverRecyclerEntityList.add(new MineRecyclerEntity(serverTitles[i],serverImgs[i]));
        }
        serverAdapter.notifyDataSetChanged();
    }
    private void initMoreRecommendRecycler() {
        moreRecommendEntityList.clear();
        moreRecommendAdapter = new MineRecyclerAdapter(R.layout.mine_bottom_recycler_item,moreRecommendEntityList);
        more_recommend_recycler.setLayoutManager(new GridLayoutManager(getContext(),5));
        more_recommend_recycler.setAdapter(moreRecommendAdapter);
        moreRecommendAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                MineRecyclerEntity mineRecyclerEntity = moreRecommendEntityList.get(position);
                String title = mineRecyclerEntity.getTitle();
                switch (title) {
                    case "申请联合运营": {
                        Intent intent = new Intent(getContext(), CertificationActivity.class);
                        intent.putExtra("isServer", false);
                        startActivity(intent);
                        break;
                    }
                    case "商家认证": {
                        Intent intent = new Intent(getContext(), CertificationActivity.class);
                        intent.putExtra("isServer", true);
                        startActivity(intent);
                        break;
                    }
                    case "我要邀请":
                            showToast("暂未开放");
                        break;
                    case "资源置换":
                        showToast("暂未开放");
                        break;
                    default:
                        break;
                }
            }
        });
        for (int i = 0; i < moreRecommendImgs.length; i++) {
            MineRecyclerEntity mineRecyclerEntity = new MineRecyclerEntity(moreRecommendTitles[i], moreRecommendImgs[i]);
            if(Utils.getUserInfo().getUserType().equals("1")&&moreRecommendTitles[i].equals("申请联合运营")){
                continue;
            }
            moreRecommendEntityList.add(mineRecyclerEntity);
        }
        moreRecommendAdapter.notifyDataSetChanged();
    }
    @OnClick({R.id.mine_history_linear,R.id.mine_follow_linear,R.id.title_iv,R.id.mine_set_iv,R.id.nickname_tv,
            R.id.guarantee_now_btn,R.id.guarantee_all_iv,R.id.guarantee_all_tv,R.id.release_all_iv,R.id.release_all_tv,R.id.match_recent_now_btn})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.match_recent_now_btn:
                ReleaseServerActivity.startAty(getContext(),null);
                break;
            case R.id.mine_follow_linear:
                startActivity(new Intent(getContext(), MineFollowActivity.class));
                break;
            case R.id.mine_history_linear:
                startActivity(new Intent(getContext(), MineHistoryActivity.class));
                break;
            case R.id.title_iv:
            case R.id.nickname_tv:
            case R.id.mine_set_iv:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.guarantee_now_btn:
//                ServerHallActivity.startAty(getContext(),"","", Utils.getUserInfo().getUserType());
                GuaranteeOrderEntity guaranteeOrderEntity = new GuaranteeOrderEntity();
                guaranteeOrderEntity.setId("0");
                guaranteeOrderEntity.setInvited_user_id("0");
                String userType = Utils.getUserInfo().getUserType();
                guaranteeOrderEntity.setUserType(userType);
                if(userType.equals("1")){
                    guaranteeOrderEntity.setSellerLink(Utils.getUserInfo().getTelegram());
                }else {
                    guaranteeOrderEntity.setBuyerLink(Utils.getUserInfo().getTelegram());
                }
                TakeGuaranteeActivity.startAty(getContext(),guaranteeOrderEntity,true);
                break;
            case R.id.guarantee_all_iv:
            case R.id.guarantee_all_tv:
                startActivity(new Intent(getContext(), GuaranteeOrderActivity.class));
                break;
            case R.id.release_all_tv:
            case R.id.release_all_iv:
                startActivity(new Intent(getContext(), MineReleaseActivity.class));
                break;

            default:
                break;
        }
    }
    private void requestBanner() {
        HttpApiUtils.pathNormalRequest(getActivity(), this, RequestUtils.SYSTEM_NOTICE, "9", new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<MineBannerEntity> mineBannerEntities = JSONArray.parseArray(result, MineBannerEntity.class);
                mineBannerEntityArrayList.addAll(mineBannerEntities);
                if(mineBannerEntityArrayList.size()==0){
                    mine_banner_linear.setVisibility(View.GONE);
                }else {
                    mine_banner_linear.setVisibility(View.VISIBLE);
                }
                mine_fragment_banner
                        .setIndicatorVisibility(View.VISIBLE)
                        //轮播切换时间
                        .setInterval(2000)
                        //是否开启循环
                        .setCanLoop(true)
                        //是否开启自动轮播
                        .setAutoPlay(true)
                        //设置圆角
                        .setRoundCorner(7)
                        //指示器颜色
                        .setIndicatorColor(ContextCompat.getColor(getContext(),R.color.white), ContextCompat.getColor(getContext(),R.color.black))
                        .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                        //指示器位置
                        .setIndicatorGravity(IndicatorGravity.END)
                        //设置指示器的宽度/直径
                        .setIndicatorWidth(10)
                        //设置指示器样式( DASH:矩形  CIRCLE:圆点)
                        .setIndicatorStyle(IndicatorStyle.CIRCLE)
                        //页面滚动时间
                        .setScrollDuration(1000)
                        //绑定banner视图,以及数据的加载方式
                        .setHolderCreator(BannerViewHolder::new)
                        //点击事件
                        .setOnPageClickListener(position -> {
                            //页面跳转
                            MineBannerEntity mineBannerEntity = mineBannerEntityArrayList.get(position);
                            String liveShowPage = mineBannerEntity.getLiveShowPage();
                            String link = mineBannerEntity.getLink();
                            //liveShowPage 跳转 0无；1网页链接 拿link；2 富文本 拿content;3纸飞机 拿link
                            if(liveShowPage.equals("1")){
                                BannerWebViewActivity.startActivity(getContext(), link,mineBannerEntity.getTheme());
                            }else if(liveShowPage.equals("2")){
                                BannerWebViewActivity.startActivity(getContext(), mineBannerEntity.getContent(),mineBannerEntity.getTheme());
                            }else if(liveShowPage.equals("3")){
                                initContactPop(link);
                                contactPop.showAtLocation(nickname_tv, Gravity.BOTTOM,0,0);
                                Utils.darkenBackground(getActivity(),0.7f);
                            }

                        })
                        //绑定数据源
                        .create(mineBannerEntityArrayList);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
    private void initContactPop(String contactString) {
        ArrayList<ContactEntity>contactEntityArrayList = new ArrayList<>();
        contactEntityArrayList.add(new ContactEntity("telegram",contactString));
            contactPop = new ContactPop(getContext(),contactEntityArrayList);
    }



}