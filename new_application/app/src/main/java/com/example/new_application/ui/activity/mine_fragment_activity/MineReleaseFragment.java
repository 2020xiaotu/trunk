package com.example.new_application.ui.activity.mine_fragment_activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.new_application.adapter.MineReleaseAdapter;
import com.example.new_application.base.BaseFragment;
import com.example.new_application.bean.MineReleaseEntity;
import com.example.new_application.bean.MineReleaseEvenEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.home_activity.ServerDetailsActivity;
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

public class MineReleaseFragment extends BaseFragment {

    @BindView(R.id.loading_linear)
    ConstraintLayout loading_linear;
    @BindView(R.id.error_linear)
    LinearLayout error_linear;
    @BindView(R.id.reload_tv)
    TextView reload_tv;
    @BindView(R.id.nodata_linear)
    LinearLayout nodata_linear;
    @BindView(R.id.mine_release_recycler)
    RecyclerView mine_release_recycler;
    @BindView(R.id.mine_release_refresh)
    SmartRefreshLayout mine_release_refresh;
    int pageNo=1;
    String checkStatus="";
    MineReleaseAdapter mineReleaseAdapter;
    ArrayList<MineReleaseEntity>mineReleaseEntityArrayList = new ArrayList<>();
    MineReleaseActivity mineReleaseActivity;
    int modifyRequestCode=1000;
    @Override
    protected void init(Bundle savedInstanceState) {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        mineReleaseActivity = (MineReleaseActivity) getActivity();
        initStatus();
        initRecycler();
        requestReleaseList(false,false);
    }
    public static MineReleaseFragment newInstance(int position){
        MineReleaseFragment mineReleaseFragment = new MineReleaseFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CommonStr.POSITION,position);
        mineReleaseFragment.setArguments(bundle);
        return  mineReleaseFragment;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initStatus() {
        String title = mineReleaseActivity.getTitleList().get(getArguments().getInt(CommonStr.POSITION));
        if(title.equals("全部")){
            checkStatus ="";
        }else if(title.equals("审核中")){
            checkStatus = "1";
        }else if(title.equals("未通过")){
            checkStatus = "3";
        }else if(title.equals("已发布")){
            checkStatus = "2";
        }
    }
    private void initRecycler() {
        mineReleaseAdapter = new MineReleaseAdapter(R.layout.mine_release_item,mineReleaseEntityArrayList);
        mine_release_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mine_release_recycler.setAdapter(mineReleaseAdapter);
        mineReleaseAdapter.addChildClickViewIds(R.id.left_btn,R.id.center_btn,R.id.right_btn);
        mineReleaseAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                MineReleaseEntity mineReleaseEntity = mineReleaseEntityArrayList.get(position);
                switch (view.getId()){
                    case R.id.left_btn:
                        /**
                         * 查看详情
                         */
                        ServerDetailsActivity.startAty(MineReleaseFragment.this,mineReleaseEntity.getId());
                        break;
                    case R.id.center_btn:
                        /**
                         * 修改
                         */
                        ReleaseServerActivity.startAty(MineReleaseFragment.this,mineReleaseEntity,modifyRequestCode);
                        break;
                    case R.id.right_btn:
                        /**
                         * 删除
                         */
                        requestDeleteRealease(position, mineReleaseEntity);
                        break;
                    default:
                        break;
                }
            }
        });
        mineReleaseAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                MineReleaseEntity mineReleaseEntity = mineReleaseEntityArrayList.get(position);
                ServerDetailsActivity.startAty(MineReleaseFragment.this,mineReleaseEntity.getId());
            }
        });
    }

    /**
     * 删除发布的资源
     * @param position
     * @param mineReleaseEntity
     */
    private void requestDeleteRealease(int position, MineReleaseEntity mineReleaseEntity) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("id",mineReleaseEntity.getId());
        HttpApiUtils.wwwRequest(getActivity(), MineReleaseFragment.this, RequestUtils.DELETE_RELEASE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showToast("删除成功");
                mineReleaseAdapter.removeDataByPosition(position);
                if(mineReleaseEntityArrayList.size()==0){
                    nodata_linear.setVisibility(View.VISIBLE);
                    mine_release_refresh.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_release;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==modifyRequestCode&&resultCode==RESULT_OK){
            mine_release_refresh.autoRefresh();
        }
    }

    @Override
    protected void initRefresh() {
        super.initRefresh();
        RefreshUtils.initRefreshLoadMore(getContext(), mine_release_refresh, new RefreshUtils.OnRefreshLoadMoreLintener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                pageNo=1;
                requestReleaseList(true,false);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                pageNo++;
                requestReleaseList(false,true);
            }
        });
    }

    @Override
    public void errorRefresh() {
        super.errorRefresh();
        requestReleaseList(false,false);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void requestListEven(MineReleaseEvenEntity mineReleaseEvenEntity){
        requestReleaseList(false,false);
    }
    private void requestReleaseList(boolean isRefresh, boolean isLoadMore) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("current",pageNo);
        data.put("size",10);
        data.put("checkStatus",checkStatus);
        data.put("userType",-1);
        data.put("myCollect",0);
        data.put("latest",-1);
        if(StringMyUtil.isNotEmpty(mineReleaseActivity.getTwoLevelClassification())){
            data.put("twoLevelClassification",mineReleaseActivity.getTwoLevelClassification());
        }
        HttpApiUtils.wwwShowLoadRequest(getActivity(), this, RequestUtils.RELEASE_list, data, loading_linear, error_linear, reload_tv, mine_release_refresh, isLoadMore, isRefresh, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<MineReleaseEntity> mineReleaseEntities = JSONArray.parseArray(result, MineReleaseEntity.class);
                RefreshUtils.succse(pageNo,mine_release_refresh,loading_linear,nodata_linear,mineReleaseEntities.size(),isLoadMore,isRefresh,mineReleaseEntityArrayList);
                mineReleaseEntityArrayList.addAll(mineReleaseEntities);
                mineReleaseAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
                RefreshUtils.fail(isRefresh,isLoadMore,pageNo,mine_release_refresh);
            }
        });
    }




}