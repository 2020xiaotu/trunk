package com.example.red_pakege.activity.agent_center_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.red_pakege.R;
import com.example.red_pakege.adapter.agent_center_adapter.ChildShowMoreAdapter;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.model.ChildShowMoreModel;
import com.example.red_pakege.util.ActionBarUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;

public class UserReportActivity extends BaseActivity {
        private Toolbar toolbar;
        private ImageView actionBarLeftIv;
        private ImageView actionBarRightIv;
        private TextView actionBarTitleTv;

        private RefreshLayout refreshLayout;
        private RecyclerView mRecy;
        private ChildShowMoreAdapter childShowMoreAdapter;
        private ArrayList<ChildShowMoreModel> childShowMoreModelArrayList =new ArrayList<>();

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        bindView();
        ActionBarUtil.initMainActionbar(this,toolbar);
        initRecycle();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_user_report;
    }

    private void initRecycle() {
        childShowMoreAdapter=new ChildShowMoreAdapter(childShowMoreModelArrayList);
        mRecy.setLayoutManager(new LinearLayoutManager(this));
        mRecy.setAdapter(childShowMoreAdapter);
        childShowMoreModelArrayList.add(new ChildShowMoreModel("充值奖励","¥0.00","首充奖励赠送","¥0.00","二充奖励赠送"));
        childShowMoreModelArrayList.add(new ChildShowMoreModel("邀请会员完成充值","¥0.00","首充奖励赠送","¥0.00","二充奖励赠送"));
        childShowMoreModelArrayList.add(new ChildShowMoreModel("发包与抢包满额奖励","¥0.00","发包奖励","¥0.00","抢包奖励"));
        childShowMoreModelArrayList.add(new ChildShowMoreModel("豹子顺子奖励与直推奖励","¥0.00","豹子顺子奖励","¥0.00","直推佣金奖励"));
    }

    private void bindView() {
        toolbar=findViewById(R.id.message_fragent_toolbar);
        actionBarLeftIv=findViewById(R.id.action_left_iv);
        actionBarRightIv=findViewById(R.id.action_right_iv);
        actionBarTitleTv=findViewById(R.id.actionBar_title);
        actionBarTitleTv.setText("个人报表");
        actionBarRightIv.setVisibility(View.INVISIBLE);
        actionBarLeftIv.setImageResource(R.drawable.icon_back);
        refreshLayout=findViewById(R.id.user_report_refresh);
        mRecy=findViewById(R.id.user_report_recycleView);
    }
}
