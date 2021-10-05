package com.example.red_pakege.activity.agent_center_activity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.red_pakege.R;
import com.example.red_pakege.adapter.agent_center_adapter.ReportAdapter;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.model.ReportModel;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.DateUtil;
import com.example.red_pakege.util.Utils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class MineReportActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView actionLeftIv;
    private ImageView actionRoghtIv;
    private TextView chooseDateTv;
    private LinearLayout chooseDateLinear;
    private TextView actionTitleTv;

    private RefreshLayout refreshLayout;
    private RecyclerView mRecy;
    private ArrayList<ReportModel>reportModelArrayList = new ArrayList<>();
    private ReportAdapter reportAdapter;

    private LinearLayout startDateLinear;
    private LinearLayout endDateLinear;
    private TextView showStartDateTv;
    private TextView showEndDateTv;


    //日期选择pop
    private PopupWindow chooseDatePop;
    private RadioButton todayTv;
    private RadioButton yestodayTv;
    private RadioButton thisMonthtv;
    private RadioButton lastMonthTv;
    private RadioButton thisWeekTv;
    private RadioButton lastWeekTv;
    private ArrayList<RadioButton> chooseDateRbtList =new ArrayList<>();
    private TextView chooseDateSureTv;

    private String chooseDateStr;
    private Calendar mixDate=Calendar.getInstance();;//时间选择的起始时间
    private Calendar maxDate=Calendar.getInstance();;//时间选择的结束时间
    private Date selectedDateLeft;//每次切换时间时选中的开始时间 用于再次弹出时间选择器时的默认选中
    private Date selectedDateRight;//同上

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//请求接口时的格式
    private SimpleDateFormat sdfShow = new SimpleDateFormat("yyyy-MM-dd");//界面显示的时间格式
    private String startDate;//请求数据时的开始时间
    private String endDate;//请求数据时的结束时间

    private enum DateStatus{TODAY,YESTODAY,THIS_WEEK,LAST_WEEK,THIS_MONTH,LAST_MONTH};

    private DateStatus dateStatus;
    //设置actionbar右侧的日期textView
    public void setChooseDateStr(RadioButton radioButton) {
        chooseDateStr=radioButton.getText().toString();
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_mine_report;
    }

    @Override
    protected void initView() {
        super.initView();
        bindView();
        ActionBarUtil.initMainActionbar(this,toolbar);
        initRecycle();
        initTime();
    }
        //第一次进入时,时间相关初始化
    private void initTime() {
        Calendar calendarMax = Calendar.getInstance();
        calendarMax.setTime(new Date());
        maxDate.set(calendarMax.get(Calendar.YEAR),calendarMax.get(Calendar.MONTH),calendarMax.get(Calendar.DAY_OF_MONTH));
        mixDate.set(calendarMax.get(Calendar.YEAR)-20,0,1);
        Calendar todayCalendar = DateUtil.getTodayCalendar(new Date());
        Date startTime = DateUtil.getStartTime(todayCalendar);
        selectedDateLeft=startTime;

        startDate=sdf.format(selectedDateLeft);//请求数据时的开始时间
        showStartDateTv.setText(sdfShow.format(new Date()));
        showEndDateTv.setText(sdfShow.format(new Date()));
        Date endTime = DateUtil.getEndTime(todayCalendar);//得到当天的结束时间(23:59:59)
//        selectedDateRight =endTime;//第一次进入时,将右侧选中的时间设置为当前时间
        selectedDateRight=startTime;//结束时间也设置为startTime(设为endTime的话,第一次弹出pickerView时,由于获取不到当前的23:59:59,默认选中是第一个时间,即无法实现默认选中的小伙==效果)
//        System.out.println("aaaaaaa selectedDateLeft"+ sdf.format(selectedDateLeft));
//        System.out.println("aaaaaaa selectedDateRight"+ sdf.format(selectedDateRight));
        endDate=sdf.format(endTime);//请求数据时的结束时间(第一次进入请求数据时,需要用当天的23:59:59)
    }
        /*
        开始时间选择器
         */
    private void showStartPickerView(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDateLeft);
