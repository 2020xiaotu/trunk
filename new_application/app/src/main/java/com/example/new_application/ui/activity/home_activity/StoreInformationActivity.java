package com.example.new_application.ui.activity.home_activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alibaba.fastjson.JSONObject;
import com.androidkun.xtablayout.XTabLayout;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.example.new_application.R;
import com.example.new_application.adapter.HonestyMerchantChildAdapter;
import com.example.new_application.adapter.StoreCenterServerLabelAdapter;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.ContactEntity;
import com.example.new_application.bean.HomeLabelEntity;
import com.example.new_application.bean.HomeNoticeEntity;
import com.example.new_application.bean.ServerDetailsEntity;
import com.example.new_application.bean.ServerLabelEntity;
import com.example.new_application.bean.StoreDetailsEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.main_tab_activity.LoginActivity;
import com.example.new_application.ui.fragment.store_fragment.OtherFragment;
import com.example.new_application.ui.fragment.store_fragment.ReleaseFragment;
import com.example.new_application.ui.fragment.store_fragment.StoreInformationFragment;
import com.example.new_application.ui.pop.ContactPop;
import com.example.new_application.ui.pop.HomeAtyPop;
import com.example.new_application.utils.CommonToolbarUtil;
import com.example.new_application.utils.GlideLoadViewUtil;
import com.example.new_application.utils.StatusBarUtils2;
import com.example.new_application.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.new_application.utils.StatusBarUtils2.getStatusBarHeight;

public class StoreInformationActivity extends BaseActivity implements XTabLayout.OnTabSelectedListener {
    @BindView(R.id.toolbar_back_iv)
    ImageView toolbar_back_iv;
    @BindView(R.id.toolbar_title_tv)
    TextView toolbar_title_tv;
    @BindView(R.id.store_information_tab)
    XTabLayout store_information_tab;
    @BindView(R.id.store_information_viewPager)
    ViewPager store_information_viewPager;
    @BindView(R.id.name_tv)
    TextView name_tv;
    @BindView(R.id.company_avatar_iv)
    ImageView company_avatar_iv;
    @BindView(R.id.label_recycler)
    RecyclerView label_recycler;
    @BindView(R.id.favorites_tv)
    TextView favorites_tv;
    @BindView(R.id.center_label_linear)
    LinearLayout center_label_linear;
    @BindView(R.id.center_label_recycler)
    RecyclerView center_label_recycler;
    StoreCenterServerLabelAdapter  storeCenterServerLabelAdapter;
    ArrayList<ServerLabelEntity> serverLabelEntityArrayList = new ArrayList<>();
    HonestyMerchantChildAdapter honestyMerchantChildAdapter;
    ArrayList<HomeLabelEntity>homeLabelEntityArrayList = new ArrayList<>();
    public ArrayList<String> titleList  = new ArrayList<>();
    private StoreDetailsTabAdapter StoreDetailsTabAdapter;
    private ServerDetailsEntity.MemberInfoVoBean memberInfoVoBean;
    private int flag = 0;
    private StoreDetailsEntity storeDetailsEntity;
    private ContactPop contactPop;
    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
    @Override
    public int getLayoutId() {
        return R.layout.activity_store_information;
    }

    @Override
    public void getIntentData() {
        memberInfoVoBean = (ServerDetailsEntity.MemberInfoVoBean) getIntent().getSerializableExtra("memberInfoVoBean");
    }
    public static void startAty(Context context,ServerDetailsEntity.MemberInfoVoBean memberInfoVoBean){
        Intent intent = new Intent(context, StoreInformationActivity.class);
        intent.putExtra("memberInfoVoBean",memberInfoVoBean);
        context.startActivity(intent);
    }
    public ServerDetailsEntity.MemberInfoVoBean getMemberInfoVoBean() {
        return memberInfoVoBean;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initTab();
        initCenterLabelRecycler();
        initLabelRecycler();
        requestStoreInfo();
    }

