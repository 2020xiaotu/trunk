package com.zz.live.ui.activity.start_live_activity.open_result_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zz.live.R;
import com.zz.live.base.BaseActivity;
import com.zz.live.bean.BeiJInOpenResultModel;
import com.zz.live.bean.BeiJinOpenResultModel2;
import com.zz.live.net.api.HttpApiUtils;
import com.zz.live.ui.adapter.RaceOpenAdapter;
import com.zz.live.ui.adapter.RaceOpenAdapterRight;
import com.zz.live.utils.CommonToolbarUtil;
import com.zz.live.utils.DateUtil;
import com.zz.live.utils.RequestUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import okhttp3.Headers;

public class RaceOpenActivity extends BaseActivity implements View.OnClickListener {
private RadioButton one;
private RadioButton two;
private RadioButton three;
private RadioButton four;
private TextView chooseTimeText;
private ImageView choosetimeIma;
private LinearLayout chooseTimeLinear;
private ArrayList<RadioButton> radioButtons =new ArrayList<>();
private PopupWindow popupWindow;
private TextView timeOne;
private TextView timeTwo;
private TextView timeThree;
private TextView timeFour;
private TextView timeFive;
private TextView timeSix;
private TextView timeSeven;
private String todayDate;
private View backgroundView;
private RecyclerView mRecyleLeft;
private RaceOpenAdapter raceOpenAdapter;
private RaceOpenAdapterRight raceOpenAdapterRight;
private ArrayList<BeiJInOpenResultModel> beiJInOpenResultModels =new ArrayList<>();
private ArrayList<BeiJinOpenResultModel2> beiJInOpenResultModel2s =new ArrayList<>();
private RefreshLayout refreshLayout;//???????????? ????????????
private int pageNum=1;
private TextView backTextView;
private int type_id;//??????type_id
private LinearLayout nodataLinear;
private ConstraintLayout loadingLinear;


    @Override
    public int getLayoutId() {
        return R.layout.activity_bei_jin_race_open;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        Intent intent = getIntent();
        type_id = intent.getIntExtra("type_id", 1);//?????????activity??????????????????

        initPop();//????????????pop???????????????
        bindView();//????????????????????????????????????
        initRecycleView();//recycleview??????????????????
        initView();//?????????(??????recycleView??????)??????????????????
        initRefresh();//???????????? ???????????????????????????
    }

