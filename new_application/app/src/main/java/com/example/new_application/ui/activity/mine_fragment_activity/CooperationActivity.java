package com.example.new_application.ui.activity.mine_fragment_activity;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.CertificationAdapter;
import com.example.new_application.adapter.CooperationAdapter;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.ContactEntity;
import com.example.new_application.bean.CooperationEntity;
import com.example.new_application.bean.HelpChildEntity;
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

public class CooperationActivity extends BaseActivity {
    @BindView(R.id.cooperation_recycler)
    RecyclerView cooperation_recycler;
    @BindView(R.id.cooperation_refresh)
    SmartRefreshLayout cooperation_refresh;
    @BindView(R.id.loading_linear)
    ConstraintLayout loading_linear;
    @BindView(R.id.error_linear)
    LinearLayout error_linear;
    @BindView(R.id.reload_tv)
    TextView reload_tv;
    @BindView(R.id.nodata_linear)
    LinearLayout nodata_linear;
    CooperationAdapter cooperationAdapter;
    ArrayList<CooperationEntity>cooperationEntityArrayList = new ArrayList<>();


    int pageNo = 1;
    private ContactPop contactPop;
    /**
     * 底部常见问题列表
     */
    private CertificationAdapter certificationAdapter;
    ArrayList<HelpChildEntity>certificationEntityArrayList = new ArrayList<>();
    RecyclerView problem_recycler;

    @Override
    public int getLayoutId() {
        return R.layout.activity_cooperation;
    }

    @Override
    public void getIntentData() {

    }
    @Override
    public void errorRefresh() {
        super.errorRefresh();
        requestList(false,false);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        CommonToolbarUtil.initToolbar(this,"联系客服");
        initRefresh();
        initRecycler();
        requestList(false,false);
    }

    private void initRefresh() {
        RefreshUtils.initRefreshLoadMore(this, cooperation_refresh, new RefreshUtils.OnRefreshLoadMoreLintener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                pageNo =1;
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
        data.put("size",10);
        HttpApiUtils.wwwShowLoadRequest(this, null, RequestUtils.COOPERATION, data, loading_linear, error_linear, reload_tv, cooperation_refresh, isLoadMore, isRefresh, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<CooperationEntity> cooperationEntities = JSONArray.parseArray(result, CooperationEntity.class);
                RefreshUtils.succse(pageNo,cooperation_refresh,loading_linear,nodata_linear,cooperationEntities.size(),isLoadMore,isRefresh,cooperationEntityArrayList);
                cooperationEntityArrayList.addAll(cooperationEntities);
                cooperationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
                RefreshUtils.fail(isRefresh,isLoadMore,pageNo,cooperation_refresh);
            }
        });
    }

    private void initRecycler() {
        cooperationAdapter = new CooperationAdapter(R.layout.cooparetion_recycler_item,cooperationEntityArrayList);
        cooperation_recycler.setLayoutManager(new LinearLayoutManager(this));
        cooperation_recycler.setAdapter(cooperationAdapter);
        cooperationAdapter.addChildClickViewIds(R.id.cooperation_btn);
        cooperationAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                CooperationEntity cooperationEntity = cooperationEntityArrayList.get(position);
                initContactPop(cooperationEntity.getContactInformation());
                contactPop.showAtLocation(cooperation_recycler, Gravity.BOTTOM,0,0);
                Utils.darkenBackground(CooperationActivity.this,0.5f);
            }
        });
        View footView  = LayoutInflater.from(this).inflate(R.layout.cooperation_foot_view,null);
        problem_recycler = footView.findViewById(R.id.problem_recycler);
        initFootViewRecycler();
        cooperationAdapter.addFooterView(footView);
    }

    private void initFootViewRecycler() {
        certificationAdapter = new CertificationAdapter(R.layout.certification_item,certificationEntityArrayList);
        problem_recycler.setLayoutManager(new LinearLayoutManager(this));
        problem_recycler.setAdapter(certificationAdapter);
        certificationAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                HelpChildEntity helpChildEntity = certificationEntityArrayList.get(position);
                helpChildEntity.setOpen(!helpChildEntity.isOpen());
                certificationAdapter.notifyItemChanged(position);
            }
        });
        requestProblemList();
    }
    private void requestProblemList() {
        HttpApiUtils.pathNormalRequest(this, null, RequestUtils.TIP_CONTENT, 4+"", new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<HelpChildEntity> helpChildEntities = JSONArray.parseArray(result, HelpChildEntity.class);
                certificationEntityArrayList.addAll(helpChildEntities);
                certificationAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
    private void initContactPop(String contactString) {
        ArrayList<ContactEntity>contactEntityArrayList = new ArrayList<>();
        contactEntityArrayList.add(new ContactEntity("telegram",contactString));
            contactPop = new ContactPop(this,contactEntityArrayList);
    }
}