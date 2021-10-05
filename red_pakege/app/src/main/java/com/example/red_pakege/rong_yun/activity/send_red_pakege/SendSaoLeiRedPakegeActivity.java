package com.example.red_pakege.rong_yun.activity.send_red_pakege;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.net.api.HttpApiUtils;
import com.example.red_pakege.rong_yun.model.SaoLeiRoomModel;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.RequestUtil;
import com.example.red_pakege.util.StringMyUtil;

import java.math.BigDecimal;
import java.util.HashMap;

import okhttp3.Headers;

public class SendSaoLeiRedPakegeActivity extends BaseActivity implements View.OnClickListener {
        private Toolbar toolbar;
        private ImageView leftIv;
        private ImageView rightIv;
        private TextView topAmountTv;
        private TextView topTipTv;
        private EditText amountEtv;
        private TextView pakegeNumTv;
        private TextView amountTipTv;
        private EditText leishuEtv;
        private TextView leishuTipTv;
        private Button sendBtn;

        private String mTargetId;

        private  int hbNum;
        private  BigDecimal fbMinPrice;
        private  BigDecimal fbMaxPrice;
        private int typeId;
        private int game;

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_send_saolei_red_pakege;
    }

    @Override
    protected void initView() {
        super.initView();
        bindView();
        getIntentData();
        bindActionBar();
        requestRoomInfo();
        initEtv();
    }

    private void initEtv() {
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
                    BigDecimal nowAmount = new BigDecimal(toString).setScale(2);
                    topAmountTv.setText("¥"+nowAmount);
                    if(nowAmount.compareTo(fbMinPrice)<0){
                        topTipTv.setVisibility(View.VISIBLE);
                        topTipTv.setText("红包金额不能少于"+fbMinPrice);
                    }else if(nowAmount.compareTo(fbMaxPrice)>0){
                        topTipTv.setVisibility(View.VISIBLE);
                        topTipTv.setText("红包金额不能大于"+fbMaxPrice);
                    }else {
                        topTipTv.setVisibility(View.INVISIBLE);
                    }
                }else {
                    topTipTv.setVisibility(View.VISIBLE);
                    topTipTv.setText("红包发布范围:"+fbMinPrice+"-"+fbMaxPrice);
                }
            }
        });
        leishuEtv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String leishuStr = leishuEtv.getText().toString();
                if(StringMyUtil.isNotEmpty(leishuStr)){
                    int parseInt = Integer.parseInt(leishuStr);
                    if(parseInt<0){
                        topTipTv.setVisibility(View.VISIBLE);
                        topTipTv.setText("雷数不能小于0");
                    }else if(parseInt>9){
                        topTipTv.setVisibility(View.VISIBLE);
                        topTipTv.setText("雷数不能大于9");
                    }else {
                        topTipTv.setVisibility(View.INVISIBLE);
                    }
                }else {
                    topTipTv.setVisibility(View.VISIBLE);
                    topTipTv.setText("雷数范围:0-9");
                }
            }
        });
    }

    private void requestRoomInfo() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("id",mTargetId);
        HttpApiUtils.normalRequest(this, RequestUtil.ROOM_INFO, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result, Headers headers) {
                SaoLeiRoomModel saoLeiRoomModel = JSONObject.parseObject(result, SaoLeiRoomModel.class);
                SaoLeiRoomModel.DataBean data = saoLeiRoomModel.getData();
                hbNum = data.getHbNum();//红包个数
                fbMinPrice = data.getFbMinPrice().setScale(2);//发包最低金额
                fbMaxPrice = data.getFbMaxPrice().setScale(2);//发包最高金额
                pakegeNumTv.setText(hbNum+"");
                amountEtv.setHint(fbMinPrice+"-"+fbMaxPrice);
                amountTipTv.setText("红包发布范围:"+fbMinPrice+"-"+fbMaxPrice);

            }

            @Override
            public void onFaild(String msg) {

            }
        });
    }

    private void getIntentData() {
        mTargetId=getIntent().getStringExtra("targetId");
        typeId=getIntent().getIntExtra("typeId",0);
        game=getIntent().getIntExtra("game",0);
    }

    private void bindView() {
        toolbar=findViewById(R.id.message_fragent_toolbar);
        leftIv=findViewById(R.id.action_left_iv);
        leftIv.setImageResource(R.drawable.icon_back);
        rightIv=findViewById(R.id.action_right_iv);
        rightIv.setVisibility(View.INVISIBLE);
        topAmountTv=findViewById(R.id.saolei_send_top_big_amount_tv);
        topTipTv=findViewById(R.id.saolei_send_red_top_tip_tv);
        amountEtv=findViewById(R.id.saolei_red_amount_etv);
        pakegeNumTv=findViewById(R.id.saolei_red_count_tv);
        amountTipTv=findViewById(R.id.mix_max_amount_tip_tv);
        leishuEtv=findViewById(R.id.leishu_etv);
        leishuTipTv=findViewById(R.id.mix_max_leishu_tip_tv);
        sendBtn=findViewById(R.id.saolei_send_sure_btn);
    }

    @Override
    protected void initClick() {
        super.initClick();
        sendBtn.setOnClickListener(this);
        leftIv.setOnClickListener(this);
    }

    private void bindActionBar() {
        ActionBarUtil.initMainActionbar(this,toolbar);
    }
    public static void startAty(Context context, String targetId,int typeId, int game){
        Intent intent = new Intent(context, SendSaoLeiRedPakegeActivity.class);
        intent.putExtra("targetId",targetId);
        intent.putExtra("typeId",typeId);
        intent.putExtra("game",game);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.saolei_send_sure_btn:
                sendBtn.setClickable(false);
                if(StringMyUtil.isNotEmpty(amountEtv.getText().toString())&&StringMyUtil.isNotEmpty(leishuEtv.getText().toString())&&topTipTv.getVisibility()!=View.VISIBLE){

                    HashMap<String, Object> data = new HashMap<>();
                    data.put("roomId",mTargetId);
                    data.put("amount",amountEtv.getText().toString());
                    data.put("hbNum",hbNum);
                    data.put("lotteryNo",leishuEtv.getText().toString());
                    data.put("typeId",typeId);
                    data.put("game",game);
                    //TODO put 参数
                    HttpApiUtils.request(SendSaoLeiRedPakegeActivity.this, RequestUtil.SEND_SAOLEI_RED, data, new HttpApiUtils.OnRequestLintener() {
                        @Override
                        public void onSuccess(String result, Headers headers) {
                            sendBtn.setClickable(true);
                            finish();
                        }

                        @Override
                        public void onFaild(String msg) {
                            sendBtn.setClickable(true);
                        }
                    });
                }
                break;
            case R.id.action_left_iv:
                finish();
                break;
                default:
                    break;
        }
    }
}
