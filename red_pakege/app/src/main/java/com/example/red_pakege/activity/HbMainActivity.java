package com.example.red_pakege.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.animation.Animator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.fragment.main_tab_fragment.ContactFragment;
import com.example.red_pakege.fragment.main_tab_fragment.GameGroupFragment;
import com.example.red_pakege.fragment.main_tab_fragment.HbMessageFragment;
import com.example.red_pakege.fragment.main_tab_fragment.HbMineFragment;
//import com.example.red_pakege.fragment.main_tab_fragment.RecharFragment;
import com.example.red_pakege.fragment.main_tab_fragment.RechargeRootFragment;
import com.example.red_pakege.net.api.HttpApiUtils;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.ActivityUtil;
import com.example.red_pakege.util.RequestUtil;
import com.example.red_pakege.util.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


import static com.example.red_pakege.util.AppUtil.CheckLoginStatus;
import static com.example.red_pakege.util.RouteUtil.start2LoginAndRegisActivity;


import io.rong.imlib.RongIMClient;
import me.yokeyword.fragmentation.SupportFragment;
import okhttp3.Headers;


public class HbMainActivity extends BaseActivity implements View.OnClickListener {
    LinearLayout messageLinear;
    LinearLayout rechargeLinear;
    LinearLayout gameGroupLinear;
    LinearLayout contactLinear;
    LinearLayout mineLinear;
    ImageView messageIv;
    ImageView rechargeIv;
    ImageView gameGroupIv;
    ImageView contactIv;
    ImageView mineIv;
    TextView massageTv;
    TextView rechargeTv;
    TextView gameGroupTv;
    TextView contactTv;
    TextView mineTv;
    ArrayList<LinearLayout> tabLinearList = new ArrayList<>();
    private static final int MESSAGE_TAB = 0;
    private static final int RECHARGE_TAB = 1;
    private static final int GAME_GROUP_TAB = 2;
    private static final int CONTACT_TAB = 3;
    private static final int MINE_TAB = 4;
//    private FragmentManager fragmentManager;

//    RecharFragment recharFragment;
//    HbMineFragment mineFragment;
    private SupportFragment[] mFragments = new SupportFragment[5];
  //  SharedPreferenceHelperImpl mSharedPreferenceHelperImpl = new SharedPreferenceHelperImpl();

//    private String rongToken="d244hr7UW9TpncvakKFEHJWFDSean4gLYugkggWqojlles0qK4xwssnX21H3nKQjuh2lVry5zNVw9sU0iRn8qQ==";
    private String rongToken;//="H5Buwc8+eXNN9nojNtPukJWFDSean4gLYugkggWqojlles0qK4xwsk2kfhIcdsb3sMkZrilreoBw9sU0iRn8qQ==";
    private static String TAG="HbMainActivity";
    private SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
     //   fragmentManager = getSupportFragmentManager();
    //    ActionBarUtil.initMainActionbar(this, findViewById(R.id.mainActivity_toolbar));
        bindView();
        initFragments();
        getRongToken();
//        connectRong();

