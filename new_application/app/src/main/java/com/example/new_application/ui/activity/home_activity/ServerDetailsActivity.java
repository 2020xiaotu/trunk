package com.example.new_application.ui.activity.home_activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.cambodia.zhanbang.rxhttp.sp.SharedPreferenceHelperImpl;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.ClassificationDetailsAdapter;
import com.example.new_application.adapter.DetailsPhotoAdapter;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.ClassificationDetailsEntity;
import com.example.new_application.bean.ContactEntity;
import com.example.new_application.bean.GuaranteeOrderEntity;
import com.example.new_application.bean.HelpChildEntity;
import com.example.new_application.bean.DetailsPhotoEntity;
import com.example.new_application.bean.ServerDetailsEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.main_tab_activity.LoginActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.ReleaseServerActivity;
import com.example.new_application.ui.pop.CommonSurePop;
import com.example.new_application.ui.pop.ContactPop;
import com.example.new_application.utils.CommonToolbarUtil;
import com.example.new_application.utils.GlideEngine;
import com.example.new_application.utils.GlideLoadViewUtil;
import com.example.new_application.utils.Utils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ServerDetailsActivity extends BaseActivity {
    @BindView(R.id.server_details_avatar_iv)
    ImageView server_details_avatar_iv;
    @BindView(R.id.server_details_name_tv)
    TextView server_details_name_tv;
    @BindView(R.id.server_details_checked_tv)
    TextView server_details_checked_tv;
    String resourceMessageId;
    @BindView(R.id.server_details_title_tv)
    TextView server_details_title_tv;
    @BindView(R.id.server_details_content_tv)
    TextView server_details_content_tv;
    @BindView(R.id.show_more_content_linear)
    LinearLayout show_more_content_linear;
    @BindView(R.id.release_column_tv)
    TextView release_column_tv;
    @BindView(R.id.deal_type_tv)
    TextView deal_type_tv;
    @BindView(R.id.type_name_tv)
    TextView type_name_tv;
    @BindView(R.id.release_num_tv)
    TextView release_num_tv;
    @BindView(R.id.copy_release_num_tv)
    TextView copy_release_num_tv;
    @BindView(R.id.show_more_content_iv)
    ImageView show_more_content_iv;
    @BindView(R.id.show_more_content_tv)
    TextView show_more_content_tv;
    @BindView(R.id.contact_top_constrainsLayout)
    ConstraintLayout contact_top_constrainsLayout;
    @BindView(R.id.favorites_tv)
    TextView favorites_tv;
    @BindView(R.id.user_type_tv)
    TextView user_type_tv;
    @BindView(R.id.guarantee_tv)
    TextView guarantee_tv;
    @BindView(R.id.price_tv)
    TextView price_tv;
    @BindView(R.id.commission_tv)
    TextView commission_tv;
    @BindView(R.id.commission_tip_tv)
    TextView commission_tip_tv;
    ContactPop contactPop;
    @BindView(R.id.toolbar_right_tv)
    TextView toolbar_right_tv;
    @BindView(R.id.toolbar_title_tv)
    TextView toolbar_title_tv;
    @BindView(R.id.recommend_recycler)
    RecyclerView recommend_recycler;
    @BindView(R.id.recommend_linear)
    LinearLayout recommend_linear;
    @BindView(R.id.sub_title_tv)
    TextView sub_title_tv;
    @BindView(R.id.photo_recycler)
    RecyclerView photo_recycler;
    @BindView(R.id.split20_tv)
    TextView split20_tv;
    @BindView(R.id.label_tv)
    TextView label_tv;
    @BindView(R.id.label_tip_tv)
    TextView label_tip_tv;
    private ClassificationDetailsAdapter recommendAdapter;
    ArrayList<ClassificationDetailsEntity> recommendList = new ArrayList<>();
    private ServerDetailsEntity serverDetailsEntity;
    int flag = 0;
    private CommonSurePop commonSurePop;
    private String ruleContent;
    SharedPreferenceHelperImpl sp = new SharedPreferenceHelperImpl();
    private String guaranteeType;
    private String messageId;
    ArrayList<DetailsPhotoEntity> detailsPhotoEntityArrayList = new ArrayList<>();
    DetailsPhotoAdapter detailsPhotoAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.activity_server_details;
    }

    @Override
    public void getIntentData() {
        resourceMessageId = getIntent().getStringExtra("resourceMessageId");
        messageId = getIntent().getStringExtra("messageId");
    }
    public static void startAty(Context context,String resourceMessageId){
        Intent intent = new Intent(context, ServerDetailsActivity.class);
        intent.putExtra("resourceMessageId",resourceMessageId);
        context.startActivity(intent);
    }
    public static void startAty(Fragment fragment, String resourceMessageId){
        Intent intent = new Intent(fragment.getContext(), ServerDetailsActivity.class);
        intent.putExtra("resourceMessageId",resourceMessageId);
        fragment.startActivity(intent);
    }

    /**
     * 消息列表跳转
     * @param fragment
     * @param messageId 请求消息已读的id
     * @param resourceMessageId 请求消息详情的id
     */
    public static void startAty(Fragment fragment,String messageId ,String resourceMessageId){
        Intent intent = new Intent(fragment.getContext(), ServerDetailsActivity.class);
        intent.putExtra("resourceMessageId",resourceMessageId);
        intent.putExtra("messageId",messageId);
        fragment.startActivity(intent);
    }
    @Override
    protected void init(Bundle savedInstanceState) {
        CommonToolbarUtil.initToolbar(this,"服务详情");
        toolbar_right_tv.setVisibility(View.VISIBLE);
        toolbar_right_tv.setText("规则");
        toolbar_right_tv.setTextColor(Color.parseColor("#FA6400"));
        initPhotoAdapter();
        requestRule();
        requestDetails();
        if(StringMyUtil.isNotEmpty(messageId)){
            requestReadMessage(messageId);
        }

    }

    private void initPhotoAdapter() {
        detailsPhotoAdapter = new DetailsPhotoAdapter(R.layout.server_details_photo_recycler_item,detailsPhotoEntityArrayList);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        layout.setOrientation(RecyclerView.HORIZONTAL);
        photo_recycler.setLayoutManager(layout);
        photo_recycler.setAdapter(detailsPhotoAdapter);
        detailsPhotoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                List<LocalMedia> selectList = new ArrayList<>();
                for (int i = 0; i < detailsPhotoEntityArrayList.size(); i++) {
                    LocalMedia localMedia = new LocalMedia();
                    localMedia.setPath(Utils.checkImageUrl(detailsPhotoEntityArrayList.get(i).getUrl()));
                    selectList.add(localMedia);
                }
                if (selectList.size() > 0) {
                    PictureSelector.create(ServerDetailsActivity.this)
                            .themeStyle(R.style.picture_default_style) // xml设置主题
                            //.setPictureWindowAnimationStyle(animationStyle)// 自定义页面启动动画
                            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)// 设置相册Activity方向，不设置默认使用系统
                            .isNotPreviewDownload(true)// 预览图片长按是否可以下载
                            .imageEngine(GlideEngine.createGlideEngine())// 外部传入图片加载引擎，必传项
                            .openExternalPreview(position, selectList);
                }
            }
        });

    }

    /**
     * 更换消息未读状态
     * @param messageId
     */
    private void requestReadMessage(String messageId) {
        HttpApiUtils.pathNormalRequest(this, null, RequestUtils.MESSAGE_DETAILS, messageId, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG", "onSuccess: 消息已读" );
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
    private void requestDetails() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("resourceMessageId",Long.parseLong(resourceMessageId));
        HttpApiUtils.wwwRequest(this, null, RequestUtils.RESOURCE_DETAILS, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                serverDetailsEntity = JSONObject.parseObject(result, ServerDetailsEntity.class);
                ServerDetailsEntity.MemberInfoVoBean memberInfoVo1 = serverDetailsEntity.getMemberInfoVo();
                ServerDetailsEntity.MemberInfoVoBean memberInfoVo = memberInfoVo1;
                ServerDetailsEntity.ResourceMessageBean resourceMessage = serverDetailsEntity.getResourceMessage();
                String verifyStatus = memberInfoVo.getVerifyStatus();
                guaranteeType = serverDetailsEntity.getResourceMessage().getGuaranteeType();
                String priceType = serverDetailsEntity.getResourceMessage().getPriceType();//价格类型(1：一口价，2：范围价格，3：议价)
                String price = serverDetailsEntity.getResourceMessage().getPrice();
                String label = memberInfoVo.getLabel();
                if(StringMyUtil.isNotEmpty(label)){
                    label_tv.setText(label);
                }else {
                    label_tv.setText("用户未填写");
                }
                if(priceType.equals("3")){
                    price_tv.setText("议价");
                }else if(priceType.equals("2")||priceType.equals("1")){
                    price_tv.setText("¥"+price);
                }
                GlideLoadViewUtil.LoadTitleView(ServerDetailsActivity.this,memberInfoVo.getImage(),server_details_avatar_iv);
                if(guaranteeType.equals("1")){
                    guarantee_tv.setBackgroundColor(Color.parseColor("#FFD100"));
                    guarantee_tv.setText("委托担保");
                    guarantee_tv.setClickable(true);
                    deal_type_tv.setText("担保交易");
                    commission_tip_tv.setVisibility(View.VISIBLE);
                    commission_tv.setVisibility(View.VISIBLE);
                    String commissionType = serverDetailsEntity.getResourceMessage().getCommissionType();//佣金类型(0买家付,1卖家付,2各付一半)
                    if(commissionType.equals("0")){
                        commission_tv.setText("买家付");
                    }else if(commissionType.equals("1")){
                        commission_tv.setText("卖家付");
                    }else if(commissionType.equals("2")) {
                        commission_tv.setText("各付一半");
                    }else {
                        commission_tv.setText("商议");
                    }
                }else {
                    guarantee_tv.setBackgroundColor(Color.parseColor("#808080"));
                    guarantee_tv.setText("商家拒绝担保");
                    guarantee_tv.setClickable(false);
                    deal_type_tv.setText("自行交易");
                    commission_tip_tv.setVisibility(View.GONE);
                    commission_tv.setVisibility(View.GONE);
                }
                if(verifyStatus.equals("3")){
                    server_details_checked_tv.setText("已认证");
                    server_details_checked_tv.setBackgroundResource(R.drawable.certification_status_shape);
                }else {
                    server_details_checked_tv.setText("未认证");
                    server_details_checked_tv.setBackgroundResource(R.drawable.un_certification_status_shape);
                }

                server_details_name_tv.setText(memberInfoVo.getNickname());
                server_details_title_tv.setText(resourceMessage.getTitle());
                server_details_content_tv.setText(resourceMessage.getContent());
                release_column_tv.setText(resourceMessage.getOneLevelClassificationName()+"/"+resourceMessage.getTwoLevelClassificationName());
                release_num_tv.setText(resourceMessage.getOrderCode());
                String userType = memberInfoVo1.getUserType();
                if(userType.equals("1")){
                    user_type_tv.setText("雇主");
                    toolbar_title_tv.setText("需求详情");
                    sub_title_tv.setText("需求详情");
                    type_name_tv.setText("需求标题");
                    label_tip_tv.setText("需求标签");
                }else {
                    user_type_tv.setText("服务商");
                    toolbar_title_tv.setText("服务详情");
                    sub_title_tv.setText("服务详情");
                    type_name_tv.setText("服务标题");
                    label_tip_tv.setText("服务标签");
                }
                String memberCollectResourceMessage = serverDetailsEntity.getMemberCollectResourceMessage();
                if(memberCollectResourceMessage.equals("1")){
                    favorites_tv.setText("已收藏");
                    flag=2;
                }else {
                    favorites_tv.setText("收藏");
                    flag=1;
                }

                initRecommendRecycler();
                requestRecommendList();

                String attachment = serverDetailsEntity.getResourceMessage().getAttachment();
                if(StringMyUtil.isNotEmpty(attachment)){
                    List<String> stringList = Arrays.asList(attachment.split(","));
                    for (int i = 0; i < stringList.size(); i++) {
                        detailsPhotoEntityArrayList.add(new DetailsPhotoEntity(stringList.get(i)));
                        detailsPhotoAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    private void requestRecommendList() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("userType",serverDetailsEntity.getMemberInfoVo().getUserType());
        data.put("twoLevelClassification",serverDetailsEntity.getResourceMessage().getTwoLevelClassification());
        data.put("current",1);
        data.put("latest",1);
        HttpApiUtils.wwwNormalRequest(this, null, RequestUtils.SERVER_HALL_LIST, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<ClassificationDetailsEntity> classificationDetailsEntityList = JSONArray.parseArray(result, ClassificationDetailsEntity.class);
                if(classificationDetailsEntityList.size() == 0){
                    recommend_linear.setVisibility(View.GONE);
                    split20_tv.setVisibility(View.VISIBLE);
                }else {
                    recommend_linear.setVisibility(View.VISIBLE);
                    split20_tv.setVisibility(View.GONE);

                }
                recommendList.addAll(classificationDetailsEntityList);
                recommendAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    private void initRecommendRecycler() {
        recommendAdapter = new ClassificationDetailsAdapter(R.layout.classification_details_item, recommendList);
        recommend_recycler.setLayoutManager(new LinearLayoutManager(this));
        recommend_recycler.setAdapter(recommendAdapter);
        recommendAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ClassificationDetailsEntity classificationDetailsEntity = recommendList.get(position);
                ServerDetailsActivity.startAty(ServerDetailsActivity.this,classificationDetailsEntity.getId());
            }
        });
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

            commonSurePop.showAtLocation(copy_release_num_tv, Gravity.CENTER,0,0);
            Utils.darkenBackground(this,0.7f);
        }
    }

    private void requestRule() {
        HttpApiUtils.pathRequest(this, null, RequestUtils.TIP_CONTENT, 1+"", new HttpApiUtils.OnRequestLintener() {
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
    @OnClick({R.id.show_more_content_linear,R.id.copy_release_num_tv,R.id.favorites_iv,R.id.guarantee_tv,R.id.contact_now_tv,R.id.contact_top_constrainsLayout,R.id.favorites_tv,
                R.id.toolbar_right_tv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.toolbar_right_tv:
                initRulePop();
                break;
            case R.id.show_more_content_linear:
                if(server_details_content_tv.getMaxLines()==5){
                    server_details_content_tv.setMaxLines(100);
                    show_more_content_iv.setImageResource(R.drawable.fwxq_sq);
                    show_more_content_tv.setText("收起");
                }else {
                    server_details_content_tv.setMaxLines(5);
                    show_more_content_iv.setImageResource(R.drawable.fwxq_zk);
                    show_more_content_tv.setText("展开");
                }
                break;
            case R.id.copy_release_num_tv:
                Utils.copyStr("copy_release_num_tv",release_num_tv.getText().toString());
                break;
            case R.id.contact_top_constrainsLayout:
                if(serverDetailsEntity!=null){
              /*      Intent intent = new Intent(ServerDetailsActivity.this, StoreInformationActivity.class);
                    intent.putExtra("serverDetailsEntity",serverDetailsEntity);
                    startActivity(intent);*/
                    StoreInformationActivity.startAty(ServerDetailsActivity.this,serverDetailsEntity.getMemberInfoVo());
                }
                break;
            case R.id.favorites_iv:
            case R.id.favorites_tv:
                if(flag==0 || Utils.isFastClick()){
                    showtoast("未能成功获取收藏状态, 请稍后再试");
                    return;
                }
                if(StringMyUtil.isEmptyString(sp.getToken())){
                        startActivity(new Intent(ServerDetailsActivity.this,LoginActivity.class));
                }else {
                    requestFollowResource();
                }
                break;
            case R.id.guarantee_tv:
                if(guaranteeType.equals("1")){
                    if(StringMyUtil.isEmptyString(sp.getToken())){
                        startActivity(new Intent(ServerDetailsActivity.this,LoginActivity.class));
                    }else {
                        if(serverDetailsEntity.getMemberInfoVo().getId().equals(Utils.getUserInfo().getId())){
                            showtoast("无法对自己发布的帖子委托担保");
                            return;
                        }
                        GuaranteeOrderEntity guaranteeOrderEntity = new GuaranteeOrderEntity();
                        ServerDetailsEntity.ResourceMessageBean resourceMessage = serverDetailsEntity.getResourceMessage();
                        guaranteeOrderEntity.setTitle(resourceMessage.getTitle());
                        guaranteeOrderEntity.setContent(resourceMessage.getContent());
                        guaranteeOrderEntity.setPrice(resourceMessage.getPrice());
                        String userType = Utils.getUserInfo().getUserType();
                        guaranteeOrderEntity.setUserType(userType);
                        if(userType.equals("1")){
                            guaranteeOrderEntity.setSellerLink(Utils.getUserInfo().getTelegram());
                        }else {
                            guaranteeOrderEntity.setBuyerLink(Utils.getUserInfo().getTelegram());
                        }
                        guaranteeOrderEntity.setInvited_user_id(serverDetailsEntity.getMemberInfoVo().getId());
                        guaranteeOrderEntity.setId(resourceMessage.getId());
                        TakeGuaranteeActivity.startAty(ServerDetailsActivity.this,guaranteeOrderEntity);
                    }
                }

                break;
            case R.id.contact_now_tv:
                initContactPop();
                if(contactPop!=null){
                    contactPop.showAtLocation(contact_top_constrainsLayout, Gravity.BOTTOM,0,0);
                    Utils.darkenBackground(ServerDetailsActivity.this,0.7f);
                }
                break;
            default:
                break;
        }
    }
    /**
     * 收藏帖子
     */
    private void requestFollowResource() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("flag",flag);
        data.put("resourceMessageId",serverDetailsEntity.getResourceMessage().getId());
        HttpApiUtils.wwwRequest(ServerDetailsActivity.this, null, RequestUtils.FOLLOW_RESOURCE, data, new HttpApiUtils.OnRequestLintener() {
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

    private void initContactPop() {
        if(serverDetailsEntity==null){
            /**
             * 接口未请求成功
             */
            showtoast("未能获取到服务详情");
            return;
        }
        ArrayList<ContactEntity>contactEntityArrayList = new ArrayList<>();
        ServerDetailsEntity.MemberInfoVoBean memberInfoVo = serverDetailsEntity.getMemberInfoVo();
        String weixin = memberInfoVo.getWeixin();
        String qq = memberInfoVo.getQq();
        String whatsapp = memberInfoVo.getWhatsapp();
        String telegram = memberInfoVo.getTelegram();
        String skype = memberInfoVo.getSkype();
        String bat = memberInfoVo.getBat();
        String potato = memberInfoVo.getPotato();
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
        if(StringMyUtil.isNotEmpty(bat)){
            contactEntityArrayList.add(new ContactEntity("bat",bat));
        }
        if(StringMyUtil.isNotEmpty(potato)){
            contactEntityArrayList.add(new ContactEntity("potato",potato));
        }
            contactPop = new ContactPop(ServerDetailsActivity.this,contactEntityArrayList);
    }
}