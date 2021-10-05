package com.example.red_pakege.rong_yun.activity.send_red_pakege;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.model.JinQiangRtnModel;
import com.example.red_pakege.net.api.HttpApiUtils;
import com.example.red_pakege.rong_yun.adapter.JinQiangBtnAdapter;
import com.example.red_pakege.rong_yun.model.SaoLeiRoomModel;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.RequestUtil;
import com.example.red_pakege.util.StringMyUtil;
import com.example.red_pakege.widget.CommonAdapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Headers;

public class SendJinQiangPakegeActivity extends BaseActivity implements View.OnClickListener, TextWatcher {
    private Toolbar toolbar;
    private ImageView actionLeftIv;
    private TextView actionTitleTv;

    //红包个数
    private RecyclerView hbNumRecycle;
    private JinQiangBtnAdapter hbNumAdapter;
    private ArrayList<JinQiangRtnModel> hbNumList = new ArrayList<>();
    
    //雷号
    private RecyclerView bombNumRecycle;
    private JinQiangBtnAdapter bombNumAdapter;
    private ArrayList<JinQiangRtnModel> bombNumList = new ArrayList<>();

    private int selectorHbNum;//选中的红包个数
    private ArrayList<JinQiangRtnModel> selectorList = new ArrayList<>();
    private RadioButton noBtn;
    private ImageView sendJinQiangIv;
    private EditText amountEtv;
    private int hbNum;
    private BigDecimal fbMinPrice=BigDecimal.ZERO;
    private BigDecimal fbMaxPrice=BigDecimal.TEN;
    private String mTargetId;
    private int typeId;
    private int game;
    private TextView amountTv;

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_jin_qiang_send;
    }

    @Override
    protected void initView() {
        super.initView();
        getIntentData();
        bindView();
        requestRoomInfo();
        ActionBarUtil.initMainActionbar(this,toolbar);
        initHbNumRecycle();
        initBobmNumRecycle();
    }

    private void getIntentData() {
        mTargetId=getIntent().getStringExtra("targetId");
        typeId=getIntent().getIntExtra("typeId",0);
        game=getIntent().getIntExtra("game",0);
    }

    private void bindView() {
        hbNumRecycle= findViewById(R.id.jinqiang_hb_num_recycle);
        bombNumRecycle= findViewById(R.id.jinqiang_bobm_num_recycle);
        toolbar=findViewById(R.id.message_fragent_toolbar);
        actionLeftIv=findViewById(R.id.action_left_iv);
        actionLeftIv.setImageResource(R.drawable.icon_back);
        findViewById(R.id.action_right_iv).setVisibility(View.INVISIBLE);
        actionTitleTv=findViewById(R.id.actionBar_title);
        actionTitleTv.setText("发禁枪红包");
        noBtn=findViewById(R.id.no_rbt);
        noBtn.setOnClickListener(this);
        sendJinQiangIv=findViewById(R.id.send_red_pakeget);
        sendJinQiangIv.setOnClickListener(this);
        amountEtv=findViewById(R.id.jinqiang_amount_etv);
        amountTv=findViewById(R.id.jinqiang_amount_tv);
        amountEtv.addTextChangedListener(this);
    }
    private void requestRoomInfo() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("id",mTargetId);
        HttpApiUtils.normalRequest(this, RequestUtil.ROOM_INFO, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result, Headers headers) {
                SaoLeiRoomModel saoLeiRoomModel = JSONObject.parseObject(result, SaoLeiRoomModel.class);
                SaoLeiRoomModel.DataBean data = saoLeiRoomModel.getData();
                fbMinPrice = data.getFbMinPrice().setScale(2);//发包最低金额
                fbMaxPrice = data.getFbMaxPrice().setScale(2);//发包最高金额
                amountEtv.setHint(fbMinPrice+"-"+fbMaxPrice);
            }

            @Override
            public void onFaild(String msg) {

            }
        });
    }

    /**
     * 点击tab时的动画效果
     *
     * @param imageView 当前tab对应的icon imageView
     */
    private void startAnimation(ImageView imageView) {
        //
        YoYo.with(Techniques.Pulse)
                .duration(700)
//                .repeat(1000)
                .onEnd(new YoYo.AnimatorCallback() {
                    @Override
                    public void call(Animator animator) {
                        //动画结束监听
                    }
                })
                .playOn(imageView);
    }

    @Override
    protected void initClick() {
        super.initClick();
        actionLeftIv.setOnClickListener(this);
    }

    private void initHbNumRecycle() {
        hbNumAdapter= new JinQiangBtnAdapter(hbNumList);
        hbNumRecycle.setLayoutManager(new GridLayoutManager(this,5));
        hbNumRecycle.setAdapter(hbNumAdapter);

        hbNumList.add(new JinQiangRtnModel(5));
        hbNumList.add(new JinQiangRtnModel(7));
        hbNumList.add(new JinQiangRtnModel(9));
        hbNumList.add(new JinQiangRtnModel(10));
        hbNumList.add(new JinQiangRtnModel(6));
        for (int i = 0; i < hbNumList.size(); i++) {
            hbNumList.get(i).setType(1);
        }
        hbNumAdapter.setOnItemClickListener(new CommonAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                JinQiangRtnModel jinQiangRtnModel = hbNumList.get(position);
                selectorHbNum = jinQiangRtnModel.getNum();
                noBtn.setChecked(false);
                noBtn.setSelected(false);
                if(selectorHbNum==6){
                    noBtn.setVisibility(View.VISIBLE);
                }else {
                    noBtn.setVisibility(View.INVISIBLE);
                }
                clearBobmSelector();
            }
        });
    }
    private void initBobmNumRecycle() {
        bombNumAdapter= new JinQiangBtnAdapter(bombNumList,selectorList);
        bombNumRecycle.setLayoutManager(new GridLayoutManager(this,5));
        bombNumRecycle.setAdapter(bombNumAdapter);
        for (int i = 1; i < 10; i++) {
            bombNumList.add(new JinQiangRtnModel(i));
        }
        bombNumList.add(new JinQiangRtnModel(0));
        for (int i = 0; i < bombNumList.size(); i++) {
            bombNumList.get(i).setType(2);
        }
        bombNumAdapter.setOnItemClickListener(new CommonAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String tipOneStr="红包个数为";
                String tipTwoStr="最多埋";
                String tipThreeStr="个雷";
                JinQiangRtnModel jinQiangRtnModel = bombNumList.get(position);
                int size = selectorList.size();
                if((size<=1&&selectorHbNum==0)||selectorHbNum==0){
                    showtoast("请先选择红包个数");
                } else if(selectorHbNum==6){
                    if(noBtn.isChecked()){
                        if(size>5){
                            showtoast(tipOneStr+selectorHbNum+"的不中玩法,最多埋"+"5"+tipThreeStr);
                            jinQiangRtnModel.setStatus(0);
                            selectorList.remove(jinQiangRtnModel);
                        }
                    }else {
                        if(size>6){
                            showtoast(tipOneStr+selectorHbNum+tipTwoStr+selectorHbNum+tipThreeStr);
                            jinQiangRtnModel.setStatus(0);
                            selectorList.remove(jinQiangRtnModel);
                        }
                    }
                }else if(selectorHbNum==5||selectorHbNum==7){
                    if(size>selectorHbNum){
                        showtoast(tipOneStr+selectorHbNum+tipTwoStr+selectorHbNum+tipThreeStr);
                        jinQiangRtnModel.setStatus(0);
                        selectorList.remove(jinQiangRtnModel);
                    }
                }else if(selectorHbNum==9) {
                    if(size>7){
                        showtoast(tipOneStr+selectorHbNum+tipTwoStr+"7"+tipThreeStr);
                        jinQiangRtnModel.setStatus(0);
                        selectorList.remove(jinQiangRtnModel);
                    }
                }else if(selectorHbNum==10){
                    if(size>8){
                        showtoast(tipOneStr+selectorHbNum+tipTwoStr+"8"+tipThreeStr);
                        jinQiangRtnModel.setStatus(0);
                        selectorList.remove(jinQiangRtnModel);
                    }
                }
            }
        });

    }

    public static void startAty(Context context, String targetId, int typeId, int game){
        Intent intent = new Intent(context, SendJinQiangPakegeActivity.class);
        intent.putExtra("targetId",targetId);
        intent.putExtra("typeId",typeId);
        intent.putExtra("game",game);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_left_iv:
                finish();
                break;
            case R.id.no_rbt:
                if(noBtn.isSelected()){
                    noBtn.setChecked(false);
                    noBtn.setSelected(false);
                }else {
                    noBtn.setChecked(true);
                    noBtn.setSelected(true);
                }
                clearBobmSelector();
                break;
            case R.id.send_red_pakeget:
                startAnimation(sendJinQiangIv);
                String str = amountEtv.getText().toString();
                BigDecimal toStringBig = new BigDecimal(StringMyUtil.isEmptyString(str)?"0":str);
                if(StringMyUtil.isEmptyString(str)){
                    showtoast("请先输入金额");
                }else if(toStringBig.compareTo(fbMinPrice)==-1){
                    showtoast("最低发包金额:"+fbMinPrice+"元");
                }else if(toStringBig.compareTo(fbMaxPrice)==1){
                    showtoast("最高发包金额:"+fbMaxPrice+"元");
                } else if(selectorHbNum==0){
                    showtoast("请选择包数");
                }else {
                   if(selectorHbNum==6&&noBtn.getVisibility()==View.VISIBLE){//选择6包不中,至少选择2个雷号
                       if(selectorList.size()<2){
                           showtoast("红包个数为6的不中玩法,至少埋2个雷");
                       }else {
                           //TODO 请求发包接口
                           requestSendRed();
                       }
                   }else if(selectorHbNum==10){
                       if(selectorList.size()<3){
                           showtoast("红包个数为10至少埋3个雷");
                       }else {
                           //TODO 请求发包接口
                           requestSendRed();
                       }
                   }else {
                       //TODO 请求发包接口
                       requestSendRed();
                   }
                }
                break;
                default:
                    break;
        }
    }

    private void requestSendRed() {
        String lotteryNo="";
        for (int i = 0; i < selectorList.size(); i++) {
            lotteryNo+=selectorList.get(i).getNum()+",";
        }
        String needStr=lotteryNo.substring(0,lotteryNo.length()-1);
        HashMap<String, Object> data = new HashMap<>();
        int playingMethod=0;
        if(noBtn.getVisibility()== View.VISIBLE){
            playingMethod=0;//不中
        }else if(selectorList.size()==1){
            playingMethod=1;//单雷
        }else {
            playingMethod=2;  //连环雷
        }
        data.put("amount",amountEtv.getText().toString());
        data.put("hbNum",selectorHbNum);
        data.put("lotteryNo",needStr);
        data.put("playingMethod",playingMethod);
        data.put("roomId",mTargetId);
        data.put("typeId",typeId);
        HttpApiUtils.request(SendJinQiangPakegeActivity.this, RequestUtil.SEND_JINQIANG_RED, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result, Headers headers) {
                finish();
            }

            @Override
            public void onFaild(String msg) {
                System.out.println(msg);
            }
        });
    }

    //点击不字按钮,或者重新选择包数,清空选中的雷号,并清除selectorList
    private void clearBobmSelector() {

        for (int i = 0; i < bombNumList.size(); i++) {
            bombNumList.get(i).setStatus(0);
        }
        bombNumAdapter.notifyDataSetChanged();
        selectorList.clear();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String toString = amountEtv.getText().toString();
        amountTv.setText(StringMyUtil.isEmptyString(toString)?"¥ 0":"¥ "+toString);
    }
}
