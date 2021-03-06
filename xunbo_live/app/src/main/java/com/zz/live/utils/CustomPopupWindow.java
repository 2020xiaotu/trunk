package com.zz.live.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.google.gson.Gson;
import com.zz.live.R;
import com.zz.live.bean.Happy10OpenResultMedol;
import com.zz.live.bean.Happy8OpenResultMedol;
import com.zz.live.bean.KuaiSanTodayResultModel;
import com.zz.live.bean.MarksixLottery;
import com.zz.live.bean.PcddTodayResultModel;
import com.zz.live.bean.RaceLottery;
import com.zz.live.bean.SscTodayOpenMedol;
import com.zz.live.myView.MyCornerTextView;
import com.zz.live.net.api.HttpApiUtils;
import com.zz.live.ui.activity.start_live_activity.open_result_activity.Happy8OpentActivity;
import com.zz.live.ui.activity.start_live_activity.open_result_activity.KuaiSanOpenActivity;
import com.zz.live.ui.activity.start_live_activity.open_result_activity.LuckFarmOpenActivity;
import com.zz.live.ui.activity.start_live_activity.open_result_activity.PcDanOpenActivity;
import com.zz.live.ui.activity.start_live_activity.open_result_activity.RaceOpenActivity;
import com.zz.live.ui.activity.start_live_activity.open_result_activity.SixOpenActivity;
import com.zz.live.ui.activity.start_live_activity.open_result_activity.SscOpenActivity;
import com.zz.live.ui.adapter.Happy10OPenResultAdapter;
import com.zz.live.ui.adapter.Happy8OPenResultAdapter;
import com.zz.live.ui.adapter.KuaiSanOpenResultAdapter;
import com.zz.live.ui.adapter.MarksixOpenresultAdapter;
import com.zz.live.ui.adapter.PcddOpentTodayResultAdapter;
import com.zz.live.ui.adapter.RaceOpenresultAdapter;
import com.zz.live.ui.adapter.SscTodayOpenAdapter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Headers;

/**
 *
 */
public class CustomPopupWindow {
   /*
   ????????????pop
    */
    public PopupWindow menuPop;
    public TextView betNote;//??????????????? "????????????"
    public TextView openResult;//??????????????? "????????????"
    public TextView gameRule;//??????????????? "????????????"
    public TextView twoCahngLongTv;//??????????????? "????????????"
    public TextView investTv;//??????????????? "????????????"
    public TextView mineCenterTv;//??????????????? "????????????"
    public TextView todayWinLoseTv;//??????????????? "????????????"
    public TextView onlineKf;//???????????????"????????????"

    /*
    ???????????????????????????????????????pop
     */

    public PopupWindow kuaiSanTodayOpenResultPop;
    public MyCornerTextView kuaiSanshowMore; //??????????????????
    public RecyclerView todayResultRecy;//recycleView
    private KuaiSanOpenResultAdapter kuaiSanKuaiSanOpenResultAdapter;//?????????
    private ArrayList<KuaiSanTodayResultModel> kuaiSanTodayResultModelArrayList =new ArrayList<>();//recycleView????????????
    private ConstraintLayout kuaisanLoadIngLinear;

/*        *//*
    ??????????????????????????????????????????pop
     *//*

    public  PopupWindow sixTodayOpenResultPop;
    public  MyCornerTextView sixshowMore; //??????????????????
    public  RecyclerView sixTodayResultRecy;//recycleView
    private  KuaiSanOpenResultAdapter sixKuaiSanOpenResultAdapter;//?????????
    private  ArrayList<KuaiSanTodayResultModel> sixTodayResultModelArrayList =new ArrayList<>();//recycleView????????????*/


        /*
    pcdd?????????????????????????????????pop
     */

    public PopupWindow pcddTodayOpenResultPop;
    public  MyCornerTextView pcddshowMore; //??????????????????
    public RecyclerView pcddtodayResultRecy;//recycleView
    private ConstraintLayout pcddLoadingLinear;
    private PcddOpentTodayResultAdapter pcddOpenResultAdapter;//?????????
    private ArrayList<PcddTodayResultModel> pcddTodayResultModelArrayList =new ArrayList<>();//recycleView????????????

    /*
    ????????????8?????????????????????????????????pop
     */

    public PopupWindow bjTodayOpenResultPop;
    public  MyCornerTextView bjshowMore; //??????????????????
    public RecyclerView bjtodayResultRecy;//recycleView
    private ConstraintLayout bjLoadingLinear;
    private Happy8OPenResultAdapter happy8OPenResultAdapter;//?????????
    private ArrayList<Happy8OpenResultMedol> bjTodayResultModelArrayList =new ArrayList<>();//recycleView????????????
    /*
    ????????????10?????????????????????????????????pop
     */

    public PopupWindow happy10TodayOpenResultPop;
    public  MyCornerTextView happy10showMore; //??????????????????
    public RecyclerView happy10todayResultRecy;//recycleView
    private ConstraintLayout happy10LoadingLinear;
    private Happy10OPenResultAdapter happy10OPenResultAdapter;//?????????
    private ArrayList<Happy10OpenResultMedol> happy10TodayResultModelArrayList =new ArrayList<>();//recycleView????????????


    /*
      ssc?????????????????????????????????pop
     */

    public PopupWindow sscTodayOpenResultPop;
    public  MyCornerTextView sscShowMore; //??????????????????
    public RecyclerView sscTodayResultRecy;//recycleView
    private ConstraintLayout sscLoadingLinear;
    private SscTodayOpenAdapter sscTodayOpenAdapter;//?????????
    private ArrayList<SscTodayOpenMedol> sscTodayOpenMedolArrayList =new ArrayList<>();//recycleView????????????

