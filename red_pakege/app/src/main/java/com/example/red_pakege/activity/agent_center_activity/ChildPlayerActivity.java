package com.example.red_pakege.activity.agent_center_activity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.red_pakege.R;
import com.example.red_pakege.activity.message_fragment_activity.OnLineKefuActivity;
import com.example.red_pakege.adapter.agent_center_adapter.ChildPlayerAdapter;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.model.ChildPlayerModel;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.widget.CommonAdapter;
import com.example.red_pakege.util.Utils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.util.ArrayList;

public class ChildPlayerActivity extends BaseActivity implements View.OnClickListener {
        private Toolbar toolbar;
        private ImageView actionBarLeftIv;
        private ImageView actionBarRightIv;
        private TextView actionTitleTv;

        private RefreshLayout refreshLayout;

        //recycleView 相关
        private RecyclerView mRecycle;
        private ChildPlayerAdapter childPlayerAdapter;
        private ArrayList<ChildPlayerModel>childPlayerModelArrayList = new ArrayList<>();

        //recycleView头部
        private TextView teamUserNumTv;//团队成员
        private TextView agentNumTv;//代理人数
        private TextView playerNumTv;//玩家人数
        private TextView zhituiNumTv;//直推成员
        private TextView zhituiAgentNumTv;//直推代理
        private TextView zhituiPlayerNumTv;//直推玩家
        private EditText childIdEtv; //id输入框
        private TextView searchTypeTv;//搜索类型
        private LinearLayout searchTypeLinear;
        private LinearLayout searchLinear;//搜索按钮

        //搜索类型pop相关
        private PopupWindow searchTypePop;


    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        bindView();
        ActionBarUtil.initMainActionbar(this,toolbar);
        initRecleView();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_child_player;
    }

    @Override
    protected void initClick() {
        super.initClick();
        actionBarRightIv.setOnClickListener(this);
        actionBarLeftIv.setOnClickListener(this);
    }

    private void initRecleView() {
        childPlayerAdapter=new ChildPlayerAdapter(childPlayerModelArrayList,this);
        mRecycle.setLayoutManager(new LinearLayoutManager(this));
        mRecycle.setAdapter(childPlayerAdapter);
        for (int i = 0; i < 20; i++) {
            childPlayerModelArrayList.add(new ChildPlayerModel("打不死的小强","1","22","1","10","100"));
        }
        View headView = LayoutInflater.from(this).inflate(R.layout.child_recycle_head_layout,null);
        bindHeadView(headView);
        childPlayerAdapter.addHeaderView(headView);
        //点击 查看详情 跳转个人报表
        childPlayerAdapter.setOnItemClickListener(new CommonAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                    startActivity(new Intent(ChildPlayerActivity.this,UserReportActivity.class));
            }
        });
    }

    private void bindHeadView(View headView) {
        teamUserNumTv=headView.findViewById(R.id.team_user_num_tv);
        agentNumTv=headView.findViewById(R.id.agent_num_tv);
        playerNumTv=headView.findViewById(R.id.player_num_tv);
        zhituiNumTv=headView.findViewById(R.id.zhitui_player_num_tv);
        zhituiAgentNumTv=headView.findViewById(R.id.zhitui_agent_num_tv);
        zhituiPlayerNumTv=headView.findViewById(R.id.zhitui_player_num_tv);
        childIdEtv=headView.findViewById(R.id.child_id_etv);
        searchTypeTv=headView.findViewById(R.id.search_type_tv);
        searchTypeLinear=headView.findViewById(R.id.search_type_linear);
        searchLinear=headView.findViewById(R.id.search_linear);
        searchTypeLinear.setOnClickListener(this);
        searchLinear.setOnClickListener(this);
    }

    private void bindView() {
        toolbar=findViewById(R.id.message_fragent_toolbar);
        actionBarLeftIv=findViewById(R.id.action_left_iv);
        actionBarRightIv=findViewById(R.id.action_right_iv);
        actionTitleTv=findViewById(R.id.actionBar_title);
        actionBarLeftIv.setImageResource(R.drawable.icon_back);
        actionTitleTv.setText("下级玩家");
        actionBarRightIv.setImageResource(R.drawable.kefu);
        refreshLayout=findViewById(R.id.child_player_refresh);
        mRecycle=findViewById(R.id.child_player_recycle);
        refreshLayout.setRefreshHeader(new ClassicsHeader(this));
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.action_left_iv:
                finish();
                break;
            case R.id.action_right_iv:
                startActivity(new Intent(ChildPlayerActivity.this, OnLineKefuActivity.class));
                break;
            case R.id.search_linear:
                //TODO 筛选搜索

                break;
            case R.id.search_type_linear:
                //点击弹出搜索类型pop
                if(searchTypePop==null){
                    initSearchPop();
                }
                searchTypePop.showAtLocation(actionBarLeftIv, Gravity.BOTTOM,0,0);
                Utils.darkenBackground(ChildPlayerActivity.this,0.7f);
                break;
            case R.id.all_type_tv:
                searchTypeTv.setText("全部");
                searchTypePop.dismiss();
                break;
            case R.id.agent_type_tv:
                searchTypeTv.setText("代理类型");
                searchTypePop.dismiss();
                break;
            case R.id.vip_type_tv:
                searchTypeTv.setText("玩家类型");
                searchTypePop.dismiss();
                break;
            case R.id.cancel_type_tv:
                searchTypePop.dismiss();
                break;
                default:
                    break;
        }
    }

    private void initSearchPop() {
        View searchView = LayoutInflater.from(ChildPlayerActivity.this).inflate(R.layout.child_player_search_type_pop,null);
        searchView.findViewById(R.id.all_type_tv).setOnClickListener(this);//全部类型
        searchView.findViewById(R.id.agent_type_tv).setOnClickListener(this);//代理类型
        searchView.findViewById(R.id.vip_type_tv).setOnClickListener(this);//玩家类型
        searchView.findViewById(R.id.cancel_type_tv).setOnClickListener(this);//取消
        searchTypePop=new PopupWindow(searchView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);
        searchTypePop.setAnimationStyle(R.style.pop_buttom_to_top_150);
        searchTypePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.darkenBackground(ChildPlayerActivity.this,1f);
            }
        });
    }
}