    private void initCenterLabelRecycler() {
        storeCenterServerLabelAdapter = new StoreCenterServerLabelAdapter(R.layout.store_label_recycler_item,serverLabelEntityArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        center_label_recycler.setLayoutManager(layoutManager);
        center_label_recycler.setAdapter(storeCenterServerLabelAdapter);
    }

    private void initLabelRecycler() {
        honestyMerchantChildAdapter  = new HonestyMerchantChildAdapter(R.layout.classification_details_child_item,homeLabelEntityArrayList,true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        label_recycler.setLayoutManager(linearLayoutManager);
        label_recycler.setAdapter(honestyMerchantChildAdapter);
    }

    private void requestStoreInfo() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("memberId", memberInfoVoBean.getId());
        HttpApiUtils.wwwRequest(this, null, RequestUtils.STORE_DETAILS, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                storeDetailsEntity = JSONObject.parseObject(result, StoreDetailsEntity.class);
                StoreDetailsEntity.MemberInfoVoBean memberInfoVo = storeDetailsEntity.getMemberInfoVo();
                name_tv.setText(memberInfoVo.getNickname());
                GlideLoadViewUtil.LoadTitleView(StoreInformationActivity.this,memberInfoVo.getImage(),company_avatar_iv);
                /**
                 * 处理labelRecycler
                 */
                String officialMark = memberInfoVo.getOfficialMark();
                if(StringMyUtil.isNotEmpty(officialMark)){
                    List<String> labelIdList = Arrays.asList(officialMark.split(","));
                    List<HomeLabelEntity> homeLabelEntityList = Utils.getHomeLabelEntity();
                    for (int j = 0; j < homeLabelEntityList.size(); j++) {
                        HomeLabelEntity homeLabelEntity = homeLabelEntityList.get(j);
                        for (int k = 0; k < labelIdList.size(); k++) {
                            String id = labelIdList.get(k);
                            if(homeLabelEntity.getId().equals(id)){
                                homeLabelEntityArrayList.add(homeLabelEntity);
                            }
                        }
                    }
                    honestyMerchantChildAdapter.notifyDataSetChanged();
                }

                String memberCollectResourceMessage = storeDetailsEntity.getMemberCollectResourceMessage();
                if(memberCollectResourceMessage.equals("1")){
                    favorites_tv.setText("已收藏");
                    flag=2;
                }else {
                    favorites_tv.setText("收藏");
                    flag=1;
                }

                /**
                 * 服务标签
                 */
                String label = memberInfoVo.getLabel();
                if(StringMyUtil.isNotEmpty(label)){
                    String[] split = label.split(",");
                    if(split.length==0){
                        center_label_linear.setVisibility(View.GONE);
                    }else {
                        center_label_linear.setVisibility(View.VISIBLE);
                    }
                    for (int i = 0; i < split.length; i++) {
                        serverLabelEntityArrayList.add(new ServerLabelEntity(split[i]));
                    }
                    storeCenterServerLabelAdapter.notifyDataSetChanged();
                }else {
                    center_label_linear.setVisibility(View.GONE);
                }



            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    @Override
    protected void initStatusBar() {
        super.initStatusBar();
        View viewById = findViewById(R.id.toolbar_linear);
        viewById.setBackgroundColor(ContextCompat.getColor(this,R.color.transparent));//toolbar设置透明
//        toolbar_back_iv.setImageResource(R.drawable.icon_arrow_back);//设置白色返回键
        toolbar_title_tv.setTextColor(Color.WHITE);
        toolbar_back_iv.setImageResource(R.drawable.arrwo_back_white);
        CommonToolbarUtil.initToolbar(this,"");//标题

        //rootView第一个子view的图片顶入状态栏
        StatusBarUtils2.with(this)
                .init();
        //设置toolBar父view的marginTop()
        ViewGroup.LayoutParams layoutParams = viewById.getLayoutParams();
        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) layoutParams;
        layoutParams1.setMargins(0, getStatusBarHeight(this),0,0);
        viewById.setLayoutParams(layoutParams1);
    }

    @OnClick({R.id.contact_now_tv,R.id.favorites_tv,R.id.favorites_iv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.contact_now_tv:
                initContactPop();
                break;
            case R.id.favorites_tv:
            case R.id.favorites_iv:
                if(flag==0){
                    showtoast("未能成功获取收藏状态, 请稍后再试");
                    return;
                }
                if(StringMyUtil.isEmptyString(sp.getToken())){
                    startActivity(new Intent(StoreInformationActivity.this, LoginActivity.class));
                }else {
                    requestFollowStore();
                }
                break;
            default:
                break;
        }
    }
    private void initContactPop() {
        if(memberInfoVoBean ==null){
            /**
             * 接口未请求成功
             */
            showtoast("未能获取到服务详情");
            return;
        }
        ArrayList<ContactEntity>contactEntityArrayList = new ArrayList<>();
        ServerDetailsEntity.MemberInfoVoBean memberInfoVo = memberInfoVoBean;
        String weixin = memberInfoVo.getWeixin();
        String qq = memberInfoVo.getQq();
        String whatsapp = memberInfoVo.getWhatsapp();
        String telegram = memberInfoVo.getTelegram();
        String skype = memberInfoVo.getSkype();
        String potato = memberInfoVo.getPotato();
        String bat = memberInfoVo.getBat();
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
        if(StringMyUtil.isNotEmpty(potato)){
            contactEntityArrayList.add(new ContactEntity("potato",potato));
        }
        if(StringMyUtil.isNotEmpty(bat)){
            contactEntityArrayList.add(new ContactEntity("bat",bat));
        }

        if(contactPop == null){
            contactPop = new ContactPop(StoreInformationActivity.this,contactEntityArrayList);
        }
        contactPop.showAtLocation(favorites_tv, Gravity.BOTTOM,0,0);
        Utils.darkenBackground(StoreInformationActivity.this,0.7f);
    }
    private void requestFollowStore() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("flag",flag);
        data.put("follow_user_id",storeDetailsEntity.getMemberInfoVo().getId());
        HttpApiUtils.wwwRequest(this, null, RequestUtils.FOLLOW_STORE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                String toString = favorites_tv.getText().toString();
                if(toString.equals("取消收藏")){
                    showtoast("取消收藏成功");
                    favorites_tv.setText("收藏");
                    flag=1;
                }else {
                    showtoast("收藏成功");
                    favorites_tv.setText("取消收藏");
                    flag=2;
                }
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }

    private void initTab() {
        titleList.add("资料");
        titleList.add("发布");
        titleList.add("评价");
        titleList.add("案例");
        StoreDetailsTabAdapter = new StoreDetailsTabAdapter(getSupportFragmentManager(),titleList,this);
        store_information_viewPager.setAdapter(StoreDetailsTabAdapter);
        store_information_tab.setupWithViewPager(store_information_viewPager);
        for (int i = 0; i < titleList.size(); i++) {
            XTabLayout.Tab tabAt = store_information_tab.getTabAt(i);
            tabAt.setCustomView(StoreDetailsTabAdapter.getTabView(i));
            if(i==0){
                TextView textView= tabAt.getCustomView().findViewById(R.id.message_tab_title_tv);
                textView.setTextColor(Color.parseColor("#222222"));
                textView.setTextSize(16);
            }
        }
        store_information_tab.addOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(XTabLayout.Tab tab) {
        if(tab==null){
            return;
        }
        View customView = tab.getCustomView();
        if(customView==null){
            return;
        }
        TextView textView=  customView.findViewById(R.id.message_tab_title_tv);
        textView.setTextSize(16);
        textView.setTextColor(Color.parseColor("#FF6801"));
    }

    @Override
    public void onTabUnselected(XTabLayout.Tab tab) {
        if(tab==null){
            return;
        }
        View customView = tab.getCustomView();
        if(customView==null){
            return;
        }
        TextView textView=  customView.findViewById(R.id.message_tab_title_tv);
        textView.setTextSize(14);
        textView.setTextColor(Color.parseColor("#222222"));
    }

    @Override
    public void onTabReselected(XTabLayout.Tab tab) {


    }

    public class StoreDetailsTabAdapter extends FragmentPagerAdapter {
        private ArrayList<String> titleList;
        private Context context;
        public StoreDetailsTabAdapter(@NonNull FragmentManager fm, ArrayList<String> titleList, Context context) {
            super(fm);
            this.titleList = titleList;
            this.context = context;
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return StoreInformationFragment.newInstance(position);
            }else if(position == 1){
                return ReleaseFragment.newInstance(position);
            }else {
                return OtherFragment.newInstance(position);
            }
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
        //自定义tab item布局
        public View getTabView(int position) {
            View v = LayoutInflater.from(context).inflate(R.layout.message_tab_view_layout, null);
            TextView tv = v.findViewById(R.id.message_tab_title_tv);
            tv.setText(titleList.get(position));
            if (position == 0) {
                tv.setTextSize(16);
            }
            return v;
        }
    }
}