    public PopupWindow raceTodayOpenResultPop;
    public  MyCornerTextView raceShowMore; //??????????????????
    public RecyclerView raceTodayResultRecy;//recycleView
    private ConstraintLayout raceLoadingLinear;
    private RaceOpenresultAdapter raceOpenresultAdapter;//?????????
    private RaceLottery raceLottery;
    private List<RaceLottery.RaceLotteryBean> raceTodayOpenList =  new ArrayList<>();

    public PopupWindow marksixTodayOpenResultPop;
    public  MyCornerTextView marksixShowMore; //??????????????????
    public RecyclerView marksixTodayResultRecy;//recycleView
    private ConstraintLayout sixLoadingLinear;
    private MarksixOpenresultAdapter marksixOpenresultAdapter;//?????????
    private MarksixLottery marksixLottery;
    private List<MarksixLottery.marksixLotteryBean> marksixTodayOpenList =  new ArrayList<>();


    /*
    ???????????????????????????  "????????????" popwindow
     */
    public PopupWindow gameRulePop; //pop??????
    public TextView gameRuleCancel; //????????????
    public TextView gameRuleSure;  //????????????
    public TextView lotteryNameText; //?????????typename
    public TextView gameRuleTextView; //????????????????????????

    private static final int FULL_SCREEN_FLAG =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;



