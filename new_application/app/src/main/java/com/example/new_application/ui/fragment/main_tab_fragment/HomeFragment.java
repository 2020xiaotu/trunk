package com.example.new_application.ui.fragment.main_tab_fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.alibaba.fastjson.JSONArray;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.BannerViewHolder;
import com.example.new_application.adapter.ClassificationDetailsAdapter;
import com.example.new_application.adapter.HomeCenterRecyclerAdapter;
import com.example.new_application.adapter.HomeCenterZSQAdapter;
import com.example.new_application.base.BaseFragment;
import com.example.new_application.base.HomeTwoLevelEntity;
import com.example.new_application.bean.ClassificationDetailsChildEntity;
import com.example.new_application.bean.ClassificationDetailsEntity;
import com.example.new_application.bean.ContactEntity;
import com.example.new_application.bean.HomeCenterEntity;
import com.example.new_application.bean.HomeLabelEntity;
import com.example.new_application.bean.MineBannerEntity;
import com.example.new_application.bean.PaoMaEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.home_activity.HonestyMerchantActivity;
import com.example.new_application.ui.activity.home_activity.SearchActivity;
import com.example.new_application.ui.activity.home_activity.ServerDetailsActivity;
import com.example.new_application.ui.activity.home_activity.ServerHallActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.HelpCenterActivity;
import com.example.new_application.ui.activity.user_info_activity.BannerWebViewActivity;
import com.example.new_application.ui.activity.user_info_activity.OnLineKeFuActivity;
import com.example.new_application.ui.pop.ContactPop;
import com.example.new_application.ui.pop.RightMenuPop;
import com.example.new_application.utils.GlideLoadViewUtil;
import com.example.new_application.utils.Utils;
import com.sunfusheng.marqueeview.MarqueeView;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.constants.IndicatorStyle;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.mtjsoft.www.gridviewpager_recycleview.GridViewPager;


public class HomeFragment extends BaseFragment   {
    @BindView(R.id.home_grid_viewpager2)
    GridViewPager home_grid_viewpager2;
    @BindView(R.id.home_marquee_text)
    MarqueeView home_marquee_text;
    @BindView(R.id.home_center_viewPager2)
    ViewPager2 home_center_viewPager2;
    @BindView(R.id.home_center_zsq_recycler)
    RecyclerView home_center_zsq_recycler;
    @BindView(R.id.home_center_more_tv)
    TextView home_center_more_tv;
    @BindView(R.id.home_center_more_iv)
    ImageView home_center_more_iv;
    @BindView(R.id.right_menu_iv)
    ImageView right_menu_iv;
    @BindView(R.id.home_banner_linear)
    LinearLayout home_banner_linear;
    @BindView(R.id.home_fragment_banner)
    BannerViewPager<MineBannerEntity, BannerViewHolder> home_fragment_banner;
    ArrayList<MineBannerEntity> homeBannerEntityArrayList = new ArrayList<>();
    @BindView(R.id.home_classification_details_recycler)
    RecyclerView home_classification_details_recycler;
    ClassificationDetailsAdapter classificationDetailsAdapter;
    ArrayList<ClassificationDetailsEntity>classificationDetailsEntityArrayList = new ArrayList<>();
    HomeCenterRecyclerAdapter homeCenterRecyclerAdapter;
    ArrayList<HomeCenterEntity>homeCenterEntityArrayList = new ArrayList<>();
    ArrayList<String> paomaDataList = new ArrayList<>();
    ArrayList<PaoMaEntity> PaoMaEntity = new ArrayList<>();
    int rowCount = 0;
    private List<Boolean> zsqList = new ArrayList<>();
    private HomeCenterZSQAdapter homeCenterZSQAdapter;
    private ContactPop contactPop;
    private RightMenuPop rightMenuPop;

