package com.example.new_application.ui.fragment.store_fragment;

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
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.ClassificationDetailsAdapter;
import com.example.new_application.base.BaseFragment;
import com.example.new_application.bean.ClassificationDetailsChildEntity;
import com.example.new_application.bean.ClassificationDetailsEntity;
import com.example.new_application.bean.HomeLabelEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.home_activity.ServerDetailsActivity;
import com.example.new_application.ui.activity.home_activity.StoreInformationActivity;
import com.example.new_application.utils.CommonStr;
import com.example.new_application.utils.RefreshUtils;
import com.example.new_application.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
public class ReleaseFragment extends BaseFragment {
    @BindView(R.id.release_recycler)
    RecyclerView release_recycler;
    @BindView(R.id.release_refresh)
    SmartRefreshLayout release_refresh;
    @BindView(R.id.error_linear)
    LinearLayout errorLinear;
    @BindView(R.id.reload_tv)
    TextView reloadTv;
    @BindView(R.id.loading_linear)
    ConstraintLayout loadingLinear;
    @BindView(R.id.nodata_linear)
    LinearLayout nodataLinear;
    ClassificationDetailsAdapter classificationDetailsAdapter;
    ArrayList<ClassificationDetailsEntity> classificationDetailsEntityArrayList = new ArrayList<>();
    int pageNo = 1;
    int pageSize = 10;
    StoreInformationActivity storeInformationActivity;
    @Override
    protected void init(Bundle savedInstanceState) {
        if(getActivity() instanceof StoreInformationActivity){
            storeInformationActivity = (StoreInformationActivity) getActivity();
        }
        initRecycler();
        requestList(false,false);
    }

    private void initRecycler() {
        classificationDetailsAdapter = new ClassificationDetailsAdapter(R.layout.classification_details_item,classificationDetailsEntityArrayList);
        release_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        release_recycler.setAdapter(classificationDetailsAdapter);
        classificationDetailsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ClassificationDetailsEntity classificationDetailsEntity = classificationDetailsEntityArrayList.get(position);

                ServerDetailsActivity.startAty(ReleaseFragment.this,classificationDetailsEntity.getId());
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_release;
    }
    public static ReleaseFragment newInstance(int position){
        ReleaseFragment releaseFragment = new ReleaseFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(CommonStr.POSITION,position);
        releaseFragment.setArguments(bundle);
        return releaseFragment;
    }
    @Override
    protected void initRefresh() {
        super.initRefresh();
        RefreshUtils.initRefreshLoadMore(getContext(), release_refresh, new RefreshUtils.OnRefreshLoadMoreLintener() {
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
        data.put("size",pageSize);
        data.put("memberId",storeInformationActivity.getMemberInfoVoBean().getId());
        data.put("userType",-1);
        HttpApiUtils.wwwShowLoadRequest(getActivity(), this, RequestUtils.RELEASE_list, data, loadingLinear, errorLinear, reloadTv, release_refresh, isLoadMore, isRefresh, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<ClassificationDetailsEntity> classificationDetailsEntityList = JSONArray.parseArray(result, ClassificationDetailsEntity.class);
                RefreshUtils.succse(pageNo,release_refresh,loadingLinear,nodataLinear,classificationDetailsEntityList.size(),isLoadMore,isRefresh,classificationDetailsEntityArrayList);
                for (int i = 0; i < classificationDetailsEntityList.size(); i++) {
                    ClassificationDetailsEntity classificationDetailsEntity = classificationDetailsEntityList.get(i);
                    String checkStatus = classificationDetailsEntity.getCheckStatus();//checkStatus = 2 官方显示官方认证
                    String priceType = classificationDetailsEntity.getPriceType();//价格类型(1：一口价，2：范围价格，3：议价)
                    String officialMark = classificationDetailsEntity.getOfficialMark();//标签id. 逗号拼接,用于筛选下面的标签
                    ArrayList<ClassificationDetailsChildEntity> childList = new ArrayList<>();
                    Utils.initServerLabelList(classificationDetailsEntity, officialMark, childList);
                    classificationDetailsEntity.setClassificationDetailsChildEntityArrayList(childList);
                    classificationDetailsEntityArrayList.add(classificationDetailsEntity);
                    classificationDetailsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(String msg) {
                RefreshUtils.fail(isRefresh,isLoadMore,pageNo,release_refresh);
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