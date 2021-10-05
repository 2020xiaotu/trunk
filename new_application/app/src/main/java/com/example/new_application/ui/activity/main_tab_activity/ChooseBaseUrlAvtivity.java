package com.example.new_application.ui.activity.main_tab_activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.common.BaseStringObserver;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.BaseUrlAdapter;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.BaseUrlEntity;
import com.example.new_application.net.api.HttpApiImpl;
import com.example.new_application.utils.CommonToolbarUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class ChooseBaseUrlAvtivity extends BaseActivity {
    @BindView(R.id.base_url_recycler)
    RecyclerView base_url_recycler;
    BaseUrlAdapter baseUrlAdapter;
    ArrayList<BaseUrlEntity> baseUrlEntityArrayList = new ArrayList<>();
    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();


    @Override
    public int getLayoutId() {
        return R.layout.activity_choose_base_url_avtivity;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) { ;
        initRecycler();
    }

    @Override
    protected void initStatusBar() {
        super.initStatusBar();
        CommonToolbarUtil.initToolbar(this,"选择线路");
        CommonToolbarUtil.initStatusBarColor(this);
    }

    private void initRecycler() {
        baseUrlAdapter = new BaseUrlAdapter(R.layout.base_url_recycler_item,baseUrlEntityArrayList);
        base_url_recycler.setLayoutManager(new LinearLayoutManager(this));
        base_url_recycler.setAdapter(baseUrlAdapter);
        baseUrlAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                for (int i = 0; i < baseUrlEntityArrayList.size(); i++) {
                    BaseUrlEntity appRequestDomainsBean = baseUrlEntityArrayList.get(i);
                    if(position==i){
                        appRequestDomainsBean.setCheck(true);
                        sp.setNewBaseUrl(appRequestDomainsBean.getDomain());
                        startActivity(new Intent(ChooseBaseUrlAvtivity.this,SplashActivity.class));
                        finish();
                    }else {
                        appRequestDomainsBean.setCheck(false);
                    }
                }
                baseUrlAdapter.notifyDataSetChanged();
            }
        });
        String urlList = sp.getUrlList();
        String newBaseUrl = sp.getNewBaseUrl();
        List<BaseUrlEntity> baseUrlEntityList = JSONArray.parseArray(urlList, BaseUrlEntity.class);
        chooseBestUrl(baseUrlEntityList);
        for (int i = 0; i < baseUrlEntityList.size(); i++) {
            BaseUrlEntity appRequestDomainsBean = baseUrlEntityList.get(i);
            if(newBaseUrl.equals(appRequestDomainsBean.getDomain())){
                appRequestDomainsBean.setCheck(true);
            }
        }
        baseUrlEntityArrayList.addAll(baseUrlEntityList);
        baseUrlAdapter.notifyDataSetChanged();

    }

    /**
     * 记录baseUrl访问速度
     * @param
     */
    private void chooseBestUrl(List<BaseUrlEntity> baseUrlEntityArrayList) {

        for (int i = 0; i < baseUrlEntityArrayList.size(); i++) {
            BaseUrlEntity appRequestDomainsBean1 = baseUrlEntityArrayList.get(i);
            appRequestDomainsBean1.setStartTime(System.currentTimeMillis());
            appRequestDomainsBean1.setEndTime(0);//每次都清空endTime
//            Observable<Response<ResponseBody>> compose = new HttpApiImpl(appRequestDomainsBean1.getDomain())
            String apiHost;
   /*         if( mSharedPreferenceHelperImpl.getAppVestFlag()==1){
                SecurityConnection conn = AppVest.getServerIPAndPort("www.tiantian.com", 1002);
                apiHost=  String.format("http://%s:%s/web/ws/", conn.getServerIp(), conn.getServerPort());
            }else {*/
                apiHost = appRequestDomainsBean1.getDomain();
//            }
            Observable<Response<ResponseBody>> compose = new HttpApiImpl(apiHost)
                    .pingTest()
                    .timeout(10000, TimeUnit.MILLISECONDS)
                    .compose(RxTransformerUtils.io_main());
            compose.as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner)this)))
                    .subscribe(new BaseStringObserver<ResponseBody>() {
                        @Override
                        public void onSuccess(String result) {
                            appRequestDomainsBean1.setEndTime(System.currentTimeMillis());
                            appRequestDomainsBean1.setSuccess(true);
                            try {
                                sp.setNewBaseUrl( appRequestDomainsBean1.getDomain());
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            baseUrlAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onFail(String msg) {
                            appRequestDomainsBean1.setSuccess(false);
                            baseUrlAdapter.notifyDataSetChanged();
                        }
                    });
        }
    }
    @OnClick(R.id.refresh_request_tv)
    public void onClick(View view){
        switch (view.getId()){
            case R.id.refresh_request_tv:
                chooseBestUrl(baseUrlEntityArrayList);
                break;
            default:
                break;
        }
    }
}
