package com.zz.live.ui.activity.main_tab_activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.lifecycle.LifecycleOwner;
import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.common.BaseStringObserver;
import com.cambodia.zhanbang.rxhttp.net.model.SingleLoginEvent;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.RxTransformerUtils;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.gyf.immersionbar.ImmersionBar;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.tencent.rtmp.TXLiveBase;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.zz.live.BuildConfig;
import com.zz.live.MainActivity;
import com.zz.live.MyApplication;
import com.zz.live.R;
import com.zz.live.base.BaseActivity;
import com.zz.live.bean.BaseUrlEntity;
import com.zz.live.bean.LiveParamEntity;
import com.zz.live.bean.SystemParamsEntity;
import com.zz.live.net.api.HttpApiImpl;
import com.zz.live.net.api.HttpApiUtils;
import com.zz.live.ui.activity.main_tab_activity.house_keeper_activity.AnchorHouseKeeperActivity;
import com.zz.live.ui.pop.CommonChoosePop;
import com.zz.live.utils.AESUtil;
import com.zz.live.utils.ClearCache;
import com.zz.live.utils.CommonStr;
import com.zz.live.utils.RequestUtils;
import com.zz.live.utils.SharePreferencesUtil;
import com.zz.live.utils.StringMyUtil;
import com.zz.live.utils.Utils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.splash_image)
    ImageView splash_image;
    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
    CommonChoosePop commonChoosePop;
    @BindView(R.id.error_linear)
    LinearLayout error_linear;
    @BindView(R.id.reload_tv)
    TextView reload_tv;
    private String TAG="SplashActivity";
    Handler handler = new Handler();
    private KProgressHUD kProgressHUD;
    private BaseUrlEntity.DataBean bestDomainBean;
    private int failCount=0;
    private List<BaseUrlEntity.DataBean> dataBeanList;


    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        if(sp.getLoginStatus()){
            String urlList = sp.getUrlList();
            BaseUrlEntity baseUrlEntity = JSONObject.parseObject(urlList, BaseUrlEntity.class);
            if(baseUrlEntity!=null&&baseUrlEntity.getData().size()!=0){
                chooseBestUrl(baseUrlEntity);
            }else {
                requestSystemParams();
                requestAppStatistics();
            }
        }else {
            skipLogin();
        }

    }

    /**
     * ??????????????????
     * @param baseUrlEntity
     */
    private void chooseBestUrl(BaseUrlEntity baseUrlEntity) {
        kProgressHUD = KProgressHUD.create(this);
        kProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setLabel("????????????????????????")
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        dataBeanList = baseUrlEntity.getData();
        BaseUrlEntity.DataBean appRequestDomainsBean = new BaseUrlEntity.DataBean();
        appRequestDomainsBean.setDomain(AESUtil.encrypt(BuildConfig.API_HOST1));
        dataBeanList.add(appRequestDomainsBean);

        for (int i = 0; i < dataBeanList.size(); i++) {
            BaseUrlEntity.DataBean dataBean = dataBeanList.get(i);

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
                                    Log.e(TAG, "onSuccess: ????????????: "+dataBean.getDomain() );
                                    requestSystemParams();
                                    requestAppStatistics();
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
                                requestSystemParams();
                                requestAppStatistics();
                            }
                        }
                    });
        }
    }

    /**
     * APP??????
     */
    private void requestAppStatistics() {
        if(sp.getLoginStatus()){
            HttpApiUtils.pathNormalRequest(this, RequestUtils.APP_STATISTICS, Utils.getUserInfoBean().getId() + "", new HttpApiUtils.OnRequestLintener() {
                @Override
                public void onSuccess(String result) {
                    Log.e(TAG, "onSuccess: app??????????????????");
                }

                @Override
                public void onFail(String msg) {
                }
            });
        }
    }

    /**
     * ??????????????????
     */
    private void requestBaseUrlList() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("current",1);
        data.put("size",100);
        HttpApiUtils.wwwNormalRequest(this, null, RequestUtils.BASE_URL_LIST, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                sp.setUrlList(result);
                if(kProgressHUD!=null&&kProgressHUD.isShowing()){
                    kProgressHUD.dismiss();
                }
                if(sp.getLoginStatus()&&Utils.getUserInfoBean()!=null){//??????????????????????????????
                    int userType = Utils.getUserInfoBean().getUserType();
                    if(userType==1){
                        skipHouseKeeperHome();
                    }else {
                        skipLiveHome();
                    }
                }else {
                    skipLogin();
                }
            }

            @Override
            public void onFail(String msg) {
                error_linear.setVisibility(View.VISIBLE);
                initChooseBaseUrlPop();
            }
        });
    }

    @Override
    protected void initStatusBar() {
        super.initStatusBar();
        ImmersionBar.with(this)
                .statusBarDarkFont(true)   //????????????????????????????????????????????????
                .navigationBarColor(R.color.transparent)
                .init();
    }

    //????????????
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void singleLogin(SingleLoginEvent singleLoginEvent) {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClass(SplashActivity.this, LoginActivity.class);
        intent.putExtra(CommonStr.SINGLE_LOGIN,singleLoginEvent.isSingleLogin());
        intent.putExtra("flag",singleLoginEvent.getFlag());
        ClearCache.clearCache(MyApplication.getInstance());
        startActivity(intent);
    }
    private void requestSystemParams() {
        error_linear.setVisibility(View.GONE);
        HttpApiUtils.wwwRequest(this,null, RequestUtils.SYSTEM_PATAMS, new HashMap<String, Object>(), new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                SharePreferencesUtil.putString(SplashActivity.this,CommonStr.SYSTEM_PARAM,result);
                SystemParamsEntity systemParamsEntity = JSONObject.parseObject(result, SystemParamsEntity.class);
                SystemParamsEntity.DataBean data = systemParamsEntity.getData();
                //??????????????????
                String fileDomainUrl = data.getFileDomainUrl();
                SharePreferencesUtil.putString(SplashActivity.this, CommonStr.DOMIAN_URL,fileDomainUrl);
                //cp????????????
                String cpImageDomain = data.getCpImageDomain();
                SharePreferencesUtil.putString(SplashActivity.this, CommonStr.CP_DOMIAN_URL,cpImageDomain);
                //????????????
                String cpWebsiteName = data.getCpWebsiteName();
                SharePreferencesUtil.putString(SplashActivity.this, CommonStr.CP_BASE_URL,cpWebsiteName);

                //?????????????????????
                int watchRadio = data.getWatchRadio();
                SharePreferencesUtil.putInt(SplashActivity.this,CommonStr.WATCH_RADIO,watchRadio);
                //??????????????????
                String customerAddress = data.getCustomerAddress();
                SharePreferencesUtil.putString(SplashActivity.this,CommonStr.CUSTOMER,customerAddress);
                //????????????
                String nature = data.getNature();
                SharePreferencesUtil.putString(SplashActivity.this,CommonStr.NATURE,nature);
                requestAppKey();
            }

            @Override
            public void onFail(String msg) {
                error_linear.setVisibility(View.VISIBLE);
                initChooseBaseUrlPop();
            }
        });
    }

    /**
     * ?????????????????????appKey
     */
    private void requestAppKey() {
        error_linear.setVisibility(View.GONE);
        HttpApiUtils.wwwRequest(this, null,RequestUtils.TX_LIVE_PARAMS, new HashMap<String, Object>(), new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                try {
                    initAppkey(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFail(String msg) {
//                MHSDK.getInstance().init(MyApplication.getInstance(),"b162086e4e077d15341a4ce7ab56cd9e");
                error_linear.setVisibility(View.VISIBLE);
                initChooseBaseUrlPop();
            }
        });
    }

    private void initAppkey(String result) throws Exception {
        LiveParamEntity liveParamEntity = JSONObject.parseObject(result, LiveParamEntity.class);
        LiveParamEntity.DataBean dataBean = liveParamEntity.getData();
        //licenceURL
        String licenceURL = AESUtil.decrypt(dataBean.getSecretId());
        //licenceKey
        String licenceKey = AESUtil.decrypt(dataBean.getSecretKey());
        //?????????????????????sdk
        TXLiveBase.getInstance().setLicence(MyApplication.getInstance(), licenceURL, licenceKey);
        //??????key
        String meiHuKey = AESUtil.decrypt(dataBean.getMEIHU());

//                MHSDK.getInstance().init(MyApplication.getInstance(),StringMyUtil.isEmptyString(meiHuKey)?"b162086e4e077d15341a4ce7ab56cd9e":meiHuKey);
        //??????key
        String umKey = AESUtil.decrypt(dataBean.getUM_ANDROID());
        //????????????secret
        String umPushSecret = AESUtil.decrypt(dataBean.getUMPUSH_ANDROID());

        initUmeng(umKey,umPushSecret);

        requestBaseUrlList();
    }

    private void initUmeng(String appKey,String pushSecret) {
        /**
         * ?????????common???
         * ??????1:??????????????????????????????????????????
         * ??????2:?????? app key???????????????????????????Manifest??????????????????app key????????????????????????????????????Manifest????????????app key??????????????????????????????
         * ??????3:?????? channel???????????????????????????Manifest??????????????????channel????????????????????????????????????Manifest????????????channel?????????????????????????????????channel???????????????channel??????????????????
         * ??????4:??????????????????????????????????????????UMConfigure.DEVICE_TYPE_PHONE??????????????????????????????UMConfigure.DEVICE_TYPE_BOX?????????????????????????????????
         * ??????5:Push???????????????secret???????????????Push?????????????????????Push???secret???????????????
         * */
        if(!BuildConfig.DEBUG){
            UMConfigure.init(MyApplication.getInstance(), appKey,BuildConfig.FLAVOR, UMConfigure.DEVICE_TYPE_PHONE, null);
            // ??????AUTO??????????????????
//        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
            // ????????????SDK????????????
            UMConfigure.setLogEnabled(BuildConfig.DEBUG?false:true);
            //?????????????????????
            MobclickAgent.setCatchUncaughtExceptions(BuildConfig.DEBUG?false:true);
        }

    }
    @OnClick({R.id.reload_tv})
    public void  onClick(View v){
        switch (v.getId()){
            case R.id.reload_tv:
                requestSystemParams();
                break;
            default:
                break;
        }
    }
    private void skipLogin() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClass(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void skipHouseKeeperHome() {
        Intent intent = new Intent(SplashActivity.this, AnchorHouseKeeperActivity.class);
        startActivity(intent);
    }

    private void skipLiveHome() {
        closeLoading();
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
//        finish();
    }
    /**
     * ????????????pop
     */
    private void initChooseBaseUrlPop() {
        if(commonChoosePop == null){
            commonChoosePop = new CommonChoosePop(SplashActivity.this,SplashActivity.this,"????????????","??????????????????,?????????????????????","????????????");
            commonChoosePop.setOnClickLintener(new CommonChoosePop.OnClickLintener() {
                @Override
                public void onSureClick(View view) {
                    startActivity(new Intent(SplashActivity.this, ChooseBaseUrlAvtivity.class));
                    commonChoosePop.dismiss();
                }
            });
        }
        if(StringMyUtil.isNotEmpty(sp.getUrlList())){
            commonChoosePop.showAtLocation(splash_image, Gravity.CENTER,0,0);
            Utils.darkenBackground(SplashActivity.this,0.7f);
        }
    }
    @Override
    public void onNetChange(boolean netWorkState) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(handler!=null){
            handler.removeCallbacks(null);
            handler=null;
        }
        if(kProgressHUD!=null){
            kProgressHUD.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        if(handler!=null){
            handler.removeCallbacks(null);
            handler=null;
        }
        super.onDestroy();
    }
    /**
     *
     *??????ping???????????????
     *
     * @param dataBean
     * @return
     */
    public static long pingSuccessTime(BaseUrlEntity.DataBean dataBean) {
        String delay = new String();
        Process p = null;
        try {
            String domain = dataBean.getDomain();
            URL url = new URL(domain);
            String host = url.getHost();
            p = Runtime.getRuntime().exec("ping -c 1 " + host);
            System.out.println("??????: "+host);
            BufferedReader buf = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String str = new String();
            while ((str = buf.readLine()) != null) {
                if (str.contains("avg")) {
                    int i = str.indexOf("/", 20);
                    int j = str.indexOf(".", i);
                    System.out.println("??????:" + str.substring(i + 1, j));
                    delay = str.substring(i + 1, j);
                    return Long.parseLong(StringMyUtil.isEmptyString(delay)?"0":delay);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Long.parseLong(StringMyUtil.isEmptyString(delay)?"100000":delay);
    }

}
