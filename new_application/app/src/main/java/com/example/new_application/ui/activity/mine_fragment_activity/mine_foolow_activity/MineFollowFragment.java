package com.example.new_application.ui.activity.mine_fragment_activity.mine_foolow_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.example.new_application.R;
import com.example.new_application.adapter.FollowResourceAdapter;
import com.example.new_application.adapter.FollowStoreAdapter;
import com.example.new_application.base.BaseFragment;
import com.example.new_application.bean.ClassificationDetailsChildEntity;
import com.example.new_application.bean.ClassificationDetailsEntity;
import com.example.new_application.bean.HomeLabelEntity;
import com.example.new_application.bean.ServerDetailsEntity;
import com.example.new_application.bean.UserInfoEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.home_activity.ServerDetailsActivity;
import com.example.new_application.ui.activity.home_activity.StoreInformationActivity;
import com.example.new_application.utils.CommonStr;
import com.example.new_application.utils.RefreshUtils;
import com.example.new_application.utils.RouteUtils;
import com.example.new_application.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;


public class MineFollowFragment extends BaseFragment {
    @BindView(R.id.loading_linear)
    ConstraintLayout loading_linear;
    @BindView(R.id.error_linear)
    LinearLayout error_linear;
    @BindView(R.id.reload_tv)
    TextView reload_tv;
    @BindView(R.id.nodata_linear)
    LinearLayout nodata_linear;
    @BindView(R.id.follow_recycler)
    RecyclerView follow_recycler;
    @BindView(R.id.follow_refresh)
    SmartRefreshLayout follow_refresh;
    FollowResourceAdapter followResourceAdapter;
    ArrayList<ClassificationDetailsEntity> followResourceList = new ArrayList<>();

    FollowStoreAdapter followStoreAdapter;
    ArrayList<ServerDetailsEntity.MemberInfoVoBean> followStoreList = new ArrayList<>();

    int position=0;
    int pageNo=1;
    int type;

    @Override
    protected void init(Bundle savedInstanceState) {
        initIntentData();
        initRecycler();
    }

    private void initIntentData() {
        position = getArguments().getInt(CommonStr.POSITION);
        if(position==0){
            type =1;
        }else {
            type =2;
        }
    }

