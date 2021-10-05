package com.example.new_application;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.cretin.www.cretinautoupdatelibrary.interfaces.AppDownloadListener;
import com.cretin.www.cretinautoupdatelibrary.interfaces.AppUpdateInfoListener;
import com.cretin.www.cretinautoupdatelibrary.model.DownloadInfo;
import com.cretin.www.cretinautoupdatelibrary.model.TypeConfig;
import com.cretin.www.cretinautoupdatelibrary.utils.AppUpdateUtils;
import com.example.new_application.adapter.BannerViewHolder;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.base.BasePopupWindow;
import com.example.new_application.bean.AllMessageUnReadEvenEntity;
import com.example.new_application.bean.AppUpdateModel;
import com.example.new_application.bean.AppUploadEntity;
import com.example.new_application.bean.GuaranteeOrderEntity;
import com.example.new_application.bean.HomeAddEntity;
import com.example.new_application.bean.HomeNoticeEntity;
import com.example.new_application.bean.MineBannerEntity;
import com.example.new_application.bean.UnReadMessageEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.home_activity.TakeGuaranteeActivity;
import com.example.new_application.ui.activity.main_tab_activity.LoginActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.CertificationActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.ReleaseServerActivity;
import com.example.new_application.ui.activity.user_info_activity.BannerWebViewActivity;
import com.example.new_application.ui.fragment.main_tab_fragment.ClassificationFragment;
import com.example.new_application.ui.fragment.main_tab_fragment.HomeFragment;
import com.example.new_application.ui.fragment.main_tab_fragment.MineFragment;
import com.example.new_application.ui.fragment.main_tab_fragment.TabMessageFragment;
import com.example.new_application.ui.pop.HomeAddPop;
import com.example.new_application.ui.pop.HomeAtyPop;
import com.example.new_application.utils.ActivityUtil;
import com.example.new_application.utils.ScreenUtils;
import com.example.new_application.utils.Utils;
import com.zhpan.bannerview.constants.IndicatorGravity;
import com.zhpan.bannerview.constants.IndicatorSlideMode;
import com.zhpan.bannerview.constants.IndicatorStyle;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.internal.Util;

