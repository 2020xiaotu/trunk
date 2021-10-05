package com.example.new_application.ui.fragment.feedback_fragment;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.new_application.R;
import com.example.new_application.adapter.MineFeedbackModel;
import com.example.new_application.adapter.MineFeedbackRecycleAdapter;
import com.example.new_application.base.BaseFragment;
import com.example.new_application.bean.UpdateMineFeedback;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
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

/*
我的反馈
 */
public class MineFeedbackFragment extends BaseFragment {
    @BindView(R.id.mine_feedback_recycle)
    RecyclerView mRecy;
    @BindView(R.id.refresh)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.error_linear)
    LinearLayout errorLinear;
    @BindView(R.id.reload_tv)
    TextView reloadTv;
    @BindView(R.id.loading_linear)
    ConstraintLayout loadingLinear;
    @BindView(R.id.nodata_linear)
    LinearLayout nodataLinear;
    private MineFeedbackRecycleAdapter mineFeedbackRecycleAdapter;
    private ArrayList<MineFeedbackModel> mineFeedbackModelArrayList = new ArrayList<>();

    private int pageNum = 1;



    @Override
    protected void init(Bundle savedInstanceState) {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        initRecycle();

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_feedback;
    }

    @Override
    protected void initRefresh() {
        super.initRefresh();
        RefreshUtils.initRefreshLoadMore(getContext(), refreshLayout, new RefreshUtils.OnRefreshLoadMoreLintener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                pageNum=1;
                getMineFeedback(pageNum,false,true);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                pageNum++;
                getMineFeedback(pageNum,true,false);
            }
        });
    }

    @Override
    public void errorRefresh() {
        super.errorRefresh();
        getMineFeedback(pageNum,false,false);
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void updateMineFeedback(UpdateMineFeedback updateMineFeedback){
        if(updateMineFeedback.isUpdate()){
            getMineFeedback(1,false,false);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(refreshLayout!=null){
            refreshLayout.closeHeaderOrFooter();
        }
        EventBus.getDefault().unregister(this);
    }




    private void initRecycle() {
        mineFeedbackRecycleAdapter = new MineFeedbackRecycleAdapter(R.layout.mine_feedback_recycle_item,mineFeedbackModelArrayList);
        mRecy.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecy.setAdapter(mineFeedbackRecycleAdapter);

        getMineFeedback(1, false, false);
    }

    private void getMineFeedback(int pageNo, boolean isLoadMore, boolean isRefresh) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("current",pageNo);
        data.put("size",10);
        HttpApiUtils.wwwShowLoadRequest(getActivity(), this, RequestUtils.MINE_FEEDBACK_LIST, data, loadingLinear, errorLinear, reloadTv, refreshLayout, isLoadMore, isRefresh, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<MineFeedbackModel> mineFeedbackModelList = JSONArray.parseArray(result, MineFeedbackModel.class);
                RefreshUtils.succse(pageNum, refreshLayout, loadingLinear, nodataLinear,mineFeedbackModelList.size(), isLoadMore, isRefresh, mineFeedbackModelArrayList);
                mineFeedbackModelArrayList.addAll(mineFeedbackModelList);
                mineFeedbackRecycleAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
                RefreshUtils.fail(isRefresh,isLoadMore,pageNo,refreshLayout);
            }
        });
    }
}