    /**
     * ????????????????????????????????????pop
     * @param contextWeakReference ?????????
//     * @param onMenuPopClickListener ?????????????????????
     */
    public  void initKuaiSanTodayResult(final WeakReference<Context> contextWeakReference/* Context context*//*,final CustomPopupWindow.OnMenuPopClickListener onMenuPopClickListener*/, final int type_id, boolean isLive){
        Context context = contextWeakReference.get();
        if(null==context){
            return;
        }
        Context applicationContext = context;
        View contentView = LayoutInflater.from(applicationContext).inflate(R.layout.kuaisan_today_open_result_popwindow, null);
        if(isLive){
            int i = Utils.intgetWinndowHeight((Activity) applicationContext);
            int height = (int) (i * 0.65);
            kuaiSanTodayOpenResultPop = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, height==0?1000:height, false);//?????????popupWindow

        }else {
            kuaiSanTodayOpenResultPop = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);//?????????popupWindow
        }
        setPopFlag(kuaiSanTodayOpenResultPop);
        kuaiSanTodayOpenResultPop.setAnimationStyle(R.style.down_to_up150);//??????????????????
        //recycleView??????
        todayResultRecy=contentView.findViewById(R.id.today_open_result_pop_recycle);
        kuaisanLoadIngLinear =contentView.findViewById(R.id.live_loading_linear);
        kuaiSanKuaiSanOpenResultAdapter =new KuaiSanOpenResultAdapter(kuaiSanTodayResultModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        todayResultRecy.setLayoutManager(linearLayoutManager);
        todayResultRecy.setAdapter(kuaiSanKuaiSanOpenResultAdapter);
        //recycleView??????

        //????????????pop???disMIss??????
        kuaiSanTodayOpenResultPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground((Activity) context,1f);//??????????????????
            }
        });

        kuaiSanshowMore=contentView.findViewById(R.id.show_more);//??????????????????
        kuaiSanshowMore.setfilColor(Color.parseColor("#f8a52a"));//??????????????????
        kuaiSanshowMore.setCornerSize(10);//????????????

        //?????? ???????????? ???????????????????????????activity
        kuaiSanshowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kuaiSanTodayOpenResultPop.dismiss();
                Intent intent = new Intent(context, KuaiSanOpenActivity.class);
                intent.putExtra("type_id", type_id);
                context.startActivity(intent);
            }
        });
    }

    private void setPopFlag(PopupWindow popupWindow) {
        popupWindow.setOutsideTouchable(true);
        popupWindow.getContentView().setSystemUiVisibility(FULL_SCREEN_FLAG);
        popupWindow.update();
    }

    /**
     * pcdd??????????????????????????????pop
     * @param context ?????????
    //     * @param onMenuPopClickListener ?????????????????????
     */
    public  void initPcddTodayResult(final Context context/*,final CustomPopupWindow.OnMenuPopClickListener onMenuPopClickListener*/, final int type_id, boolean isLive){

        View contentView = LayoutInflater.from(context).inflate(R.layout.pcdd_today_open_result_popwindow, null);
        if(isLive){
            int i = Utils.intgetWinndowHeight((Activity) context);
            int height = (int) (i * 0.65);
            pcddTodayOpenResultPop = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, height==0?1000:height, false);//?????????popupWindow

        }else {
            pcddTodayOpenResultPop = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);//?????????popupWindow
        }
        setPopFlag(pcddTodayOpenResultPop);
        pcddTodayOpenResultPop.setAnimationStyle(R.style.popAlphaanim0_3);//??????????????????
        //recycleView??????
        pcddtodayResultRecy=contentView.findViewById(R.id.pcdd_open_result_pop_recycle);
        pcddLoadingLinear=contentView.findViewById(R.id.live_loading_linear);
        pcddOpenResultAdapter =new PcddOpentTodayResultAdapter(pcddTodayResultModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        pcddtodayResultRecy.setLayoutManager(linearLayoutManager);
        pcddtodayResultRecy.setAdapter(pcddOpenResultAdapter);
        //recycleView??????

        //????????????pop???disMIss??????
        pcddTodayOpenResultPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground((Activity) context,1f);//??????????????????
            }
        });

        pcddshowMore=contentView.findViewById(R.id.show_more);//??????????????????
        pcddshowMore.setfilColor(Color.parseColor("#f8a52a"));//??????????????????
        pcddshowMore.setCornerSize(10);//????????????

        //?????? ???????????? ???????????????????????????activity
        pcddshowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pcddTodayOpenResultPop.dismiss();
                Intent intent = new Intent(context, PcDanOpenActivity.class);
                intent.putExtra("type_id", type_id);
                context.startActivity(intent);
            }
        });
    }

    /*
    ?????????????????????8 ????????????pop
     */
    public  void initHappy8TodayResult(final Context context/*,final CustomPopupWindow.OnMenuPopClickListener onMenuPopClickListener*/, final int type_id, boolean isLive){
        View contentView = LayoutInflater.from(context).inflate(R.layout.happy8_today_open_result_popwindow, null);
        if(isLive){
            int i = Utils.intgetWinndowHeight((Activity) context);
            int height = (int) (i * 0.65);
            bjTodayOpenResultPop = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, height==0?1000:height, false);//?????????popupWindow

        }else {
            bjTodayOpenResultPop = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);//?????????popupWindow
        }
        setPopFlag(bjTodayOpenResultPop);
        bjTodayOpenResultPop.setAnimationStyle(R.style.popAlphaanim0_3);//??????????????????
        //recycleView??????
        bjtodayResultRecy=contentView.findViewById(R.id.bj_today_open_result_pop_recycle);
        happy8OPenResultAdapter =new Happy8OPenResultAdapter(bjTodayResultModelArrayList);
        bjLoadingLinear=contentView.findViewById(R.id.live_loading_linear);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        bjtodayResultRecy.setLayoutManager(linearLayoutManager);
        bjtodayResultRecy.setAdapter(happy8OPenResultAdapter);
        //recycleView??????

        //????????????pop???disMIss??????
        bjTodayOpenResultPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground((Activity) context,1f);//??????????????????
            }
        });

        bjshowMore=contentView.findViewById(R.id.show_more);//??????????????????
        bjshowMore.setfilColor(Color.parseColor("#f8a52a"));//??????????????????
        bjshowMore.setCornerSize(10);//????????????
        //?????? ???????????? ???????????????????????????activity
        bjshowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bjTodayOpenResultPop.dismiss();
                Intent intent = new Intent(context, Happy8OpentActivity.class);
                intent.putExtra("type_id", type_id);
                context.startActivity(intent);
            }
        });
    }

    /*
   ?????????????????????10????????????pop
    */
    public  void initHappy10TodayResult(final Context context, final int game/*,final CustomPopupWindow.OnMenuPopClickListener onMenuPopClickListener*/, final int type_id, boolean isLive){
        View contentView = LayoutInflater.from(context).inflate(R.layout.happy10_today_open_result_popwindow, null);
        if(isLive){
            int i = Utils.intgetWinndowHeight((Activity) context);
            int height = (int) (i * 0.65);
            happy10TodayOpenResultPop = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, height==0?1000:height, false);//?????????popupWindow

        }else {
            happy10TodayOpenResultPop = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);//?????????popupWindow
        }
        setPopFlag(happy10TodayOpenResultPop);
        happy10TodayOpenResultPop.setAnimationStyle(R.style.popAlphaanim0_3);//??????????????????
        //recycleView??????
        happy10todayResultRecy=contentView.findViewById(R.id.happy10_today_open_result_pop_recycle);
        happy10LoadingLinear=contentView.findViewById(R.id.live_loading_linear);
        happy10OPenResultAdapter =new Happy10OPenResultAdapter(happy10TodayResultModelArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        happy10todayResultRecy.setLayoutManager(linearLayoutManager);
        happy10todayResultRecy.setAdapter(happy10OPenResultAdapter);
        //recycleView??????

        //????????????pop???disMIss??????
        happy10TodayOpenResultPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground((Activity) context,1f);//??????????????????
            }
        });

        happy10showMore=contentView.findViewById(R.id.show_more);//??????????????????
        happy10showMore.setfilColor(Color.parseColor("#f8a52a"));//??????????????????
        happy10showMore.setCornerSize(10);//????????????
        //?????? ???????????? ?????????????????????activity
        happy10showMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(context, LuckFarmOpenActivity.class);
                    intent.putExtra("type_id", type_id);
                    intent.putExtra("game", game);
                    context.startActivity(intent);
                happy10TodayOpenResultPop.dismiss();
            }
        });
    }
    /*
?????????????????? ????????????pop
 */
    public  void initSscTodayResultPop(final Context context, final  int game/*,final CustomPopupWindow.OnMenuPopClickListener onMenuPopClickListener*/, final int type_id, Boolean isLive){
        View contentView = LayoutInflater.from(context).inflate(R.layout.ssc_today_open_result_popwindow, null);
        if(isLive){
            int i = Utils.intgetWinndowHeight((Activity) context);
            int height = (int) (i * 0.65);
            sscTodayOpenResultPop = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, height==0?1000:height, false);//?????????popupWindow

        }else {
            sscTodayOpenResultPop = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);//?????????popupWindow
        }
        setPopFlag(sscTodayOpenResultPop);
