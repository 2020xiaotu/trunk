package com.example.new_application.ui.activity.mine_fragment_activity.mine_history_activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.StoreHistoryAdapter;
import com.example.new_application.adapter.ResourceHistoryAdapter;
import com.example.new_application.base.BaseFragment;
import com.example.new_application.bean.ClassificationDetailsChildEntity;
import com.example.new_application.bean.ClassificationDetailsEntity;
import com.example.new_application.bean.HistoryDeleteAllEvenModel;
import com.example.new_application.bean.HistoryRequestDeleteEvenModel;
import com.example.new_application.bean.HistoryShowDeleteEvenModel;
import com.example.new_application.bean.HomeLabelEntity;
import com.example.new_application.bean.ServerDetailsEntity;
import com.example.new_application.bean.UserInfoEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.home_activity.ServerDetailsActivity;
import com.example.new_application.ui.activity.home_activity.StoreInformationActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.mine_foolow_activity.MineFollowFragment;
import com.example.new_application.utils.CommonStr;
import com.example.new_application.utils.RefreshUtils;
import com.example.new_application.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class MineHistoryFragment extends BaseFragment {
    @BindView(R.id.loading_linear)
    ConstraintLayout loading_linear;
    @BindView(R.id.error_linear)
    LinearLayout error_linear;
    @BindView(R.id.reload_tv)
    TextView reload_tv;
    @BindView(R.id.nodata_linear)
    LinearLayout nodata_linear;
    @BindView(R.id.history_recycler)
    RecyclerView history_recycler;
    @BindView(R.id.history_refresh)
    SmartRefreshLayout history_refresh;
    ResourceHistoryAdapter resourceHistoryAdapter;
    ArrayList<ClassificationDetailsEntity> resourceHistoryList = new ArrayList<>();
    MineHistoryActivity mineHistoryActivity;

    StoreHistoryAdapter storeHistoryAdapter;
    ArrayList<ServerDetailsEntity.MemberInfoVoBean> storeHistoryList = new ArrayList<>();
    int position=0;
    int pageNo=1;
    int type;
    @Override
    protected void init(Bundle savedInstanceState) {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        mineHistoryActivity= (MineHistoryActivity) getActivity();
        initIntentData();
        initRecycler();
    }

    private void initIntentData() {
        position = getArguments().getInt(CommonStr.POSITION);
        if(position==0){
            type =0;
        }else {
            type =3;
        }
    }
    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_history;
    }
    public static MineHistoryFragment newInstance(int position){
        MineHistoryFragment mineHistoryFragment = new MineHistoryFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CommonStr.POSITION,position);
        mineHistoryFragment.setArguments(bundle);
        return mineHistoryFragment;
    }

    /**
     * 点击右上角删除按钮, 显示列表的删除选中按钮
     * @param historyShowDeleteEvenModel
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void showDelete(HistoryShowDeleteEvenModel historyShowDeleteEvenModel){
        if(position == historyShowDeleteEvenModel.getCurrentPosition()){
            if(position == 0){
                for (int i = 0; i < resourceHistoryList.size(); i++) {
                    resourceHistoryList.get(i).setShow(historyShowDeleteEvenModel.isShow());
                    resourceHistoryAdapter.notifyDataSetChanged();
                }
            }else {
                for (int i = 0; i < storeHistoryList.size(); i++) {
                    storeHistoryList.get(i).setShow(historyShowDeleteEvenModel.isShow());
                    storeHistoryAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    /**
     * 点击删除全部按钮发送的通知, 将所有列表改为选中状态, 准备删除
     * @param historyDeleteAllEvenModel
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void chooseAll(HistoryDeleteAllEvenModel historyDeleteAllEvenModel){
        if(position == historyDeleteAllEvenModel.getCurrentPosition()){
            if(position == 0){
                for (int i = 0; i < resourceHistoryList.size(); i++) {
                    resourceHistoryList.get(i).setCheck(true);
                    resourceHistoryAdapter.notifyDataSetChanged();
                }
                mineHistoryActivity. delete_choose_tv.setText("删除所选("+resourceHistoryList.size()+")");
            }else {
                for (int i = 0; i < storeHistoryList.size(); i++) {
                    storeHistoryList.get(i).setCheck(true);
                    storeHistoryAdapter.notifyDataSetChanged();
                }
                mineHistoryActivity. delete_choose_tv.setText("删除所选("+storeHistoryList.size()+")");
            }
        }
    }

    /**
     * 请求删除
     *
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void delete(HistoryRequestDeleteEvenModel historyRequestDeleteEvenModel){
        if(position == historyRequestDeleteEvenModel.getCurrentPosition()){
            requestDelete();
        }
    }
    @Override
    protected void initRefresh() {
        super.initRefresh();
        RefreshUtils.initRefreshLoadMore(getContext(), history_refresh, new RefreshUtils.OnRefreshLoadMoreLintener() {
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
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initRecycler() {
        if(position==0){
            resourceHistoryAdapter = new ResourceHistoryAdapter(R.layout.history_recycler_item, resourceHistoryList);
            history_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
            history_recycler.setAdapter(resourceHistoryAdapter);
            resourceHistoryAdapter.addChildClickViewIds(R.id.delete_selector_iv);
            resourceHistoryAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
                @Override
                public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                    ClassificationDetailsEntity classificationDetailsEntity = resourceHistoryList.get(position);
                    boolean check = classificationDetailsEntity.isCheck();
                    classificationDetailsEntity.setCheck(!check);
                    resourceHistoryAdapter.notifyDataSetChanged();
                    int count = 0;
                    for (int i = 0; i < resourceHistoryList.size(); i++) {
                        ClassificationDetailsEntity classificationDetailsEntity1 = resourceHistoryList.get(i);
                        if(classificationDetailsEntity1.isCheck()){
                            count++;
                        }
                    }
                    mineHistoryActivity.delete_choose_tv.setText("删除所选("+count+")");
                }
            });
            resourceHistoryAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                    ClassificationDetailsEntity classificationDetailsEntity = resourceHistoryList.get(position);
                    ServerDetailsActivity.startAty(MineHistoryFragment.this,classificationDetailsEntity.getId());
                }
            });
        }else {
            storeHistoryAdapter = new StoreHistoryAdapter(getContext(), storeHistoryList);
            history_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
            history_recycler.setAdapter(storeHistoryAdapter);
            storeHistoryAdapter.setOnItemClickListener(new StoreHistoryAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ServerDetailsEntity.MemberInfoVoBean memberInfoVoBean = storeHistoryList.get(position);
                    switch (view.getId()){
                        case R.id.history_wrap_constraintLayout:
                            StoreInformationActivity.startAty(getContext(),memberInfoVoBean);
                            break;
                        case R.id.delete_selector_iv:

                            boolean check = memberInfoVoBean.isCheck();
                            memberInfoVoBean.setCheck(!check);
                            storeHistoryAdapter.notifyDataSetChanged();
                            int count = 0;
                            for (int i = 0; i < storeHistoryList.size(); i++) {
                                ServerDetailsEntity.MemberInfoVoBean memberInfoVoBean1 = storeHistoryList.get(i);
                                if(memberInfoVoBean1.isCheck()){
                                    count++;
                                }
                            }
                            mineHistoryActivity.delete_choose_tv.setText("删除所选("+count+")");
                            break;
                        default:
                            break;
                    }

                }
            });


        }
        requestList(false,false);

    }
    private void requestDelete() {
        if(position == 0){
            String memberCollectMessageId="";
            for (int i = 0; i < resourceHistoryList.size(); i++) {
                ClassificationDetailsEntity classificationDetailsEntity = resourceHistoryList.get(i);
                if(classificationDetailsEntity.isCheck()){
                    memberCollectMessageId+=classificationDetailsEntity.getMemberCollectMessageId()+",";
                }
            }
            requestDeleteResource(memberCollectMessageId.substring(0,memberCollectMessageId.length()-1));
        }else {
            String memberCollectMessageId="";
            for (int i = 0; i < storeHistoryList.size(); i++) {
                ServerDetailsEntity.MemberInfoVoBean memberInfoVoBean = storeHistoryList.get(i);
                if(memberInfoVoBean.isCheck()){
                    memberCollectMessageId+=memberInfoVoBean.getMemberCollectMessageId()+",";
                }
            }
            requestDeleteStore(memberCollectMessageId.substring(0,memberCollectMessageId.length()-1));
        }
    }

    /**
     * 删除帖子记录
     * @param memberCollectMessageId
     */
    private void requestDeleteResource(String memberCollectMessageId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("ids",memberCollectMessageId);
        HttpApiUtils.wwwRequest(getActivity(), this, RequestUtils.DELETE_FOLLOW_RESOURCE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showToast("删除成功");
                    for (int i = 0; i < resourceHistoryList.size(); i++) {
                        ClassificationDetailsEntity classificationDetailsEntity = resourceHistoryList.get(i);
                        if(classificationDetailsEntity.isCheck()){
                            resourceHistoryList.remove(classificationDetailsEntity);
                            i--;
                        }
                        resourceHistoryAdapter.notifyDataSetChanged();
                    }
                if(resourceHistoryList.size()==0){
                    nodata_linear.setVisibility(View.VISIBLE);
                    history_refresh.setVisibility(View.GONE);
                }else {
                    nodata_linear.setVisibility(View.GONE);
                    history_refresh.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    /**
     * 删除店铺记录
     * @param memberCollectMessageId
     */
    private void requestDeleteStore(String memberCollectMessageId) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("ids",memberCollectMessageId);
        HttpApiUtils.wwwRequest(getActivity(), this, RequestUtils.DELETE_FOLLOW_STORE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showToast("删除成功");
                for (int i = 0; i < storeHistoryList.size(); i++) {
                    ServerDetailsEntity.MemberInfoVoBean memberInfoVoBean = storeHistoryList.get(i);
                    if(memberInfoVoBean.isCheck()){
                        storeHistoryList.remove(memberInfoVoBean);
                        i--;
                    }
                    storeHistoryAdapter.notifyDataSetChanged();
                }
                if(storeHistoryList.size()==0){
                    nodata_linear.setVisibility(View.VISIBLE);
                    history_refresh.setVisibility(View.GONE);
                }else {
                    nodata_linear.setVisibility(View.GONE);
                    history_refresh.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
    private void requestList(boolean isRefresh,boolean isLoadMore) {
        if(position==0){
            requestResourceHistory(isRefresh,isLoadMore);
        }else {
            requestStoreHistory(isRefresh, isLoadMore);
        }
    }

    /**
     * 请求浏览过的店铺记录列表
     * @param isRefresh
     * @param isLoadMore
     */
    private void requestStoreHistory(boolean isRefresh, boolean isLoadMore) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("current",pageNo);
        data.put("size",10);
        data.put("type",type);
        HttpApiUtils.wwwShowLoadRequest(getActivity(), this, RequestUtils.MINE_FOLLOW_STORE, data, loading_linear, error_linear, reload_tv, history_refresh, isLoadMore, isRefresh, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<ServerDetailsEntity.MemberInfoVoBean> memberInfoVoBeans = JSONArray.parseArray(result, ServerDetailsEntity.MemberInfoVoBean.class);
                RefreshUtils.succse(pageNo,history_refresh,loading_linear,nodata_linear,memberInfoVoBeans.size(),isLoadMore,isRefresh, storeHistoryList);
                storeHistoryList.addAll(memberInfoVoBeans);
                storeHistoryAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
                RefreshUtils.fail(isRefresh,isLoadMore,pageNo,history_refresh);
            }
        });
    }

    /**
     * 请求浏览过的的帖子列表
     * @param isRefresh
     * @param isLoadMore
     */
    private void requestResourceHistory(boolean isRefresh, boolean isLoadMore) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("current",pageNo);
        data.put("size",10);
        data.put("type",type);
        HttpApiUtils.wwwShowLoadRequest(getActivity(), this, RequestUtils.MINE_FOLLOW_RESOURCE, data, loading_linear, error_linear, reload_tv, history_refresh, isLoadMore, isRefresh, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<ClassificationDetailsEntity> classificationDetailsEntityList = JSONArray.parseArray(result, ClassificationDetailsEntity.class);
                RefreshUtils.succse(pageNo,history_refresh,loading_linear,nodata_linear,classificationDetailsEntityList.size(),isLoadMore,isRefresh, resourceHistoryList);
                for (int i = 0; i < classificationDetailsEntityList.size(); i++) {
                    ClassificationDetailsEntity classificationDetailsEntity = classificationDetailsEntityList.get(i);
                    String checkStatus = classificationDetailsEntity.getCheckStatus();//checkStatus = 2 官方显示官方认证
                    String priceType = classificationDetailsEntity.getPriceType();//价格类型(1：一口价，2：范围价格，3：议价)
                    String officialMark = classificationDetailsEntity.getOfficialMark();//标签id. 逗号拼接,用于筛选下面的标签
                    ArrayList<ClassificationDetailsChildEntity> childList = new ArrayList<>();
                    Utils.initServerLabelList(classificationDetailsEntity, officialMark, childList);
                    classificationDetailsEntity.setClassificationDetailsChildEntityArrayList(childList);
                    resourceHistoryList.add(classificationDetailsEntity);
                    resourceHistoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(String msg) {
                RefreshUtils.fail(isRefresh,isLoadMore,pageNo,history_refresh);
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
}