    /*
    ????????????????????????????????????
     */
private void bindView() {
    nodataLinear=findViewById(R.id.nodata_linear);
    loadingLinear=findViewById(R.id.loading_linear);
    refreshLayout=findViewById(R.id.refresh);//????????????
    raceOpenAdapter =new RaceOpenAdapter(this,beiJInOpenResultModels,beiJInOpenResultModel2s);//recycleView?????????
    mRecyleLeft=findViewById(R.id.beijin_open_recycle);//recycleView
    one=findViewById(R.id.radio_button_one);//?????? ??????
    two=findViewById(R.id.radio_button_two);//?????? ??????
    three=findViewById(R.id.radio_button_three);//?????? ??????
    four=findViewById(R.id.radio_button_four);//??????/?????? ??????
    chooseTimeText=findViewById(R.id.time_choose);//????????????????????????textView
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    long l = System.currentTimeMillis();
    Date date = new Date(l);
    todayDate = simpleDateFormat.format(date);//????????????
    chooseTimeText.setText(todayDate);
    choosetimeIma=findViewById(R.id.time_choose_img);//??????textView?????????img(????????????????????????)
    chooseTimeLinear=findViewById(R.id.time_choose_linear);//????????????????????????pop?????????
    backTextView=findViewById(R.id.open_result_action_bar_return);//actionBar????????????
    //???????????????????????????,????????????????????????????????????????????????
    radioButtons.add(one);
    radioButtons.add(two);
    radioButtons.add(three);
    radioButtons.add(four);
    /*
    ??????????????????
     */
    one.setOnClickListener(this);
    two.setOnClickListener(this);
    three.setOnClickListener(this);
    four.setOnClickListener(this);
    chooseTimeLinear.setOnClickListener(this);
    backTextView.setOnClickListener(this);
    one.performClick();
}
/*
???????????? ???????????? ???????????????
 */
private void initRefresh() {
    refreshLayout.setRefreshHeader(new ClassicsHeader(RaceOpenActivity.this));
    refreshLayout.setRefreshFooter(new ClassicsFooter(RaceOpenActivity.this));
    //????????????
    refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
        @Override
        public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
            pageNum++;//??????????????????,pagenum??????
            /*
            ?????????????????????????????????(???????????????,?????????????????????????????????)
             */
            if(one.isChecked()){//??????????????????
                getDada(pageNum,15,chooseTimeText.getText().toString(),type_id,true,false,false,false,true,false);
//                    getDada(pageNum,15,chooseTimeText.getText().toString(),type_id,true,false,false,false,true);
            }else if(two.isChecked()){//??????????????????
                getDada(pageNum,15,chooseTimeText.getText().toString(),type_id,false,true,false,false,true,false);
//                    getDada(pageNum,15,chooseTimeText.getText().toString(),type_id,false,true,false,false,true);
            }else  if(three.isChecked()){//??????????????????
                getDada(pageNum,15,chooseTimeText.getText().toString(),type_id,false,false,true,false,true,false);
//                    getDada(pageNum,15,chooseTimeText.getText().toString(),type_id,false,false,true,false,true);
            }else if(four.isChecked()){//??????/??????????????????
                getDada(pageNum,15,chooseTimeText.getText().toString(),type_id,false,false,false,true,true,false);
//                    getDada(pageNum,15,chooseTimeText.getText().toString(),type_id,false,false,false,true,true);
            }
            refreshLayout.finishLoadMore();//????????????
        }
    });

    //????????????
    refreshLayout.setOnRefreshListener(new OnRefreshListener() {
        @Override
        public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            pageNum=1;//????????????,???pagenum?????????
            initRadioButon();
            refreshLayout.finishRefresh();//????????????
        }
    });
}
private void initSwich(String name){
    for (int i = 0; i < beiJInOpenResultModels.size(); i++) {
        BeiJInOpenResultModel beiJInOpenResultModel = beiJInOpenResultModels.get(i);
        String numListCopy = beiJInOpenResultModel.getNumListCopy();
        String[] split = numListCopy.split(",");
        List<String> strings = Arrays.asList(split);
        String sum = beiJInOpenResultModel.getSum();
        String markdx = beiJInOpenResultModel.getMarkdx();
        String markds = beiJInOpenResultModel.getMarkds();
        List<String> marklh = beiJInOpenResultModel.getMarklh();
        if(name.equals("??????")){
            beiJInOpenResultModel.setNumList(numListCopy);
            beiJInOpenResultModel.setGuanYa(false);
        }else if(name.equals("??????")){
            String needString = "";
            for (String s: strings) {
                int i1 = Integer.parseInt(s);
                if(i1<=5){
                    s="???";
                }else if(i1>=6){
                    s="???";
                }
                needString+=s+"," ;
            }
            needString = needString.substring(0, needString.length()-1);
          beiJInOpenResultModel.setNumList(needString);
          beiJInOpenResultModel.setGuanYa(false);
        }else if(name.equals("??????")){
            String needString = "";
            for (String s: strings) {
                int i1 = Integer.parseInt(s);
                if(i1%2!=0){
                    s="???";
                }else {
                    s="???";
                }
                needString+=s+"," ;
            }
            needString = needString.substring(0, needString.length()-1);
            beiJInOpenResultModel.setNumList(needString);
            beiJInOpenResultModel.setGuanYa(false);
        }else if(name.equals("??????/??????")){
            String needString = "";
            needString+=sum+",";
            needString=needString+markdx+",";
            needString=needString+markds+",";
            for (String s:marklh) {
                needString+=s+",";
            }
            needString = needString.substring(0, needString.length()-1);
            beiJInOpenResultModel.setNumList(needString);
            beiJInOpenResultModel.setGuanYa(true);
        }
    }
    raceOpenAdapter.notifyDataSetChanged();
}
    @Override
    protected void initStatusBar() {
        super.initStatusBar();
        CommonToolbarUtil.initStatusBarColor(this);
    }
/**
 * ???????????????????????????
 * @param pageNum ?????????????????????(???????????????????????????)
 * @param pageSize ???????????????????????????(???????????????15???)
 * @param date     ?????? (?????? 2019-6-10)
 * @param typeId   ??????type_id(?????????????????????)
 * @param isNum    ???????????????"??????"??????(????????????????????????)
 * @param isDaXiao ???????????????"??????"??????(????????????????????????)
 * @param isDanShuang ???????????????"??????"??????(????????????????????????)
 * @param isYaGuang  ?????????????????????/????????????(????????????????????????)
 * @param isLoadMore ?????????????????????????????????(???????????????,????????????recycleView?????????)
 */