    //    LogUtils.e(mSharedPreferenceHelperImpl.getUserInfo());
    }

    private void getRongToken() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("userId",sp.getUserId());
 //       data.put("userId",14);
        HttpApiUtils.normalRequest(this, RequestUtil.RONG_TOKEN, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result, Headers headers) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                rongToken= jsonObject.getString("data");
                String message = jsonObject.getString("message");
                if(message.equals("成功")){
                    connectRong();
                }
            }

            @Override
            public void onFaild(String msg) {

            }
        });
    }

    private void connectRong(){
        RongIMClient.connect(rongToken, new RongIMClient.ConnectCallback() {
            @Override
            public void onTokenIncorrect() {
                Log.e(TAG, "onTokenIncorrect: "+"融云token失效");
            }

            @Override
            public void onSuccess(String s) {
                Log.e(TAG, "onSuccess: " +"连接融云服务器成功" );
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.e(TAG, "onError: "+"连接融云服务器失败" );
            }
        });
    }

    @Override
    protected void initClick() {
        super.initClick();
        messageLinear.setOnClickListener(this);
        rechargeLinear.setOnClickListener(this);
        gameGroupLinear.setOnClickListener(this);
        contactLinear.setOnClickListener(this);
        mineLinear.setOnClickListener(this);
        gameGroupLinear.performClick();

    }

    private void bindView() {
        messageLinear = findViewById(R.id.message_tab_linear);
        rechargeLinear = findViewById(R.id.recharge_tab_linear);
        gameGroupLinear = findViewById(R.id.gameGroup_tab_linear);
        contactLinear = findViewById(R.id.contact_tab_linear);
        mineLinear = findViewById(R.id.mine_tab_linear);
        messageIv = findViewById(R.id.message_img);
        rechargeIv = findViewById(R.id.recharge_img);
        gameGroupIv = findViewById(R.id.gameGroup_img);
        contactIv = findViewById(R.id.contact_img);
        mineIv = findViewById(R.id.mine_img);
        massageTv = findViewById(R.id.message_textview);
        rechargeTv = findViewById(R.id.recharge_textview);
        gameGroupTv = findViewById(R.id.gameGroup_textview);
        contactTv = findViewById(R.id.contact_textview);
        mineTv = findViewById(R.id.mine_textview);
        tabLinearList.add(messageLinear);
        tabLinearList.add(rechargeLinear);
        tabLinearList.add(gameGroupLinear);
        tabLinearList.add(contactLinear);
        tabLinearList.add(mineLinear);
    }

    private void initFragments(){
        SupportFragment firstFragment = findFragment(HbMessageFragment.class);
        if (firstFragment == null) {
            mFragments[MESSAGE_TAB] = HbMessageFragment.newInstance();
            mFragments[RECHARGE_TAB] = RechargeRootFragment.newInstance();
            mFragments[GAME_GROUP_TAB] = GameGroupFragment.newInstance();
            mFragments[CONTACT_TAB] = ContactFragment.newInstance();
            mFragments[MINE_TAB] = HbMineFragment.newInstance();

            loadMultipleRootFragment(R.id.main_fragment_content, GAME_GROUP_TAB,
                    mFragments[MESSAGE_TAB],
                    mFragments[RECHARGE_TAB],
                    mFragments[GAME_GROUP_TAB],
                    mFragments[CONTACT_TAB],
                    mFragments[MINE_TAB]);
            //
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFragments[MESSAGE_TAB] = firstFragment;
            mFragments[RECHARGE_TAB] = findFragment(RechargeRootFragment.class);
            mFragments[GAME_GROUP_TAB] = findFragment(GameGroupFragment.class);
            mFragments[GAME_GROUP_TAB] = findFragment(ContactFragment.class);
            mFragments[GAME_GROUP_TAB] = findFragment(HbMineFragment.class);
        }
    }

    @Override
    public void onClick(View view) {
    //    FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.message_tab_linear:
                contralClick(messageLinear);
                initTabStatus(MESSAGE_TAB);
                startAnimation(messageIv);
                showHideFragment(mFragments[MESSAGE_TAB]);
   //             HbMessageFragment messageFragment = new HbMessageFragment();
   //             transaction.replace(R.id.main_fragment_content, messageFragment);
                break;
            case R.id.recharge_tab_linear:
                contralClick(rechargeLinear);
                initTabStatus(RECHARGE_TAB);
                startAnimation(rechargeIv);
                showHideFragment(mFragments[RECHARGE_TAB]);
   //             recharFragment = new RecharFragment();
  //              transaction.replace(R.id.main_fragment_content, recharFragment);
                break;
            case R.id.gameGroup_tab_linear:
                contralClick(gameGroupLinear);
                initTabStatus(GAME_GROUP_TAB);
                startAnimation(gameGroupIv);
                showHideFragment(mFragments[GAME_GROUP_TAB]);
       //         GameGroupFragment gameGroupFragment = new GameGroupFragment();
   //             transaction.replace(R.id.main_fragment_content, gameGroupFragment);
                break;
            case R.id.contact_tab_linear:
                contralClick(contactLinear);
                initTabStatus(CONTACT_TAB);
                startAnimation(contactIv);
                showHideFragment(mFragments[CONTACT_TAB]);
     //           ContactFragment contactFragment = new ContactFragment();
   //             transaction.replace(R.id.main_fragment_content, contactFragment);
                break;
            case R.id.mine_tab_linear:
                contralClick(mineLinear);
                initTabStatus(MINE_TAB);
                startAnimation(mineIv);
                showHideFragment(mFragments[MINE_TAB]);
                break;
            default:
                break;
        }
