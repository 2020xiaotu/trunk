package com.example.red_pakege.rong_yun.activity.red_pakege_detail_activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.model.SaoLeiModel;
import com.example.red_pakege.net.api.HttpApiUtils;
import com.example.red_pakege.rong_yun.adapter.SaoLeiPakegeDetailAdapter;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.CheckJsonUtil;
import com.example.red_pakege.util.GlideLoadViewUtil;
import com.example.red_pakege.util.RefreshUtil;
import com.example.red_pakege.util.RequestUtil;
import com.example.red_pakege.util.StringMyUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Headers;

public class FuLiRedPakegeDetailActivity extends BaseActivity implements View.OnClickListener {
    private String orderCode;
    private Integer typeId;
    private Toolbar toolbar;
    private ImageView actionBarLeftIv;
    private ImageView actionBarRightIv;
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private SaoLeiPakegeDetailAdapter saoLeiPakegeDetailAdapter;
    private ArrayList<SaoLeiModel> saoLeiModelArrayList = new ArrayList<>();

    private  SharedPreferenceHelperImpl sp;
    private SimpleDateFormat sformat =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private ImageView headTitleIv;
    private TextView headNameTv;
    private TextView headAmountAndCountTv;
    private TextView headAmountTv;
    private TextView pakegeGainInfoTv;

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
    public void errorRefresh() {
        super.errorRefresh();
        getPakgeInfo(orderCode,false);
    }

    private void bindActionBar() {
        ActionBarUtil.initMainActionbar(FuLiRedPakegeDetailActivity.this,toolbar);
    }

    private void getintentInfo() {
        Intent intent = getIntent();
        orderCode= intent.getStringExtra("orderCode");
        typeId= intent.getIntExtra("typeId",0);
    }

    public static void startAty(Context context,String orderCode,int typeId){
        Intent intent = new Intent(context, FuLiRedPakegeDetailActivity.class);
        intent.putExtra("orderCode",orderCode);
        intent.putExtra("typeId",typeId);
        context.startActivity(intent);
    }
    private void initRecycle() {
        saoLeiPakegeDetailAdapter=new SaoLeiPakegeDetailAdapter(saoLeiModelArrayList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(saoLeiPakegeDetailAdapter);
        bindHeadView();
        bindFootView();

        getPakgeInfo(orderCode,false);
    }

    private void bindFootView() {
        View footView = LayoutInflater.from(FuLiRedPakegeDetailActivity.this).inflate(R.layout.status_layout,null);
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
        saoLeiPakegeDetailAdapter.addFooterView(footView);
    }

    private void getPakgeInfo(String orderCode,boolean isRefresh) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("orderCode",orderCode);
        data.put("typeId",typeId);
        HttpApiUtils.footShowLoadRequest(FuLiRedPakegeDetailActivity.this,null,RequestUtil.PAKEGE_DETAIL,data,loadingLinear,errorLinear,reloadTv, (View) refreshLayout,false,isRefresh, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result, Headers headers) {
                if(isRefresh){
                    refreshLayout.finishRefresh();
                }
                saoLeiModelArrayList.clear();
                JSONObject data = JSONObject.parseObject(result).getJSONObject("data");
//                ------------------------  ???????????? -------------------------------
                String fbImage = data.getString("fbImage");//????????????url
                String selfAmount =CheckJsonUtil. checkStringJson(data, "selfAmount");//????????????
                String remark = data.getString("remark");//??????remark
                String fbName = data.getString("fbName");//???????????????name
                String info = data.getString("info");//???????????????????????????(????????????xx??? xx??????)
                GlideLoadViewUtil.LoadCircleView(FuLiRedPakegeDetailActivity.this,fbImage,headTitleIv);
                headNameTv.setText(fbName);
                if(StringMyUtil.isEmptyString(selfAmount)){
                    headAmountTv.setVisibility(View.GONE);
                }else {
                    headAmountTv.setVisibility(View.VISIBLE);
                    headAmountTv.setText(selfAmount);
                }
                headAmountAndCountTv.setText(remark);
                pakegeGainInfoTv.setText(info);
//                ---------------------------  ???????????? ------------------------------------
                JSONArray list = data.getJSONArray("list");
                int size = list.size();
                RefreshUtil.succse(1,refreshLayout,loadingLinear,nodataLinear,size,false,isRefresh,saoLeiModelArrayList);
                for (int i = 0; i < size; i++) {
                    JSONObject jsonObject = list.getJSONObject(i);
                    String qbName = jsonObject.getString("qbName");//??????username
                    boolean isBigger = jsonObject.getBoolean("isBigger");//??????????????????
                    String amount = jsonObject.getString("amount");//????????????
                    String createdDate = jsonObject.getString("createdDate");//??????????????????
                    String qbImage = jsonObject.getString("qbImage");//??????????????????
                    Boolean isBomb = CheckJsonUtil.checkBooleanJson(jsonObject, "isBomb");
                    boolean isMySelf = jsonObject.getBoolean("isMySelf");//??????????????????
                    //String qbName, String amount, String createdDate, String qbImage, boolean isMySelf, boolean isBoom, boolean isBestLuck
                    saoLeiModelArrayList.add(new SaoLeiModel(qbName,amount,createdDate,qbImage,isMySelf,isBomb,isBigger));
                }
                    saoLeiPakegeDetailAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFaild(String msg) {
                if(isRefresh){
                    refreshLayout.finishRefresh(false);
                }
                RefreshUtil.fail(isRefresh,false,1,refreshLayout);
            }
        });
    }

    @NotNull
    private void bindHeadView() {
        View headView = LayoutInflater.from(FuLiRedPakegeDetailActivity.this).inflate(R.layout.fuli_red_pakege_detail_head_layout,null);
        headTitleIv=headView.findViewById(R.id.pakege_head_head_title_iv);//??????
        headNameTv=headView.findViewById(R.id.pakege_detail_head_name_tv);//?????????
        headAmountAndCountTv=headView.findViewById(R.id.pakege_head_amount_and_count_tv);//????????????-????????????
        headAmountTv=headView.findViewById(R.id.pakege_head_bank_amount_tv);//????????????????????????
        pakegeGainInfoTv=headView.findViewById(R.id.pakege_detail_get_info_tv);//???????????????????????????
        saoLeiPakegeDetailAdapter.addHeaderView(headView);
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
        return R.layout.saolei_activity_red_pakege_detail;
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