private void getDada(int pageNum, int pageSize, String date, int typeId, final boolean isNum, final boolean isDaXiao, final boolean isDanShuang, final boolean isYaGuang, final boolean isLoadMore, boolean showLoad) {
    if(showLoad){
        loadingLinear.setVisibility(View.VISIBLE);
        nodataLinear.setVisibility(View.GONE);
    }
    HashMap<String, Object> data = new HashMap<>();
    data.put("pageNo",pageNum);
    data.put("pageSize",pageSize);
    data.put("date",date);
    data.put("type_id",typeId);
    HttpApiUtils.cpNormalRequest(this, null, RequestUtils.REQUEST_26r, data, new HttpApiUtils.OnRequestLintener() {
        @Override
        public void onSuccess(String result) {
            loadingLinear.setVisibility(View.GONE);
            JSONObject jsonObject1= JSONObject.parseObject(result);
            JSONArray raceLotterylist = jsonObject1.getJSONArray("raceLotterylist");
            int size = raceLotterylist.size();
            if(!isLoadMore){//??????????????????,????????????,????????????????????????????????????
                beiJInOpenResultModels.clear();
            }
            if(size==0){//??????????????????????????????0???, ?????????refreshLayout?????????
                if(!isLoadMore){
                    nodataLinear.setVisibility(View.VISIBLE);
                }
                refreshLayout.finishLoadMoreWithNoMoreData();
            }else {
                nodataLinear.setVisibility(View.GONE);
            }
            for (int i = 0; i < raceLotterylist.size(); i++) {
                JSONObject jsonObject = raceLotterylist.getJSONObject(i);
                String lotteryqishu = jsonObject.getString("lotteryqishu");//????????????
                String createdDate = jsonObject.getString("lotterytime");//??????
                String lotteryNo = jsonObject.getString("lotteryNo");//????????????
                String sum = jsonObject.getString("sum");//??????/???????????????
                String markdx = jsonObject.getString("markdx");//??????/??????????????????
                String markds = jsonObject.getString("markds");//??????/??????????????????
//                    JSONArray marklh = jsonObject.getJSONArray("marklh");
                List<String> marklh = JSONObject.parseArray(jsonObject.getString("marklh"), String.class);//??????json??????????????????(?????????????????????)
//                    for (int j = 0; j < marklh.size(); j++) {
//                        System.out.print(" sssss= "+marklh1.get(j));
//                    }
                String[] split = lotteryNo.split(",");
                List<String> strings = Arrays.asList(split);
                if(isNum){//?????????????????????, ????????????lotteryNo,
                    beiJInOpenResultModels.add(new BeiJInOpenResultModel(lotteryqishu,createdDate,lotteryNo,false,lotteryNo,sum,markdx,markds,marklh));
                }else if(isDaXiao){//????????????????????? (?????????lotteryNo?????????"??????",??????????????????????????????????????????(??????????????? ))
                    String needString = "";
                    for (String s: strings) {
                        int i1 = Integer.parseInt(s);
                        if(i1<=5){
                            s="???";
                        }else if(i1>=6){
                            s="???";
                        }
                        needString+=s+"," ;
                    }
                    needString = needString.substring(0, needString.length()-1);
                    beiJInOpenResultModels.add(new BeiJInOpenResultModel(lotteryqishu,createdDate,needString,false,lotteryNo,sum,markdx,markds,marklh));
                }else if(isDanShuang){//????????????????????? (?????????lotteryNo?????????"??????",??????????????????????????????????????????(??????????????? ))
                    String needString = "";
                    for (String s: strings) {
                        int i1 = Integer.parseInt(s);
                        if(i1%2!=0){
                            s="???";
                        }else {
                            s="???";
                        }
                        needString+=s+"," ;
                    }
                    needString = needString.substring(0, needString.length()-1);
                    beiJInOpenResultModels.add(new BeiJInOpenResultModel(lotteryqishu,createdDate,needString,false,lotteryNo,sum,markdx,markds,marklh));
                }
                else if(isYaGuang){//???????????????/??????(??????????????????????????????????????????????????????(???????????????))
                    String needString = "";
                    needString+=sum+",";
                    needString=needString+markdx+",";
                    needString=needString+markds+",";
                    for (String s:marklh) {
                        needString+=s+",";
                    }
                    needString = needString.substring(0, needString.length()-1);
                    beiJInOpenResultModels.add(new BeiJInOpenResultModel(lotteryqishu,createdDate,needString,true,lotteryNo,sum,markdx,markds,marklh));
                }

            }

            raceOpenAdapter.notifyDataSetChanged();
        }

        @Override
        public void onFail(String msg) {
            loadingLinear.setVisibility(View.GONE);
        }
    });

}
        /*
        recycleView???????????????
         */
