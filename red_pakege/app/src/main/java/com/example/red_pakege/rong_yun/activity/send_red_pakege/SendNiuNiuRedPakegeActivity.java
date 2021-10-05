package com.example.red_pakege.rong_yun.activity.send_red_pakege;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.red_pakege.R;
import com.example.red_pakege.rong_yun.message.NiuNiuRedMessage;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.net.api.HttpApiUtils;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.BigDecimalUtil;
import com.example.red_pakege.util.RequestUtil;
import com.example.red_pakege.util.StringMyUtil;

import java.math.BigDecimal;
import java.util.HashMap;

import io.rong.imkit.RongIM;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import okhttp3.Headers;

public class SendNiuNiuRedPakegeActivity extends BaseActivity implements View.OnClickListener {
    private String TAG ="SendNiuNiuRedPakegeActivity";
    private Toolbar toolbar;
    private ImageView actionBarLeftIv;
    private ImageView actionBarRightIv;
    private TextView actionTitleTv;

    private EditText amountEtv;
    private EditText numEtv;
    private Button sendBtn;
    private TextView topTipTv;
    private TextView topBigAmountTv;

    private BigDecimal mixAmount;
    private BigDecimal maxAmount;
    private int mixNum;
    private int maxNum;

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_send_red_pakege;
    }

    @Override
    protected void initView() {
        super.initView();
        bindView();
        getIntentData();
        ActionBarUtil.initMainActionbar(this,toolbar);
        initEtv();//输入框hint 输入监听
    }

    private void getIntentData() {
        Intent intent = getIntent();
        mixAmount= BigDecimalUtil.getBigDecimal(intent.getStringExtra("mixAmount"),2);
        maxAmount= BigDecimalUtil.getBigDecimal(intent.getStringExtra("maxAmount"),2);
        mixNum=intent.getIntExtra("mixNum",0);
        maxNum=intent.getIntExtra("maxNum",0);
    }
    /**
     * 跳转当前activity
     * @param context 上下文
     * @param mixAmount 红包最小金额
     * @param maxAmount 红包最大金额
     * @param mixNum 最低红包个数
     * @param maxNum 最高红包个数
     */
    public static void startAty(Context context,String mixAmount, String maxAmount, int mixNum, int maxNum){
        Intent intent = new Intent(context, SendNiuNiuRedPakegeActivity.class);
        intent.putExtra("mixAmount",mixAmount);
        intent.putExtra("maxAmount",maxAmount);
        intent.putExtra("mixNum",mixNum);
        intent.putExtra("maxNum",maxNum);
        context.startActivity(intent);
    }
    private void bindView() {
        toolbar=findViewById(R.id.message_fragent_toolbar);
        actionBarLeftIv=findViewById(R.id.action_left_iv);
        actionBarRightIv=findViewById(R.id.action_right_iv);
        actionTitleTv=findViewById(R.id.actionBar_title);
        actionTitleTv.setText("牛牛红包");
        actionBarLeftIv.setImageResource(R.drawable.icon_back);

        actionBarRightIv.setVisibility(View.INVISIBLE);



        topTipTv=findViewById(R.id.niuniu_send_red_top_tip_tv);
        amountEtv=findViewById(R.id.niuniu_red_amount_etv);
        numEtv=findViewById(R.id.niuniu_red_number_etv);
        sendBtn=findViewById(R.id.niuniu_send_sure_btn);
        topBigAmountTv=findViewById(R.id.niuniu_send_top_big_amount_tv);


    }

    @Override
    protected void initClick() {
        super.initClick();
        actionBarLeftIv.setOnClickListener(this);
        sendBtn.setOnClickListener(this);
    }

    private void initEtv() {
        amountEtv.setHint(mixAmount+"-"+maxAmount);
        numEtv.setHint(mixNum+"-"+maxNum);
        amountEtv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String toString = amountEtv.getText().toString();
                if(StringMyUtil.isNotEmpty(toString)){
                    BigDecimal nowAmount = BigDecimalUtil.getBigDecimal(toString,2);
                    if(nowAmount.compareTo(mixAmount)<0){
                        topTipTv.setText("牛牛红包金额不能小于"+mixAmount+"元");
                        topTipTv.setVisibility(View.VISIBLE);
                    }else if(nowAmount.compareTo(maxAmount)>0){
                        topTipTv.setText("牛牛红包金额不能大于"+maxAmount+"元");
                        topTipTv.setVisibility(View.VISIBLE);
                    }else{
                        topTipTv.setVisibility(View.INVISIBLE);
                    }
                    topBigAmountTv.setText("¥"+BigDecimalUtil.getBigDecimal(toString,2));
                }else{
                    topTipTv.setText("请输入红包金额");
                    topTipTv.setVisibility(View.VISIBLE);
                    topBigAmountTv.setText("¥"+"0");
                }

            }
        });
        numEtv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String toString = numEtv.getText().toString();
                if(StringMyUtil.isNotEmpty(toString)){
                    int nowNum = Integer.parseInt(toString);
                    if(nowNum<mixNum||nowNum>maxNum){
                        topTipTv.setText("牛牛红包个数范围"+mixNum+"-"+maxNum);
                        topTipTv.setVisibility(View.VISIBLE);
                    }else {
                        topTipTv.setVisibility(View.INVISIBLE);
                    }
                }else {
                    topTipTv.setText("请输入红包个数");
                    topTipTv.setVisibility(View.VISIBLE);
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_left_iv:
                finish();
                break;
            case R.id.niuniu_send_sure_btn:
                sendBtn.setClickable(false);
                //两个输入框不为空 且顶部提示错误的textView处于隐藏状态
                if(StringMyUtil.isNotEmpty(amountEtv.getText().toString())&&StringMyUtil.isNotEmpty(numEtv.getText().toString())&&topTipTv.getVisibility()==View.INVISIBLE){
                    HashMap<String, Object> data = new HashMap<>();
                    data.put("roomId","10");
                    data.put("amount",amountEtv.getText().toString());
                    HttpApiUtils.normalRequest(SendNiuNiuRedPakegeActivity.this, RequestUtil.SEND_NIUNIU, data, new HttpApiUtils.OnRequestLintener() {
                        @Override
                        public void onSuccess(String result, Headers headers) {
//                            sendMessageTest();
                            finish();
                            sendBtn.setClickable(true);

                        }

                        @Override
                        public void onFaild(String msg) {
                            sendBtn.setClickable(true);
                        }
                    });


                }else {
                    showtoast("请确认发包金额和红包个数");
                    sendBtn.setClickable(true);
                }
                break;
            default:
                break;
        }
    }

    private void sendMessageTest() {
        NiuNiuRedMessage niuNiuRedMessage = new NiuNiuRedMessage("111", 1, "牛牛红包", "200-10");
        RongIM.getInstance().sendMessage(Message.obtain("1", Conversation.ConversationType.GROUP, niuNiuRedMessage)
                , "pushContent", null, new IRongCallback.ISendMessageCallback() {
                    @Override
                    public void onAttached(Message message) {
                        sendBtn.setClickable(true);
                    }

                    @Override
                    public void onSuccess(Message message) {
                        sendBtn.setClickable(true);
                        finish();
                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                        sendBtn.setClickable(true);
                    }
                }
        );
    }
}
