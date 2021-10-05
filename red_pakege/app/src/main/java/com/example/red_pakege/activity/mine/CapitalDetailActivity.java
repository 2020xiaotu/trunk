package com.example.red_pakege.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.red_pakege.R;
import com.example.red_pakege.adapter.mine_adapter.CapitalDetailAdapter;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.model.CapitalDetailModel;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.RouteUtil;
import com.example.red_pakege.util.ToastUtils;
import com.example.red_pakege.widget.AbSpacesItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.red_pakege.activity.mine.RecordActivity.TYPE.CommisionRecord;
import static com.example.red_pakege.activity.mine.RecordActivity.TYPE.DrawRecord;
import static com.example.red_pakege.activity.mine.RecordActivity.TYPE.RechargeRecord;
import static com.example.red_pakege.activity.mine.RecordActivity.TYPE.RewardRecord;
import static com.example.red_pakege.activity.mine.RecordActivity.TYPE.WaitRecord;
import static com.example.red_pakege.activity.mine.RecordActivity.TYPE.WinLoseRecord;
import static com.example.red_pakege.config.Constants.POSITION;

public class CapitalDetailActivity extends BaseActivity {


    @BindView(R.id.iv_commonbar_back)
    ImageView ivCommonbarBack;
    @BindView(R.id.tv_commonbar_title)
    TextView tvCommonbarTitle;
    @BindView(R.id.toolbar_common)
    Toolbar toolbarCommon;
    @BindView(R.id.recy_capitaldetail)
    RecyclerView recyCapitaldetail;

    GridLayoutManager gridLayoutManager;
    CapitalDetailAdapter capitalDetailAdapter;

    private String[]titles={"充值记录","提款记录","奖金记录","盈亏记录","佣金记录","敬请期待"};
    private int[]icons ={R.drawable.icon_cz_record,R.drawable.icon_grab_record,R.drawable.icon_reward_record,R.drawable.icon_yk_record,R.drawable.icon_yj_record,R.drawable.icon_wait_update};
    private List<CapitalDetailModel> mList = new ArrayList<>();
    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_capitaldetail;
    }

    @Override
    protected void initView() {
        for (int i=0;i<6;i++){
            CapitalDetailModel capitalDetailModel = new CapitalDetailModel();
            capitalDetailModel.setTitle(titles[i]);
            capitalDetailModel.setIcon(icons[i]);
            mList.add(capitalDetailModel);
        }

        gridLayoutManager = new GridLayoutManager(this,2);
        recyCapitaldetail.setLayoutManager(gridLayoutManager);
        capitalDetailAdapter = new CapitalDetailAdapter(R.layout.item_recy_capitaldetail,mList);
        recyCapitaldetail.addItemDecoration(new AbSpacesItemDecoration(1));
        recyCapitaldetail.setAdapter(capitalDetailAdapter);
    }

    @Override
    protected void initToolbar() {
        ActionBarUtil.initMainActionbar(this, toolbarCommon);
    }

    @Override
    protected void initEventAndData() {

        ivCommonbarBack.setOnClickListener(v -> onBackPressedSupport());
        tvCommonbarTitle.setText("资金明细");
        capitalDetailAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showToast(""+position);
                switch (position){
                    case 0:
                        RouteUtil.start2RecordActivity(CapitalDetailActivity.this,RechargeRecord);
                        break;
                    case 1:
                        RouteUtil.start2RecordActivity(CapitalDetailActivity.this,DrawRecord);
                        break;
                    case 2:
                        RouteUtil.start2RecordActivity(CapitalDetailActivity.this,RewardRecord);
                        break;
                    case 3:
                        RouteUtil.start2RecordActivity(CapitalDetailActivity.this,WinLoseRecord);
                        break;
                    case 4:
                        RouteUtil.start2RecordActivity(CapitalDetailActivity.this,CommisionRecord);
                        break;
                    case 5:
                     //   RouteUtil.start2RecordActivity(CapitalDetailActivity.this,WaitRecord);
                        ToastUtils.showToast("敬请期待");
                        break;
                }
            }
        });

    }


}
