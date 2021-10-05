package com.example.red_pakege.activity.mine;

import android.content.Intent;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bigkoo.pickerview.view.TimePickerView;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.example.red_pakege.R;
import com.example.red_pakege.adapter.mine_adapter.DrawRecordRecyAdapter;
import com.example.red_pakege.adapter.mine_adapter.GridViewAdapter;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.util.DateUtil;
import com.example.red_pakege.util.ToastUtils;
import com.example.red_pakege.util.ToolsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

import static com.example.red_pakege.util.ActionBarUtil.initMainActionbar;

public class DrawingRecordActivity extends BaseActivity {

    @BindView(R.id.tv_commondrawing_title)
    TextView mTitle;
    @BindView(R.id.common_drawing_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_commondrawing_back)
    ImageView mBack;
    @BindView(R.id.tv_commondrawing_right)
    TextView tvRight;
    //   @BindView(R.id.gridview_drawingrecord) GridView mGridView;
    @BindView(R.id.ll_bill_all)
    LinearLayout ll_bill_all;
    @BindView(R.id.ll_bill_balance)
    LinearLayout ll_bill_balance;
    @BindView(R.id.ll_bill_start)
    LinearLayout ll_bill_start;
    @BindView(R.id.ll_bill_stop)
    LinearLayout ll_bill_stop;
    @BindView(R.id.recy_drawingrecord)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_bill_start)
    TextView tv_bill_start;
    @BindView(R.id.tv_bill_stop)
    TextView tv_bill_stop;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;


    DrawRecordRecyAdapter mRecyAdapter;
    List<String> mDataList = new ArrayList<>();
    //   private String[] titles = {"全部类型", "累计金额", "开始时间", "结束时间"};
    //   private int[] icons = {R.drawable.ic_bill_all, R.drawable.ic_bill_balance, R.drawable.ic_bill_start, R.drawable.ic_bill_stop};

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_drawing_record;
    }

    @Override
    protected void initToolbar() {
        initMainActionbar(this, mToolbar);
        mTitle.setText("提款记录");
        tvRight.setVisibility(View.GONE);
    }

    @Override
    protected void initView() {
        //    mGridView.setAdapter(new GridViewAdapter(this,titles,icons));
        tv_bill_start.setText(DateUtil.getCurrentDateStrYMD());
        tv_bill_stop.setText(DateUtil.getCurrentDateStrYMD());
        inirRecyView();
    }

    private void inirRecyView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyAdapter = new DrawRecordRecyAdapter(R.layout.item_recy_drawrecord,mDataList);
        mRecyclerView.setAdapter(mRecyAdapter);
    }

    @Override
    protected void initEventAndData() {

        //TODO 请求接口 。。
        // << start 模拟数据
        mDataList.clear();
        for (int i =0;i<10;i++){
            mDataList.add(""+i);
        }
        mRecyAdapter.replaceData(mDataList);
        //>> end


        mBack.setOnClickListener(v -> onBackPressedSupport());
        ll_bill_all.setOnClickListener(v -> {
            ToastUtils.showToast("1");
        });
        ll_bill_balance.setOnClickListener(v -> {
            ToastUtils.showToast("2");
        });
        ll_bill_start.setOnClickListener(v -> {
            //     ToastUtils.showToast("3");
            ToolsUtils.PickViewCV(this, tv_bill_start, "开始时间", tv_bill_start.getText().toString(), (String strDate) -> {
                LogUtils.e(strDate);
                //=======
                for (int i=0;i<5;i++){
                    mDataList.add(""+i);
                }
                mRecyAdapter.replaceData(mDataList);
                    //=====
            });

        });
        ll_bill_stop.setOnClickListener(v -> {
            ToolsUtils.PickViewCV(this, tv_bill_stop, "开始时间", tv_bill_stop.getText().toString(), (String strDate) -> {
                LogUtils.e(strDate);
            });
        });
        mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000);//传入false表示刷新失败
            }
        });
        mSmartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(2000);//传入false表示加载失败
            }
        });
    }

}