    @Override
    protected void initRefresh() {
        super.initRefresh();
        RefreshUtils.initRefreshLoadMore(getContext(), follow_refresh, new RefreshUtils.OnRefreshLoadMoreLintener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                pageNo=1;
                requestList(true,false);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
            pageNo++;
                requestList(false,true);
            }
        });
    }

    @Override
    public void errorRefresh() {
        super.errorRefresh();
       requestList(false,false);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_m_ine_follw;
    }
    private void initRecycler() {
        if(position==0){
            followResourceAdapter = new FollowResourceAdapter(getContext(), followResourceList);
            follow_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
            follow_recycler.setAdapter(followResourceAdapter);
//        WeSwipe.attach(follow_recycler);
            followResourceAdapter.setOnSlideListener(new FollowResourceAdapter.onSlideListener() {
                @Override
                public void onDeleteListener(int position) {
                    ClassificationDetailsEntity classificationDetailsEntity = followResourceList.get(position);
                    requestDelete( classificationDetailsEntity.getMemberCollectMessageId(),position);
                }

                @Override
                public void onShareListener(int position) {
                    RouteUtils.start2Share(getContext(),Utils.getSystemParamsEntity().getSystemParameterInfoMap().getDownLoadShareContent());
                }
            });

            followResourceAdapter.setOnItemClickListener(new FollowResourceAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ClassificationDetailsEntity classificationDetailsEntity = followResourceList.get(position);
                    ServerDetailsActivity.startAty(MineFollowFragment.this,classificationDetailsEntity.getId());
                }
            });
        }else {
            followStoreAdapter = new FollowStoreAdapter(getContext(), followStoreList);
            follow_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
            follow_recycler.setAdapter(followStoreAdapter);
            followStoreAdapter.setOnSlideListener(new FollowStoreAdapter.onSlideListener() {
                @Override
                public void onDeleteListener(int position) {
                    ServerDetailsEntity.MemberInfoVoBean memberInfoVoBean = followStoreList.get(position);
                    requestDelete( memberInfoVoBean.getMemberCollectMessageId(),position);
                }

                @Override
                public void onShareListener(int position) {
                    RouteUtils.start2Share(getContext(),Utils.getSystemParamsEntity().getSystemParameterInfoMap().getDownLoadShareContent());
                }
            });
            followStoreAdapter.setOnItemClickListener(new FollowStoreAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ServerDetailsEntity.MemberInfoVoBean memberInfoVoBean = followStoreList.get(position);
                    StoreInformationActivity.startAty(getContext(),memberInfoVoBean);
                }
            });
        }

        requestList(false,false);


    }

    private void requestDelete(String memberCollectMessageId,int itemPosition) {
        if(position == 0){
            requestDeleteResource(memberCollectMessageId,itemPosition);
        }else {
            requestDeleteStore(memberCollectMessageId,itemPosition);
        }
    }

    private void requestDeleteStore(String memberCollectMessageId,int itemPosition) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("ids",memberCollectMessageId);
        HttpApiUtils.wwwRequest(getActivity(), MineFollowFragment.this, RequestUtils.DELETE_FOLLOW_STORE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showToast("删除成功");
                followStoreAdapter.removeDataByPosition(itemPosition);
                if(followStoreList.size()==0){
                    nodata_linear.setVisibility(View.VISIBLE);
                    follow_refresh.setVisibility(View.GONE);
                }else {
                    nodata_linear.setVisibility(View.GONE);
                    follow_refresh.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    private void requestDeleteResource(String memberCollectMessageId,int itemPosition) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("ids",memberCollectMessageId);
        HttpApiUtils.wwwRequest(getActivity(), MineFollowFragment.this, RequestUtils.DELETE_FOLLOW_RESOURCE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showToast("删除成功");
                followResourceAdapter.removeDataByPosition(itemPosition);
                if(followResourceList.size()==0){
                    nodata_linear.setVisibility(View.VISIBLE);
                    follow_refresh.setVisibility(View.GONE);
                }else {
                    nodata_linear.setVisibility(View.GONE);
                    follow_refresh.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    private void requestList(boolean isRefresh,boolean isLoadMore) {
        if(position==0){
            requestMineFollowResource(isRefresh,isLoadMore);
        }else {
            requestMineFollowStore(isRefresh, isLoadMore);
        }
    }

    private void requestMineFollowStore(boolean isRefresh, boolean isLoadMore) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("current",pageNo);
        data.put("size",10);
        data.put("type",type);
        HttpApiUtils.wwwShowLoadRequest(getActivity(), MineFollowFragment.this, RequestUtils.MINE_FOLLOW_STORE, data, loading_linear, error_linear, reload_tv, follow_refresh, isLoadMore, isRefresh, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<ServerDetailsEntity.MemberInfoVoBean> memberInfoVoBeans = JSONArray.parseArray(result, ServerDetailsEntity.MemberInfoVoBean.class);
                RefreshUtils.succse(pageNo,follow_refresh,loading_linear,nodata_linear,memberInfoVoBeans.size(),isLoadMore,isRefresh, followStoreList);
                followStoreList.addAll(memberInfoVoBeans);
                followStoreAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
                RefreshUtils.fail(isRefresh,isLoadMore,pageNo,follow_refresh);
            }
        });
    }

    /**
     * 我收藏的帖子
     * @param isRefresh
     * @param isLoadMore
     */
    private void requestMineFollowResource(boolean isRefresh,boolean isLoadMore) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("current",pageNo);
        data.put("size",10);
        data.put("type",type);
        HttpApiUtils.wwwShowLoadRequest(getActivity(), MineFollowFragment.this, RequestUtils.MINE_FOLLOW_RESOURCE, data, loading_linear, error_linear, reload_tv, follow_refresh, isLoadMore, isRefresh, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<ClassificationDetailsEntity> classificationDetailsEntityList = JSONArray.parseArray(result, ClassificationDetailsEntity.class);
                RefreshUtils.succse(pageNo,follow_refresh,loading_linear,nodata_linear,classificationDetailsEntityList.size(),isLoadMore,isRefresh, followResourceList);
                for (int i = 0; i < classificationDetailsEntityList.size(); i++) {
                    ClassificationDetailsEntity classificationDetailsEntity = classificationDetailsEntityList.get(i);
                    String checkStatus = classificationDetailsEntity.getCheckStatus();//checkStatus = 2 官方显示官方认证
                    String priceType = classificationDetailsEntity.getPriceType();//价格类型(1：一口价，2：范围价格，3：议价)
                    String officialMark = classificationDetailsEntity.getOfficialMark();//标签id. 逗号拼接,用于筛选下面的标签
                    ArrayList<ClassificationDetailsChildEntity> childList = new ArrayList<>();
                    Utils.initServerLabelList(classificationDetailsEntity, officialMark, childList);
                    classificationDetailsEntity.setClassificationDetailsChildEntityArrayList(childList);
                    followResourceList.add(classificationDetailsEntity);
                    followResourceAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(String msg) {
                RefreshUtils.fail(isRefresh,isLoadMore,pageNo,follow_refresh);
            }
        });
    }

    /**
     * 处理官方认证和价格标签
     * @param checkStatus
     * @param priceType
     * @return
     */
    @NotNull
    private  ArrayList<ClassificationDetailsChildEntity> handlerOfficialPriceLaBel(String checkStatus, String priceType) {
        ArrayList<ClassificationDetailsChildEntity> childList = new ArrayList<>();
        if (checkStatus.equals("2")) {
            ClassificationDetailsChildEntity classificationDetailsChildEntity = new ClassificationDetailsChildEntity("官方认证");
            classificationDetailsChildEntity.setOfficial(true);
            childList.add(classificationDetailsChildEntity);
        }
        ClassificationDetailsChildEntity classificationDetailsChildEntity = new ClassificationDetailsChildEntity();
        if (priceType.equals("1")) {
            classificationDetailsChildEntity.setName("一口价");
            childList.add(classificationDetailsChildEntity);
        } else if (priceType.equals("3")) {
            classificationDetailsChildEntity.setName("议价");
            childList.add(classificationDetailsChildEntity);
        }
        return childList;
    }

    public static MineFollowFragment newInstance(int position){
        MineFollowFragment mineFollowFragment = new MineFollowFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CommonStr.POSITION,position);
        mineFollowFragment.setArguments(bundle);
        return mineFollowFragment;
    }
}