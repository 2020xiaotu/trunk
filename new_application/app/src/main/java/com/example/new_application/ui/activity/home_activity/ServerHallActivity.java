package com.example.new_application.ui.activity.home_activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.new_application.R;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.RequestServerHallEvenEntity;
import com.example.new_application.ui.activity.main_tab_activity.LoginActivity;
import com.example.new_application.ui.pop.AllClassificationPop;
import com.example.new_application.ui.pop.RightMenuPop;
import com.example.new_application.utils.CommonStr;
import com.example.new_application.utils.Utils;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class ServerHallActivity extends BaseActivity {

    @BindView(R.id.classification_back_iv)
    ImageView classification_back_iv;
    @BindView(R.id.server_hall_order_tab)
    XTabLayout server_hall_order_tab;
    @BindView(R.id.server_hall_viewPager)
    ViewPager server_hall_viewPager;
    @BindView(R.id.server_hall_search_etv)
    EditText server_hall_search_etv;
    @BindView(R.id.zonghe_tv)
    TextView zonghe_tv;
    @BindView(R.id.least_tv)
    TextView least_tv;
    @BindView(R.id.choose_linear)
    LinearLayout choose_linear;
    @BindView(R.id.choose_tv)
    TextView choose_tv;
    @BindView(R.id.follow_linear)
    LinearLayout follow_linear;
    @BindView(R.id.follow_tv)
    TextView follow_tv;
    @BindView(R.id.choose_iv)
    ImageView choose_iv;
    @BindView(R.id.follow_iv)
    ImageView follow_iv;
    @BindView(R.id.right_menu_iv)
    ImageView right_menu_iv;
    ArrayList<String>titleList = new ArrayList<>();
    private ServerHallTabAdapter serverHallTabAdapter;
    String useType;
    String latest = "-1";//是否是最近 (-1:不查最新，1：最新的)
    String myCollect ="0"; //是否关注 0否 1是
    String oneLevelClassification ; //一级分类id 查全部不传
    String twoLevelClassification ; //二级分类id 查全部不传
    int guaranteeType = 3;//交易模式(0：自行交易,1：担保交易 3为全部, 不传)
    private AllClassificationPop allClassificationPop;
    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
    private RightMenuPop rightMenuPop;

    @Override
    public int getLayoutId() {
        return R.layout.activity_server_hall;
    }

    @Override
    public void getIntentData() {
        useType = getIntent().getStringExtra("useType");
        twoLevelClassification = getIntent().getStringExtra("twoLevelClassification");
        oneLevelClassification = getIntent().getStringExtra("oneLevelClassification");
    }

    public static void startAty(Context context,String  oneLevelClassification,String twoLevelClassification,String useType){
        Intent intent = new Intent(context, ServerHallActivity.class);
        intent.putExtra("useType",useType);
        intent.putExtra("twoLevelClassification",twoLevelClassification);
        intent.putExtra("oneLevelClassification",oneLevelClassification);
        context.startActivity(intent);
    }
    @Override
    protected void init(Bundle savedInstanceState) {
        initTab();
        server_hall_search_etv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    postEvenBus();
                    Utils.hideSoftKeyBoard(ServerHallActivity.this);
                }
                return false;
            }
        });
    }

    private void initTab() {
        if(useType.equals("1")){
            titleList.add("服务大厅");
            titleList.add("需求大厅");
        }else {
            titleList.add("需求大厅");
            titleList.add("服务大厅");
        }
        serverHallTabAdapter = new ServerHallTabAdapter(getSupportFragmentManager());
        server_hall_viewPager.setAdapter(serverHallTabAdapter);
        server_hall_order_tab.setupWithViewPager(server_hall_viewPager);
    }

    @OnClick({R.id.classification_back_iv,R.id.zonghe_tv,R.id.least_tv,R.id.choose_linear,R.id.follow_linear,R.id.right_menu_iv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.right_menu_iv:
                if(rightMenuPop ==null){
                    rightMenuPop = new RightMenuPop(ServerHallActivity.this);
                }
                rightMenuPop.showAsDropDown(right_menu_iv,0,0,Gravity.BOTTOM);
                Utils.darkenBackground(ServerHallActivity.this,0.7f);
                break;
            case R.id.classification_back_iv:
                finish();
                break;
            case R.id.zonghe_tv:
                zonghe_tv.setTextColor(Color.parseColor("#FA6400"));
                least_tv.setTextColor(Color.parseColor("#575757"));
                choose_tv.setTextColor(Color.parseColor("#575757"));
                follow_tv.setTextColor(Color.parseColor("#575757"));
                follow_iv.setImageResource(R.drawable.gz_wdl);
                latest="-1";
                myCollect="0";
                postEvenBus();
                break;
            case R.id.least_tv:
                zonghe_tv.setTextColor(Color.parseColor("#575757"));
                least_tv.setTextColor(Color.parseColor("#FA6400"));
                choose_tv.setTextColor(Color.parseColor("#575757"));
                follow_tv.setTextColor(Color.parseColor("#575757"));
                follow_iv.setImageResource(R.drawable.gz_wdl);
                latest="1";
                myCollect="0";
                postEvenBus();
                break;
            case R.id.choose_linear:
                zonghe_tv.setTextColor(Color.parseColor("#575757"));
                least_tv.setTextColor(Color.parseColor("#575757"));
                choose_tv.setTextColor(Color.parseColor("#FA6400"));
                follow_tv.setTextColor(Color.parseColor("#575757"));
                follow_iv.setImageResource(R.drawable.gz_wdl);
                latest="-1";
                myCollect="0";
                initClassificationPop();
                break;
            case R.id.follow_linear:
                if(StringMyUtil.isEmptyString(sp.getToken())){
                    startActivity(new Intent(ServerHallActivity.this, LoginActivity.class));
                    return;
                }
                zonghe_tv.setTextColor(Color.parseColor("#575757"));
                least_tv.setTextColor(Color.parseColor("#575757"));
                choose_tv.setTextColor(Color.parseColor("#575757"));
                follow_tv.setTextColor(Color.parseColor("#FA6400"));
                follow_iv.setImageResource(R.drawable.gz_dl);
                latest="-1";
                myCollect="1";
                postEvenBus();
                break;
            default:
                break;
        }
    }

    private void initClassificationPop() {
        if(allClassificationPop == null){
            allClassificationPop = new AllClassificationPop(ServerHallActivity.this,null);
        }
        allClassificationPop.showAtLocation(choose_linear, Gravity.CENTER,0,0);
        Utils.darkenBackground(ServerHallActivity.this,0.7f);
    }

    public void postEvenBus() {
        EventBus.getDefault().post(new RequestServerHallEvenEntity());
    }

    class ServerHallTabAdapter extends FragmentPagerAdapter {
        public ServerHallTabAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return ServerHallFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return titleList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }

    public XTabLayout getServer_hall_order_tab() {
        return server_hall_order_tab;
    }

    public ArrayList<String> getTitleList() {
        return titleList;
    }

    public EditText getServer_hall_search_etv() {
        return server_hall_search_etv;
    }

    public String getUseType() {
        return useType;
    }


    public String getLatest() {
        return latest;
    }

    public String getMyCollect() {
        return myCollect;
    }

    public String getOneLevelClassification() {
        return oneLevelClassification;
    }

    public String getTwoLevelClassification() {
        return twoLevelClassification;
    }

    public void setOneLevelClassification(String oneLevelClassification) {
        this.oneLevelClassification = oneLevelClassification;
    }

    public void setTwoLevelClassification(String twoLevelClassification) {
        this.twoLevelClassification = twoLevelClassification;
    }

    public int getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(int guaranteeType) {
        this.guaranteeType = guaranteeType;
    }
}