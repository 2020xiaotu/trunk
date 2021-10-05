package com.example.new_application.ui.activity.home_activity;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.HonestyMerchantAdapter;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.ContactEntity;
import com.example.new_application.bean.HelpChildEntity;
import com.example.new_application.bean.ServerDetailsEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.pop.CommonSurePop;
import com.example.new_application.ui.pop.ContactPop;
import com.example.new_application.utils.CommonToolbarUtil;
import com.example.new_application.utils.RefreshUtils;
import com.example.new_application.utils.Utils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HonestyMerchantActivity extends BaseActivity {
    @BindView(R.id.loading_linear)
    ConstraintLayout loading_linear;
    @BindView(R.id.error_linear)
    LinearLayout error_linear;
    @BindView(R.id.reload_tv)
    TextView reload_tv;
    @BindView(R.id.nodata_linear)
    LinearLayout nodata_linear;
    @BindView(R.id.honesty_merchant_refresh)
    SmartRefreshLayout honesty_merchant_refresh;
    @BindView(R.id.toolbar_right_iv)
    ImageView toolbar_right_iv;
    @BindView(R.id.honesty_merchant_recycler)
    RecyclerView honesty_merchant_recycler;
    HonestyMerchantAdapter honestyMerchantAdapter;
    ArrayList<ServerDetailsEntity.MemberInfoVoBean>honestyMerchantEntityArrayList = new ArrayList<>();
    int pageNo = 1 ;
    private ContactPop contactPop;
    private CommonSurePop commonSurePop;
    private String ruleContent;

    @Override
    public int getLayoutId() {
        return R.layout.activity_honesty_merchant;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void errorRefresh() {
        super.errorRefresh();
        requestList(false,false);
    }
    private void requestRule() {
        HttpApiUtils.pathRequest(this, null, RequestUtils.TIP_CONTENT, 8+"", new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<HelpChildEntity> helpChildEntities = JSONArray.parseArray(result, HelpChildEntity.class);
                if(helpChildEntities.size()>=1){
                    ruleContent = helpChildEntities.get(0).getContent();
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
    @Override
    protected void init(Bundle savedInstanceState) {
        toolbar_right_iv.setVisibility(View.VISIBLE);
        toolbar_right_iv.setImageResource(R.drawable.cjwt_icon);
        CommonToolbarUtil.initToolbar(this,"信誉专区");
        requestRule();
        initRefresh();
        initRecycler();
    }
    private void initRulePop() {
        if(commonSurePop == null){
            if(StringMyUtil.isNotEmpty(ruleContent)){
                commonSurePop = new CommonSurePop(this,"规则说明",ruleContent);
            }else {
                showtoast("暂无规则");
            }
        }
        if(commonSurePop!=null){

            commonSurePop.showAtLocation(toolbar_right_iv, Gravity.CENTER,0,0);
            Utils.darkenBackground(this,0.7f);
        }
    }
    @OnClick({R.id.toolbar_right_iv})
    public void  onCLick(View view){
        switch (view.getId()){
            case R.id.toolbar_right_iv:
                initRulePop();
                break;
            default:
                break;
        }
    }
    private void initRefresh() {
        RefreshUtils.initRefreshLoadMore(this, honesty_merchant_refresh, new RefreshUtils.OnRefreshLoadMoreLintener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                pageNo=1;
                requestList(true,false);

            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                pageNo++;
                requestList(false,true);
            }
        });
    }
    private void initContactPop(ServerDetailsEntity.MemberInfoVoBean memberInfoVo) {
        ArrayList<ContactEntity>contactEntityArrayList = new ArrayList<>();
        String weixin = memberInfoVo.getWeixin();
        String qq = memberInfoVo.getQq();
        String whatsapp = memberInfoVo.getWhatsapp();
        String telegram = memberInfoVo.getTelegram();
        String skype = memberInfoVo.getSkype();
        if(StringMyUtil.isNotEmpty(telegram)){
            contactEntityArrayList.add(new ContactEntity("telegram",telegram));
        }
        if(StringMyUtil.isNotEmpty(skype)){
            contactEntityArrayList.add(new ContactEntity("skype",skype));
        }
        if(StringMyUtil.isNotEmpty(whatsapp)){
            contactEntityArrayList.add(new ContactEntity("whatsapp",whatsapp));
        }
        if(StringMyUtil.isNotEmpty(weixin)){
            contactEntityArrayList.add(new ContactEntity("微信",weixin));
        }
        if(StringMyUtil.isNotEmpty(qq)){
            contactEntityArrayList.add(new ContactEntity("qq",qq));
        }

        contactPop = new ContactPop(this,contactEntityArrayList);
        contactPop.showAtLocation(honesty_merchant_refresh, Gravity.BOTTOM,0,0);
        Utils.darkenBackground(this,0.7f);
    }
    private void initRecycler() {
        honestyMerchantAdapter = new HonestyMerchantAdapter(R.layout.honesty_merchant_item,honestyMerchantEntityArrayList);
        honesty_merchant_recycler.setLayoutManager(new LinearLayoutManager(this));
        honesty_merchant_recycler.setAdapter(honestyMerchantAdapter);
        honestyMerchantAdapter.addChildClickViewIds(R.id.free_consult_iv);
        honestyMerchantAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                ServerDetailsEntity.MemberInfoVoBean memberInfoVoBean = honestyMerchantEntityArrayList.get(position);
                initContactPop(memberInfoVoBean);
            }
        });
        honestyMerchantAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ServerDetailsEntity.MemberInfoVoBean memberInfoVoBean = honestyMerchantEntityArrayList.get(position);
                StoreInformationActivity.startAty(HonestyMerchantActivity.this,memberInfoVoBean);
            }
        });
        requestList(false,false);
    }

    private void requestList(boolean isRefresh, boolean isLoadMore) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("current",pageNo);
        data.put("size",10);
        HttpApiUtils.wwwShowLoadRequest(this, null, RequestUtils.HONESTY_MERCHANT, data, loading_linear, error_linear, reload_tv, honesty_merchant_refresh, isLoadMore, isRefresh, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<ServerDetailsEntity.MemberInfoVoBean> honestyMerchantEntities = JSONArray.parseArray(result, ServerDetailsEntity.MemberInfoVoBean.class);
                RefreshUtils.succse(pageNo,honesty_merchant_refresh,loading_linear,nodata_linear,honestyMerchantEntities.size(),isLoadMore,isRefresh,honestyMerchantEntityArrayList);
                honestyMerchantEntityArrayList.addAll(honestyMerchantEntities);
                honestyMerchantAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
                RefreshUtils.fail(isRefresh,isLoadMore,pageNo,honesty_merchant_refresh);
            }
        });
    }
}