    public static HomeFragment newInstance() {
        return  new HomeFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    protected void init(Bundle savedInstanceState) {
        initTopViewPager2();
        initPaoma();
        initCenterZsqRecycler();
        initCenterViewPager2();
        initBottomRecycler();
        requestBanner();
    }

    private void initBottomRecycler() {
        classificationDetailsAdapter = new ClassificationDetailsAdapter(R.layout.classification_details_item,classificationDetailsEntityArrayList);
        home_classification_details_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        home_classification_details_recycler.setAdapter(classificationDetailsAdapter);
        classificationDetailsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ClassificationDetailsEntity classificationDetailsEntity = classificationDetailsEntityArrayList.get(position);
                ServerDetailsActivity.startAty(HomeFragment.this,classificationDetailsEntity.getId());
            }
        });
        requestBottomRecycler();
    }
    private void requestBanner() {
        HttpApiUtils.pathNormalRequest(getActivity(), this, RequestUtils.SYSTEM_NOTICE, "10", new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<MineBannerEntity> mineBannerEntities = JSONArray.parseArray(result, MineBannerEntity.class);
                homeBannerEntityArrayList.addAll(mineBannerEntities);
                if(homeBannerEntityArrayList.size()==0){
                    home_banner_linear.setVisibility(View.GONE);
                }else {
                    home_banner_linear.setVisibility(View.VISIBLE);
                }
                home_fragment_banner
                        .setIndicatorVisibility(View.VISIBLE)
                        //??????????????????
                        .setInterval(2000)
                        //??????????????????
                        .setCanLoop(true)
                        //????????????????????????
                        .setAutoPlay(true)
                        //????????????
                        .setRoundCorner(7)
                        //???????????????
                        .setIndicatorColor(ContextCompat.getColor(getContext(),R.color.white), ContextCompat.getColor(getContext(),R.color.black))
                        .setIndicatorSlideMode(IndicatorSlideMode.SMOOTH)
                        //???????????????
                        .setIndicatorGravity(IndicatorGravity.END)
                        //????????????????????????/??????
                        .setIndicatorWidth(10)
                        //?????????????????????( DASH:??????  CIRCLE:??????)
                        .setIndicatorStyle(IndicatorStyle.CIRCLE)
                        //??????????????????
                        .setScrollDuration(1000)
                        //??????banner??????,???????????????????????????
                        .setHolderCreator(BannerViewHolder::new)
                        //????????????
                        .setOnPageClickListener(position -> {
                            //????????????
                            MineBannerEntity mineBannerEntity = homeBannerEntityArrayList.get(position);
                            String liveShowPage = mineBannerEntity.getLiveShowPage();
                            String link = mineBannerEntity.getLink();
                            //liveShowPage ?????? 0??????1???????????? ???link???2 ????????? ???content;3????????? ???link
                            if(liveShowPage.equals("1")){
                                BannerWebViewActivity.startActivity(getContext(), link,mineBannerEntity.getTheme());
                            }else if(liveShowPage.equals("2")){
                                BannerWebViewActivity.startActivity(getContext(), mineBannerEntity.getContent(),mineBannerEntity.getTheme());
                            }else if(liveShowPage.equals("3")){
                                initContactPop(link);
                                contactPop.showAtLocation(right_menu_iv, Gravity.BOTTOM,0,0);
                                Utils.darkenBackground(getActivity(),0.7f);
                            }

                        })
                        //???????????????
                        .create(homeBannerEntityArrayList);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
    private void requestBottomRecycler() {
        HttpApiUtils.wwwNormalRequest(getActivity(), this, RequestUtils.HOME_LIST, new HashMap<String, Object>(), new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<ClassificationDetailsEntity> classificationDetailsEntityList = JSONArray.parseArray(result, ClassificationDetailsEntity.class);
                for (int i = 0; i < classificationDetailsEntityList.size(); i++) {
                    ClassificationDetailsEntity classificationDetailsEntity = classificationDetailsEntityList.get(i);
                    String checkStatus = classificationDetailsEntity.getCheckStatus();//checkStatus = 2 ????????????????????????
                    String priceType = classificationDetailsEntity.getPriceType();//????????????(1???????????????2??????????????????3?????????)
                    String officialMark = classificationDetailsEntity.getOfficialMark();//??????id. ????????????,???????????????????????????
                    ArrayList<ClassificationDetailsChildEntity> childList = new ArrayList<>();
                    Utils.initServerLabelList(classificationDetailsEntity, officialMark, childList);
                    classificationDetailsEntity.setClassificationDetailsChildEntityArrayList(childList);
                    classificationDetailsEntityArrayList.add(classificationDetailsEntity);
                }
                classificationDetailsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    /**
     * ?????????????????????????????????
     * @param checkStatus
     * @param priceType
     * @return
     */
    @NotNull
    private  ArrayList<ClassificationDetailsChildEntity> handlerOfficialPriceLaBel(String checkStatus, String priceType) {
        ArrayList<ClassificationDetailsChildEntity> childList = new ArrayList<>();
        if (checkStatus.equals("2")) {
            ClassificationDetailsChildEntity classificationDetailsChildEntity = new ClassificationDetailsChildEntity("????????????");
            classificationDetailsChildEntity.setOfficial(true);
            childList.add(classificationDetailsChildEntity);
        }
        ClassificationDetailsChildEntity classificationDetailsChildEntity = new ClassificationDetailsChildEntity();
        if (priceType.equals("1")) {
            classificationDetailsChildEntity.setName("?????????");
            childList.add(classificationDetailsChildEntity);
        } else if (priceType.equals("3")) {
            classificationDetailsChildEntity.setName("??????");
            childList.add(classificationDetailsChildEntity);
        }
        return childList;
    }

    private void initCenterZsqRecycler() {
        homeCenterZSQAdapter = new HomeCenterZSQAdapter(R.layout.item_zsq,zsqList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        home_center_zsq_recycler.setLayoutManager(linearLayoutManager);
        home_center_zsq_recycler.setAdapter(homeCenterZSQAdapter);
    }

    private void initCenterViewPager2() {
        homeCenterRecyclerAdapter = new HomeCenterRecyclerAdapter(R.layout.home_center_viewpager2_item,homeCenterEntityArrayList);
        home_center_viewPager2.setAdapter(homeCenterRecyclerAdapter);
        homeCenterRecyclerAdapter.setOnItemClickListener(new HomeCenterRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                HomeCenterEntity homeCenterEntity = homeCenterEntityArrayList.get(position);
                //liveShowPage????????????//0??????1???????????????2???????????????3???????????????4)
                String liveShowPage = homeCenterEntity.getLiveShowPage();
                if(liveShowPage.equals("1")){
                    if(StringMyUtil.isNotEmpty(homeCenterEntity.getLink())){
                        BannerWebViewActivity.startActivity(getContext(),homeCenterEntity.getLink(),homeCenterEntity.getTheme());
                    }
                }else if(liveShowPage.equals("2")){
                    startActivity(new Intent(getContext(), HonestyMerchantActivity.class));
                }else if(liveShowPage.equals("3")){
                    startActivity(new Intent(getContext(), HelpCenterActivity.class));
                }
            }
        });
        home_center_viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                for (int i =0;i<zsqList.size();i++){
                    zsqList.set(i,false);
                }
                if(zsqList.size()>position){
                    zsqList.set(position,true);
                }
                homeCenterZSQAdapter.notifyDataSetChanged();
            }
        });
        /**
         * ??????center ????????????
         */
        requestCenterList();

    }
    private void initContactPop(String contactString) {
        ArrayList<ContactEntity>contactEntityArrayList = new ArrayList<>();
        contactEntityArrayList.add(new ContactEntity("telegram",contactString));
            contactPop = new ContactPop(getContext(),contactEntityArrayList);
    }
    private void requestCenterList() {
        HttpApiUtils.pathNormalRequest(getActivity(), this, RequestUtils.SYSTEM_NOTICE, "1", new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<HomeCenterEntity> homeCenterEntityList = JSONArray.parseArray(result, HomeCenterEntity.class);
                homeCenterEntityArrayList.addAll(homeCenterEntityList);
                if(homeCenterEntityList.size()==0){
                    home_center_viewPager2.setVisibility(View.GONE);
                    home_center_zsq_recycler.setVisibility(View.GONE);
                }else {
                    home_center_viewPager2.setVisibility(View.VISIBLE);
                    home_center_zsq_recycler.setVisibility(View.VISIBLE);
                }
                /**
                 * ?????????????????????
                 */
                int totalPage;
                if (homeCenterEntityArrayList.size()%4==0){
                    totalPage = homeCenterEntityArrayList.size()/4;
                }else {
                    totalPage = homeCenterEntityArrayList.size()/4+1;
                }
                for (int i=0;i<totalPage;i++){
                    if(i==0){
                        zsqList.add(true);//?????????????????????
                    }else {
                        zsqList.add(false);
                    }
                }
                homeCenterRecyclerAdapter.notifyDataSetChanged();
                homeCenterZSQAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    private void initPaoma() {
        home_marquee_text.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
            @Override
            public void onItemClick(int position, TextView textView) {
                PaoMaEntity paoMaEntity = PaoMaEntity.get(position);
                ServerHallActivity.startAty(getContext(),"","",Utils.getUserInfo().getUserType());
            }
        });
        HashMap<String, Object> data = new HashMap<>();
        data.put("current",1);
        data.put("size",20);
        HttpApiUtils.wwwNormalRequest(getActivity(), this, RequestUtils.HOME_PAOMA, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<PaoMaEntity> paoMaEntities = JSONArray.parseArray(result, PaoMaEntity.class);
                for (int i = 0; i < paoMaEntities.size(); i++) {
                    PaoMaEntity paoMaEntity = paoMaEntities.get(i);
                    paomaDataList.add(paoMaEntity.getContent());
                    PaoMaEntity.add(paoMaEntity);
                }
                home_marquee_text.startWithList(paomaDataList, R.anim.anim_bottom_in, R.anim.anim_top_out);
            }

            @Override
            public void onFail(String msg) {

            }
        });

    }


    private void initTopViewPager2() {
        HttpApiUtils.pathNormalRequest(getActivity(), this, RequestUtils.TWO_LEVEL_LIST, "-1", new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<HomeTwoLevelEntity> homeTwoLevelEntityList = JSONArray.parseArray(result, HomeTwoLevelEntity.class);
                int size = homeTwoLevelEntityList.size();
                /**
                 * ???????????????????????????????????????item???????????????
                 */
                if(size<=15){
                    HomeTwoLevelEntity homeTwoLevelEntity = new HomeTwoLevelEntity();
                    homeTwoLevelEntity.setName("????????????");
                    homeTwoLevelEntityList.add(homeTwoLevelEntity);
                }else {
                    HomeTwoLevelEntity homeTwoLevelEntity = new HomeTwoLevelEntity();
                    homeTwoLevelEntity.setName("????????????");
                    homeTwoLevelEntityList.add(14,homeTwoLevelEntity);
                    homeTwoLevelEntityList.add(homeTwoLevelEntityList.size(),homeTwoLevelEntity);
                }
                if(homeTwoLevelEntityList.size() ==0){
                    rowCount = 0;
                }else if(size <=5){
                    rowCount = 1;
                }else if(size >5 && size <11) {
                    rowCount = 2;
                }else {
                    rowCount = 3;
                }

                home_grid_viewpager2
                        // ?????????????????????
                        .setDataAllCount(homeTwoLevelEntityList.size())
                        //????????????
                        .setRowCount(rowCount)
                        // ?????????????????????????????????
//                .setCoverPageTransformer()
                        // ?????????????????????????????????
//                .setTopOrDownPageTransformer(TopOrDownPageTransformer.ModeType.MODE_DOWN)
                        // ???????????????????????????
                        .setGalleryPageTransformer()
                        // ????????????
                        .setImageTextLoaderInterface(new GridViewPager.ImageTextLoaderInterface() {
                            @Override
                            public void displayImageText(ImageView imageView, TextView textView, int position) {
                                HomeTwoLevelEntity homeTwoLevelEntity = homeTwoLevelEntityList.get(position);
                                textView.setText(homeTwoLevelEntity.getName());
                                if(homeTwoLevelEntity.getName().equals("????????????")){
                                    imageView.setImageResource(R.drawable.quanbfl_icon);
                                }else {
                                    GlideLoadViewUtil.FLoadCircleView(HomeFragment.this,homeTwoLevelEntity.getPicture(),imageView);
                                }
                            }
                        })
                        // Item??????
                        .setGridItemClickListener(new GridViewPager.GridItemClickListener() {
                            @Override
                            public void click(int position) {
                                if(Utils.isNotFastClick()){
                                    HomeTwoLevelEntity homeTwoLevelEntity= homeTwoLevelEntityList.get(position);
                                    ServerHallActivity.startAty(getContext(),"",homeTwoLevelEntity.getId(),Utils.getUserInfo().getUserType());
                                }
                            }
                        })
                        // ??????Item??????
                        .setGridItemLongClickListener(new GridViewPager.GridItemLongClickListener() {
                            @Override
                            public void longClick(int position) {
                            }
                        })
                        .show();
            }

            @Override
            public void onFail(String msg) {

            }
        });

    }
    @OnClick({R.id.home_center_more_iv,R.id.home_center_more_tv,R.id.right_menu_iv,R.id.classification_search_linear})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.home_center_more_iv:
            case R.id.home_center_more_tv:
               ServerHallActivity.startAty(getContext(),"","",Utils.getUserInfo().getUserType());
                break;
            case R.id.right_menu_iv:
                if(rightMenuPop ==null){
                    rightMenuPop = new RightMenuPop(getContext());
                }
                rightMenuPop.showAsDropDown(right_menu_iv,0,0,Gravity.BOTTOM);
                Utils.darkenBackground(getActivity(),0.7f);

                break;
            case R.id.classification_search_linear:
                startActivity(new Intent(getContext(), SearchActivity.class));
                break;
            default:
                break;
        }
    }

}