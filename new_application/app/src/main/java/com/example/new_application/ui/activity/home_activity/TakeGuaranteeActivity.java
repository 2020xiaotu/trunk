package com.example.new_application.ui.activity.home_activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.ReleaseServerAdapter;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.GuaranteeOrderEntity;
import com.example.new_application.bean.HelpChildEntity;
import com.example.new_application.bean.MineServerEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.pop.CommonSurePop;
import com.example.new_application.utils.CommonToolbarUtil;
import com.example.new_application.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TakeGuaranteeActivity extends BaseActivity {
    @BindView(R.id.guarantee_title_etv)
    EditText guarantee_title_etv;
    @BindView(R.id.guarantee_content_etv)
    EditText guarantee_content_etv;
    @BindView(R.id.buyer_contact_etv)
    EditText buyer_contact_etv;
    @BindView(R.id.seller_contact_etv)
    EditText seller_contact_etv;
    @BindView(R.id.is_buyer_tv)
    TextView is_buyer_tv;
    @BindView(R.id.is_buyer_iv)
    ImageView is_buyer_iv;
    @BindView(R.id.is_seller_tv)
    TextView is_seller_tv;
    @BindView(R.id.is_seller_iv)
    ImageView is_seller_iv;
    @BindView(R.id.guarantee_commit_btn)
    Button guarantee_commit_btn;
    @BindView(R.id.price_etv)
    EditText price_etv;
    @BindView(R.id.content_length_tv)
    TextView content_length_tv;
    @BindView(R.id.commission_linear)
    LinearLayout commission_linear;
    @BindView(R.id.commission_recycler)
    RecyclerView commission_recycler;
    @BindView(R.id.toolbar_right_tv)
    TextView toolbar_right_tv;
    ReleaseServerAdapter commissionAdapter;
    ArrayList<MineServerEntity> commissionList = new ArrayList<>();
    private GuaranteeOrderEntity guaranteeOrderEntity;//担保订单entity 只有修改担保时会传
    private int userType =1;
    private boolean showCommission = false;//是否显示佣金类型
    int commissionType = 0;//佣金类型(0买家付,1卖家付,2各付一半)
    private CommonSurePop commonSurePop;
    private String ruleContent;
    boolean isModify = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_take_guarantee;
    }

    @Override
    public void getIntentData() {
        guaranteeOrderEntity = (GuaranteeOrderEntity) getIntent().getSerializableExtra("guaranteeOrderEntity");
        showCommission = getIntent().getBooleanExtra("showCommission",false);
        isModify = getIntent().getBooleanExtra("isModify",false);
    }



    /**
     * 修改担保时跳转
     * @param fragment
     * @param guaranteeOrderEntity 担保列表model
     */
    public static void startAty(Context context,Fragment fragment, GuaranteeOrderEntity guaranteeOrderEntity,boolean isModify,int requestCode){
        Intent intent;
        if(context ==null){
            intent = new Intent(fragment.getContext(), TakeGuaranteeActivity.class);
        }else {
            intent = new Intent(context, TakeGuaranteeActivity.class);
        }
        intent.putExtra("guaranteeOrderEntity",guaranteeOrderEntity);
        intent.putExtra("isModify",isModify);
        if(context ==null){
            fragment.startActivityForResult(intent,requestCode);
        }else {
            Activity activity= (Activity) context;
            activity.startActivityForResult(intent,requestCode);
        }
    }

    /**
     * 帖子详情跳转
     * @param context
     * @param guaranteeOrderEntity 赋值帖子对应内容 默认赋值给view
     */
    public static void startAty(Context context , GuaranteeOrderEntity guaranteeOrderEntity ){
        Intent intent = new Intent(context, TakeGuaranteeActivity.class);
        intent.putExtra("guaranteeOrderEntity",guaranteeOrderEntity);
        context.startActivity(intent);
    }

    /**
     * 站外跳转
     * @param context
     * @param guaranteeOrderEntity  只赋值id 和invited_user_id , 发起担保用到
     * @param showCommission  是否显示佣金类型(站外交易显示)
     */
    public static void startAty(Context  context, GuaranteeOrderEntity guaranteeOrderEntity, boolean showCommission ){
        Intent intent = new Intent(context, TakeGuaranteeActivity.class);
        intent.putExtra("guaranteeOrderEntity",guaranteeOrderEntity);
        intent.putExtra("showCommission",showCommission);
        context.startActivity(intent);
    }
    @Override
    protected void init(Bundle savedInstanceState) {
        if(isModify){
            CommonToolbarUtil.initToolbar(this,"修改担保");
        }else {
            CommonToolbarUtil.initToolbar(this,"委托担保");
        }
        toolbar_right_tv.setVisibility(View.VISIBLE);
        toolbar_right_tv.setText("规则");
        toolbar_right_tv.setTextColor(Color.parseColor("#FA6400"));
        initCommissionRecycler();
        initModifyData();
        requestRule();
        guarantee_content_etv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = guarantee_content_etv.getText().toString().length();
                if(length<10||length>100){
                    if(length!=0){
                        String str="<font color=\"#FF0000\">"+length+"</font>"+"/100";
                        content_length_tv.setText(Html.fromHtml(str));
                    }
                }else {
                    content_length_tv.setText(length+"/100");
                }
            }
        });

        guarantee_content_etv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                //触摸的是EditText并且当前EditText可以滚动则将事件交给EditText处理；否则将事件交由其父类处理
                if ((view.getId() == R.id.guarantee_content_etv && Utils.canVerticalScroll(guarantee_content_etv))) {
                    view.getParent().requestDisallowInterceptTouchEvent(true);
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        view.getParent().requestDisallowInterceptTouchEvent(false);
                    }
                }
                return false;
            }
        });
    }

    private void initCommissionRecycler() {
        if(showCommission){
            commission_linear.setVisibility(View.VISIBLE);
        }else {
            commission_linear.setVisibility(View.GONE);
        }
        commissionAdapter = new ReleaseServerAdapter(R.layout.release_server_item,commissionList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        commission_recycler.setLayoutManager(linearLayoutManager);
        commission_recycler.setAdapter(commissionAdapter);
        commissionAdapter.addChildClickViewIds(R.id.release_server_tv);
        commissionAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                for (int i = 0; i < commissionList.size(); i++) {
                    commissionList.get(i).setCheck(false);
                }
                MineServerEntity mineServerEntity = commissionList.get(position);
                mineServerEntity.setCheck(true);
                commissionAdapter.notifyDataSetChanged();

                commissionType = mineServerEntity.getCommissionType();
            }
        });
        addCommissionTypeData();
    }
    private void addCommissionTypeData() {
        commissionList.clear();
        MineServerEntity mineServerEntity = new MineServerEntity();
        mineServerEntity.setTwoLevelClassificationName("买家付");
        mineServerEntity.setCommissionType(0);
        mineServerEntity.setCheck(true);
        commissionList.add(mineServerEntity);
        MineServerEntity mineServerEntity1 = new MineServerEntity();
        mineServerEntity1.setTwoLevelClassificationName("卖家付");
        mineServerEntity1.setCommissionType(1);
        commissionList.add(mineServerEntity1);
        MineServerEntity mineServerEntity2 = new MineServerEntity();
        mineServerEntity2.setTwoLevelClassificationName("各付一半");
        mineServerEntity2.setCommissionType(2);
        commissionList.add(mineServerEntity2);
        MineServerEntity mineServerEntity3 = new MineServerEntity();
        mineServerEntity3.setTwoLevelClassificationName("商议");
        mineServerEntity3.setCommissionType(3);
        commissionList.add(mineServerEntity3);
        commissionAdapter.notifyDataSetChanged();
    }
    /**
     * 担保订单点击修改跳转时,赋值默认值
     */
    private void initModifyData() {
            String title = guaranteeOrderEntity.getTitle();
            if(StringMyUtil.isNotEmpty(title)){
                guarantee_title_etv.setText(title);
            }
            String content = guaranteeOrderEntity.getContent();
            if(StringMyUtil.isNotEmpty(content)){
                guarantee_content_etv.setText(content);
            }
            String price = guaranteeOrderEntity.getPrice();
            if(StringMyUtil.isNotEmpty(price)){
                price_etv.setText(price);
            }
            String buyerLink = guaranteeOrderEntity.getBuyerLink();
            if(StringMyUtil.isNotEmpty(buyerLink)){
                buyer_contact_etv.setText(buyerLink);
            }
            String sellerLink = guaranteeOrderEntity.getSellerLink();
            if(StringMyUtil.isNotEmpty(sellerLink)){
                seller_contact_etv.setText(sellerLink);
            }

            String userType = guaranteeOrderEntity.getUserType();
            if(userType.equals("1")){
               sellerClick();
            }else {
                buyerClick();
            }
    }
    private void requestRule() {
        HttpApiUtils.pathRequest(this, null, RequestUtils.TIP_CONTENT, 3+"", new HttpApiUtils.OnRequestLintener() {
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
    private void initRulePop() {
        if(commonSurePop == null){
            if(StringMyUtil.isNotEmpty(ruleContent)){
                commonSurePop = new CommonSurePop(this,"规则说明",ruleContent);
            }else {
                showtoast("暂无规则");
            }
        }
        if(commonSurePop!=null){
            commonSurePop.showAtLocation(toolbar_right_tv, Gravity.CENTER,0,0);
            Utils.darkenBackground(this,0.7f);
        }
    }
    @OnClick({R.id.is_buyer_tv,R.id.is_seller_tv,R.id.guarantee_commit_btn,R.id.toolbar_right_tv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.toolbar_right_tv:
                // 规则弹窗
                initRulePop();
                break;
            case R.id.is_buyer_tv:
                buyerClick();
/*                if(StringMyUtil.isEmptyString(buyer_contact_etv.getText().toString())){
                    String buyerLink = guaranteeOrderEntity.getBuyerLink();
                    if(StringMyUtil.isNotEmpty(buyerLink)){
                        buyer_contact_etv.setText(buyerLink);
                        buyer_contact_etv.setSelection(buyer_contact_etv.getText().toString().length());
                    }
                }
                if(StringMyUtil.isEmptyString(seller_contact_etv.getText().toString())){
                    String sellerLink = guaranteeOrderEntity.getSellerLink();
                    if(StringMyUtil.isNotEmpty(sellerLink)){
                        seller_contact_etv.setText(sellerLink);
                        seller_contact_etv.setSelection(sellerLink.length());
                    }
                }*/
//                seller_contact_etv.setText("");
                ChangeContactValue();
                break;
            case R.id.is_seller_tv:
                sellerClick();
                ChangeContactValue();
//                buyer_contact_etv.setText("");
                break;
            case R.id.guarantee_commit_btn:
                checkParams();
                if(isModify){
                    /**
                     * 修改担保
                     */
                    requestModifyGuarantee();

                }else {
                    /**
                     * 请求发起担保
                     */
                    requestInitiateGuarantee();
                }
                break;
            default:
                break;
        }
    }

    private void sellerClick() {
        userType = 2;
        is_buyer_tv.setBackgroundResource(R.drawable.certification_un_check_shape);
        is_seller_tv.setBackgroundResource(R.drawable.certification_check_shape);
        is_buyer_iv.setVisibility(View.GONE);
        is_seller_iv.setVisibility(View.VISIBLE);
    }

    private void buyerClick() {
        userType =1 ;
        is_buyer_tv.setBackgroundResource(R.drawable.certification_check_shape);
        is_seller_tv.setBackgroundResource(R.drawable.certification_un_check_shape);
        is_buyer_iv.setVisibility(View.VISIBLE);
        is_seller_iv.setVisibility(View.GONE);
    }

    private void ChangeContactValue() {
        String oldBuyerContact = buyer_contact_etv.getText().toString();
        String oldSellerContact = seller_contact_etv.getText().toString();
        buyer_contact_etv.setText(oldSellerContact);
        seller_contact_etv.setText(oldBuyerContact);
        if (StringMyUtil.isNotEmpty(oldBuyerContact)) {
            seller_contact_etv.setSelection(oldBuyerContact.length());
        }
        if (StringMyUtil.isNotEmpty(oldSellerContact)) {
            buyer_contact_etv.setSelection(oldSellerContact.length());
        }
    }

    private boolean checkParams() {
        if(StringMyUtil.isEmptyString(buyer_contact_etv.getText().toString())){
            showtoast("请输入买家联系方式");
            return false;
        }else if(StringMyUtil.isEmptyString(guarantee_content_etv.getText().toString())){
            showtoast("请输入交易内容");
            return false;
        }else if(guarantee_content_etv.getText().toString().length()>100){
            showtoast("交易内容超出字数限制");
            return false;
        }else if(StringMyUtil.isEmptyString(price_etv.getText().toString())){
            showtoast("请输入交易价格");
            return false;
        }else if(StringMyUtil.isEmptyString(seller_contact_etv.getText().toString())){
            showtoast("请输入卖家联系方式");
            return false;
        }else if(StringMyUtil.isEmptyString(guarantee_title_etv.getText().toString())){
            showtoast("请输入交易标题");
            return false;
        }
        return true;
    }

    /**
     * 请求修改担保
     */
    private void requestModifyGuarantee() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("buyerLink",buyer_contact_etv.getText().toString());
        data.put("commissionType",guaranteeOrderEntity.getCommissionType());
        data.put("content",guarantee_content_etv.getText().toString());
        data.put("id",guaranteeOrderEntity.getId());
        data.put("price",price_etv.getText().toString());
        data.put("sellerLink",seller_contact_etv.getText().toString());
        data.put("title",guarantee_title_etv.getText().toString());
        data.put("userType", userType);
        HttpApiUtils.wwwRequest(this, null, RequestUtils.MODIFY_GUARANTEE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showtoast("修改成功");
                setResult(RESULT_OK);
                finish();

            }

            @Override
            public void onFail(String msg) {
            }
        });
    }

    /**
     * 请求发起担保
     */
    private void requestInitiateGuarantee() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("buyerLink",buyer_contact_etv.getText().toString());
        data.put("content",guarantee_content_etv.getText().toString());
        String invited_user_id = guaranteeOrderEntity.getInvited_user_id();
        data.put("invited_user_id", invited_user_id);
        data.put("price",price_etv.getText().toString());
        data.put("sellerLink",seller_contact_etv.getText().toString());
        data.put("title",guarantee_title_etv.getText().toString());
        data.put("userType", userType);

        if(commission_linear.getVisibility() == View.VISIBLE){
            data.put("commissionType", commissionType);
        }
        data.put("resourceMessageId",guaranteeOrderEntity.getId());
        HttpApiUtils.wwwRequest(this, null, RequestUtils.INITIATE_GUARANTEE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showtoast("发起担保成功");
                finish();
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }
}