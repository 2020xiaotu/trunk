package com.example.new_application.ui.activity.home_activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.ClassificationDetailsAdapter;
import com.example.new_application.base.BaseFragment;
import com.example.new_application.bean.ClassificationDetailsChildEntity;
import com.example.new_application.bean.ClassificationDetailsEntity;
import com.example.new_application.bean.HomeLabelEntity;
import com.example.new_application.bean.RequestServerHallEvenEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
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

public class ServerHallFragment extends BaseFragment {
    @BindView(R.id.server_hall_refresh)
    SmartRefreshLayout server_hall_refresh;
    @BindView(R.id.server_hall_recycler)
    RecyclerView server_hall_recycler;
    @BindView(R.id.loading_linear)
    ConstraintLayout loading_linear;
    @BindView(R.id.error_linear)
    LinearLayout error_linear;
    @BindView(R.id.reload_tv)
    TextView reload_tv;
    @BindView(R.id.nodata_linear)
    LinearLayout nodata_linear;
    int pageNo=1;

    private ClassificationDetailsAdapter serverHallAdapter;
    ArrayList<ClassificationDetailsEntity> serverHallList = new ArrayList<>();

    int userType =2; //-1:?????????1??????????????????2???????????????
    ServerHallActivity serverHallActivity;
    @Override
    protected void init(Bundle savedInstanceState) {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        serverHallActivity = (ServerHallActivity) getActivity();
        initRecycler();
        requestServerHallList(false,false);
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_server_hall;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public static ServerHallFragment newInstance(int position){
        ServerHallFragment serverHallFragment = new ServerHallFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CommonStr.POSITION,position);
        serverHallFragment.setArguments(bundle);
        return serverHallFragment;
    }

    @Override
    public void errorRefresh() {
        super.errorRefresh();
        requestServerHallList(false,false);
    }

    @Override
    protected void initRefresh() {
        super.initRefresh();
        RefreshUtils.initRefreshLoadMore(getContext(), server_hall_refresh, new RefreshUtils.OnRefreshLoadMoreLintener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                pageNo=1;
                requestServerHallList(true,false);

            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                pageNo++;
                requestServerHallList(false,true);
            }
        });
    }

    /**
     * activity??????????????????????????????????????????
     * @param requestServerHallEvenEntity
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void requestServerListEven(RequestServerHallEvenEntity requestServerHallEvenEntity){
        pageNo=1;
        requestServerHallList(false,false);

    }
    public void requestServerHallList(boolean isRefresh, boolean isLoadMore) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("current",pageNo);
        data.put("latest",serverHallActivity.getLatest());
        data.put("myCollect",serverHallActivity.getMyCollect());
        if(StringMyUtil.isNotEmpty(serverHallActivity.getOneLevelClassification())){
            data.put("oneLevelClassification",serverHallActivity.getOneLevelClassification());
        }

        if(StringMyUtil.isNotEmpty(serverHallActivity.getTwoLevelClassification())){
            data.put("twoLevelClassification",serverHallActivity.getTwoLevelClassification());
        }
        if(serverHallActivity.getTitleList().get(getArguments().getInt(CommonStr.POSITION)).equals("????????????")){
            data.put("userType",2);
        }else {
            data.put("userType",1);
        }
        String title = serverHallActivity.getServer_hall_search_etv().getText().toString();
        if(StringMyUtil.isNotEmpty(title)){
            data.put("title",title);
        }
        int guaranteeType = serverHallActivity.getGuaranteeType();
        if(guaranteeType!=3){
            data.put("guaranteeType",guaranteeType);
        }
        HttpApiUtils.wwwShowLoadRequest(getActivity(), this, RequestUtils.SERVER_HALL_LIST, data, loading_linear, error_linear, reload_tv, server_hall_refresh, isLoadMore, isRefresh, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<ClassificationDetailsEntity> classificationDetailsEntityList = JSONArray.parseArray(result, ClassificationDetailsEntity.class);
                RefreshUtils.succse(pageNo,server_hall_refresh,loading_linear,nodata_linear,classificationDetailsEntityList.size(),isLoadMore,isRefresh,serverHallList);
                for (int i = 0; i < classificationDetailsEntityList.size(); i++) {
                    ClassificationDetailsEntity classificationDetailsEntity = classificationDetailsEntityList.get(i);
                    String checkStatus = classificationDetailsEntity.getCheckStatus();//checkStatus = 2 ????????????????????????
                    String priceType = classificationDetailsEntity.getPriceType();//????????????(1???????????????2??????????????????3?????????)
                    String officialMark = classificationDetailsEntity.getOfficialMark();//??????id. ????????????,???????????????????????????
                    ArrayList<ClassificationDetailsChildEntity> childList = new ArrayList<>();
                    Utils.initServerLabelList(classificationDetailsEntity, officialMark, childList);
                    classificationDetailsEntity.setClassificationDetailsChildEntityArrayList(childList);
                    serverHallList.add(classificationDetailsEntity);
                    serverHallAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(String msg) {
               RefreshUtils.fail(isRefresh,isLoadMore,pageNo,server_hall_refresh);
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

    private void initRecycler() {
        serverHallAdapter = new ClassificationDetailsAdapter(R.layout.classification_details_item, serverHallList);
        server_hall_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        server_hall_recycler.setAdapter(serverHallAdapter);
        serverHallAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ClassificationDetailsEntity classificationDetailsEntity = serverHallList.get(position);
                ServerDetailsActivity.startAty(ServerHallFragment.this,classificationDetailsEntity.getId());
            }
        });
    }


}