//        transaction.commit();
    }

    /**
     * 点击tab时的动画效果
     *
     * @param imageView 当前tab对应的icon imageView
     */
    private void startAnimation(ImageView imageView) {
        YoYo.with(Techniques.StandUp)
                .duration(700)
                .onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        //动画结束监听
                    }
                })
                .playOn(imageView);
    }

    /**
     * tab点击后设为不可点击
     *
     * @param tabLinear 当前点击的tab
     */
    private void contralClick(LinearLayout tabLinear) {
        for (int i = 0; i < tabLinearList.size(); i++) {
            tabLinearList.get(i).setClickable(true);
        }
        tabLinear.setClickable(false);
    }

    /**
     * tab的imageView textView相关状态设置
     *
     * @param tabNum
     */
    private void initTabStatus(int tabNum) {
        switch (tabNum) {
            case MESSAGE_TAB:
                messageIv.setImageResource(R.drawable.ic_msg_sel);
                rechargeIv.setImageResource(R.drawable.bg_nor);
                gameGroupIv.setImageResource(R.drawable.ic_group_nor);
                contactIv.setImageResource(R.drawable.ic_contact_nor);
                mineIv.setImageResource(R.drawable.ic_me_nor);
                massageTv.setTextColor(getResources().getColor(R.color.red));
                rechargeTv.setTextColor(getResources().getColor(R.color.defaultColor));
                gameGroupTv.setTextColor(getResources().getColor(R.color.defaultColor));
                contactTv.setTextColor(getResources().getColor(R.color.defaultColor));
                mineTv.setTextColor(getResources().getColor(R.color.defaultColor));
                break;
            case RECHARGE_TAB:
                messageIv.setImageResource(R.drawable.ic_msg_nor);
                rechargeIv.setImageResource(R.drawable.bg_sel);
                gameGroupIv.setImageResource(R.drawable.ic_group_nor);
                contactIv.setImageResource(R.drawable.ic_contact_nor);
                mineIv.setImageResource(R.drawable.ic_me_nor);
                massageTv.setTextColor(getResources().getColor(R.color.defaultColor));
                rechargeTv.setTextColor(getResources().getColor(R.color.red));
                gameGroupTv.setTextColor(getResources().getColor(R.color.defaultColor));
                contactTv.setTextColor(getResources().getColor(R.color.defaultColor));
                mineTv.setTextColor(getResources().getColor(R.color.defaultColor));
                break;
            case GAME_GROUP_TAB:
                messageIv.setImageResource(R.drawable.ic_msg_nor);
                rechargeIv.setImageResource(R.drawable.bg_nor);
                gameGroupIv.setImageResource(R.drawable.ic_group_sel);
                contactIv.setImageResource(R.drawable.ic_contact_nor);
                mineIv.setImageResource(R.drawable.ic_me_nor);
                massageTv.setTextColor(getResources().getColor(R.color.defaultColor));
                rechargeTv.setTextColor(getResources().getColor(R.color.defaultColor));
                gameGroupTv.setTextColor(getResources().getColor(R.color.red));
                contactTv.setTextColor(getResources().getColor(R.color.defaultColor));
                mineTv.setTextColor(getResources().getColor(R.color.defaultColor));
                break;
            case CONTACT_TAB:
                messageIv.setImageResource(R.drawable.ic_msg_nor);
                rechargeIv.setImageResource(R.drawable.bg_nor);
                gameGroupIv.setImageResource(R.drawable.ic_group_nor);
                contactIv.setImageResource(R.drawable.ic_contact_sel);
                mineIv.setImageResource(R.drawable.ic_me_nor);
                massageTv.setTextColor(getResources().getColor(R.color.defaultColor));
                rechargeTv.setTextColor(getResources().getColor(R.color.defaultColor));
                gameGroupTv.setTextColor(getResources().getColor(R.color.defaultColor));
                contactTv.setTextColor(getResources().getColor(R.color.red));
                mineTv.setTextColor(getResources().getColor(R.color.defaultColor));
                break;
            case MINE_TAB:
                messageIv.setImageResource(R.drawable.ic_msg_nor);
                rechargeIv.setImageResource(R.drawable.bg_nor);
                gameGroupIv.setImageResource(R.drawable.ic_group_nor);
                contactIv.setImageResource(R.drawable.ic_contact_nor);
                mineIv.setImageResource(R.drawable.ic_me_sel);
                massageTv.setTextColor(getResources().getColor(R.color.defaultColor));
                rechargeTv.setTextColor(getResources().getColor(R.color.defaultColor));
                gameGroupTv.setTextColor(getResources().getColor(R.color.defaultColor));
                contactTv.setTextColor(getResources().getColor(R.color.defaultColor));
                mineTv.setTextColor(getResources().getColor(R.color.red));
                break;
            default:
                break;
        }
    }


    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        }  else {
            Log.e(TAG, "onBackPressedSupport MainActivity 调用");
            doubleClickExitAndBkg();
        }
    }




}