private void initRecycleView() {
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    mRecyleLeft.setLayoutManager(linearLayoutManager);
    mRecyleLeft.setAdapter(raceOpenAdapter);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    long l = System.currentTimeMillis();
    Date date = new Date(l);
    todayDate = simpleDateFormat.format(date);//????????????
    //???????????????????????????
    getDada(1,15,todayDate,type_id,true,false,false,false,false,true);
//        getDada(1,15,todayDate,type_id,true,false,false,false,false);
}
    /*
    ???????????????pop??????
     */
private void initPop() {
    View view = LayoutInflater.from(this).inflate(R.layout.race_open_result_time_pop, null);
    backgroundView=view.findViewById(R.id.background_view);//pop?????????????????????
    timeOne =view.findViewById(R.id.time_one);
    timeTwo =view.findViewById(R.id.time_two);
    timeThree =view.findViewById(R.id.time_three);
    timeFour =view.findViewById(R.id.time_four);
    timeFive =view.findViewById(R.id.time_five);
    timeSix =view.findViewById(R.id.time_six);
    timeSeven =view.findViewById(R.id.time_seven);
    timeOne.setOnClickListener(this);
    timeTwo.setOnClickListener(this);
    timeThree.setOnClickListener(this);
    timeFour.setOnClickListener(this);
    timeFive.setOnClickListener(this);
    timeSix.setOnClickListener(this);
    timeSeven.setOnClickListener(this);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    todayDate = simpleDateFormat.format(date);//????????????
    Date dateAfter1 = DateUtil.getDateBefore(date, 1);//1???????????????
    Date dateAfter2 = DateUtil.getDateBefore(date, 2);//2???????????????
    Date dateAfter3 = DateUtil.getDateBefore(date, 3);//3???????????????
    Date dateAfter4 = DateUtil.getDateBefore(date, 4);//4???????????????
    Date dateAfter5 = DateUtil.getDateBefore(date, 5);//5???????????????
    Date dateAfter6 = DateUtil.getDateBefore(date, 6);//6???????????????
    timeOne.setText(todayDate);
    timeTwo.setText(simpleDateFormat.format(dateAfter1));
    timeThree.setText(simpleDateFormat.format(dateAfter2));
    timeFour.setText(simpleDateFormat.format(dateAfter3));
    timeFive.setText(simpleDateFormat.format(dateAfter4));
    timeSix.setText(simpleDateFormat.format(dateAfter5));
    timeSeven.setText(simpleDateFormat.format(dateAfter6));
    popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
    popupWindow.setAnimationStyle(R.style.popAlphaanim0_3);//??????????????????
    backgroundView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            popupWindow.dismiss();//??????????????????,??????pop(????????????????????????MATCH_PARENT,????????????,???????????????????????????)
        }
    });
    //pop???????????????
    popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
        @Override
        public void onDismiss() {//pop?????????,?????????????????????
            choosetimeIma.setPivotX(choosetimeIma.getWidth()/2);
            choosetimeIma.setPivotY(choosetimeIma.getHeight()/2);//?????????????????????
            choosetimeIma.setRotation(0);
//                 backgroundView.getBackground().setAlpha(100);
        }
    });
}
    //?????????????????????,????????????textView
public void initView() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    long timeMillis = System.currentTimeMillis();
    Date date = new Date(timeMillis);
    todayDate = simpleDateFormat.format(date);//????????????
    chooseTimeText.setText(todayDate);
}


    /*
    ???????????? ?????? ?????? ?????? ??????/?????? ??????????????????, ?????????3???????????????????????????
    ??????refreshLayout???pageNum??????????????????
     */
private void initRadioClick(RadioButton radioButton) {
    for (int i = 0; i < radioButtons.size();i++) {
        radioButtons.get(i).setChecked(false);
    }
    radioButton.setChecked(true);
    //???????????? ?????? ?????? ?????? ??????/?????? ?????????,????????????????????????,??????????????????,????????????????????????pageNum???refreshLayout?????????
    //??????????????????????????????????????????, ????????????????????????,???????????????,????????????????????????
    //???????????????????????????????????????pop,???????????????????????????
    pageNum=1;
    refreshLayout.resetNoMoreData();
}
    /*
    ????????????
     */
