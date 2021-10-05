package com.example.new_application.ui.activity.mine_fragment_activity;

import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.new_application.BuildConfig;
import com.example.new_application.R;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.MineServerEntity;
import com.example.new_application.bean.UserInfoEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.main_tab_activity.LoginActivity;
import com.example.new_application.ui.activity.user_info_activity.AccountSafeActivity;
import com.example.new_application.ui.activity.user_info_activity.InformationActivity;
import com.example.new_application.ui.pop.CommonTipPop;
import com.example.new_application.utils.CommonToolbarUtil;
import com.example.new_application.utils.GlideCacheUtil;
import com.example.new_application.utils.GlideLoadViewUtil;
import com.example.new_application.utils.Utils;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.avatar_constrainLayout)
    ConstraintLayout avatar_constrainLayout;
    @BindView(R.id.avatar_iv)
    ImageView avatar_iv;
    @BindView(R.id.complete_info_linear)
    LinearLayout complete_info_linear;
    @BindView(R.id.account_safe_constrainLayout)
    ConstraintLayout account_safe_constrainLayout;
    @BindView(R.id.clear_cache_constrainLayout)
    ConstraintLayout clear_cache_constrainLayout;
    @BindView(R.id.cache_size_tv)
    TextView cache_size_tv;
    @BindView(R.id.version_constrainLayout)
    ConstraintLayout version_constrainLayout;
    @BindView(R.id.version_tv)
    TextView version_tv;
    @BindView(R.id.exit_login_btn)
    Button exit_login_btn;
    @BindView(R.id.setting_nickname_tv)
    TextView setting_nickname_tv;
    @BindView(R.id.complete_tip_tv)
    TextView complete_tip_tv;
    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
    boolean requestMineServerSuccess = false;
    boolean requestUserInfoSuccess = false;
    private List<MineServerEntity> mineServerEntityList;
    Handler handler = new Handler();
    private CommonTipPop commonTipPop;

    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        CommonToolbarUtil.initToolbar(this,"设置");
        version_tv.setText(BuildConfig.VERSION_NAME);
        String cacheSize = GlideCacheUtil.getInstance().getCacheSize(this);
        cache_size_tv.setText(cacheSize);
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestUserInfo();
        requestMineServer();
    }

    private void requestMineServer() {
        HttpApiUtils.wwwNormalRequest(this, null, RequestUtils.MINE_SERVER_TYPE, new HashMap<String, Object>(), new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                mineServerEntityList = JSONArray.parseArray(result, MineServerEntity.class);
                requestMineServerSuccess =true;
                initCompleteTip();
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }
    private void requestUserInfo() {
        HttpApiUtils.wwwNormalRequest(this, null, RequestUtils.USER_INFO, new HashMap<String, Object>(), new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                UserInfoEntity userInfoEntity = JSONObject.parseObject(result, UserInfoEntity.class);
                sp.setUserInfo(result);
                requestUserInfoSuccess = true;
                GlideLoadViewUtil.LoadTitleView(SettingActivity.this,userInfoEntity.getImage(),avatar_iv);
                setting_nickname_tv.setText(userInfoEntity.getNickname());
                initCompleteTip();
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    /**
     * 是否显示资料不完善的提示
     */
    private void initCompleteTip(){
        if(requestMineServerSuccess && requestUserInfoSuccess){
            UserInfoEntity userInfoEntity = Utils.getUserInfo();
            String skype = userInfoEntity.getSkype();
            String telegram = userInfoEntity.getTelegram();
            String weixin = userInfoEntity.getWeixin();
            String qq = userInfoEntity.getQq();
            String whatsapp = userInfoEntity.getWhatsapp();
            boolean noContact = StringMyUtil.isEmptyString(skype) && StringMyUtil.isEmptyString(telegram) && StringMyUtil.isEmptyString(weixin) && StringMyUtil.isEmptyString(qq) && StringMyUtil.isEmptyString(whatsapp);
            if(noContact || mineServerEntityList.size()==0){
                complete_info_linear.setVisibility(View.VISIBLE);
            }else {
                complete_info_linear.setVisibility(View.GONE);
            }
        }

    }
    @OnClick({R.id.avatar_constrainLayout,R.id.complete_info_linear,R.id.account_safe_constrainLayout,R.id.version_constrainLayout,R.id.exit_login_btn,R.id.clear_cache_constrainLayout})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.avatar_constrainLayout:
                startActivity(new Intent(SettingActivity.this, InformationActivity.class));
                break;
            case R.id.complete_info_linear:
                break;
            case R.id.account_safe_constrainLayout:
                startActivity(new Intent(SettingActivity.this, AccountSafeActivity.class));
                break;
            case R.id.version_constrainLayout:
                break;
            case R.id.exit_login_btn:
                initExitLoginPop();
                break;
            case R.id.clear_cache_constrainLayout:
                initClearCachePOp();
                break;
            default:
                break;
        }
    }

    private void initClearCachePOp() {
        if(commonTipPop==null){
            commonTipPop = new CommonTipPop(SettingActivity.this, SettingActivity.this, "确定清除所有缓存", "已缓存的图片等信息将会全部清除");
            commonTipPop.setOnClickLintener(new CommonTipPop.OnClickLintener() {
                @Override
                public void onSureClick(View view) {
                    GlideCacheUtil.getInstance().clearImageAllCache(SettingActivity.this);
                    showtoast("正在清理缓存");
                    handler.postDelayed(clearCacheRunnable,50);
                }
            });
        }

        commonTipPop.showAtLocation(clear_cache_constrainLayout,Gravity.CENTER,0,0);
        Utils.darkenBackground(SettingActivity.this,0.7f);
    }

    private void initExitLoginPop() {
        CommonTipPop exitLoginPop = new CommonTipPop(this,null,"退出","确定退出登录吗?");
        exitLoginPop.setOnClickLintener(new CommonTipPop.OnClickLintener() {
            @Override
            public void onSureClick(View view) {
                Utils.clearCache();
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
            }
        });
        exitLoginPop.showAtLocation(exit_login_btn, Gravity.CENTER,0,0);
        Utils.darkenBackground(this,0.7f);
    }

    Runnable clearCacheRunnable =new Runnable() {
        @Override
        public void run() {
            String cacheSize = GlideCacheUtil.getInstance().getCacheSize(SettingActivity.this);
            if(cacheSize.equals("0.00M")){
                cache_size_tv.setText(cacheSize);
                showtoast("已清除缓存");
                handler.removeCallbacks(clearCacheRunnable);
                if(commonTipPop!=null){
                    commonTipPop.dismiss();
                }
            }else {
                handler.postDelayed(clearCacheRunnable,50);
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}