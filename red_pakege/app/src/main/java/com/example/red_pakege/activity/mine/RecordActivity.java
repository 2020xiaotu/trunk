package com.example.red_pakege.activity.mine;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.example.red_pakege.R;
import com.example.red_pakege.adapter.mine_adapter.RecordAdapter;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.model.RecordModel;
import com.example.red_pakege.mvp.contract.RecordContract;
import com.example.red_pakege.mvp.presenter.RecordPresenter;
import com.example.red_pakege.net.entity.DrawRecordEntity;
import com.example.red_pakege.net.entity.RechargeRecordEntity;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.ToolsUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


import static com.example.red_pakege.config.Constants.POSITION;
import static com.example.red_pakege.util.DateUtil.getDateAfter;
import static com.example.red_pakege.util.DateUtil.getDateBefore;
import static com.example.red_pakege.util.DateUtil.getDateStrYMD;

public class RecordActivity extends BaseActivity<RecordPresenter> implements RecordContract.IRecordView {


    @BindView(R.id.iv_commonbar_back)
    ImageView ivCommonbarBack;
    @BindView(R.id.tv_commonbar_title)
    TextView tvCommonbarTitle;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.ll_bill_all)
    LinearLayout llBillAll;
    @BindView(R.id.ll_bill_balance)
    LinearLayout llBillBalance;
    @BindView(R.id.ll_bill_start)
    LinearLayout llBillStart;
    @BindView(R.id.ll_bill_stop)
    LinearLayout llBillStop;
    @BindView(R.id.recy_record)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tv_bill_balance)
    TextView tvBillBalance;
    @BindView(R.id.tv_bill_start)
    TextView tvBillStart;
    @BindView(R.id.tv_bill_stop)
    TextView tvBillStop;
    @BindView(R.id.tv_bill_leiji)
    TextView tv_bill_leiji;

    private TYPE mPosition;

    private int pageNo = 1;
    private int pageSize = 10;

    private int cwsSelection = 0;  //类型选择器
    private String status = "";   //充值记录 status  "" 全部  0 充值中 1 已充值
    List<String> cwsList = new ArrayList<>();  //类型选择List


    RecordAdapter mRecyAdapter;
    List<RecordModel> mDataList = new ArrayList<>();

    public enum TYPE {
        RechargeRecord,
        DrawRecord,
        RewardRecord,
        WinLoseRecord,
        CommisionRecord,
        WaitRecord
    }

    @Override
    protected RecordPresenter createPresenter() {
        return new RecordPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_record;
    }

    @Override
    protected void mGetIntentData() {
        Bundle bundle = getIntent().getExtras();
        mPosition = (TYPE) bundle.getSerializable(POSITION);

    }

    @Override
    protected void initToolbar() {
        ActionBarUtil.initMainActionbar(this, toolbarCommon);
        ivCommonbarBack.setOnClickListener(v -> onBackPressedSupport());
    }

    @Override
    protected void initView() {
        tvBillStart.setText(getDateStrYMD(getDateBefore(new Date(), 7)));
        tvBillStop.setText(getDateStrYMD(new Date()));
        switch (mPosition) {
            case RechargeRecord:
                tvCommonbarTitle.setText("充值记录");
                tv_bill_leiji.setText("累计充值");
                cwsList.add("全部");
                cwsList.add("充值中");
                cwsList.add("已充值");
                cwsList.add("充值失败");
                break;
            case DrawRecord:
                tvCommonbarTitle.setText("提款记录");
                tv_bill_leiji.setText("累计提款");
                cwsList.add("全部");
                cwsList.add("申请中");
                cwsList.add("提现成功");
                cwsList.add("提现失败");
                break;
            case RewardRecord:
                tvCommonbarTitle.setText("奖金记录");
                break;
            case WinLoseRecord:
                tvCommonbarTitle.setText("盈亏记录");
                break;
            case CommisionRecord:
                tvCommonbarTitle.setText("佣金记录");
                break;
        }

        inirRecyView();

    }

    private void inirRecyView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyAdapter = new RecordAdapter(R.layout.item_recy_record, mDataList);
        mRecyclerView.setAdapter(mRecyAdapter);
    }

    @Override
    protected void initEventAndData() {
        Request(true);  //默认第一次请求全部数据

        llBillAll.setOnClickListener(v -> {
            if (cwsList.size() == 0) {
                return;
            }
            ToolsUtils.pickViewCWS(this, cwsList, "类型选择", cwsSelection, new ToolsUtils.CWSSelectedListener() {
                @Override
                public void selected(int position) {
                    cwsSelection = position;
                    refreshLayout.resetNoMoreData();
                    LogUtils.e("类型选择：" + cwsList.get(position));
                    //充值记录
                    if (mPosition == TYPE.RechargeRecord) {
                        switch (position) {
                            case 0:
                                status = "";  //全部状态
                                break;
                            case 1:
                                status = "0";  //充值中
                                break;
                            case 2:
                                status = "1"; //已充值
                                break;
                            case 3:
                                status = "2"; //充值失败
                                break;
                        }
                    }
                    if (mPosition == TYPE.DrawRecord) {
                        switch (position) {
                            case 0:
                                status = "";  //全部状态
                                break;
                            case 1:
                                status = "0";  //申请中
                                break;
                            case 2:
                                status = "1"; //提现成功
                                break;
                            case 3:
                                status = "-1"; //提现失败
                                break;
                        }
                    }
                    //选择器请求充值记录接口
                    pageNo = 1;
                    Request(true);

                }
            });
        });
        llBillBalance.setOnClickListener(v -> {
            //   ToastUtils.showToast("2");
            refreshLayout.resetNoMoreData();

        });
        llBillStart.setOnClickListener(v -> {
            //     ToastUtils.showToast("3");
            refreshLayout.resetNoMoreData();
            ToolsUtils.PickViewCV(this, tvBillStart, "开始时间", tvBillStart.getText().toString(), (String strDate) -> {
                LogUtils.e(strDate);
                //=======
                pageNo = 1;
                Request(true);

                //=====
            });

        });
        llBillStop.setOnClickListener(v -> {
            refreshLayout.resetNoMoreData();
            ToolsUtils.PickViewCV(this, tvBillStop, "结束时间", tvBillStop.getText().toString(), (String strDate) -> {
                LogUtils.e(strDate);
                pageNo = 1;
                Request(true);
            });
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshLayout.resetNoMoreData();
                pageNo = 1;
                Request(true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
            //    refreshLayout.resetNoMoreData();
                pageNo++;
                Request(false);
            }
        });

    }


    private void Request(boolean isRestart) {
        String startDate = tvBillStart.getText().toString() + " 00:00:00";
        String endDate = tvBillStop.getText().toString() + " 23:59:59";
        Map map = new HashMap();
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("pageNo", pageNo);
        map.put("pageSize", pageSize);
        map.put("status", status);
        switch (mPosition) {
            case RechargeRecord:
                mPresenter.ReqChargeRecord(map, isRestart);
                break;
            case DrawRecord:
                mPresenter.ReqDrawRecord(map, isRestart);
                break;
            case RewardRecord:

                break;
            case WinLoseRecord:

                break;
            case CommisionRecord:

                break;
        }

    }

    @Override
    public void showChargeRecord(RechargeRecordEntity entity, boolean isRestart) {
        LogUtils.e(entity.toString());
        refreshLayout.finishRefresh();
        if (!isRestart && entity.getData().getRows().size() == 0) {
            refreshLayout.finishRefreshWithNoMoreData();
        } else {
            refreshLayout.finishLoadMore();
        }
        List<RecordModel> mList = new ArrayList<>();
        for (RechargeRecordEntity.DataBean.RowsBean bean : entity.getData().getRows()) {
            RecordModel recordModel = new RecordModel();
            recordModel.setMtype(TYPE.RechargeRecord);
            recordModel.setType(bean.getTypeName());
            recordModel.setMoney("" + bean.getPrice());
            recordModel.setTime(bean.getDepositDate());
            recordModel.setStatus("" + bean.getStatusX());
            mList.add(recordModel);
        }
        //TODO 刷新adapter
        if (isRestart) {
            mDataList.clear();
        }
        mDataList.addAll(mList);
        CalTotalMoney(mDataList);
        mRecyAdapter.replaceData(mDataList);
    }

    @Override
    public void showDrawRecord(DrawRecordEntity entity, boolean isRestart) {

        LogUtils.e(entity.toString());
        refreshLayout.finishRefresh();
        if (!isRestart && entity.getData().getRows().size() == 0) {
            refreshLayout.finishRefreshWithNoMoreData();
        } else {
            refreshLayout.finishLoadMore();
        }
        List<RecordModel> mList = new ArrayList<>();
        for (DrawRecordEntity.DataBean.RowsBean bean : entity.getData().getRows()) {
            RecordModel recordModel = new RecordModel();
            recordModel.setMtype(TYPE.DrawRecord);
            recordModel.setType("余额提现");
            recordModel.setMoney("" + bean.getPrice());
            recordModel.setTime(bean.getLastModifiedDate());
            recordModel.setStatus("" + bean.getStatusX());
            mList.add(recordModel);
        }
        //TODO 刷新adapter
        if (isRestart) {
            mDataList.clear();
        }
        mDataList.addAll(mList);
        CalTotalMoney(mDataList);
        mRecyAdapter.replaceData(mDataList);
    }

    /**
     * 计算总金额
     *
     * @param list
     */
    private void CalTotalMoney(List<RecordModel> list) {
        int totalmoney = 0;
        for (int i = 0; i < list.size(); i++) {
            totalmoney += Integer.parseInt(list.get(i).getMoney());
        }
        tvBillBalance.setText("¥" + totalmoney);
    }

    @Override
    public void showErrorMsg(String errorMsg) {
        super.showErrorMsg(errorMsg);
        refreshLayout.finishRefresh();
    }
}