@Override
public void onClick(View v) {
    switch (v.getId()){
        case R.id.radio_button_one://????????????
            initRadioClick(one);
//                getDada(1,15,chooseTimeText.getText().toString(),type_id,true,false,false,false,false);
//                getDada(1,15,chooseTimeText.getText().toString(),type_id,true,false,false,false,false);
            initSwich("??????");
            break;
        case R.id.radio_button_two://????????????
            initRadioClick(two);
//                getDada(1,15,chooseTimeText.getText().toString(),type_id,false,true,false,false,false);
//                getDada(1,15,chooseTimeText.getText().toString(),type_id,false,true,false,false,false);
            initSwich("??????");
            break;
        case R.id.radio_button_three://?????? ??????
            initRadioClick(three);
//                getDada(1,15,chooseTimeText.getText().toString(),type_id,false,false,true,false,false);
//                getDada(1,15,chooseTimeText.getText().toString(),type_id,false,false,true,false,false);
            initSwich("??????");
            break;
        case R.id.radio_button_four://????????????
            initRadioClick(four);
//                getDada(1,15,chooseTimeText.getText().toString(),type_id,false,false,false,true,false);
//                getDada(1,15,chooseTimeText.getText().toString(),type_id,false,false,false,true,false);
            initSwich("??????/??????");
            break;
        case R.id.time_choose_linear://????????????????????????pop
            popupWindow.showAsDropDown(chooseTimeLinear,0,0, Gravity.BOTTOM);
            //????????????
            choosetimeIma.setPivotX(choosetimeIma.getWidth()/2);
            choosetimeIma.setPivotY(choosetimeIma.getHeight()/2);//?????????????????????
            choosetimeIma.setRotation(180);
            backgroundView.getBackground().setAlpha(50);
            break;
        case R.id.time_one://pop??????????????????(????????????pop,????????????????????????,?????????chooseTimeText)(??????????????????date,??????chooseTimeText???getText())
            chooseTimeText.setText(timeOne.getText().toString());
            popupWindow.dismiss();
            initRadioButon();
            break;
        case R.id.time_two://pop??????????????????.....
            chooseTimeText.setText(timeTwo.getText().toString());
            popupWindow.dismiss();
            initRadioButon();
            break;
        case R.id.time_three://???....
            chooseTimeText.setText(timeThree.getText().toString());
            popupWindow.dismiss();
            initRadioButon();
            break;
        case R.id.time_four://???....
            chooseTimeText.setText(timeFour.getText().toString());
            popupWindow.dismiss();
            initRadioButon();
            break;
        case R.id.time_five://???....
            chooseTimeText.setText(timeFive.getText().toString());
            popupWindow.dismiss();
            initRadioButon();
            break;
            case R.id.time_six://???....
            chooseTimeText.setText(timeSix.getText().toString());
            popupWindow.dismiss();
                initRadioButon();
            break;
        case R.id.time_seven://???....
            chooseTimeText.setText(timeSeven.getText().toString());
            popupWindow.dismiss();
            initRadioButon();
            break;
        case R.id.open_result_action_bar_return:
            finish();
            break;
    }
}

private void initRadioButon() {
    //???????????? ?????? ?????? ?????? ??????/?????? ?????????,????????????????????????,??????????????????,????????????????????????pageNum???refreshLayout?????????
    //??????????????????????????????????????????, ????????????????????????,???????????????,????????????????????????
    //??????????????????????????????????????? ?????? ?????? ?????? ?????????????????????
    pageNum=1;
    refreshLayout.resetNoMoreData();
    if(one.isChecked()){
        getDada(pageNum,15,chooseTimeText.getText().toString(),type_id,true,false,false,false,false,true);
//            getDada(pageNum,15,chooseTimeText.getText().toString(),type_id,true,false,false,false,false);
    }else if(two.isChecked()){
        getDada(pageNum,15,chooseTimeText.getText().toString(),type_id,false,true,false,false,false,true);
//            getDada(pageNum,15,chooseTimeText.getText().toString(),type_id,false,true,false,false,false);
    }else  if(three.isChecked()){
        getDada(pageNum,15,chooseTimeText.getText().toString(),type_id,false,false,true,false,false,true);
//            getDada(pageNum,15,chooseTimeText.getText().toString(),type_id,false,false,true,false,false);
    }else if(four.isChecked()){
        getDada(pageNum,15,chooseTimeText.getText().toString(),type_id,false,false,false,true,false,true);
//            getDada(pageNum,15,chooseTimeText.getText().toString(),type_id,false,false,false,true,false);
    }
}

    @Override
    public void onNetChange(boolean netWorkState) {

    }
}
