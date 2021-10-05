package com.example.new_application.ui.activity.mine_fragment_activity;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.example.new_application.R;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.ContactEntity;
import com.example.new_application.bean.GuaranteeOrderEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.ui.activity.home_activity.TakeGuaranteeActivity;
import com.example.new_application.ui.pop.ContactPop;
import com.example.new_application.utils.CommonToolbarUtil;
import com.example.new_application.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;

public class GuaranteeOrderDetailsActivity extends BaseActivity {
    private static final String TAG = "GuaranteeOrderDetailsActivity";
    @BindView(R.id.title_tv)
    TextView title_tv;
    @BindView(R.id.price_tv)
    TextView price_tv;
    @BindView(R.id.commission_tv)
    TextView commission_tv;
    @BindView(R.id.deal_name_tv)
    TextView deal_name_tv;
    @BindView(R.id.commission_chat_tv)
    TextView commission_chat_tv;
    @BindView(R.id.status_tv)
    TextView status_tv;
    @BindView(R.id.id_tv)
    TextView id_tv;
    @BindView(R.id.id_copy_tv)
    TextView id_copy_tv;
    @BindView(R.id.content_tv)
    TextView content_tv;
    @BindView(R.id.bottom_linear)
    LinearLayout bottom_linear;
    @BindView(R.id.guarantee_left_btn)
    Button guarantee_left_btn;
    @BindView(R.id.guarantee_right_btn)
    Button guarantee_right_btn;
    @BindView(R.id.bottom_tv)
    TextView bottom_tv;
    GuaranteeOrderEntity guaranteeOrderEntity;
    static int MODIFY_REQUEST_CODE =1000;
    private String contact;
    private ContactPop contactPop;
    String memberOrderId;
    String messageId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_guarantee_order_details;
    }

    @Override
    public void getIntentData() {
        guaranteeOrderEntity = (GuaranteeOrderEntity) getIntent().getSerializableExtra("guaranteeOrderEntity");
        memberOrderId = getIntent().getStringExtra("memberOrderId");
        messageId = getIntent().getStringExtra("messageId");

    }
    public static void startAty(Fragment fragment, GuaranteeOrderEntity guaranteeOrderEntity,int requestCode){
        Intent intent = new Intent(fragment.getContext(), GuaranteeOrderDetailsActivity.class);
        intent.putExtra("guaranteeOrderEntity",guaranteeOrderEntity);
        fragment.startActivityForResult(intent,requestCode);
    }

    /**
     * 消息列表跳转
     * @param fragment
     * @param memberOrderId
     * @param messageId 更改消息未读状态的字段
     * @param requestCode
     */
    public static void startAty(Fragment fragment, String  memberOrderId,String messageId,int requestCode){
        Intent intent = new Intent(fragment.getContext(), GuaranteeOrderDetailsActivity.class);
        intent.putExtra("memberOrderId",memberOrderId);
        intent.putExtra("messageId",messageId);
        fragment.startActivityForResult(intent,requestCode);
    }
    @Override
    protected void init(Bundle savedInstanceState) {
        CommonToolbarUtil.initToolbar(this,"订单详情");
        if(guaranteeOrderEntity!=null){
            initViewValue(guaranteeOrderEntity);
        }else {
            /**
             * 从担保消息跳转, 请求消息详情
             */
            //消息详情
            requestDetails();
        }
        if(StringMyUtil.isNotEmpty(messageId)){
            requestReadMessage(messageId);
        }
    }

    /**
     * 请求消息详情
     */
    private void requestDetails() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("memberOrderId",memberOrderId);
        HttpApiUtils.wwwRequest(this, null, RequestUtils.GUARANTEE_DETAILS, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                GuaranteeOrderEntity guaranteeOrderEntity = JSONObject.parseObject(result, GuaranteeOrderEntity.class);
                initViewValue(guaranteeOrderEntity);
            }

            @Override
            public void onFail(String msg) {

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
    private void initViewValue(GuaranteeOrderEntity guaranteeOrderEntity) {
        title_tv.setText(guaranteeOrderEntity.getTitle());
        price_tv.setText(guaranteeOrderEntity.getPrice());
        deal_name_tv.setText(initUserName(guaranteeOrderEntity));
        id_tv.setText(guaranteeOrderEntity.getOrderCode());
        content_tv.setText(guaranteeOrderEntity.getContent());
        String commissionType = guaranteeOrderEntity.getCommissionType();
        if(commissionType.equals("0")){
            commission_tv.setText("买家付");
        }else if(commissionType.equals("1")){
            commission_tv.setText("卖家付");
        }else if(commissionType.equals("2")){
            commission_tv.setText("各付一半");
        }else {
            commission_tv.setText("商议");
        }

        String status = guaranteeOrderEntity.getStatus();
        if(status.equals("0")){
            bottom_linear.setVisibility(View.VISIBLE);
            bottom_tv.setVisibility(View.GONE);
            status_tv.setText("用户委托中");
            if(guaranteeOrderEntity.getUser_id().equals(Utils.getUserInfo().getId())){
                guarantee_left_btn.setText("修改");
                guarantee_right_btn.setText("取消担保");
            }else {
                guarantee_left_btn.setText("取消担保");
                guarantee_right_btn.setText("同意担保");
            }
        }else {
            bottom_linear.setVisibility(View.GONE);
            bottom_tv.setVisibility(View.VISIBLE);
            String value;
            if(status.equals("1")){
                value = "平台审核中";
            }else if(status.equals("2")){
                value = "平台担保中";
            }else if(status.equals("3")){
                value = "平台拒绝担保";
            }else if(status.equals("4")){
                value = "用户取消订单";
            }else if(status.equals("11")){
                value = "担保成功";
            }else if(status.equals("12")){
                value = "担保失败";
            }else {
                value = "未知";
            }
            status_tv.setText(value);
            bottom_tv.setText(value);
        }
    }


    @OnClick({R.id.commission_chat_tv,R.id.id_copy_tv,R.id.bottom_tv,R.id.guarantee_left_btn,R.id.guarantee_right_btn})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.commission_chat_tv:
                if(StringMyUtil.isNotEmpty(contact)){
                    initContactPop(contact);
                    contactPop.showAtLocation(commission_chat_tv, Gravity.BOTTOM,0,0);
                    Utils.darkenBackground(GuaranteeOrderDetailsActivity.this,0.7f);
                }
                break;
            case R.id.id_copy_tv:
                Utils.copyStr("id",id_tv.getText().toString());
                break;
            case R.id.bottom_tv:

                break;
            case R.id.guarantee_left_btn:
                initBottomClick(guarantee_left_btn.getText().toString());
                break;
            case R.id.guarantee_right_btn:
                initBottomClick(guarantee_right_btn.getText().toString());
                break;

            default:
                break;
        }
    }

    private void initBottomClick(String string) {
        if (string.equals("修改")) {
            TakeGuaranteeActivity.startAty(GuaranteeOrderDetailsActivity.this, null, guaranteeOrderEntity, true, MODIFY_REQUEST_CODE);
        } else if (string.equals("取消担保")) {
            requestCancelGuarantee(guaranteeOrderEntity);
        } else if (string.equals("同意担保")) {
            requestAgreeGuarantee(guaranteeOrderEntity);
        }
    }

    /**
     * 同意担保
     * @param guaranteeOrderEntity
     */
    private void requestAgreeGuarantee(GuaranteeOrderEntity guaranteeOrderEntity) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("id",guaranteeOrderEntity.getId());
        HttpApiUtils.wwwRequest(this, null, RequestUtils.AGREE_GUARANTEE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showtoast("担保成功");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    /**
     * 取消担保
     * @param guaranteeOrderEntity
     */
    private void requestCancelGuarantee(GuaranteeOrderEntity guaranteeOrderEntity) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("id",guaranteeOrderEntity.getId());
        HttpApiUtils.wwwRequest(this, null, RequestUtils.CANCEL_GUARANTEE, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                showtoast("取消成功");
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    private void initContactPop(String contactString) {
        ArrayList<ContactEntity> contactEntityArrayList = new ArrayList<>();
        contactEntityArrayList.add(new ContactEntity("telegram",contactString));
            contactPop = new ContactPop(this,contactEntityArrayList);
    }
    private String initUserName(GuaranteeOrderEntity guaranteeOrderEntity) {
        String value = "";
         contact = "";
        if(guaranteeOrderEntity.getId().equals(Utils.getUserInfo().getId())){
            //我发起的担保

            if(guaranteeOrderEntity.getUserType().equals("1")){
                contact=guaranteeOrderEntity.getSellerLink();
                //我是买家
                if(guaranteeOrderEntity.getType().equals("1")){
                    //站外，显示卖家（对方）联系方式
                    value = guaranteeOrderEntity.getSellerLink();
                }else {
                    //站内，显示卖家（对方）昵称
                    value = guaranteeOrderEntity.getUsername();
                }
            }else {
                contact=guaranteeOrderEntity.getBuyerLink();
                //我是卖家
                if(guaranteeOrderEntity.getType().equals("1")){
                    //站外，显示买家（对方）联系方式
                    value = guaranteeOrderEntity.getBuyerLink();
                }else {
                    //站内，显示买家（对方）昵称
                    value = guaranteeOrderEntity.getUsername();
                }
            }
        }else {
            //对方发起的担保
            if(guaranteeOrderEntity.getUserType().equals("1")){
                //对方是买家
                contact=guaranteeOrderEntity.getBuyerLink();
                if(guaranteeOrderEntity.getType().equals("1")){
                    //站外，显示买家（对方）联系方式
                    value = guaranteeOrderEntity.getBuyerLink();
                }else {
                    //站内，显示买家（对方）昵称
                    value = guaranteeOrderEntity.getUsername();
                }
            }else {
                //对方是卖家
                contact=guaranteeOrderEntity.getSellerLink();
                if(guaranteeOrderEntity.getType().equals("1")){
                    //站外，显示卖家（对方）联系方式
                    value = guaranteeOrderEntity.getSellerLink();
                }else {
                    //站内，显示卖家（对方）昵称
                    value = guaranteeOrderEntity.getUsername();
                }
            }
        }
        return value;
    }
}