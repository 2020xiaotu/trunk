package com.example.new_application.ui.activity.mine_fragment_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.GuaranteeOrderAdapter;
import com.example.new_application.base.BaseFragment;
import com.example.new_application.bean.GuaranteeOrderEntity;
import com.example.new_application.bean.GuaranteeOrderEvenEntity;
import com.example.new_application.bean.RequestServerHallEvenEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.home_activity.TakeGuaranteeActivity;
import com.example.new_application.utils.CommonStr;
import com.example.new_application.utils.RefreshUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;

public class GuaranteeOrderFragment extends BaseFragment {

    @BindView(R.id.loading_linear)
    ConstraintLayout loading_linear;
    @BindView(R.id.error_linear)
    LinearLayout error_linear;
    @BindView(R.id.reload_tv)
    TextView reload_tv;
    @BindView(R.id.nodata_linear)
    LinearLayout nodata_linear;
    @BindView(R.id.guarantee_order_recycler)
    RecyclerView guarantee_order_recycler;
    @BindView(R.id.guarantee_order_refresh)
    SmartRefreshLayout guarantee_order_refresh;
    GuaranteeOrderAdapter guaranteeOrderAdapter;
    ArrayList<GuaranteeOrderEntity>guaranteeOrderEntityArrayList = new ArrayList<>();
    int pageNo=1;
    GuaranteeOrderActivity guaranteeOrderActivity;
    int status =-1;
    static int MODIFY_REQUEST_CODE =1000;
    static int DETAILS_REQUEST_CODE =1001;
    @Override
    protected void init(Bundle savedInstanceState) {
        guaranteeOrderActivity = (GuaranteeOrderActivity) getActivity();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        initStatus();
        initRecycler();
        requestOrderList(false,false);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_guarantee_order;
    }

    public static GuaranteeOrderFragment newInstance(int position){
        GuaranteeOrderFragment guaranteeOrderFragment = new GuaranteeOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CommonStr.POSITION,position);
        guaranteeOrderFragment.setArguments(bundle);
        return  guaranteeOrderFragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void errorRefresh() {
        super.errorRefresh();
        requestOrderList(false,false);
    }
    @Override
    protected void initRefresh() {
        super.initRefresh();
        RefreshUtils.initRefreshLoadMore(getContext(), guarantee_order_refresh, new RefreshUtils.OnRefreshLoadMoreLintener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                pageNo=1;
                requestOrderList(true,false);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                pageNo++;
                requestOrderList(false,true);
            }
        });
    }
    private void initRecycler() {
        guaranteeOrderAdapter = new GuaranteeOrderAdapter(R.layout.guatantee_order_item,guaranteeOrderEntityArrayList);
        guarantee_order_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        guarantee_order_recycler.setAdapter(guaranteeOrderAdapter);
        guaranteeOrderAdapter.addChildClickViewIds(R.id.order_left_btn,R.id.order_right_btn);
        guaranteeOrderAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                GuaranteeOrderEntity guaranteeOrderEntity = guaranteeOrderEntityArrayList.get(position);
                Button button = (Button) view;
                String toString = button.getText().toString();
                if(toString.equals("同意担保")){
                    requestAgreeGuarantee(guaranteeOrderEntity);
                }else if(toString.equals("取消担保")){
                    requestCancelGuarantee(guaranteeOrderEntity);
                }else if(toString.equals("修改")){
                    TakeGuaranteeActivity.startAty(null,GuaranteeOrderFragment.this,guaranteeOrderEntity,true,MODIFY_REQUEST_CODE);
                }
            }
        });
        guaranteeOrderAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                GuaranteeOrderEntity guaranteeOrderEntity = guaranteeOrderEntityArrayList.get(position);
                GuaranteeOrderDetailsActivity.startAty(GuaranteeOrderFragment.this,guaranteeOrderEntity,DETAILS_REQUEST_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode == MODIFY_REQUEST_CODE){
                /**
                 * 修改订单返回
                 */
                guarantee_order_refresh.autoRefresh();
            }else if(requestCode == DETAILS_REQUEST_CODE){
                /**
                 * 订单详情返回
                 */
                guarantee_order_refresh.autoRefresh();
            }
        }
    }

    /**
     * 同意担保
     * @param guaranteeOrderEntity
     */
    private void requestAgreeGuarantee(GuaranteeOrderEntity guaranteeOrderEntity) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("id",guaranteeOrderEntity.getId());
        HttpApiUtils.wwwRequest(getActivity(), this, RequestUtils.AGREE_GUARANTEE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showToast("担保成功");
                guarantee_order_refresh.autoRefresh();
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
    private void requestCancelGuarantee(GuaranteeOrderEntity guaranteeOrderEntity) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("id",guaranteeOrderEntity.getId());
        HttpApiUtils.wwwRequest(getActivity(), this, RequestUtils.CANCEL_GUARANTEE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showToast("取消成功");
                guarantee_order_refresh.autoRefresh();
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
    private void initStatus() {
        String title = guaranteeOrderActivity.getTitleList().get(getArguments().getInt(CommonStr.POSITION));
        if(title.equals("全部")){
            status = -1;
        }else if(title.equals("用户委托中")){
            status = 0;
        }else if(title.equals("平台审核中")){
            status = 1;
        }else if(title.equals("平台担保中")){
            status = 2;
        }else if(title.equals("平台拒绝")){
            status = 3;
        }else if(title.equals("用户取消订单")){
            status = 4;
        }else if(title.equals("担保成功")){
            status = 11;
        }else if(title.equals("担保失败")){
            status = 12;
        }
    }

    private void requestOrderList(boolean isRefresh,boolean isLoadMore) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("current",pageNo);
        data.put("size",10);
        data.put("flag",guaranteeOrderActivity.getFlag());
        data.put("status",status);
        HttpApiUtils.wwwShowLoadRequest(getActivity(), this, RequestUtils.GUARANTEE_ORDER_LIST, data, loading_linear, error_linear, reload_tv, guarantee_order_refresh, isLoadMore, isRefresh, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<GuaranteeOrderEntity> guaranteeOrderEntityList = JSONArray.parseArray(result, GuaranteeOrderEntity.class);
                RefreshUtils.succse(pageNo,guarantee_order_refresh,loading_linear,nodata_linear,guaranteeOrderEntityList.size(),isLoadMore,isRefresh,guaranteeOrderEntityArrayList);
                guaranteeOrderEntityArrayList.addAll(guaranteeOrderEntityList);
                guaranteeOrderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
                RefreshUtils.fail(isRefresh,isLoadMore,pageNo,guarantee_order_refresh);
            }
        });

    }
    /**
     * activity点击切换筛选发出的通知
     * @param guaranteeOrderEvenEntity
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void requestListEven(GuaranteeOrderEvenEntity guaranteeOrderEvenEntity){
        requestOrderList(false,false);

    }




}