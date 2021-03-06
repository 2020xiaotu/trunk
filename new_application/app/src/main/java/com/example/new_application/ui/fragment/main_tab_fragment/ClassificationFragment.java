package com.example.new_application.ui.fragment.main_tab_fragment;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.BannerViewHolder;
import com.example.new_application.adapter.ClassificationParentAdapter;
import com.example.new_application.adapter.ClassificationRecyclerAdapter;
import com.example.new_application.base.BaseFragment;
import com.example.new_application.bean.ClassificationEntity;
import com.example.new_application.bean.ClassificationParentEntity;
import com.example.new_application.bean.ContactEntity;
import com.example.new_application.bean.HomeCenterEntity;
import com.example.new_application.bean.MineBannerEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.home_activity.SearchActivity;
import com.example.new_application.ui.activity.home_activity.ServerHallActivity;
import com.example.new_application.ui.activity.user_info_activity.BannerWebViewActivity;
import com.example.new_application.ui.pop.AllClassificationPop;
import com.example.new_application.ui.pop.ContactPop;
import com.example.new_application.utils.RefreshUtils;
import com.example.new_application.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.zhpan.bannerview.BannerViewPager;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.constants.IndicatorStyle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ClassificationFragment extends BaseFragment {
    @BindView(R.id.classification_search_linear)
    LinearLayout classification_search_linear;
    @BindView(R.id.classification_recycler)
    RecyclerView classification_recycler;
    ClassificationRecyclerAdapter classificationRecyclerAdapter;
    ArrayList<ClassificationEntity>classificationEntityArrayList = new ArrayList<>();
    @BindView(R.id.two_classification_recycler)
    RecyclerView two_classification_recycler;
    @BindView(R.id.two_classification_refresh)
    SmartRefreshLayout two_classification_refresh;
    ClassificationParentAdapter classificationParentAdapter;
    ArrayList<ClassificationParentEntity>classificationParentEntityArrayList = new ArrayList<>();

    @BindView(R.id.classification_banner_linear)
    LinearLayout classification_banner_linear;
    @BindView(R.id.classification_fragment_banner)
    BannerViewPager<MineBannerEntity, BannerViewHolder> classification_fragment_banner;
    ArrayList<MineBannerEntity>mineBannerEntityArrayList = new ArrayList<>();
    private ContactPop contactPop;

    public static ClassificationFragment newInstance() {
        return  new ClassificationFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_classification;
    }
    @Override
    protected void init(Bundle savedInstanceState) {
        initRecycler();
        requestBanner();
        initTwoLevelRecycler();
        requestServerList(false);
    }

    @Override
    protected void initRefresh() {
        super.initRefresh();
        RefreshUtils.initRefresh(getContext(), two_classification_refresh, new RefreshUtils.OnRefreshLintener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                requestServerList(true);
            }
        });
    }
    private void requestBanner() {
        HttpApiUtils.pathNormalRequest(getActivity(), this, RequestUtils.SYSTEM_NOTICE, "8", new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                    List<MineBannerEntity> mineBannerEntities = JSONArray.parseArray(result, MineBannerEntity.class);
                    mineBannerEntityArrayList.addAll(mineBannerEntities);
                    if(mineBannerEntityArrayList.size()==0){
                        classification_banner_linear.setVisibility(View.GONE);
                    }else {
                        classification_banner_linear.setVisibility(View.VISIBLE);
                    }
                    classification_fragment_banner
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
                                MineBannerEntity mineBannerEntity = mineBannerEntityArrayList.get(position);
                                String liveShowPage = mineBannerEntity.getLiveShowPage();
                                String link = mineBannerEntity.getLink();
                                //liveShowPage ?????? 0??????1???????????? ???link???2 ????????? ???content;3????????? ???link
                                if(liveShowPage.equals("1")){
                                    BannerWebViewActivity.startActivity(getContext(), link,mineBannerEntity.getTheme());
                                }else if(liveShowPage.equals("2")){
                                    BannerWebViewActivity.startActivity(getContext(), mineBannerEntity.getContent(),mineBannerEntity.getTheme());
                                }else if(liveShowPage.equals("3")){
                                    initContactPop(link);
                                    contactPop.showAtLocation(classification_recycler, Gravity.BOTTOM,0,0);
                                    Utils.darkenBackground(getActivity(),0.7f);
                                }
                            })
                            //???????????????
                            .create(mineBannerEntityArrayList);
                }


            @Override
            public void onFail(String msg) {

            }
        });
    }
    @OnClick({R.id.classification_search_linear})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.classification_search_linear:
                startActivity(new Intent(getContext(),SearchActivity.class));
                break;
            default:
                break;
        }
    }
    private void initContactPop(String contactString) {
        ArrayList<ContactEntity>contactEntityArrayList = new ArrayList<>();
        contactEntityArrayList.add(new ContactEntity("telegram",contactString));
            contactPop = new ContactPop(getContext(),contactEntityArrayList);
    }
    private void initTwoLevelRecycler() {
        classificationParentAdapter = new ClassificationParentAdapter(R.layout.classification_parent_recycler_item,classificationParentEntityArrayList);
        two_classification_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        two_classification_recycler.setAdapter(classificationParentAdapter);
        classificationParentAdapter.setOnItemClickListener(new ClassificationParentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int parentPosition, int childPosition) {
                if(Utils.isNotFastClick()){
                    ClassificationParentEntity classificationParentEntity = classificationParentEntityArrayList.get(parentPosition);
                    ServerHallActivity.startAty(getContext(),classificationParentEntity.getId(),classificationParentEntity.getChildList().get(childPosition).getId(),Utils.getUserInfo().getUserType());
                }

            }
        });
        two_classification_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                /**
                 * ????????????, ????????????
                 */
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                // ??????LinearLayoutManager??????????????????????????????????????????view???????????????
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                    //?????????????????????view?????????
                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                    //??????????????????item????????????
                    int lastVisibleItemPosition = linearManager.findLastVisibleItemPosition();
                    ClassificationParentEntity classificationParentEntity = classificationParentEntityArrayList.get(firstItemPosition);
                    String twoLevelName = classificationParentEntity.getName();
                    if(lastVisibleItemPosition!=classificationEntityArrayList.size()-1){
                        /**
                         * ???????????????????????????????????????????????????item,???????????????????????????,
                         */
                        for (int i = 0; i < classificationEntityArrayList.size(); i++) {
                            ClassificationEntity classificationEntity = classificationEntityArrayList.get(i);
                            String oneLevelName = classificationEntity.getName();
                            if(oneLevelName.equals(twoLevelName)){
                                classificationEntity.setCheck(true);
                            }else {
                                classificationEntity.setCheck(false);
                            }

                        }
                        classificationRecyclerAdapter.notifyDataSetChanged();
                    }

                }
            }
        });
    }

    protected boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }
    private void initRecycler() {
        classificationRecyclerAdapter = new ClassificationRecyclerAdapter(R.layout.classification_recycler_item_layout,classificationEntityArrayList);
        classification_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        classification_recycler.setAdapter(classificationRecyclerAdapter);
        classificationRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ClassificationEntity classificationEntity = classificationEntityArrayList.get(position);

                for (int i = 0; i < classificationEntityArrayList.size(); i++) {
                    classificationEntityArrayList.get(i).setCheck(false);
                }
                classificationEntity.setCheck(true);
                classificationRecyclerAdapter.notifyDataSetChanged();
                /**
                 * ???????????? ????????????
                 */
                    for (int i = 0; i < classificationParentEntityArrayList.size(); i++) {
                        String name = classificationParentEntityArrayList.get(i).getName();
                        if(name.equals(classificationEntity.getName())){
                            LinearLayoutManager layoutManager = (LinearLayoutManager) two_classification_recycler.getLayoutManager();
                            layoutManager.scrollToPositionWithOffset(position,0);
                        }
                    }


            }
        });
    }

    private void requestServerList(boolean isRefresh) {
        HttpApiUtils.wwwNormalRequest(getActivity(), this, RequestUtils.ALL_CLASSIFICATION, new HashMap<String, Object>(),  new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                if(isRefresh){
                    two_classification_refresh.finishRefresh(true);
                }
                classificationEntityArrayList.clear();
                classificationParentEntityArrayList.clear();
                List<ClassificationParentEntity> classificationParentEntityList = JSONArray.parseArray(result, ClassificationParentEntity.class);
                for (int i = 0; i < classificationParentEntityList.size(); i++) {
                    /**
                     * ????????????????????????????????????
                     */
                    ClassificationParentEntity classificationParentEntity = classificationParentEntityList.get(i);
                    List<ClassificationParentEntity.ChildListBean> childList = classificationParentEntity.getChildList();
                    ClassificationEntity classificationEntity = new ClassificationEntity(classificationParentEntity.getName(), childList.size()+"");
                    if(i == 0){
                        classificationEntity.setCheck(true);
                    }
                    classificationEntityArrayList.add(classificationEntity);
                    /**
                     *        ????????????????????????????????????
                     */

                    classificationParentEntityArrayList.add(classificationParentEntity);
                }
                classificationRecyclerAdapter.notifyDataSetChanged();
                classificationParentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
                if(isRefresh){
                    two_classification_refresh.finishRefresh(false);
                }
            }
        });
    }
}