//        System.out.println("aaaaaaa showStartPickerView selectedDateLeft"+ sdf.format(selectedDateLeft));
        TimePickerView timePickerView= new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
            selectedDateLeft =date;
            Calendar todayCalendar = DateUtil.getTodayCalendar(date);
            Date startTime = DateUtil.getStartTime(todayCalendar);
            startDate=sdf.format(startTime);
            showStartDateTv.setText(sdfShow.format(startTime));
            }
        })
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setTextXOffset(10,10,10,10,10,10)
//                        .setContentSize(18)//滚轮文字大小
                .setTitleSize(16)//标题文字大小
                .setTitleText("开始时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleColor(getResources().getColor(R.color.defaultColor))//标题文字颜色
                .setSubmitColor(Color.parseColor("#FE525B"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#FE525B"))//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(mixDate,maxDate)//起始终止年月日设定
                .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .setDecorView(findViewById(R.id.content))
               .setDate(calendar)
                .build();
        timePickerView.show();
    }
    /*
    结束时间选择器
     */
    private void showEndPickerView(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDateRight);
//        System.out.println("aaaaaaa showEndPickerView selectedDateRight"+ sdf.format(selectedDateRight));
        TimePickerView timePickerView= new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                selectedDateRight =date;
                Calendar todayCalendar = DateUtil.getTodayCalendar(date);
                Date endTime = DateUtil.getEndTime(todayCalendar);
                endDate=sdf.format(endTime);
                showEndDateTv.setText(sdfShow.format(endTime));
                //TODO 请求数据
            }
        })
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setTextXOffset(10,10,10,10,10,10)
//                        .setContentSize(18)//滚轮文字大小
                .setTitleSize(16)//标题文字大小
                .setTitleText("结束时间")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleColor(getResources().getColor(R.color.defaultColor))//标题文字颜色
                .setSubmitColor(Color.parseColor("#FE525B"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#FE525B"))//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
//                        .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(mixDate,maxDate)//起始终止年月日设定
                .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//                .setDecorView(findViewById(R.id.content))
                .setDate(calendar)
                .build();
        timePickerView.show();
    }

    private void initRecycle() {
        reportAdapter= new ReportAdapter(reportModelArrayList);
//       mRecy.addItemDecoration(new MainClidItemGridDecoration(this));
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        mRecy.setLayoutManager(gridLayoutManager);
        mRecy.setAdapter(reportAdapter);
        View headView = LayoutInflater.from(this).inflate(R.layout.report_recycle_headview,null);
        bindHeadView(headView);
        reportAdapter.addHeaderView(headView);
        for (int i = 0; i < 4; i++) {
            String title = null;
            ReportModel reportModel = new ReportModel();
            if(i==0){
                title="玩家活跃度";
            }else if(i==1){
                title="充值与提现";
            }else if(i==2){
                title="发包与抢包";
            }else if(i==3){
                title="奖金与佣金";
            }
            reportModel.setContent(title);
            reportModelArrayList.add(reportModel);
            for (int j = 0; j < 4; j++) {
                reportModelArrayList.add(new ReportModel("¥0.00","新注册人数"));
            }
        }


    }

    private void bindHeadView(View headView) {
        startDateLinear=headView.findViewById(R.id.start_date_linear);
        endDateLinear=headView.findViewById(R.id.end_date_linear);
        showStartDateTv=headView.findViewById(R.id.start_date_show_tv);
        showEndDateTv=headView.findViewById(R.id.end_date_show_tv);
    }

    private void bindView() {
        toolbar=findViewById(R.id.report_toolbar);
        actionLeftIv=findViewById(R.id.report_action_left_iv);
        actionRoghtIv=findViewById(R.id.report_action_right_iv);
        chooseDateTv=findViewById(R.id.choose_date_tv);
        chooseDateLinear=findViewById(R.id.choose_date_linear);
        actionTitleTv=findViewById(R.id.report_actionBar_title);

        actionTitleTv.setText("我的报表");

        mRecy=findViewById(R.id.report_recycle);
    }

    @Override
    protected void initClick() {
        super.initClick();
        actionLeftIv.setOnClickListener(this);
        chooseDateLinear.setOnClickListener(this);
        actionLeftIv.setOnClickListener(this);
        startDateLinear.setOnClickListener(this);
        endDateLinear.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.report_action_left_iv:
                finish();
                break;
            case R.id.choose_date_linear:
                //TODO  弹出日期选择pop
                if(chooseDatePop==null){
                    initChooseDatePop();
                }
                chooseDatePop.showAtLocation(actionLeftIv, Gravity.BOTTOM,0,0);
                Utils.darkenBackground(MineReportActivity.this,0.7f);
                break;
            case R.id.start_date_linear:
                //TODO 开始时间选择
                showStartPickerView();
                break;
            case R.id.end_date_linear:
                //TODO 结束时间选择
                showEndPickerView();
                break;
            case R.id.report_today_tv:
                initButtonStatus(todayTv);
                setChooseDateStr(todayTv);
                dateStatus =DateStatus.TODAY;
                break;
            case R.id.report_yestoday_tv:
                initButtonStatus(yestodayTv);
                setChooseDateStr(yestodayTv);
                dateStatus =DateStatus.YESTODAY;
                break;
            case R.id.report_this_week_tv:
                initButtonStatus(thisWeekTv);
                setChooseDateStr(thisWeekTv);
                dateStatus =DateStatus.THIS_WEEK;
                break;
            case R.id.report_last_week_tv:
                initButtonStatus(lastWeekTv);
                setChooseDateStr(lastWeekTv);
                dateStatus =DateStatus.LAST_WEEK;
                break;
            case R.id.report_this_month_tv:
                initButtonStatus(thisMonthtv);
                setChooseDateStr(thisMonthtv);
                dateStatus =DateStatus.THIS_MONTH;
                break;
            case R.id.report_last_month_tv:
                initButtonStatus(lastMonthTv);
                setChooseDateStr(lastMonthTv);
                dateStatus =DateStatus.LAST_MONTH;
                break;
            case R.id.choose_date_true_tv:
                chooseDatePop.dismiss();
                chooseDateTv.setText(chooseDateStr);
                initChooseDate();
                //TODO 根据选择的时间请求数据
                break;

            default:
                break;


        }
    }
    //每次筛选时间后的时间相关初始化(pickerView默认选中时间和showStartDateTv showEndDateTv 的显示相关)
    private void initChooseDate(){
        switch (dateStatus){
            case TODAY:
                initSelectorTime(new Date(),new Date(),true);
                break;
            case YESTODAY:
                initSelectorTime(DateUtil.getYesterdayDate(new Date()),DateUtil.getYesterdayDate(new Date()),false);
                break;
            case THIS_WEEK:
                initSelectorTime(DateUtil.getWeekTimeStart(),new Date(),true);
                break;
            case LAST_WEEK:
                ArrayList<Date> lastWeekList = DateUtil.getLastWeekList(new ArrayList<Date>(), new Date());
                Date dateStart = lastWeekList.get(0);//上周开始时间
                Date dateEnd = lastWeekList.get(1);//上周结束时间
                initSelectorTime(dateStart,dateEnd,false);
                break;
            case THIS_MONTH:
                initSelectorTime(DateUtil.getMonthBegin(new Date()),new Date(),true);
                break;
            case LAST_MONTH:
                Calendar calendarStart = Calendar.getInstance();
                int year = calendarStart.get(Calendar.YEAR);
                int month = calendarStart.get(Calendar.MONTH)-1;
                int dayOfMonth = calendarStart.get(Calendar.DAY_OF_MONTH);
                if(month<=0){
                    year=year-1;
                    month=12;
                }
                calendarStart.set(Calendar.YEAR,year);
                calendarStart.set(Calendar.MONTH,month);
                calendarStart.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                Date monthBegin = DateUtil.getMonthBegin(calendarStart.getTime());
                Date monthEnd = DateUtil.getMonthEnd(calendarStart.getTime());
                initSelectorTime(monthBegin,monthEnd,false);
                break;
                default:
                    break;
        }
    }

    /**
     * 点击actionbar中时间选择的确定按钮后的相关操作
     * @param leftDate 确定按钮点击后,计算出的开始时间(显示在左侧pickerView)
     * @param rightDate 确定按钮点击后,计算出的结束时间(显示在右侧pickerView)
     * @param isUsedToday 确定按钮点击后,结束时间是否为当天日期(不为当天日期时,startDate取23:59:59,后面看后台接口,如果允许当天时间传23:59:59的话,此字段可以去掉)
     */
    private void initSelectorTime(Date leftDate, Date rightDate,boolean isUsedToday) {
        Calendar calendarLeft = Calendar.getInstance();
        Calendar calendarRight = Calendar.getInstance();
        calendarLeft.setTime(leftDate);
        calendarRight.setTime(rightDate);
        selectedDateLeft = DateUtil.getStartTime(calendarLeft);
        //显示的时间不去endTime (用了endTime时,只要涉及到当天日期的时间选择,下一次弹出pickerView时不会默认选中今天)
        selectedDateRight=DateUtil.getStartTime(calendarRight);
/*        if(isUsedToday){
            selectedDateRight=calendarRight.getTime();
            System.out.println("aaaaaaa useToday  "+sdf.format(selectedDateRight));
        }else {
            selectedDateRight=DateUtil.getEndTime(calendarRight);
            System.out.println("aaaaaaa !useToday  "+sdf.format(selectedDateRight));
        }*/
        startDate=sdf.format(selectedDateLeft);
        if(isUsedToday){
            endDate=sdf.format(calendarRight.getTime());
        }else {
            endDate=sdf.format(DateUtil.getEndTime(calendarRight));
        }

        showStartDateTv.setText(sdfShow.format(selectedDateLeft));
        showEndDateTv.setText(sdfShow.format(selectedDateRight));
    }
        /*
        点击actionBar右侧 ,弹出的时间筛选pop相关初始化
         */
    private void initChooseDatePop() {
        View view = LayoutInflater.from(MineReportActivity.this).inflate(R.layout.report_date_choose_pop,null);
        todayTv=view.findViewById(R.id.report_today_tv);
        yestodayTv=view.findViewById(R.id.report_yestoday_tv);
        thisWeekTv=view.findViewById(R.id.report_this_week_tv);
        lastWeekTv=view.findViewById(R.id.report_last_week_tv);
        thisMonthtv=view.findViewById(R.id.report_this_month_tv);
        lastMonthTv=view.findViewById(R.id.report_last_month_tv);
        chooseDateRbtList.add(todayTv);
        chooseDateRbtList.add(yestodayTv);
        chooseDateRbtList.add(thisWeekTv);
        chooseDateRbtList.add(lastWeekTv);
        chooseDateRbtList.add(thisMonthtv);
        chooseDateRbtList.add(lastMonthTv);
        chooseDateSureTv=view.findViewById(R.id.choose_date_true_tv);
        chooseDateSureTv.setOnClickListener(this);
        for (RadioButton radioButton:chooseDateRbtList) {
            radioButton.setOnClickListener(this);
        }
        //默认选中今天
        todayTv.setSelected(true);
        chooseDatePop =new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);
        chooseDatePop.setAnimationStyle(R.style.pop_buttom_to_top_150);
        chooseDatePop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.darkenBackground(MineReportActivity.this,1f);
            }
        });
    }

    /*
    控制所有的radioButton中,只有一个为选中状态
     */
    private void initButtonStatus(RadioButton selectorRbt){
        for (RadioButton radioButton:chooseDateRbtList) {
            radioButton.setSelected(false);
        }
        selectorRbt.setSelected(true);
    }

}
