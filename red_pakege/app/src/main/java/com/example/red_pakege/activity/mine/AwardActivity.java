package com.example.red_pakege.activity.mine;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.red_pakege.R;
import com.example.red_pakege.adapter.mine_adapter.AwardRecyAdapter;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.RouteUtil;
import com.example.red_pakege.widget.AbSpacesItemDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class AwardActivity extends BaseActivity {

    @BindView(R.id.toolbar_common)
    Toolbar mToolbar;
    @BindView(R.id.iv_commonbar_back)
    ImageView mBack;
    @BindView(R.id.recy_award)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;

    LinearLayoutManager layoutManager;
    AwardRecyAdapter awardRecyAdapter;

    List<String> imageUrlList = new ArrayList<>();

    private int pageNo = 1;
    private static final int pageSize = 10;

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_award;
    }

    @Override
    protected void initView() {
        initRecyView();
    }

    @Override
    protected void initToolbar() {
        ActionBarUtil.initMainActionbar(this, mToolbar);
    }


    @Override
    protected void initEventAndData() {
        mBack.setOnClickListener(v -> onBackPressedSupport());
        RequestAward(pageNo,pageSize,true);

        awardRecyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RouteUtil.start2WebActivity(AwardActivity.this,"https://m.jd.com/");
            }
        });

        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
         //       refreshlayout.finishRefresh(2000);//传入false表示刷新失败
                pageNo = 1;
                RequestAward(pageNo,pageSize,true);
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
            //    refreshlayout.finishLoadMore(2000);//传入false表示加载失败
                pageNo++;
                RequestAward(pageNo,pageSize,false);
            }
        });
    }

    /**
     * 初始化 recyView
     */
    private void initRecyView(){
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new AbSpacesItemDecoration(4));
        awardRecyAdapter = new AwardRecyAdapter(R.layout.item_recy_award,imageUrlList);
        mRecyclerView.setAdapter(awardRecyAdapter);
    }


    private void RequestAward(int pageNo,int pageSize,boolean isRestart){
        mSmartRefreshLayout.finishRefresh();

        /**
         *   请求成功   recyclerView  刷新
         */
        imageUrlList.clear();
        for (int i =0;i<10;i++){
            imageUrlList.add("http://images.www16xxoo.com/upload/images/20191124/1574530558863.jpg");
        }
        if (!isRestart){
            mSmartRefreshLayout.finishLoadMore();
        }


        awardRecyAdapter.replaceData(imageUrlList);


    }


}
