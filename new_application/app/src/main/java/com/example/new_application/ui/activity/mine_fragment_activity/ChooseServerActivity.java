package com.example.new_application.ui.activity.mine_fragment_activity;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.ChooseServerParentAdapter;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.ChooseServerParentEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.utils.CommonToolbarUtil;
import com.example.new_application.utils.RefreshUtils;
import com.example.new_application.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ChooseServerActivity extends BaseActivity {

    @BindView(R.id.choose_server_refresh)
    SmartRefreshLayout choose_server_refresh;
    @BindView(R.id.choose_server_recycler)
    RecyclerView choose_server_recycler;
    @BindView(R.id.toolbar_right_tv)
    TextView toolbar_right_tv;
    @BindView(R.id.error_linear)
    LinearLayout errorLinear;
    @BindView(R.id.reload_tv)
    TextView reloadTv;
    @BindView(R.id.loading_linear)
    ConstraintLayout loadingLinear;
    @BindView(R.id.nodata_linear)
    LinearLayout nodataLinear;
    ChooseServerParentAdapter chooseServerParentAdapter;
    ArrayList<ChooseServerParentEntity>chooseServerParentEntityArrayList = new ArrayList<>();
    ArrayList<String> selectorServerList;
    @Override
    public int getLayoutId() {
        return R.layout.activity_c_hoose_server;
    }

    @Override
    public void getIntentData() {
        selectorServerList = getIntent().getStringArrayListExtra("selectorServerList");
    }

    @Override
    public void errorRefresh() {
        super.errorRefresh();
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initToolbar();
        initRecycler();
        requestServerList(false,false);
        initRefresh();
    }
    public static void startAcy(Context context,ArrayList<String> selectorServerList){
        Intent intent = new Intent(context, ChooseServerActivity.class);
        intent.putStringArrayListExtra("selectorServerList",selectorServerList);
        context.startActivity(intent);
    }
    private void initRefresh() {
        RefreshUtils.initRefresh(this, choose_server_refresh, new RefreshUtils.OnRefreshLintener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                requestServerList(false,false);
            }
        });
    }

    private void initRecycler() {
        chooseServerParentAdapter = new ChooseServerParentAdapter(R.layout.choose_server_parent_item,chooseServerParentEntityArrayList);
        choose_server_recycler.setLayoutManager(new LinearLayoutManager(this));
        choose_server_recycler.setAdapter(chooseServerParentAdapter);
        chooseServerParentAdapter.addChildClickViewIds(R.id.choose_server_parent_wrap_relativeLayout);
        chooseServerParentAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                ChooseServerParentEntity chooseServerParentEntity = chooseServerParentEntityArrayList.get(position);
                chooseServerParentEntity.setOpen(!chooseServerParentEntity.isOpen());
                chooseServerParentAdapter.notifyItemChanged(position);
            }
        });
    }

    private void requestServerList(boolean isLoadMore,boolean isRefresh) {
        HttpApiUtils.wwwShowLoadRequest(this, null, RequestUtils.ALL_CLASSIFICATION, new HashMap<String, Object>(), loadingLinear, errorLinear, reloadTv, choose_server_refresh, isLoadMore, isRefresh, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<ChooseServerParentEntity> chooseServerParentEntities = JSONArray.parseArray(result, ChooseServerParentEntity.class);
                RefreshUtils.succse(0,choose_server_refresh,loadingLinear,nodataLinear,chooseServerParentEntities.size(),isLoadMore,isRefresh,chooseServerParentEntityArrayList);
                /**
                 * 筛选用户已经选择过的服务, 默认为选中状态
                 */
                for (int i = 0; i < chooseServerParentEntities.size(); i++) {
                    List<ChooseServerParentEntity.ChildListBean> childList = chooseServerParentEntities.get(i).getChildList();
                    for (int j = 0; j < childList.size(); j++) {
                        ChooseServerParentEntity.ChildListBean childListBean = childList.get(j);
                        for (int k = 0; k < selectorServerList.size(); k++) {
                            if(childListBean.getId().equals(selectorServerList.get(k))){
                                childListBean.setCheck(true);
                            }
                        }
                    }
                }
                chooseServerParentEntityArrayList.addAll(chooseServerParentEntities);
                chooseServerParentAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFail(String msg) {
                RefreshUtils.fail(isRefresh,isLoadMore,1,choose_server_refresh);
            }
        });
    }
    @OnClick({R.id.toolbar_right_tv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.toolbar_right_tv:
               List<String>twoLevelClassification = new ArrayList<>();
                for (int i = 0; i < chooseServerParentEntityArrayList.size(); i++) {
                    List<ChooseServerParentEntity.ChildListBean> childList = chooseServerParentEntityArrayList.get(i).getChildList();
                    for (int j = 0; j < childList.size(); j++) {
                        ChooseServerParentEntity.ChildListBean childListBean = childList.get(j);
                        boolean check = childListBean.isCheck();
                        if(check){
                            twoLevelClassification.add(childListBean.getId());
                        }
                    }
                }
                HashMap<String, Object> data = new HashMap<>();
                data.put("twoLevelClassification",Utils.ArrayToStrWithComma(twoLevelClassification));
                data.put("updateFlag",3);
                HttpApiUtils.wwwRequest(ChooseServerActivity.this, null, RequestUtils.UPDATE_USER_INFO, data, new HttpApiUtils.OnRequestLintener() {
                    @Override
                    public void onSuccess(String result) {
                      showtoast("保存成功");
                      finish();
                    }

                    @Override
                    public void onFail(String msg) {
                    }
                });
                break;
            default:
                break;
        }
    }
    private void initToolbar() {
        String userType = Utils.getUserInfo().getUserType();
        toolbar_right_tv.setVisibility(View.VISIBLE);
        toolbar_right_tv.setText("确定");
        toolbar_right_tv.setTextColor(Color.parseColor("#0391FF"));
        if(userType.equals("1")){
            CommonToolbarUtil.initToolbar(this,"所需服务");
        }else {
            CommonToolbarUtil.initToolbar(this,"服务类型");
        }
    }

}