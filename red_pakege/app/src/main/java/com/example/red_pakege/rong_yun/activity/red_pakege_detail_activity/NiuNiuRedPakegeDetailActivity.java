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
    //?????????runnable???????????????
    private boolean isCurrentRunnableRun=false;
    //?????????????????????????????????
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
            EventBus.getDefault().register(this);//??????eventBus
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
                currentStr="??????"+minute+":"+sencond;
                headCurrentTimeTv.setText(currentStr);
                handler.postDelayed(currentRunnable,1000);
//                handler.sendEmptyMessageDelayed(1,1000);
            }else {
                headCurrentTimeTv.setTextColor(getResources().getColor(R.color.default_two_color));
                currentStr="?????????";
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
                //recycleView ????????????
                Long userId = data.getLong("userId");
                String userName = data.getString("userName");
                String avatar = data.getString("avatar");//??????
                String touzhuDate = data.getString("touzhuDate");//????????????
                String lotteryTime = data.getString("lotteryTime");//?????????????????????
                int hbNum = data.getInteger("hbNum");//????????????
                BigDecimal hbAmount = data.getBigDecimal("hbAmount");//????????????
                Integer gainNum = data.getInteger("gainNum");//????????????
                BigDecimal gainAmount = data.getBigDecimal("gainAmount");//????????????
                Integer playerType = data.getInteger("playerType");//???????????? 0 ?????? 1??????  2??????

                Integer bankerWinCount = data.getInteger("bankerWinCount");//??????????????????(???????????????)
                Integer playerWinCount = data.getInteger("playerWinCount");//??????????????????(???????????????)
                BigDecimal selfAmount = data.getBigDecimal("selfAmount");//??????????????????(??????????????????)
                Integer selfNiuJi = data.getInteger("selfNiuJi");//????????????(??????????????????)
                Boolean isLottery = data.getBoolean("isLottery");//??????????????????
                Date date= sformat.parse(lotteryTime);
                currentTime= date.getTime()-(new Date().getTime()+shiJianCha);
                //TODO ???????????????????????? ???????????????isOver?????????
                boolean isOver;//????????????????????????
                if(currentTime>0||null==isLottery||isLottery==false){
                    isOver=false;
                    //???????????????????????????,????????????message,?????????????????????(?????????????????????handler????????????currentRunnable??????????????????,??????handler.hasCallBacks()??????????????????......)
                    if(!isCurrentRunnableRun){
                        handler.sendEmptyMessage(1);
                    }
                }else{
                    isOver=true;
                    if(haveResult){
                        //??????????????????????????????,????????????????????????(??????????????????,????????????????????????,????????????????????????,????????????????????????,??????????????????)
                        headCurrentTimeTv.setText("?????????????????????");
                    }
                }
                if(playerType==0){//????????????(???????????????????????????)
                    headAmountAndCountLinear.setVisibility(View.GONE);//????????????????????????
                    winOrLoseIv.setVisibility(View.GONE);//????????????icon
                    bankAndPlayerWinCountLinear.setVisibility(View.GONE);//?????????????????????????????????
//                    headCurrentTimeTv.setVisibility(View.GONE);
                }
                if(playerType==1){//??????
                    bankAndPlayerWinCountLinear.setVisibility(View.GONE);
                    if(isOver){//?????????????????????,????????????????????????
//                        if(!headCurrentTimeTv.getText().equals("?????????")){
                        boolean containsKey = data.containsKey("isWin");
                        if(containsKey){

                            boolean isWin = data.getBoolean("isWin");//????????????
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
                        //??????????????????, ??????????????????
                        winOrLoseIv.setVisibility(View.GONE);
                    }
                }
                if(playerType==2){//??????
                    bankAndPlayerWinCountLinear.setVisibility(View.VISIBLE);
                    winOrLoseIv.setVisibility(View.GONE);
                }
                    GlideLoadViewUtil.LoadCornersView(NiuNiuRedPakegeDetailActivity.this,avatar,12,headTitleIv);
                    headNameTv.setText(userName);
                    headGetInfoTv.setText("?????????"+gainNum+"/"+hbNum+",???"+gainAmount+"/"+hbAmount+"???");
                    headAmountAndCountTv.setText(hbAmount+"-"+hbNum);
                    if(selfAmount!=null){
                        int count = BigDecimalUtil.checkIsDoublePointTwo(selfAmount + "");
                            if(count==1){
                                headAmountTv.setText("??"+selfAmount+"*");
                            }
                        else {
                            headAmountTv.setText("??"+selfAmount);
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
                        //????????????????????????(?????????????????????)
                        if(bankerWinCount!=null){
                            bankWinCountTv.setText(bankerWinCount+"");
                            playerWinCountTv.setText(playerWinCount+"");
                        }
                    }
//    ---------------------------- ???????????? ---------------------------------------------------------------
                    //recycleView item??????
                    JSONArray gains = data.getJSONArray("gains");
                    int size = gains.size();
                    RefreshUtil.succse(1,refreshLayout,loadingLinear,nodataLinear,size,false,isRefresh,pakegeDetailModelArrayList);
                    for (int i = 0; i < size; i++) {
                        JSONObject jsonObject = gains.getJSONObject(i);
                        String itemUserName = jsonObject.getString("userName");//item????????????
                        Long itemUserId = jsonObject.getLong("userId");//item?????????id
                        String itemAvatar = jsonObject.getString("avatar");//item???????????????
                        String ItemTouzhuDate = jsonObject.getString("touzhuDate");//item???????????????
                        BigDecimal amount = jsonObject.getBigDecimal("amount");//item???????????????
                        Integer niuJi = jsonObject.getInteger("niuJi");//item???????????????
                        Boolean isBanker = false;
                        if(jsonObject.containsKey("isBanker")){
                             isBanker = jsonObject.getBoolean("isBanker");//??????????????? (????????????????????????icon)
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
        //layoutParam?????????????????????????????????????????????????????????
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(0,150,0,0);
        //nodataLinear???????????????footView????????????LinearLayout,???????????????LinearLayout.layoutParams????????????,??????noDataLinear ???????????????layoutParam??????????????????
        nodataLinear.setLayoutParams(layoutParams);
        errorLinear.setLayoutParams(layoutParams);
        pakegeDetailAdapter.addFooterView(footView);
    }
    @NotNull
    private void bindHeadView() {
        View headView = LayoutInflater.from(NiuNiuRedPakegeDetailActivity.this).inflate(R.layout.red_pakege_detail_head_layout,null);
        headTitleIv=headView.findViewById(R.id.pakege_head_head_title_iv);//??????
        headNameTv=headView.findViewById(R.id.pakege_detail_head_name_tv);//?????????
        headAmountAndCountTv=headView.findViewById(R.id.pakege_head_amount_and_count_tv);//????????????-????????????
        headAmountAndCountLinear=headView.findViewById(R.id.pakege_head_bank_amount_linear);
        headAmountTv=headView.findViewById(R.id.pakege_head_bank_amount_tv);//????????????????????????
        headNiujiIv=headView.findViewById(R.id.pakege_head_bank_niuji_iv);//??????????????????
        headCurrentTimeTv=headView.findViewById(R.id.pakege_head_current_tv);//?????????tv (?????????????????????????????????????????????)
        bankWinCountTv=headView.findViewById(R.id.pakege_detail_head_bank_win_count_tv);//??????????????????
        playerWinCountTv=headView.findViewById(R.id.pakege_detail_head_player_win_count_tv);//??????????????????
        headGetInfoTv=headView.findViewById(R.id.pakege_detail_get_info_tv);//??????????????????
        winOrLoseIv=headView.findViewById(R.id.pakege_detail_head_win_or_lose_iv);//??????????????????(??????????????????????????????,???????????????????????????????????????????????????)
        bankAndPlayerWinCountLinear=headView.findViewById(R.id.pakege_head_bank_win_player_win_linear);//???????????????????????????(??????????????????????????????)
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
