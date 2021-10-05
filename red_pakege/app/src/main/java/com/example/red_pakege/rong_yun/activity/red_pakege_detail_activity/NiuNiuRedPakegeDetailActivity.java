package com.example.red_pakege.rong_yun.activity.red_pakege_detail_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.net.api.HttpApiUtils;
import com.example.red_pakege.rong_yun.adapter.PakegeDetailAdapter;
import com.example.red_pakege.rong_yun.even_bus_model.CurrentModel;
import com.example.red_pakege.rong_yun.model.PakegeDetailModel;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.BigDecimalUtil;
import com.example.red_pakege.util.GlideLoadViewUtil;
import com.example.red_pakege.util.RefreshUtil;
import com.example.red_pakege.util.RequestUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import okhttp3.Headers;

public class NiuNiuRedPakegeDetailActivity extends BaseActivity implements View.OnClickListener {
    private String orderCode;
    private Integer typeId;

    private Toolbar toolbar;
    private ImageView actionBarLeftIv;
    private ImageView actionBarRightIv;
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private PakegeDetailAdapter pakegeDetailAdapter;
    private ArrayList<PakegeDetailModel> pakegeDetailModelArrayList = new ArrayList<>();

    private  SharedPreferenceHelperImpl sp;
    private SimpleDateFormat sformat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private ImageView headTitleIv;
    private TextView headNameTv;
    private TextView headAmountAndCountTv;
    private LinearLayout headAmountAndCountLinear;
    private TextView headAmountTv;
    private ImageView headNiujiIv;
    private TextView headCurrentTimeTv;
    private TextView bankWinCountTv;
    private TextView playerWinCountTv;
    private TextView headGetInfoTv;
    private ImageView winOrLoseIv;
    private LinearLayout bankAndPlayerWinCountLinear;
    private   long currentTime;
    //倒计时runnable是否在执行
    private boolean isCurrentRunnableRun=false;
    //是否收到开奖结果的广播
    private boolean haveResult=false;
    private ConstraintLayout loadingLinear;
    private LinearLayout nodataLinear;
    private LinearLayout errorLinear;
    private TextView reloadTv;
    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        getintentInfo();
        bindView();
        initRecycle();
        bindActionBar();
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);//注册eventBus
        }
    }

    @Override
    protected void initRefresh() {
        super.initRefresh();
        refreshLayout.setEnableLoadMoreWhenContentNotFull(false);
        refreshLayout.setRefreshFooter(new ClassicsFooter(this));
        ClassicsHeader refreshHeader = new ClassicsHeader(this);
        refreshHeader.setPrimaryColor(Color.parseColor("#FE4C56"));
        refreshHeader.setAccentColor(Color.WHITE);
        refreshLayout.setRefreshHeader(refreshHeader);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getPakgeInfo(orderCode,true);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN/*,sticky = true*/)
    public void finishCurrent(CurrentModel currentModel){
        String orderCode = currentModel.getOrderCode();
        if(this.orderCode!=null&&this.orderCode.equals(orderCode)){
            haveResult=true;
            refreshLayout.autoRefresh();
        }
    }
    private void bindActionBar() {
        ActionBarUtil.initMainActionbar(NiuNiuRedPakegeDetailActivity.this,toolbar);
    }

    private void getintentInfo() {
        Intent intent = getIntent();
        orderCode= intent.getStringExtra("orderCode");
        typeId= intent.getIntExtra("typeId",0);
    }

    public static void startAty(Context context,String orderCode,int typeId){
        Intent intent = new Intent(context, NiuNiuRedPakegeDetailActivity.class);
        intent.putExtra("orderCode",orderCode);
        intent.putExtra("typeId",typeId);
        context.startActivity(intent);
    }
    private void initRecycle() {
        pakegeDetailAdapter=new PakegeDetailAdapter(pakegeDetailModelArrayList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(pakegeDetailAdapter);
        bindHeadView();
        bindFootView();
        getPakgeInfo(orderCode,false);
    }
        Handler handler  =new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                            handler.postDelayed(currentRunnable,1000);
                        break;
                        default:
                            break;
                }
            }
        };
    Runnable currentRunnable = new Runnable() {
        @Override
        public void run() {
            String currentStr="";
            if(currentTime>0){
                isCurrentRunnableRun=true;
                currentTime=currentTime-1000;
                long hours = (currentTime / 1000) / (60 * 60);//(countTime/* - days * (1000 * 60 * 60 * 24)*/) / (1000 * 60 * 60);
                long minutes = ((currentTime / 1000) % (60 * 60))/ 60;//(countTime /*- days * (1000 * 60 * 60 * 24)*/ - hours * (1000 * 60 * 60)) / (1000 * 60);
                long seconds = (currentTime / 1000) % 60;//(countTime /*- days * (1000 * 60 * 60 * 24)*/ - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;

                String minute;
                String sencond;
                if(minutes==0){
                    minute="";
                }else if(minutes<10){
                    minute="0"+minutes;
                }
                else{
                    minute=minutes+"";
                }
        /*        if(seconds==0){
                  sencond="";
                }else */if(seconds<10){
                    sencond="0"+seconds;
                }
                else{
                    sencond=seconds+"";
                }
                headCurrentTimeTv.setTextColor(getResources().getColor(R.color.red));
                currentStr="剩余"+minute+":"+sencond;
                headCurrentTimeTv.setText(currentStr);
                handler.postDelayed(currentRunnable,1000);
//                handler.sendEmptyMessageDelayed(1,1000);
            }else {
                headCurrentTimeTv.setTextColor(getResources().getColor(R.color.default_two_color));
                currentStr="结算中";
                headCurrentTimeTv.setText(currentStr);
                handler.removeCallbacks(currentRunnable);
                isCurrentRunnableRun=false;
            }

        }
    };
    private void getPakgeInfo(String orderCode,boolean isRefresh) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("orderCode",orderCode);
        data.put("typeId",typeId);
