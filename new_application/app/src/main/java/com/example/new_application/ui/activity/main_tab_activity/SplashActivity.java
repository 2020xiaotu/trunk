package com.example.new_application.ui.activity.main_tab_activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.LifecycleOwner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.common.BaseStringObserver;
import com.cambodia.zhanbang.rxhttp.net.model.SingleLoginEvent;
import com.cambodia.zhanbang.rxhttp.net.utils.AESUtil;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.new_application.BuildConfig;
import com.example.new_application.MainActivity;
import com.example.new_application.R;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.BaseUrlEntity;
import com.example.new_application.bean.ContactEntity;
import com.example.new_application.bean.SystemParamsEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiImpl;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.user_info_activity.CheckOldPhoneActivity;
import com.example.new_application.ui.pop.CommonChoosePop;
import com.example.new_application.ui.pop.CommonTipPop;
import com.example.new_application.ui.pop.ContactPop;
import com.example.new_application.utils.CommonStr;
import com.example.new_application.utils.GlideLoadViewUtil;
import com.example.new_application.utils.MyApplication;
import com.example.new_application.utils.Utils;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.error_linear)
    LinearLayout error_linear;
    @BindView(R.id.reload_tv)
    TextView reload_tv;
    @BindView(R.id.splash_image)
    ImageView splash_image;
    @BindView(R.id.skip_tv)
    TextView skip_tv;
    @BindView(R.id.skip_linear)
    LinearLayout  skip_linear;
    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
    Handler handler = new Handler();
    private KProgressHUD kProgressHUD;
    private BaseUrlEntity bestDomainBean;
    private List<BaseUrlEntity> dataBeanList;
    private String TAG = "SplashActivity";
    private int failCount;
    private CommonTipPop commonTipPop;
    private Integer appAdImageDurationSeconds;
    private String appAdImage;
    private MyHolder myHolder;
    static  boolean isSkip=false;
    private ContactPop contactPop;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        myHolder = new MyHolder(new WeakReference<>(this));
        String urlList = sp.getUrlList();
        List<BaseUrlEntity> baseUrlEntityList = JSONArray.parseArray(urlList, BaseUrlEntity.class);
        if(baseUrlEntityList!=null&&baseUrlEntityList.size()!=0){
            dataBeanList = baseUrlEntityList;
            chooseBestUrl();
        }else {
            requestSystemParas();
            requestAllLabelList();
        }

    }
    /**
     * 选择最优域名
     * @param
     */
    private void chooseBestUrl() {
        kProgressHUD = KProgressHUD.create(this);
        kProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setLabel("正在选择最优线路")
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        BaseUrlEntity appRequestDomainsBean = new BaseUrlEntity();
        appRequestDomainsBean.setDomain(BuildConfig.API_HOST1);
        dataBeanList.add(appRequestDomainsBean);
        for (int i = 0; i < dataBeanList.size(); i++) {
            BaseUrlEntity dataBean = dataBeanList.get(i);
            Observable<Response<ResponseBody>> compose = new HttpApiImpl(dataBean.getDomain())
                    .pingTest()
                    .timeout(2000, TimeUnit.MILLISECONDS)
                    .compose(RxTransformerUtils.io_main());
            compose
                    .as(AutoDispose.autoDisposable(AndroidLifecycleScopeProvider.from((LifecycleOwner)this)))
                    .subscribe(new BaseStringObserver<ResponseBody>() {
                        @Override
                        public void onSuccess(String result) {
                            LogUtils.e("onSuccess " + result);
                            try {
                                if(bestDomainBean==null){
                                    bestDomainBean= dataBean;
                                    sp.setNewBaseUrl( bestDomainBean.getDomain());
                                    if(kProgressHUD!=null){
                                        kProgressHUD.dismiss();
                                    }
                                    Log.e(TAG, "onSuccess: 响应最快: "+dataBean.getDomain() );
                                    requestSystemParas();
                                    requestAllLabelList();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(String msg) {

                            failCount++;
                            if(dataBeanList!=null&&failCount==dataBeanList.size()){
                                if(kProgressHUD!=null){
                                    kProgressHUD.dismiss();
                                }
                                requestSystemParas();
                                requestAllLabelList();
                            }
                        }
                    });
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.reload_tv,R.id.skip_linear})
    public  void onClick(View view){
        switch (view.getId()){
            case R.id.reload_tv:
                requestSystemParas();
                break;
            case R.id.skip_linear:
                skip2MainActivity(SplashActivity.this);
                break;
            default:
                break;
        }
    }

    /**
     * 请求域名列表
     */
    private void requestBaseUrlList() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("current",1);
        data.put("size",100);
        HttpApiUtils.wwwNormalRequest(this, null, RequestUtils.URL_LIST, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                sp.setUrlList(result);

                if(kProgressHUD!=null&&kProgressHUD.isShowing()){
                    kProgressHUD.dismiss();
                }

            }

            @Override
            public void onFail(String msg) {
                error_linear.setVisibility(View.VISIBLE);
                initChooseBaseUrlPop();
            }
        });
    }
    /**
     * 切换线路pop
     */
    private void initChooseBaseUrlPop() {
        if(commonTipPop == null){
            commonTipPop = new CommonTipPop(SplashActivity.this,SplashActivity.this,"系统提示","网络出错，请尝试以下方法恢复网络！\n1.检查手机网络,点击切换线路\n2.划掉APP重新进入\n如果您长时间无法访问，请进入官网"+BuildConfig.OFFICIAL_WEBSITE+"重新下载！","切换线路");
            commonTipPop.setOnClickLintener(new CommonTipPop.OnClickLintener() {
                @Override
                public void onSureClick(View view) {
                    startActivity(new Intent(SplashActivity.this, ChooseBaseUrlAvtivity.class));
                    commonTipPop.dismiss();
                }
            });

            commonTipPop.setOnCancelClickListener(new CommonTipPop.OnCancelClickListener() {
                @Override
                public void onCancelClick(View view) {
                    initContactPop(Utils.getSystemParamsEntity().getOnlineCustomer());
                    if(contactPop!=null){
                        contactPop.showAtLocation(error_linear, Gravity.BOTTOM,0,0);
                        Utils.darkenBackground(SplashActivity.this,0.7f);
                    }
                }
            });
            commonTipPop.getExit_cancel_tv().setText("联系客服");
        }
        List<BaseUrlEntity> baseUrlEntityList = JSONArray.parseArray(sp.getUrlList(), BaseUrlEntity.class);
        if(baseUrlEntityList!=null && baseUrlEntityList.size()!=0){
            commonTipPop.showAtLocation(splash_image, Gravity.CENTER,0,0);
            Utils.darkenBackground(SplashActivity.this,0.7f);
        }
    }
    private void initContactPop(String contactString) {
        ArrayList<ContactEntity> contactEntityArrayList = new ArrayList<>();
        contactEntityArrayList.add(new ContactEntity("telegram",contactString));
            contactPop = new ContactPop(this,contactEntityArrayList);
    }
    //单点登录
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void singleLogin(SingleLoginEvent singleLoginEvent) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClass(SplashActivity.this, LoginActivity.class);
        intent.putExtra(CommonStr.SINGLE_LOGIN,singleLoginEvent.isSingleLogin());
        intent.putExtra("flag",singleLoginEvent.getFlag());
        Utils.clearCache();
        startActivity(intent);
    }
    private void requestAllLabelList() {
        HttpApiUtils.wwwNormalRequest(this, null, RequestUtils.HOME_LABEL, new HashMap<String, Object>(), new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                sp.setAllLabel(result);

            }

            @Override
            public void onFail(String msg) {
            }
        });
    }

    private void requestSystemParas() {
        error_linear.setVisibility(View.GONE);
        HttpApiUtils.wwwRequest(this, null, RequestUtils.SYSTEM_PARAMS, new HashMap<String, Object>(), new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                SystemParamsEntity systemParamsEntity = JSONObject.parseObject(result, SystemParamsEntity.class);
                sp.setSystemParams(result);
                 appAdImageDurationSeconds = systemParamsEntity.getAppAdImageDurationSeconds();//倒计时时间
                 appAdImage = systemParamsEntity.getAppAdImage();
                if(StringMyUtil.isNotEmpty(appAdImage)){
                    GlideLoadViewUtil.LoadNormalView(SplashActivity.this, appAdImage,splash_image);
                }
                if(appAdImageDurationSeconds==null){
                    appAdImageDurationSeconds=0;
                }

                if(appAdImageDurationSeconds!=0){
                    myHolder.post(countDownTimeRunnable);
                }else{
                    Message msg = Message.obtain();
                    msg.what = 3;
                    myHolder.sendMessage(msg);
                }
                requestBaseUrlList();
            }

            @Override
            public void onFail(String msg) {
                error_linear.setVisibility(View.VISIBLE);
                initChooseBaseUrlPop();
            }
        });
    }
    Runnable countDownTimeRunnable = new Runnable() {
        @Override
        public void run() {
            if(StringMyUtil.isEmptyString(appAdImage)){
                skip_tv.setVisibility(View.GONE);
                skip_linear.setVisibility(View.GONE);
            }
            if(appAdImageDurationSeconds>0){
                skip_tv.setText("跳过("+appAdImageDurationSeconds+")S");
                if(skip_tv.getVisibility()!=View.VISIBLE){
                    skip_tv.setVisibility(View.VISIBLE);
                    skip_linear.setVisibility(View.VISIBLE);
                }
            }else {
                skip_tv.setText("跳过(0)S");
                Message msg = Message.obtain();
                msg.what = 3;
                myHolder.sendMessage(msg);
                myHolder.removeCallbacks(this);
            }
            appAdImageDurationSeconds--;
            myHolder.postDelayed(this,1000);
        }
    };
    static class MyHolder extends Handler{
        WeakReference<SplashActivity> splashActivityWeakReference;

        public MyHolder(WeakReference<SplashActivity> splashActivityWeakReference) {
            this.splashActivityWeakReference = splashActivityWeakReference;
        }

        @Override
        public void handleMessage(Message msg) {
            SplashActivity splashActivity = splashActivityWeakReference.get();
            if(null!=splashActivity){
                switch (msg.what) {
                    case 3:
                        if(splashActivity.appAdImageDurationSeconds<=0){//如果接口请求完了倒计时还没结束,则等到倒计时结束才进入首页
                            skip2MainActivity(splashActivity);
                        }
                        break;
                    default:
                        break;
                }
            }
            super.handleMessage(msg);

        }
    }
    private static void skip2MainActivity(SplashActivity splashActivity) {
        if(!isSkip){
            MainActivity.startAty(splashActivity,true);
            splashActivity.myHolder.removeCallbacksAndMessages(null);
        }
        isSkip=true;
    }
}