public class MainActivity extends BaseActivity  {
    @BindView(R.id.tab_home_linear)
    LinearLayout tab_home_linear;
    @BindView(R.id.tab_home_iv)
    ImageView tab_home_iv;
    @BindView(R.id.tab_home_tv)
    TextView tab_home_tv;
    @BindView(R.id.tab_classification_linear)
    LinearLayout tab_classification_linear;
    @BindView(R.id.tab_classification_iv)
    ImageView tab_classification_iv;
    @BindView(R.id.tab_classification_tv)
    TextView tab_classification_tv;
    @BindView(R.id.tab_share_linear)
    LinearLayout tab_share_linear;
    @BindView(R.id.tab_share_iv)
    ImageView tab_share_iv;
    @BindView(R.id.tab_share_tv)
    TextView tab_share_tv;
    @BindView(R.id.tab_mine_linear)
    LinearLayout tab_mine_linear;
    @BindView(R.id.tab_mine_iv)
    ImageView tab_mine_iv;
    @BindView(R.id.tab_mine_tv)
    TextView tab_mine_tv;
    @BindView(R.id.main_tab_un_read_tv)
    TextView main_tab_un_read_tv;
    private HomeFragment mHomeFragment;
    private ClassificationFragment mClassificationFragment;
    private TabMessageFragment tabMessageFragment;
    private MineFragment mMineFragment;
    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
    // 当前正在显示的Fragment
    private Fragment mCurrentFragment;
    private String versionCode;
    private String versionName;
    private String updateTip;
    private HomeAddPop homeAddPop;
    private int y1, y2;//addPop 两个linear的高度
    public UnReadMessageEntity unReadMessageEntity;
    private long firstTime =0;
    boolean showAtyPop ;
    HomeAtyPop homeAtyPop;
    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void getIntentData() {
        showAtyPop = getIntent().getBooleanExtra("showAtyPop",false);
    }
    public static void startAty(Context context,boolean showAtyPop){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("showAtyPop",showAtyPop);
        context.startActivity(intent);
    }
    @Override
    protected void init(Bundle savedInstanceState) {
        tab_home_linear.performClick();
        requestAppUpload();
        if(showAtyPop){
            requestNoticeData();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(StringMyUtil.isNotEmpty(sp.getToken())){
            requestUnreadMessage();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    private void requestNoticeData() {
        HttpApiUtils.pathNormalRequest(this, null, RequestUtils.SYSTEM_NOTICE, "3", new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<HomeNoticeEntity> homeNoticeEntities = JSONArray.parseArray(result, HomeNoticeEntity.class);
                if(homeNoticeEntities.size()!=0){
                    ArrayList<HomeNoticeEntity> homeNoticeEntityArrayList = new ArrayList<>();
                    homeNoticeEntityArrayList.addAll(homeNoticeEntities);
                    homeAtyPop = new HomeAtyPop(MainActivity.this,MainActivity.this,homeNoticeEntityArrayList);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            homeAtyPop.showAtLocation(tab_home_linear,Gravity.CENTER,0,0);
                            Utils.darkenBackground(MainActivity.this,0.7f);
                        }
                    },200);
                }

            }

            @Override
            public void onFail(String msg) {
            }
        });
    }
    public void requestUnreadMessage() {
        HashMap<String, Object> data = new HashMap<>();
        HttpApiUtils.wwwNormalRequest(this, null, RequestUtils.UN_READ_MESSAGE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                 unReadMessageEntity = JSONObject.parseObject(result, UnReadMessageEntity.class);
                String typeAll = unReadMessageEntity.getTypeAll();
                if(StringMyUtil.isNotEmpty(typeAll)&&!typeAll.equals("0")){
                    main_tab_un_read_tv.setVisibility(View.VISIBLE);
                    main_tab_un_read_tv.setText(typeAll);
                }else {
                    main_tab_un_read_tv.setVisibility(View.GONE);
                }
                EventBus.getDefault().post(new AllMessageUnReadEvenEntity(unReadMessageEntity));
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    private void requestAppUpload() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("systemType",2);
        data.put("versionCode",BuildConfig.VERSION_CODE);
        HttpApiUtils.wwwNormalRequest(this, null, RequestUtils.APP_UPLOAD, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if(jsonObject.containsKey("appVersion")){
                    /**
                     * 有新版本
                     */
                    AppUploadEntity appUploadEntity = JSONObject.parseObject(result, AppUploadEntity.class);
                    AppUploadEntity.AppVersionBean appVersionBean = appUploadEntity.getAppVersion();
                    versionCode = appVersionBean.getVersionCode();
                    versionName = appVersionBean.getVersionName();
                    updateTip = appVersionBean.getDescription();
                    if(BuildConfig.VERSION_CODE !=Integer.parseInt(versionCode)){
                        boolean isForce;
                        if(appVersionBean.getIsForce().equals("1")){
                            isForce = true;
                        }else {
                            isForce = false;
                        }
                        AppUpdateModel appUploadModel = new AppUpdateModel();
                        appUploadModel.setAutoUpdateBackground(false);
                        appUploadModel.setCheckFileMD5(false);
                        appUploadModel.setSourceTypeVaule(TypeConfig.DATA_SOURCE_TYPE_MODEL);
                        appUploadModel.setUiTypeValue(TypeConfig.UI_THEME_B);
                        if (isForce) {//强制更新
                            appUploadModel.setForceUpdate(true);
                        } else {//非强制更新
                            appUploadModel.setForceUpdate(false);
                        }
                        try {
                            startUpdateApp(appUploadModel, appVersionBean.getDownLoadUrl());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
    /**
     * 弹出更新pop
     * @param appUploadModel
     * @param downloadUrl
     * @throws IOException
     */
    private void startUpdateApp(AppUpdateModel appUploadModel,String downloadUrl) throws IOException {
        DownloadInfo downloadInfo = new DownloadInfo().setApkUrl(downloadUrl)
                //EC42E7E1FE580F1E187C8E10355A13BB0
//                .setFileSize(getContentLength(downloadUrl))
                .setProdVersionCode(Integer.parseInt(versionCode))
                .setProdVersionName(versionName)
                .setForceUpdateFlag(appUploadModel.isForceUpdate() ? 1 : 0)
                .setUpdateLog(updateTip);
        AppUpdateUtils appUpdateUtils = AppUpdateUtils.getInstance();
        appUpdateUtils.getUpdateConfig().setUiThemeType(appUploadModel.getUiTypeValue());
        //打开文件MD5校验
        appUpdateUtils.getUpdateConfig().setNeedFileMD5Check(appUploadModel.isCheckFileMD5());
        appUpdateUtils.getUpdateConfig().setDataSourceType(appUploadModel.getSourceTypeVaule());
        //开启或者关闭后台静默下载功能
        appUpdateUtils.getUpdateConfig().setAutoDownloadBackground(appUploadModel.isAutoUpdateBackground());
        if (appUploadModel.isAutoUpdateBackground()) {
            //开启静默下载的时候推荐关闭通知栏进度提示
            appUpdateUtils.getUpdateConfig().setShowNotification(false);
        } else {
            appUpdateUtils.getUpdateConfig().setShowNotification(true);
        }
        appUpdateUtils
                .addAppUpdateInfoListener(new AppUpdateInfoListener() {
                    @Override
                    public void isLatestVersion(boolean isLatest) {
                        Log.e("HHHHHHHHHHHHHHH", "isLatest:" + isLatest);
                    }
                })
                .addAppDownloadListener(new AppDownloadListener() {
                    @Override
                    public void downloading(int progress) {
                        Log.e("HHHHHHHHHHHHHHH", "progress:" + progress);
                    }

                    @Override
                    public void downloadFail(String msg) {
                        Log.e("HHHHHHHHHHHHHHH", "msg:" + msg);
                    }

                    @Override
                    public void downloadComplete(String path) {
                        Log.e("HHHHHHHHHHHHHHH", "path:" + path);

                    }

                    @Override
                    public void downloadStart() {
                        Log.e("HHHHHHHHHHHHHHH", "start");
//                        sendMessage2Fragment();
                    }

                    @Override
                    public void reDownload() {
                        Log.e("HHHHHHHHHHHHHHH", "reDownload");
                    }

                    @Override
                    public void pause() {
                        Log.e("HHHHHHHHHHHHHHH", "pause");
                    }
                })
                .checkUpdate(downloadInfo);
    }
    /**
     * 首页4个Fragment切换, 使用hide和show, 而不是replace.
     *
     * @param target 要显示的目标Fragment.
     */
    private void switchFragments(Fragment target) {
        if (mCurrentFragment == target) return;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (mCurrentFragment != null) {
            // 隐藏当前正在显示的Fragment
            transaction.hide(mCurrentFragment);
        }
        if (target.isAdded()) {
            // 如果目标Fragment已经添加过,就显示它
            transaction.show(target);
        } else {
            // 否则直接添加该Fragment
            transaction.add(R.id.fl_container, target, target.getClass().getName());
        }
        transaction.commit();
        mCurrentFragment = target;
    }

    /**
     * 找回FragmentManager中存储的Fragment
     */
    private void retrieveFragments() {
        FragmentManager manager = getSupportFragmentManager();
        mHomeFragment = (HomeFragment) manager.findFragmentByTag(HomeFragment.class.getName());
        mClassificationFragment = (ClassificationFragment) manager.findFragmentByTag(Class.class.getName());
        tabMessageFragment = (TabMessageFragment) manager.findFragmentByTag(TabMessageFragment.class.getName());
        mMineFragment = (MineFragment) manager.findFragmentByTag(MineFragment.class.getName());
    }
    @OnClick({R.id.tab_home_linear,R.id.tab_classification_linear,R.id.tab_share_linear,R.id.tab_mine_linear,R.id.home_add_linear})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.tab_home_linear:// 首页
                initImgStatus(R.string.home);
                if (mHomeFragment == null) mHomeFragment = HomeFragment.newInstance();
                switchFragments(mHomeFragment);
                break;
            case R.id.tab_classification_linear:// 分类
                initImgStatus(R.string.classification);
                if (mClassificationFragment == null) mClassificationFragment = ClassificationFragment.newInstance();
                switchFragments(mClassificationFragment);
                break;
            case R.id.tab_share_linear://消息
                if(StringMyUtil.isEmptyString(sp.getToken())){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }else {
                    initImgStatus(R.string.share);
                    if (tabMessageFragment == null) tabMessageFragment = tabMessageFragment.newInstance();
                    switchFragments(tabMessageFragment);
                }

                break;
            case R.id.tab_mine_linear:// 我的
                if(StringMyUtil.isEmptyString(sp.getToken())){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }else {
                    initImgStatus(R.string.mine);
                    if (mMineFragment == null) mMineFragment = MineFragment.newInstance();
                    switchFragments(mMineFragment);
                }
                break;
            case R.id.home_add_linear:
                initHomeAddPop();
                break;
            default:
                break;
        }
    }

    private void initHomeAddPop() {
        homeAddPop = new HomeAddPop(MainActivity.this);
        homeAddPop.setOnPopItemClick(new BasePopupWindow.OnRecycleItemClick() {
            @Override
            public void onPopItemClick(View view, int position) {
                String token = sp.getToken();
                HomeAddEntity homeAddEntity = homeAddPop.getHomeAddEntityArrayList().get(position);
                int flag = homeAddEntity.getFlag();// 1 运营商发布需求 2 服务商发布服务  3 担保 4 联合运营
                if(flag==1 || flag ==2){
                    if(StringMyUtil.isNotEmpty(token)){
                        ReleaseServerActivity.startAty(MainActivity.this,null);
                    }else {
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    }
                }else if(flag == 3){
                    if(StringMyUtil.isNotEmpty(token)){
                        GuaranteeOrderEntity guaranteeOrderEntity = new GuaranteeOrderEntity();
                        guaranteeOrderEntity.setId("0");
                        guaranteeOrderEntity.setInvited_user_id("0");
                        String userType = Utils.getUserInfo().getUserType();
                        guaranteeOrderEntity.setUserType(userType);
                        if(userType.equals("1")){
                            guaranteeOrderEntity.setSellerLink(Utils.getUserInfo().getTelegram());
                        }else {
                            guaranteeOrderEntity.setBuyerLink(Utils.getUserInfo().getTelegram());
                        }
                        TakeGuaranteeActivity.startAty(MainActivity.this,guaranteeOrderEntity,true);
                    }else {
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    }

                }else {
                    if(StringMyUtil.isNotEmpty(token)){
                        Intent intent = new Intent(MainActivity.this, CertificationActivity.class);
                        intent.putExtra("isServer", true);
                        startActivity(intent);
                    }else {
                        startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    }

                }
                homeAddPop.dismiss();
            }
        });
        int[] locations1 = new int[2];
        homeAddPop.getHome_add_recycler().getLocationOnScreen(locations1);
        y1 = locations1[1];
        int[] locations2 = new int[2];
        homeAddPop.getHome_add_recycler().getLocationOnScreen(locations2);
        y2 = locations2[1];

        // 显示新建弹出框
        homeAddPop.showAtLocation(
                tab_share_linear,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

//        tran(homeAddPop.getHome_add_recycler(), y2, 150, true);
    }

    private void initImgStatus(int tabName) {
        switch (tabName){
            case R.string.home:
                tab_home_iv.setImageResource(R.drawable.shouy_dl);
                tab_classification_iv.setImageResource(R.drawable.fenlei_wd);
                tab_share_iv.setImageResource(R.drawable.xiaox_icon);
                tab_mine_iv.setImageResource(R.drawable.wod_wd);
                break;
            case R.string.classification:
                tab_home_iv.setImageResource(R.drawable.shouye_wd);
                tab_classification_iv.setImageResource(R.drawable.fenlei_dl);
                tab_share_iv.setImageResource(R.drawable.xiaox_icon);
                tab_mine_iv.setImageResource(R.drawable.wod_wd);
                break;
            case R.string.share:
                tab_home_iv.setImageResource(R.drawable.shouye_wd);
                tab_classification_iv.setImageResource(R.drawable.fenlei_wd);
                tab_share_iv.setImageResource(R.drawable.xiaox_dj_icon);
                tab_mine_iv.setImageResource(R.drawable.wod_wd);
                break;
            case R.string.mine:
                tab_home_iv.setImageResource(R.drawable.shouye_wd);
                tab_classification_iv.setImageResource(R.drawable.fenlei_wd);
                tab_share_iv.setImageResource(R.drawable.xiaox_icon);
                tab_mine_iv.setImageResource(R.drawable.wod_dl);
                break;
            default:
                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 监听返回键，点击两次退出程序
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 1000) {
                showtoast("再按一次退出程序");
                firstTime = secondTime;
            } else {
                ActivityUtil.getInstance().exitSystem();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }

    /**
     * @方法名称:tran
     * @描述: 显示弹出框时执行的动画
     * @param view
     *            要执行动画的View
     * @param y
     *            执行动画的View的高
     * @param start
     *            执行动画的开始时间
     * @返回类型：void
     */
    private void tran(final View view, final int y, final int start,
                      final boolean flag) {
        int heightPixels = getResources().getDisplayMetrics().heightPixels;
        TranslateAnimation animation;
        if (flag) {
            animation = new TranslateAnimation(0, 0, heightPixels - y,
                    -ScreenUtils.dip2px(getApplicationContext(), 50));
        } else {
            animation = new TranslateAnimation(0, 0, -ScreenUtils.dip2px(
                    getApplicationContext(), 50), heightPixels - y);
        }
        animation.setStartOffset(start);
        animation.setDuration(150);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (flag) {
                    retran(view, y, start, flag);
                }
            }
        });
        view.startAnimation(animation);
    }

    /**
     * @方法名称:retran
     * @描述: 回弹动画
     * @param view
     *            执行动画的控件
     * @param y
     *            移动的距离
     * @param start
     *            开始执行动画的时间
     * @param flag
     *            标识执行的是弹出的动画还是隐藏的动画 true标识弹出动画
     * @返回类型：void
     */
    private void retran(final View view, final int y, final int start,
                        final boolean flag) {
        TranslateAnimation animation;
        if (flag) {
            animation = new TranslateAnimation(0, 0, -ScreenUtils.dip2px(
                    getApplicationContext(), 50), 0);
        } else {
            animation = new TranslateAnimation(0, 0, 0, -ScreenUtils.dip2px(
                    getApplicationContext(), 50));
        }
        animation.setDuration(150);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (flag) {
//                    if(view.getId() == R.id.release_mine_need_linear || view.getId() == R.id.release_resource_server_linear){
                    view.setVisibility(View.VISIBLE);
                    scaleAndAlpha(homeAddPop.getHome_add_recycler());

//                    }
                } else {
                    tran(view, y, start, flag);
                }
            }
        });
        view.startAnimation(animation);
    }

    private void scaleAndAlpha(final View view) {

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1.0f);
        alphaAnimation.setDuration(100);
        alphaAnimation.setFillAfter(true);

        ScaleAnimation animation = new ScaleAnimation(0f, 1.1f, 0f, 1.1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setDuration(200);
        animation.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(alphaAnimation);
        set.addAnimation(animation);

        set.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                reScale(view);
            }
        });
        view.startAnimation(set);
    }
    private void reScale(View view) {
        ScaleAnimation animation = new ScaleAnimation(1.1f, 1.0f, 1.1f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        animation.setDuration(100);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }
}