// Context context, Fragment fragment, String url, HashMap<String, Object> data, ConstraintLayout loadingLinear, LinearLayout errorLinear, TextView reloadTv, View view, boolean isLoadmore, boolean isrefresh, OnRequestLintener onRequestLintener
        HttpApiUtils.footShowLoadRequest(this, null,RequestUtil.PAKEGE_DETAIL, data,loadingLinear,errorLinear,reloadTv, recyclerView,false,isRefresh,new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result, Headers headers) {

                try {
                 pakegeDetailModelArrayList.clear();
                 sp = new SharedPreferenceHelperImpl();
                long shiJianCha = sp.getShiJianCha();
                JSONObject data = JSONObject.parseObject(result).getJSONObject("data");
                //recycleView 头部数据
                Long userId = data.getLong("userId");
                String userName = data.getString("userName");
                String avatar = data.getString("avatar");//头像
                String touzhuDate = data.getString("touzhuDate");//发包时间
                String lotteryTime = data.getString("lotteryTime");//倒计时结束时间
                int hbNum = data.getInteger("hbNum");//红包个数
                BigDecimal hbAmount = data.getBigDecimal("hbAmount");//红包金额
                Integer gainNum = data.getInteger("gainNum");//已抢个数
                BigDecimal gainAmount = data.getBigDecimal("gainAmount");//已抢金额
                Integer playerType = data.getInteger("playerType");//玩家类型 0 普通 1闲家  2庄家

                Integer bankerWinCount = data.getInteger("bankerWinCount");//庄家赢的次数(只有庄家有)
                Integer playerWinCount = data.getInteger("playerWinCount");//闲家赢得次数(只有庄家有)
                BigDecimal selfAmount = data.getBigDecimal("selfAmount");//自己抢包金额(庄家或闲家有)
                Integer selfNiuJi = data.getInteger("selfNiuJi");//自己牛几(庄家或闲家有)
                Boolean isLottery = data.getBoolean("isLottery");//是否已经开奖
                Date date= sformat.parse(lotteryTime);
                currentTime= date.getTime()-(new Date().getTime()+shiJianCha);
                //TODO 判断红包是否抢完 抢完时要走isOver的逻辑
                boolean isOver;//红包是否已经截止
                if(currentTime>0||null==isLottery||isLottery==false){
                    isOver=false;
                    //避免用户下拉刷新后,再次发送message,导致倒计时加快(本来应该是判断handler中是否有currentRunnable的任务在执行,但是handler.hasCallBacks()方法不能调用......)
                    if(!isCurrentRunnableRun){
                        handler.sendEmptyMessage(1);
                    }
                }else{
                    isOver=true;
                    if(haveResult){
                        //收到开奖结果的通知后,才显示游戏已截止(当倒计时结束,开奖结果未更新时,如果用户刷新的话,还是要显示结算中,而不是已截止)
                        headCurrentTimeTv.setText("本包游戏已截止");
                    }
                }
                if(playerType==0){//普通玩家(没有参与抢包的用户)
                    headAmountAndCountLinear.setVisibility(View.GONE);//当前用户抢包金额
                    winOrLoseIv.setVisibility(View.GONE);//左侧胜负icon
                    bankAndPlayerWinCountLinear.setVisibility(View.GONE);//底部庄家和闲家赢的个数
//                    headCurrentTimeTv.setVisibility(View.GONE);
                }
                if(playerType==1){//闲家
                    bankAndPlayerWinCountLinear.setVisibility(View.GONE);
                    if(isOver){//红包已经截止时,显示左侧胜负图片
//                        if(!headCurrentTimeTv.getText().equals("结算中")){
                        boolean containsKey = data.containsKey("isWin");
                        if(containsKey){

                            boolean isWin = data.getBoolean("isWin");//闲家胜负
                            winOrLoseIv.setVisibility(View.VISIBLE);
                            winOrLoseIv.setVisibility(View.VISIBLE);
                            if(isWin){
                                winOrLoseIv.setImageResource(R.drawable.ic_cow_victory);
                            }else {
                                winOrLoseIv.setImageResource(R.drawable.ic_cow_defeate);
                            }
                        }

//                        }
                    }else{
                        //倒计时未结束, 隐藏胜负图片
                        winOrLoseIv.setVisibility(View.GONE);
                    }
                }
                if(playerType==2){//庄家
                    bankAndPlayerWinCountLinear.setVisibility(View.VISIBLE);
                    winOrLoseIv.setVisibility(View.GONE);
                }
                    GlideLoadViewUtil.LoadCornersView(NiuNiuRedPakegeDetailActivity.this,avatar,12,headTitleIv);
                    headNameTv.setText(userName);
                    headGetInfoTv.setText("已领取"+gainNum+"/"+hbNum+",共"+gainAmount+"/"+hbAmount+"元");
                    headAmountAndCountTv.setText(hbAmount+"-"+hbNum);
                    if(selfAmount!=null){
                        int count = BigDecimalUtil.checkIsDoublePointTwo(selfAmount + "");
                            if(count==1){
                                headAmountTv.setText("¥"+selfAmount+"*");
                            }
                        else {
                            headAmountTv.setText("¥"+selfAmount);
                        }
                        if(selfNiuJi==-1){
                            headNiujiIv.setImageResource(R.drawable.ic_cow_0);
                        }else if(selfNiuJi==1){
                            headNiujiIv.setImageResource(R.drawable.ic_cow_1);
                        }else if(selfNiuJi==2){
                            headNiujiIv.setImageResource(R.drawable.ic_cow_2);
                        }else if(selfNiuJi==3){
                            headNiujiIv.setImageResource(R.drawable.ic_cow_3);
                        }else if(selfNiuJi==4){
                            headNiujiIv.setImageResource(R.drawable.ic_cow_4);
                        }else if(selfNiuJi==5){
                            headNiujiIv.setImageResource(R.drawable.ic_cow_5);
                        }else if(selfNiuJi==6){
                            headNiujiIv.setImageResource(R.drawable.ic_cow_6);
                        }else if(selfNiuJi==7){
                            headNiujiIv.setImageResource(R.drawable.ic_cow_7);
                        }else if(selfNiuJi==8){
                            headNiujiIv.setImageResource(R.drawable.ic_cow_8);
                        }else if(selfNiuJi==9){
                            headNiujiIv.setImageResource(R.drawable.ic_cow_9);
                        }else if(selfNiuJi==10){
                            headNiujiIv.setImageResource(R.drawable.ic_cow_10);
                        }
                        //庄家闲家赢的个数(只有庄家能看到)
                        if(bankerWinCount!=null){
                            bankWinCountTv.setText(bankerWinCount+"");
                            playerWinCountTv.setText(playerWinCount+"");
                        }
                    }
//    ---------------------------- 列表数据 ---------------------------------------------------------------
                    //recycleView item数据
                    JSONArray gains = data.getJSONArray("gains");
                    int size = gains.size();
                    RefreshUtil.succse(1,refreshLayout,loadingLinear,nodataLinear,size,false,isRefresh,pakegeDetailModelArrayList);
                    for (int i = 0; i < size; i++) {
                        JSONObject jsonObject = gains.getJSONObject(i);
                        String itemUserName = jsonObject.getString("userName");//item中用户名
                        Long itemUserId = jsonObject.getLong("userId");//item中用户id
                        String itemAvatar = jsonObject.getString("avatar");//item中用户头像
                        String ItemTouzhuDate = jsonObject.getString("touzhuDate");//item中抢包时间
                        BigDecimal amount = jsonObject.getBigDecimal("amount");//item中抢包金额
                        Integer niuJi = jsonObject.getInteger("niuJi");//item中抢包金额
                        Boolean isBanker = false;
                        if(jsonObject.containsKey("isBanker")){
                             isBanker = jsonObject.getBoolean("isBanker");//是否为庄家 (庄家需要显示庄家icon)
                        }
                        //String imageUrl, String userName, String amount, int niuji, String date, boolean isBank)
                        pakegeDetailModelArrayList.add(new PakegeDetailModel(itemAvatar,itemUserName,amount+"",niuJi,ItemTouzhuDate,isBanker));
                        pakegeDetailAdapter.notifyDataSetChanged();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFaild(String msg) {
                RefreshUtil.fail(isRefresh,false,1,refreshLayout);
            }
        });
    }
    private void bindFootView() {
        View footView = LayoutInflater.from(this).inflate(R.layout.status_layout,null);
        loadingLinear=footView.findViewById(R.id.loading_linear);
        errorLinear=footView.findViewById(R.id.error_linear);
        nodataLinear=footView.findViewById(R.id.nodata_linear);
        reloadTv=footView.findViewById(R.id.reload_tv);
        //layoutParam的作用是子控件告诉父控件自己要如何布局
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0,150,0,0);
        //nodataLinear的父控件是footView的根布局LinearLayout,将自定义的LinearLayout.layoutParams设置存入,表示noDataLinear 希望只能找layoutParam中的设置布局
        nodataLinear.setLayoutParams(layoutParams);
        errorLinear.setLayoutParams(layoutParams);
        pakegeDetailAdapter.addFooterView(footView);
    }
    @NotNull
    private void bindHeadView() {
        View headView = LayoutInflater.from(NiuNiuRedPakegeDetailActivity.this).inflate(R.layout.red_pakege_detail_head_layout,null);
        headTitleIv=headView.findViewById(R.id.pakege_head_head_title_iv);//头像
        headNameTv=headView.findViewById(R.id.pakege_detail_head_name_tv);//用户名
        headAmountAndCountTv=headView.findViewById(R.id.pakege_head_amount_and_count_tv);//红包金额-红包个数
        headAmountAndCountLinear=headView.findViewById(R.id.pakege_head_bank_amount_linear);
        headAmountTv=headView.findViewById(R.id.pakege_head_bank_amount_tv);//当前用户抢包金额
        headNiujiIv=headView.findViewById(R.id.pakege_head_bank_niuji_iv);//当前用户牛几
        headCurrentTimeTv=headView.findViewById(R.id.pakege_head_current_tv);//倒计时tv (倒计时结束后显示本包游戏已截止)
        bankWinCountTv=headView.findViewById(R.id.pakege_detail_head_bank_win_count_tv);//庄家赢的次数
        playerWinCountTv=headView.findViewById(R.id.pakege_detail_head_player_win_count_tv);//玩家赢得次数
        headGetInfoTv=headView.findViewById(R.id.pakege_detail_get_info_tv);//红包领取详情
        winOrLoseIv=headView.findViewById(R.id.pakege_detail_head_win_or_lose_iv);//用户胜还是败(只有闲家进入时才显示,庄家或者没有抢包的用户进入时都隐藏)
        bankAndPlayerWinCountLinear=headView.findViewById(R.id.pakege_head_bank_win_player_win_linear);//庄家和闲家赢的个数(只有庄家进入时才显示)
        pakegeDetailAdapter.addHeaderView(headView);
    }

    private void bindView() {
        toolbar=findViewById(R.id.message_fragent_toolbar);
        actionBarLeftIv=findViewById(R.id.action_left_iv);
        actionBarRightIv=findViewById(R.id.action_right_iv);
        actionBarRightIv.setVisibility(View.INVISIBLE);
        actionBarLeftIv.setImageResource(R.drawable.icon_back);
        actionBarLeftIv.setOnClickListener(this);
        refreshLayout=findViewById(R.id.pakege_detail_refresh);
        recyclerView=findViewById(R.id.redpakege_detail_recycle);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_red_pakege_detail;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_left_iv:
                finish();
                break;
                default:
                    break;
        }
    }
}