//        sscTodayOpenResultPop = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);//?????????popupWindow
        sscTodayOpenResultPop.setAnimationStyle(R.style.popAlphaanim0_3);//??????????????????
        //recycleView??????
        sscTodayResultRecy=contentView.findViewById(R.id.ssc_today_open_result_pop_recycle);
        sscLoadingLinear=contentView.findViewById(R.id.live_loading_linear);
        sscTodayOpenAdapter =new SscTodayOpenAdapter(sscTodayOpenMedolArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        sscTodayResultRecy.setLayoutManager(linearLayoutManager);
        sscTodayResultRecy.setAdapter(sscTodayOpenAdapter);

        //????????????pop???disMIss??????
        sscTodayOpenResultPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground((Activity) context,1f);//??????????????????
            }
        });

        sscShowMore=contentView.findViewById(R.id.show_more);//??????????????????
        sscShowMore.setfilColor(Color.parseColor("#f8a52a"));//??????????????????
        sscShowMore.setCornerSize(10);//????????????
        //?????? ???????????? ?????????ssc????????????activity
        sscShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sscTodayOpenResultPop.dismiss();
                Intent intent = new Intent(context, SscOpenActivity.class);
                intent.putExtra("type_id", type_id);
                intent.putExtra("game", game);
                context.startActivity(intent);
            }
        });
    }
    public  void initRaceTodayResultPop(final Context context, final  int game, final int type_id, boolean isLive){
        View contentView = LayoutInflater.from(context).inflate(R.layout.race_today_open_result_popwindow, null);
        if(isLive){
            int i = Utils.intgetWinndowHeight((Activity) context);
            int height = (int) (i * 0.65);
            raceTodayOpenResultPop = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, height==0?1000:height, false);//?????????popupWindow
        }else {
            raceTodayOpenResultPop = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);//?????????popupWindow
        }
        setPopFlag(raceTodayOpenResultPop);
        raceTodayOpenResultPop.setAnimationStyle(R.style.popAlphaanim0_3);//??????????????????
        //recycleView??????
        raceTodayResultRecy=contentView.findViewById(R.id.race_pop_recycle);
        raceLoadingLinear =contentView.findViewById(R.id.live_loading_linear);
        if (raceOpenresultAdapter==null){
            raceOpenresultAdapter =new RaceOpenresultAdapter(context,raceTodayOpenList);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        raceTodayResultRecy.setLayoutManager(linearLayoutManager);
        raceTodayResultRecy.setAdapter(raceOpenresultAdapter);
        //recycleView??????

        //????????????pop???disMIss??????
        raceTodayOpenResultPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground((Activity) context,1f);//??????????????????
            }
        });

        raceShowMore=contentView.findViewById(R.id.race_pop_showmore);//??????????????????
        raceShowMore.setfilColor(Color.parseColor("#f8a52a"));//??????????????????
        raceShowMore.setCornerSize(10);//????????????
        //?????? ???????????? ?????????ssc????????????activity
        raceShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                raceTodayOpenResultPop.dismiss();
                Intent intent = new Intent(context, RaceOpenActivity.class);
                intent.putExtra("type_id", type_id);
                intent.putExtra("game", game);
                context.startActivity(intent);
            }
        });
    }
    public  void initMarksixTodayResultPop(final Context context, final  int game, final int type_id, boolean isLive){
        View contentView = LayoutInflater.from(context).inflate(R.layout.marksix_today_open_result_popwindow, null);
        if(isLive){
            int i = Utils.intgetWinndowHeight((Activity) context);
            int height = (int) (i * 0.65);
            marksixTodayOpenResultPop = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, height==0?1000:height, false);//?????????popupWindow
        }else {
            marksixTodayOpenResultPop = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, false);//?????????popupWindow
        }
        setPopFlag(marksixTodayOpenResultPop);
        marksixTodayOpenResultPop.setAnimationStyle(R.style.popAlphaanim0_3);//??????????????????
        //recycleView??????
        marksixTodayResultRecy=contentView.findViewById(R.id.marksix_pop_recycle);
        sixLoadingLinear=contentView.findViewById(R.id.live_loading_linear);
        if (marksixOpenresultAdapter==null){
            marksixOpenresultAdapter =new MarksixOpenresultAdapter(context,marksixTodayOpenList);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        marksixTodayResultRecy.setLayoutManager(linearLayoutManager);
        marksixTodayResultRecy.setAdapter(marksixOpenresultAdapter);
        //recycleView??????

        //????????????pop???disMIss??????
        marksixTodayOpenResultPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground((Activity) context,1f);//??????????????????
            }
        });

        marksixShowMore=contentView.findViewById(R.id.marksix_pop_showmore);//??????????????????
        marksixShowMore.setfilColor(Color.parseColor("#f8a52a"));//??????????????????
        marksixShowMore.setCornerSize(10);//????????????
        //?????? ???????????? ??????????????????????????????activity
        marksixShowMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marksixTodayOpenResultPop.dismiss();
                Intent intent = new Intent(context, SixOpenActivity.class);
                intent.putExtra("type_id", type_id);
                intent.putExtra("game", game);
                context.startActivity(intent);
            }
        });
    }

    /**
     * ?????????????????????"????????????", ???????????????????????????activity
     * @param context ?????????
     * @param type_id ??????type_id
     * @param game ???????????????id(?????????????????????,??????????????????????????????????????????)
     */
    public  void toOpenResult(final Context context, final int type_id, final  int game) {
        openResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if(game==3){//3 ?????????????????????
                    intent  = new Intent(context, RaceOpenActivity.class);
                }
                else if(game==1){//1 ?????????????????????
                    intent  = new Intent(context, KuaiSanOpenActivity.class);
                }
                else if(game==6){//????????????8
                    intent  = new Intent(context, Happy8OpentActivity.class);
                }else if(game==5){//pc??????
                    intent  = new Intent(context, PcDanOpenActivity.class);
                }/*else if(game==8){//??????10???
                    intent  = new Intent(context, Happy10OpentActivity.class);
                }*/else if(game==7||game==8){
                    intent  = new Intent(context, LuckFarmOpenActivity.class);
                }
                else if(game==2||game==9){//?????????  11???5
                    intent  = new Intent(context, SscOpenActivity.class);
                }else if(game==4){//?????????
                    intent  = new Intent(context, SixOpenActivity.class);
                }else {
//                    intent  = new Intent(context, XuanWuOpenActivity.class);
                }

                //???????????? ?????????
//                intent  = new Intent(context, KuaiSanOpenActivity.class);

                intent.putExtra("type_id",type_id);//?????????????????????activity???,?????????????????????????????????
                intent.putExtra("game",game);//?????????????????????activity???,?????????????????????????????????
                context.startActivity(intent);
                menuPop.dismiss();
            }
        });

    }

    /**
     * ????????????????????????pop
     * @param activity
     */
    public  void showGameRulePop(final Activity activity){
        gameRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameRulePop !=null){
                    menuPop.dismiss();
                    if(!activity.isFinishing()){
                        gameRulePop.showAtLocation(activity.getWindow().getDecorView(), Gravity.CENTER,0,0);
                    }
                    darkenBackground(activity,0.3f);
                }
            }
        });
    }

    
    /**
     * ??????????????????????????????pop???????????????
     * @param targetView pop???????????????(???targetView???????????????pop)
     * @param context ???????????????
     */
    public  void showKuaiSanTodayResultPop(View targetView, Context context) {
        if (kuaiSanTodayOpenResultPop != null) {
            kuaiSanTodayOpenResultPop.showAsDropDown(targetView, 0, 0, Gravity.BOTTOM);
            darkenBackground((Activity) context,0.3f);
        }
    }

    /**
     * ??????????????????8??????????????????pop???????????????
     * @param targetView pop???????????????(???targetView???????????????pop)
     * @param context ???????????????
     */
    public  void showHappy8TodayResultPop(View targetView, Context context) {
        if (bjTodayOpenResultPop != null) {
            bjTodayOpenResultPop.showAsDropDown(targetView, 0, 0, Gravity.BOTTOM);
            darkenBackground((Activity) context,0.3f);
        }
    }
    
    /**
     * ???????????????????????????????????????
     * @param type_id ??????type_id
     */
    public  void initKuaiSanTodayResultData(final Context context, final View targetView, final int type_id){
        if (kuaiSanTodayOpenResultPop != null) {
            if(null==targetView){
                Activity activity =(Activity)context;
                kuaiSanTodayOpenResultPop.showAtLocation(Utils.getContentView(activity), Gravity.BOTTOM, 0,0);
            }else {
                kuaiSanTodayOpenResultPop.showAsDropDown(targetView, 0, 0, Gravity.BOTTOM);
            }
            darkenBackground((Activity) context,0.3f);
        }
        kuaisanLoadIngLinear.setVisibility(View.VISIBLE);
        kuaiSanshowMore.setVisibility(View.GONE);
        todayResultRecy.setVisibility(View.GONE);
        HashMap<String, Object> data = new HashMap<>();
        data.put("type_id",type_id);
        data.put("pageNo",1);
        data.put("pageSize",20);
        data.put("flag",1);
        HttpApiUtils.cpNormalRequest((Activity) context, null, RequestUtils.REQUEST_8r, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                kuaisanLoadIngLinear.setVisibility(View.GONE);
                kuaiSanshowMore.setVisibility(View.VISIBLE);
                todayResultRecy.setVisibility(View.VISIBLE);
                kuaiSanTodayResultModelArrayList.clear();
                JSONObject jsonObject1 = JSONObject.parseObject(result);
                JSONArray gameLotterylist = jsonObject1.getJSONArray("gameLotterylist");
                for (int i = 0; i < gameLotterylist.size(); i++) {
                    JSONObject jsonObject = gameLotterylist.getJSONObject(i);
                    String typeqishu = jsonObject.getString("lotteryqishu");//??????
                    String lotteryNo = jsonObject.getString("lotteryNo");//????????????
                    String remark = jsonObject.getString("remark");//??????
                    String createdDate = jsonObject.getString("lotterytime");//??????
                    kuaiSanTodayResultModelArrayList.add(new KuaiSanTodayResultModel(typeqishu,lotteryNo,remark,createdDate));
                }
                kuaiSanKuaiSanOpenResultAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
                kuaisanLoadIngLinear.setVisibility(View.GONE);
                kuaiSanshowMore.setVisibility(View.VISIBLE);
                todayResultRecy.setVisibility(View.VISIBLE);
            }
        });
    }


    /**
     * ???????????????????????????????????????
     * @param type_id ??????type_id
     */
    public  void initPcddTodayResultData(final Context context, final View targetView, final int type_id){
        if (pcddTodayOpenResultPop != null) {
            if(null==targetView){
                Activity activity =(Activity)context;
                pcddTodayOpenResultPop.showAtLocation(Utils.getContentView(activity), Gravity.BOTTOM, 0,0);
            }else {

                pcddTodayOpenResultPop.showAsDropDown(targetView, 0, 0, Gravity.BOTTOM);
            }
            darkenBackground((Activity) context,0.3f);
        }
        pcddLoadingLinear.setVisibility(View.VISIBLE);
        pcddshowMore.setVisibility(View.GONE);
        pcddtodayResultRecy.setVisibility(View.GONE);
        HashMap<String, Object> data = new HashMap<>();
        data.put("type_id",type_id);
        data.put("pageNo",1);
        data.put("pageSize",20);
        data.put("flag",1);
        HttpApiUtils.cpNormalRequest((Activity) context, null, RequestUtils.REQUEST_05dd, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                pcddTodayResultModelArrayList.clear();
                pcddLoadingLinear.setVisibility(View.GONE);
                pcddshowMore.setVisibility(View.VISIBLE);
                pcddtodayResultRecy.setVisibility(View.VISIBLE);
                JSONObject jsonObject1 = JSONObject.parseObject(result);
                JSONArray danLotterylist = jsonObject1.getJSONArray("danLotterylist");
                for (int i = 0; i < danLotterylist.size(); i++) {
                    JSONObject jsonObject = danLotterylist.getJSONObject(i);
                    String typeqishu = jsonObject.getString("lotteryqishu");//??????
                    String lotterytime = jsonObject.getString("lotterytime");//??????
                    String lotteryNo = jsonObject.getString("lotteryNo");//????????????
                    String sum = jsonObject.getString("sum");//??????
                    String markdx = jsonObject.getString("markdx");//??????
                    String markds = jsonObject.getString("markds");//??????
                    pcddTodayResultModelArrayList.add(new PcddTodayResultModel(typeqishu,lotterytime,lotteryNo,sum,markdx,markds));
                }
                pcddOpenResultAdapter.notifyDataSetChanged();
                if (pcddTodayOpenResultPop != null) {
                    if(null==targetView){
                        Activity activity =(Activity)context;
                        pcddTodayOpenResultPop.showAtLocation(Utils.getContentView(activity), 0, 0, Gravity.BOTTOM);
                    }else {

                        pcddTodayOpenResultPop.showAsDropDown(targetView, 0, 0, Gravity.BOTTOM);
                    }
                    darkenBackground((Activity) context,0.3f);
                }
            }

            @Override
            public void onFail(String msg) {
                pcddLoadingLinear.setVisibility(View.GONE);
                pcddshowMore.setVisibility(View.VISIBLE);
                pcddtodayResultRecy.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * ??????????????????8???????????????????????????
     * @param type_id ??????type_id
     */
    public  void initHappy8TodayResultData(final Context context, final LinearLayout targetView, final int type_id){
        if (bjTodayOpenResultPop != null) {
            if(null==targetView){
                Activity activity =(Activity)context;
                bjTodayOpenResultPop.showAtLocation(Utils.getContentView(activity), Gravity.BOTTOM, 0,0);
            }else {

                bjTodayOpenResultPop.showAsDropDown(targetView, 0, 0, Gravity.BOTTOM);
            }
            darkenBackground((Activity) context,0.3f);
        }
        bjLoadingLinear.setVisibility(View.VISIBLE);
        bjshowMore.setVisibility(View.GONE);
        bjtodayResultRecy.setVisibility(View.GONE);
        HashMap<String, Object> data = new HashMap<>();
        data.put("type_id",type_id);
        data.put("pageNo",1);
        data.put("pageSize",20);
        data.put("flag",1);
        HttpApiUtils.cpNormalRequest((Activity) context, null, RequestUtils.REQUEST_04ha, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                bjTodayResultModelArrayList.clear();
                bjLoadingLinear.setVisibility(View.GONE);
                bjshowMore.setVisibility(View.VISIBLE);
                bjtodayResultRecy.setVisibility(View.VISIBLE);
                JSONObject jsonObject1 = JSONObject.parseObject(result);
                JSONArray happyLotterylist = jsonObject1.getJSONArray("happyLotterylist");
                for (int i = 0; i < happyLotterylist.size(); i++) {
                    JSONObject jsonObject = happyLotterylist.getJSONObject(i);
                    String lotteryqishu = jsonObject.getString("lotteryqishu");//??????
                    String lotteryNo = jsonObject.getString("lotteryNo");//????????????
                    String sum = jsonObject.getString("sum");//??????
                    String lotterytime = jsonObject.getString("lotterytime");//??????
                    bjTodayResultModelArrayList.add(new Happy8OpenResultMedol(lotteryqishu,lotterytime,lotteryNo,false,sum,"","","","","",""));
                }
                happy8OPenResultAdapter .notifyDataSetChanged();
                if (bjTodayOpenResultPop != null) {
                    if(null==targetView){
                        Activity activity =(Activity)context;
                        bjTodayOpenResultPop.showAtLocation(Utils.getContentView(activity), 0, 0, Gravity.BOTTOM);
                    }else {

                        bjTodayOpenResultPop.showAsDropDown(targetView, 0, 0, Gravity.BOTTOM);
                    }
                    darkenBackground((Activity) context,0.3f);
                }
            }

            @Override
            public void onFail(String msg) {
                bjLoadingLinear.setVisibility(View.GONE);
                bjshowMore.setVisibility(View.VISIBLE);
                bjtodayResultRecy.setVisibility(View.VISIBLE);
            }
        });

    }

    /**
     * ??????g????????????10???????????????????????????
     * @param type_id ??????type_id
     */
    public  void initHappy10TodayResultData(final Context context, final View targetView, final int type_id){
        if (happy10TodayOpenResultPop != null) {
            if(null==targetView){
                Activity activity =(Activity)context;
                happy10TodayOpenResultPop.showAtLocation(Utils.getContentView(activity), Gravity.BOTTOM, 0,0);
            }else {

                happy10TodayOpenResultPop.showAsDropDown(targetView, 0, 0, Gravity.BOTTOM);
            }
            darkenBackground((Activity) context,0.3f);
        }
        happy10LoadingLinear.setVisibility(View.VISIBLE);
        happy10showMore.setVisibility(View.GONE);
        happy10todayResultRecy.setVisibility(View.GONE);
        HashMap<String, Object> data = new HashMap<>();
        data.put("type_id",type_id);
        data.put("pageNo",1);
        data.put("pageSize",20);
        data.put("flag",1);
        HttpApiUtils.cpNormalRequest((Activity) context, null, RequestUtils.REQUEST_04happyten, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                happy10TodayResultModelArrayList.clear();
                happy10LoadingLinear.setVisibility(View.GONE);
                happy10showMore.setVisibility(View.VISIBLE);
                happy10todayResultRecy.setVisibility(View.VISIBLE);
                JSONObject jsonObject1 = JSONObject.parseObject(result);
                JSONArray happytenLotterylist = jsonObject1.getJSONArray("happytenLotterylist");
                for (int i = 0; i < happytenLotterylist.size(); i++) {
                    JSONObject jsonObject = happytenLotterylist.getJSONObject(i);
                    String lotteryqishu = jsonObject.getString("lotteryqishu");//??????
                    String lotteryNo = jsonObject.getString("lotteryNo");//????????????
                    String sum = jsonObject.getString("sum");//??????
                    String lotterytime = jsonObject.getString("lotterytime");//??????
                    String markdx = jsonObject.getString("markdx");//????????????
                    String markds = jsonObject.getString("markds");//????????????
                    happy10TodayResultModelArrayList.add(new Happy10OpenResultMedol(lotteryqishu,lotterytime,lotteryNo,sum,markdx,markds));
                }
                happy10OPenResultAdapter .notifyDataSetChanged();
                if (happy10TodayOpenResultPop != null) {
                    if(null==targetView){
                        Activity activity =(Activity)context;
                        happy10TodayOpenResultPop.showAtLocation(Utils.getContentView(activity), 0, 0, Gravity.BOTTOM);
                    }else{

                        happy10TodayOpenResultPop.showAsDropDown(targetView, 0, 0, Gravity.BOTTOM);
                    }
                    darkenBackground((Activity) context,0.3f);
                }
            }

            @Override
            public void onFail(String msg) {
                happy10LoadingLinear.setVisibility(View.GONE);
                happy10showMore.setVisibility(View.VISIBLE);
                happy10todayResultRecy.setVisibility(View.VISIBLE);
            }
        });

    }

    /**
     * ???????????????????????????????????????????????????
     * @param type_id ??????type_id
     */
    public  void initfarmTodayResultData(final Context context, final View targetView, final int type_id){
        HashMap<String, Object> data = new HashMap<>();
        data.put("type_id",type_id);
        data.put("pageNo",1);
        data.put("pageSize",20);
        data.put("flag",1);
        HttpApiUtils.cpNormalRequest((Activity) context, null, RequestUtils.REQUEST_04farm, data, new HttpApiUtils.OnRequestLintener() {

            @Override
            public void onSuccess(String result) {
                happy10TodayResultModelArrayList.clear();
                JSONObject jsonObject1 = JSONObject.parseObject(result);
                JSONArray happytenLotterylist = jsonObject1.getJSONArray("farmLotterylist");
                for (int i = 0; i < happytenLotterylist.size(); i++) {
                    JSONObject jsonObject = happytenLotterylist.getJSONObject(i);
                    String lotteryqishu = jsonObject.getString("lotteryqishu");//??????
                    String lotteryNo = jsonObject.getString("lotteryNo");//????????????
                    String sum = jsonObject.getString("sum");//??????
                    String lotterytime = jsonObject.getString("lotterytime");//??????
                    String markdx = jsonObject.getString("markdx");//????????????
                    String markds = jsonObject.getString("markds");//????????????
                    happy10TodayResultModelArrayList.add(new Happy10OpenResultMedol(lotteryqishu,lotterytime,lotteryNo,sum,markdx,markds));
                }
                happy10OPenResultAdapter .notifyDataSetChanged();
                if (happy10TodayOpenResultPop != null) {
                    happy10TodayOpenResultPop.showAsDropDown(targetView, 0, 0, Gravity.BOTTOM);
                    darkenBackground((Activity) context,0.3f);
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });

    }


    /**
     * ?????? ????????????????????? ?????????pop
     * @param context   ?????????
     * @param targetView ????????????????????????
     * @param type_id  type_id
     */
    public  void initSscTodayResultData(final Context context, final int game, final int type_id , final View targetView){
        if (sscTodayOpenResultPop != null) {
            if(null==targetView){
                Activity activity =(Activity)context;
                sscTodayOpenResultPop.showAtLocation(Utils.getContentView(activity), Gravity.BOTTOM, 0,0);
            }else {
                sscTodayOpenResultPop.showAsDropDown(targetView, 0, 0, Gravity.BOTTOM);
            }
            darkenBackground((Activity) context,0.3f);
        }
        sscLoadingLinear.setVisibility(View.VISIBLE);
        sscShowMore.setVisibility(View.GONE);
        sscTodayResultRecy.setVisibility(View.GONE);
        HashMap<String, Object> data = new HashMap<>();
        data.put("type_id",type_id);
        data.put("pageNo",1);
        data.put("pageSize",20);
        data.put("flag",1);
        String url="";
        if(game==9){
            url=RequestUtils.REQUEST_04xuanwu;
        }else{
            url=RequestUtils.REQUEST_19r;
        }
        HttpApiUtils.cpNormalRequest((Activity) context, null, url, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                sscTodayOpenMedolArrayList.clear();
                sscShowMore.setVisibility(View.VISIBLE);
                sscLoadingLinear.setVisibility(View.GONE);
                sscTodayResultRecy.setVisibility(View.VISIBLE);
                JSONObject jsonObject1 = JSONObject.parseObject(result);
                JSONArray lotterylist=null;
                if(game==2){
                    lotterylist = jsonObject1.getJSONArray("sscaiLotterylist");
                }
                else {
                    lotterylist = jsonObject1.getJSONArray("xuanwuLotterylist");
                }
                for (int i = 0; i < lotterylist.size(); i++) {
                    JSONObject jsonObject = lotterylist.getJSONObject(i);
                    String lotteryqishu = jsonObject.getString("lotteryqishu");//??????
                    String lotteryNo = jsonObject.getString("lotteryNo");//????????????
                    String sum = jsonObject.getString("sum");//??????
                    String lotterytime = jsonObject.getString("lotterytime");//??????
                    String marklh = jsonObject.getString("marklh");//??????
                    String markdx = jsonObject.getString("markdx");//??????
                    String markds = jsonObject.getString("markds");//??????
                    SscTodayOpenMedol sscTodayOpenMedol = new SscTodayOpenMedol(lotteryqishu, lotteryNo, markdx, markds, marklh,lotterytime);
                    sscTodayOpenMedol.setGame(game);
                    sscTodayOpenMedolArrayList.add(sscTodayOpenMedol);
                }
                sscTodayOpenAdapter .notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
                sscLoadingLinear.setVisibility(View.GONE);
                sscShowMore.setVisibility(View.VISIBLE);
                sscTodayResultRecy.setVisibility(View.VISIBLE);
            }
        });
    }

    /**
     * ?????? ?????????????????? ?????????pop
     * @param context   ?????????
     * @param targetView ????????????????????????
     * @param type_id  type_id
     */
    public  void initRaceTodayResultData(final Context context, final int game, final int type_id , final View targetView ){
        if (raceTodayOpenResultPop != null) {
            if(null==targetView){
                Activity activity =(Activity)context;
                raceTodayOpenResultPop.showAtLocation(Utils.getContentView(activity), Gravity.BOTTOM, 0,0);
            }else {

                raceTodayOpenResultPop.showAsDropDown(targetView, 0, 0, Gravity.BOTTOM);
            }
            darkenBackground((Activity) context,0.3f);
        }
        raceLoadingLinear.setVisibility(View.VISIBLE);
        raceShowMore.setVisibility(View.GONE);
        raceTodayResultRecy.setVisibility(View.GONE);
        Activity activity=(Activity)context;
        HashMap<String, Object> data = new HashMap<>();
        data.put("type_id",type_id);
        data.put("pageNo",1);
        data.put("pageSize",20);
        data.put("flag",1);
        HttpApiUtils.cpNormalRequest((Activity) context, null, RequestUtils.REQUEST_26r, data, new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                raceTodayOpenList.clear();
                raceLoadingLinear.setVisibility(View.GONE);
                raceShowMore.setVisibility(View.VISIBLE);
                raceTodayResultRecy.setVisibility(View.VISIBLE);
                Gson gson = new Gson();

                raceLottery =    gson.fromJson(result,RaceLottery.class);
                List<RaceLottery.RaceLotteryBean> kjlist = new ArrayList<>();
                kjlist = raceLottery.getRaceLotterylist();
                raceTodayOpenList.addAll(kjlist);
                raceOpenresultAdapter .notifyDataSetChanged();

                if (raceTodayOpenResultPop != null) {

                    if(context!=null&&!activity.isFinishing()){
                        if(null==targetView){
                            Activity activity =(Activity)context;
                            raceTodayOpenResultPop.showAtLocation(Utils.getContentView(activity), 0, 0, Gravity.BOTTOM);
                        }else {

                            raceTodayOpenResultPop.showAsDropDown(targetView, 0, 0, Gravity.BOTTOM);
                        }
                        darkenBackground((Activity) context,0.3f);
                    }

                }
            }

            @Override
            public void onFail(String msg) {
                raceLoadingLinear.setVisibility(View.GONE);
                raceShowMore.setVisibility(View.VISIBLE);
                raceTodayResultRecy.setVisibility(View.VISIBLE);
            }
        });


    }

    /**
     * ?????? ????????????????????? ?????????pop
     * @param context   ?????????
     * @param targetView ????????????????????????
     * @param type_id  type_id
     */
    public  void initMarksixTodayResultData(final Context context, final int game, final int type_id , final View targetView){
        if (marksixTodayOpenResultPop != null) {
            if(null==targetView){
                Activity activity =(Activity)context;
                marksixTodayOpenResultPop.showAtLocation(Utils.getContentView(activity), Gravity.BOTTOM, 0,0);
            }else {

                marksixTodayOpenResultPop.showAsDropDown(targetView, 0, 0, Gravity.BOTTOM);
            }
            darkenBackground((Activity) context,0.3f);
        }
        sixLoadingLinear.setVisibility(View.VISIBLE);
        marksixShowMore.setVisibility(View.GONE);
        marksixTodayResultRecy.setVisibility(View.GONE);
        HashMap<String, Object> data = new HashMap<>();
        data.put("type_id",type_id);
        data.put("pageNo",1);
        data.put("pageSize",20);
        data.put("flag",1);
        HttpApiUtils.cpNormalRequest((Activity) context, null, RequestUtils.REQUEST_05lhc, data, new HttpApiUtils.OnRequestLintener() {
                    @Override
                    public void onSuccess(String result) {
                        marksixTodayOpenList.clear();
                        sixLoadingLinear.setVisibility(View.GONE);
                        marksixShowMore.setVisibility(View.VISIBLE);
                        marksixTodayResultRecy.setVisibility(View.VISIBLE);
                        Gson gson = new Gson();

                        marksixLottery =    gson.fromJson(result,MarksixLottery.class);
                        List<MarksixLottery.marksixLotteryBean> kjlist = new ArrayList<>();
                        kjlist = marksixLottery.getMarksixLotterylist();
                        marksixTodayOpenList.addAll(kjlist);
                        marksixOpenresultAdapter .notifyDataSetChanged();

                        if (marksixTodayOpenResultPop != null) {
                            marksixTodayOpenResultPop.showAsDropDown(targetView, 0, 0, Gravity.BOTTOM);
                            darkenBackground((Activity) context,0.3f);
                        }
                    }

                    @Override
                    public void onFail(String msg) {
                        sixLoadingLinear.setVisibility(View.GONE);
                        marksixShowMore.setVisibility(View.VISIBLE);
                        marksixTodayResultRecy.setVisibility(View.VISIBLE);
                    }
                });


    }

    /**
     * ??????????????????
     * @param activity activity??????
     * @param bgcolor ?????????(0f-1f)?????????,????????????
     */
    public  void darkenBackground(Activity activity, Float bgcolor) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgcolor;
        if(bgcolor==1f){
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        }
        activity.getWindow().setAttributes(lp);
    }

}
