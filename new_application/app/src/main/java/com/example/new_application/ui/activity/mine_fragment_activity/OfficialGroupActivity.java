package com.example.new_application.ui.activity.mine_fragment_activity;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.CooperationAdapter;
import com.example.new_application.adapter.OfficialGroupAdapter;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.ContactEntity;
import com.example.new_application.bean.CooperationEntity;
import com.example.new_application.bean.OfficialGroupEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.pop.ContactPop;
import com.example.new_application.utils.CommonToolbarUtil;
import com.example.new_application.utils.RefreshUtils;
import com.example.new_application.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class OfficialGroupActivity extends BaseActivity {
    @BindView(R.id.loading_linear)
    ConstraintLayout loading_linear;
    @BindView(R.id.error_linear)
    LinearLayout error_linear;
    @BindView(R.id.reload_tv)
    TextView reload_tv;
    @BindView(R.id.nodata_linear)
    LinearLayout nodata_linear;
    @BindView(R.id.official_group_refresh)
    SmartRefreshLayout official_group_refresh;
    @BindView(R.id.official_group_recycler)
    RecyclerView official_group_recycler;
    int pageNo =1 ;
    ArrayList<OfficialGroupEntity>officialGroupEntityArrayList = new ArrayList<>();
    OfficialGroupAdapter officialGroupAdapter;
    private ContactPop contactPop;

    @Override
    public int getLayoutId() {
        return R.layout.activity_official_group;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        CommonToolbarUtil.initToolbar(this,"官方群组");
        initRefresh();
        initRecycler();
        requestList(false,false);
    }

    private void initRecycler() {
        officialGroupAdapter = new OfficialGroupAdapter(R.layout.cooparetion_recycler_item,officialGroupEntityArrayList);
        official_group_recycler.setLayoutManager(new LinearLayoutManager(this));
        official_group_recycler.setAdapter(officialGroupAdapter);
        officialGroupAdapter.addChildClickViewIds(R.id.cooperation_btn);
        officialGroupAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                OfficialGroupEntity officialGroupEntity = officialGroupEntityArrayList.get(position);
                initContactPop(officialGroupEntity.getChannel());
                contactPop.showAtLocation(official_group_recycler, Gravity.BOTTOM,0,0);
                Utils.darkenBackground(OfficialGroupActivity.this,0.5f);
            }
        });
    }
    private void initContactPop(String contactString) {
        ArrayList<ContactEntity>contactEntityArrayList = new ArrayList<>();
        contactEntityArrayList.add(new ContactEntity("telegram",contactString));
        contactPop = new ContactPop(this,contactEntityArrayList);
    }
    @Override
    public void errorRefresh() {
        super.errorRefresh();
        requestList(false,false);
    }

    private void initRefresh() {
        RefreshUtils.initRefreshLoadMore(this, official_group_refresh, new RefreshUtils.OnRefreshLoadMoreLintener() {
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

    private void requestList(boolean isRefresh, boolean isLoadMore) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("current",pageNo);
        data.put("size",15);
        HttpApiUtils.wwwShowLoadRequest(this, null, RequestUtils.OFFICIAL_GROUP, data, loading_linear, error_linear, reload_tv, official_group_refresh, isLoadMore, isRefresh, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<OfficialGroupEntity> officialGroupEntityList = JSONArray.parseArray(result, OfficialGroupEntity.class);
                RefreshUtils.succse(pageNo,official_group_refresh,loading_linear,nodata_linear,officialGroupEntityList.size(),isLoadMore,isRefresh,officialGroupEntityArrayList);
                officialGroupEntityArrayList.addAll(officialGroupEntityList);
                officialGroupAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
                RefreshUtils.fail(isRefresh,isLoadMore,pageNo,official_group_refresh);
            }
        });
    }


}