package com.example.red_pakege.activity.mine;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.red_pakege.R;
import com.example.red_pakege.adapter.mine_adapter.YueBaoRecordAdapter;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.mvp.contract.YuebaoRecordContract;
import com.example.red_pakege.mvp.presenter.YuebaoRecodPresenter;
import com.example.red_pakege.net.entity.YuebaoItemEntity;
import com.example.red_pakege.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class YueBaoRecordActivity extends BaseActivity<YuebaoRecodPresenter>
        implements YuebaoRecordContract.IYuebaoRecordView {


    @BindView(R.id.iv_yuebaorecord_back)
    ImageView ivBack;
    @BindView(R.id.iv_yuebaorecord_shaixuan)
    ImageView ivShaixuan;
    @BindView(R.id.tv_yuebaorecord_tv2)
    TextView tvAllmoney;
    @BindView(R.id.tv_yuebaorecord_tv4)
    TextView tvShouyi;
    @BindView(R.id.recy_yuebaorecord)
    RecyclerView mRecy;
    @BindView(R.id.refreshLayout_yuebaorecord)
    SmartRefreshLayout mRefreshLayout;


    private LinearLayoutManager layoutManager;
    private YueBaoRecordAdapter yueBaoRecordAdapter;
    private List<YuebaoItemEntity> mList = new ArrayList<>();

    @Override
    protected YuebaoRecodPresenter createPresenter() {
        return new YuebaoRecodPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_yuebaorecord;
    }



    @Override
    protected void initView() {
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        yueBaoRecordAdapter = new YueBaoRecordAdapter(R.layout.item_yuebaorecord,mList);
        mRecy.setLayoutManager(layoutManager);
        mRecy.setAdapter(yueBaoRecordAdapter);
    }

    @Override
    protected void initEventAndData() {
        ivBack.setOnClickListener(v -> onBackPressedSupport());
        ivShaixuan.setOnClickListener(v -> {
            ToastUtils.showToast("筛选");
        });

        for (int i =0;i<10;i++){
            YuebaoItemEntity entity = new YuebaoItemEntity();
            entity.setTime("2019-10-10 09:00:00");
            entity.setShouyi("+0.94");
            mList.add(entity);
        }
        yueBaoRecordAdapter.replaceData(mList);


    }

    @Override
    public void ReqYuebaoRecordSuccess(int type) {

    }

    @Override
    public void ReqMycountSuccess() {

    }


}
