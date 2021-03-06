package com.uyt.ying.yuan.uuuu.iuymn.mkjnb.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uyt.ying.yuan.R;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.activity.live.LiveFragment;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.activity.live.PackType;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.activity.live.RedStatus;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.activity.live.RedType;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.activity.mine_top_activity.RechargeActivity;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.eventBusModel.HbGameClassModel;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.mvvm.LiveFragmentViewModel;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.net.entity.RongcloudHBParameter;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.request.DockingUtil;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.request.MessageHead;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.request.RequestUtil;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.utils.CommonStr;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.utils.GlideLoadViewUtil;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.utils.MyApplication;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.utils.SharePreferencesUtil;
import com.uyt.ying.yuan.uuuu.iuymn.mkjnb.utils.Utils;

import org.jetbrains.annotations.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CusPackMorePop extends PopupWindow {

    private Activity mActivity;
    private Fragment mContext;
    private final View view;
    private TextView tv_qymore_unlock,tv_zx_searchmore_tisheng,tv_tj_searchmore_tisheng;
    private ConstraintLayout conslayout_qy, conslayout_zx, conslayout_tj;
    private LiveFragmentViewModel mViewModel;
    private TextView  qyMsg,tjMsg,zxMsg;
    private LiveFragment liveFragment;
    private   HbGameClassModel instance;
    private TextView zx_tip_tv;
    private    int pointGrade;
    private static  String UP_LEVEL="我要提升等级";
    private static  String GET_RED="立即领取";
    private int zxHBGrade;
    private int tjHBPlatGrade;

    public CusPackMorePop(Activity mActivity, Fragment mContext) {
        super(mActivity);
        this.mActivity = mActivity;
        this.mContext = mContext;
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.pop_pack_searchmore, null);
        mViewModel = ViewModelProviders.of(mContext).get(LiveFragmentViewModel.class);
        pointGrade = SharePreferencesUtil.getInt(MyApplication.getInstance(), CommonStr.GRADE, 0);
        initView();
        initPopWindow();
        if(mContext instanceof LiveFragment){
            liveFragment = (LiveFragment) mContext;
        }


    }

    private void initView() {
        tv_qymore_unlock = view.findViewById(R.id.tv_qymore_unlock);
        tv_zx_searchmore_tisheng = view.findViewById(R.id.tv_zx_searchmore_tisheng);
        tv_tj_searchmore_tisheng = view.findViewById(R.id.tv_tj_searchmore_tisheng);

        conslayout_qy = view.findViewById(R.id.conslayout_qy);
        conslayout_zx = view.findViewById(R.id.conslayout_zx);
        conslayout_tj = view.findViewById(R.id.conslayout_tj);
        qyMsg = conslayout_qy.findViewById(R.id.tv_qy_msg);
        tjMsg = conslayout_tj.findViewById(R.id.tv_tj_msg);
        zxMsg = conslayout_zx.findViewById(R.id.tv_zj_msg);
        zx_tip_tv=conslayout_zx.findViewById(R.id.zx_tip_tv);
//

        GlideLoadViewUtil.setBackGround(mContext.getContext(),Utils.getHomeLogo("liveIcon10"),conslayout_tj);
        //趣约红包点击跳充值
        tv_tj_searchmore_tisheng.setOnClickListener(v -> {
            mActivity.startActivity(new Intent(mActivity, RechargeActivity.class));
        });
        //专享红包点击 跳充值 或者开红包
        tv_zx_searchmore_tisheng.setOnClickListener(v->{
            String toString = tv_zx_searchmore_tisheng.getText().toString();
            if(toString.equals(UP_LEVEL)){
                mActivity.startActivity(new Intent(mActivity,RechargeActivity.class));
            }else {
                liveFragment.OpenPackPop("", RedStatus.NORMAL, RedType.ZX, liveFragment.roomId);
                CusPackMorePop.this.dismiss();
            }
        });


        /**
         * 观察 activityRank 数据
         */
            instance = HbGameClassModel.getInstance();
            RongcloudHBParameter hbParameter = instance.getHbParameter();
            initAllTypeView(hbParameter);
    }

    private void initPopWindow() {
        this.setContentView(view);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setAnimationStyle(R.style.popAlphaanim0_5);
        ColorDrawable dw = new ColorDrawable(0x00FFFFFF);
        //设置弹出窗体的背景
        this.setBackgroundDrawable(dw);
        backgroundAlpha(mActivity, 0.5f);//0.0-1.0


        this.setOnDismissListener(() -> {
            backgroundAlpha(mActivity, 1f);
        });
    }

    private void initAllTypeView(RongcloudHBParameter rongcloudHBParameter){
        if(rongcloudHBParameter == null){
            return;
        }
        RongcloudHBParameter.RongcloudHBParameterBean RongcloudHBParameterBean = rongcloudHBParameter.getRongcloudHBParameter();
        int scoreByOnePerson = RongcloudHBParameterBean.getScoreByOnePerson();
        int quYueHBMinAmount = RongcloudHBParameterBean.getQuYueHBMinAmount();
        int quYueHBMaxAmount = RongcloudHBParameterBean.getQuYueHBMaxAmount();
        int quYueHBScore = RongcloudHBParameterBean.getQuYueHBScore();
        int quYueHBGrade = RongcloudHBParameterBean.getQuYueHBGrade();
        int times = scoreByOnePerson == 0 ? 0 : quYueHBScore / scoreByOnePerson;
        int quYueHBTotalAmount = RongcloudHBParameterBean.getQuYueHBTotalAmount();
        RongcloudHBParameter.RongcloudHBParameterBean.TjHBPersonPropertyBean tjHBPersonProperty = RongcloudHBParameterBean.getTjHBPersonProperty();
        int randomFlag = tjHBPersonProperty.getRandomFlag();
        int todayCz = tjHBPersonProperty.getTodayCz();
        int todayDm = tjHBPersonProperty.getTodayDm();
        int todaySl = tjHBPersonProperty.getTodaySl();
        int yesterdayCz = tjHBPersonProperty.getYesterdayCz();
        int yesterdayDm = tjHBPersonProperty.getYesterdayDm();
        int yesterdaySl = tjHBPersonProperty.getYesterdaySl();

        ArrayList<Map<String,Object>> list = new ArrayList<>();
        list.add(initContentMap(Utils.getString(R.string.zuorichongzh),yesterdayCz));
        list.add(initContentMap(Utils.getString(R.string.zuoridamaliamg),yesterdayDm));
        list.add(initContentMap(Utils.getString(R.string.zuorisonglijine),yesterdaySl));
        list.add(initContentMap(Utils.getString(R.string.jinrichongzhi),todayCz));
        list.add(initContentMap(Utils.getString(R.string.jinridamaliang),todayDm));
        list.add(initContentMap(Utils.getString(R.string.jinrisonglijine),todaySl));

        qyMsg.setText("1.每邀请1人成功注册，可获得" + scoreByOnePerson + "积分，" + scoreByOnePerson + "积分可抽奖一次趣约红包，每包金额" + quYueHBMinAmount + "-" + quYueHBMaxAmount + "元。\n 2.必须积分达到" + quYueHBScore + "积分，等级达到VIP" + quYueHBGrade + "，才可以解锁趣约红包功能，解锁后可以连续抢" + times + "次以上红包。\n 3.抢到红包的金额高达100%，最高可抽" + quYueHBTotalAmount + "元。\n 4.此活动为限制活动，每人只能参与一次。相同IP地址、相同设备码均视为同一个人注册，如未真实注册，重复注册，等非法行为均会取消活动资格。");

        zxHBGrade = RongcloudHBParameterBean.getZxHBGrade();
        int zxHBScore = RongcloudHBParameterBean.getZxHBScore();
        double zxHBRate = RongcloudHBParameterBean.getZxHBRate();
        zxMsg.setText("1.只要您等级达到VIP" + zxHBGrade + Utils.getString(R.string.touzhuxiandingwanfa)+zxHBRate+"%专享回馈红包\n 2.达到VIP" + zxHBGrade + "的会员请第二天晚上12点前登录领取，过期作废，没有领取的不累计。\n 3.此活动随时可能取消，千万回馈红包送完为止。\n 4.此活动只有投注限定玩法才能享受"+zxHBRate+"%专享红包。\n 5.没有达到等级的请抓紧时间提升自己的等级。");

        int tjHBGrade = RongcloudHBParameterBean.getTjHBGrade();
         tjHBPlatGrade = RongcloudHBParameterBean.getTjHBPlatGrade();
        String test = "1.VIP" + tjHBGrade + Utils.getString(R.string.keqiangputonghongbao)+"\n 2.VIP"
                + tjHBPlatGrade + Utils.getString(R.string.keqiangguanfanghpngbao)+"\n";
        if(randomFlag==0){
            test = initTjMsg(list, test);
        }
        tjMsg.setText(test);

    }

    @NotNull
    private String initTjMsg(ArrayList<Map<String, Object>> list, String text) {
        for (int i = 0; i < list.size() ; i++) {
            Map<String, Object> map = list.get(i);
            Integer condition = (Integer) map.get("condition");
            if(condition>0){
                String content= (String) map.get("content");
                if(content.contains("昨日")){
                    if(!text.contains("A")){
                        text+=String.format("\n 参与条件\n \n A.等级达到VIP%d，",tjHBPlatGrade);
                    }
                }else if(content.contains("今日")){
                    if(!text.contains("B")){
                        if(text.contains("昨日")){
                            if(!text.contains("参与条件")){
                                    text+=String.format(",即可参与!\n 参与条件\n \n B.等级达到VIP%d，",tjHBPlatGrade);
                            }else {
                                text+=String.format(",即可参与!\n B.等级达到VIP%d，",tjHBPlatGrade);
                            }
                        }else {
                            if(!text.contains("参与条件")){
                                text+=String.format("\n参与条件\n \n B.等级达到VIP%d，",tjHBPlatGrade);
                            }else {
                                text+=String.format("\n B.等级达到VIP%d，",tjHBPlatGrade);
                            }
                        }
                    }
                }
                text+=content;
            }
        }
        if(list.size()!=0){
            text+=",即可参与!";
        }

        return text;
    }

    @NotNull
    private HashMap<String, Object> initContentMap(String content,int condition) {
        HashMap<String, Object> data = new HashMap<>();
        data.put("condition",condition);
        data.put("content",String.format("%s大于%d%s%s",content,condition,"元",","));
        return data;
    }

    public void showType(PackType packType){
        switch (packType){
            case QY:
                conslayout_qy.setVisibility(View.VISIBLE);
                conslayout_zx.setVisibility(View.GONE);
                conslayout_tj.setVisibility(View.GONE);
                break;
            case TJ:
                conslayout_tj.setVisibility(View.VISIBLE);
                conslayout_qy.setVisibility(View.GONE);
                conslayout_zx.setVisibility(View.GONE);
                break;
            case ZX:
                conslayout_zx.setVisibility(View.VISIBLE);
                conslayout_qy.setVisibility(View.GONE);
                conslayout_tj.setVisibility(View.GONE);
                break;
        }

                switch (packType){
                    case TJ:
                        tv_tj_searchmore_tisheng.setText(UP_LEVEL);
                        break;
                    case ZX:
                            if(pointGrade==9){
                                zx_tip_tv.setText("您已达到最高等级VIP9,已达到要求等级");
                                tv_zx_searchmore_tisheng.setText(GET_RED);
                            }else {
                            getMoneyInfo();

                    }

                        break;
                        default:
                     case QY:
                            break;
                }
    }

    /*
    获得用户资金信息
     */
    private void getMoneyInfo() {
        HashMap<String, Object> personMoney = new HashMap<>();//REQUEST_06rzq
        Utils.docking(personMoney, RequestUtil.REQUEST_06rzq, 1, new DockingUtil.ILoaderListener() {
            @Override
            public void success(String content) {
                JSONObject jsonObject = JSONObject.parseObject(content);
                //成长积分
                BigDecimal growthIntegral = jsonObject.getJSONObject("memberMoney").getBigDecimal("growthIntegral");
                getDengji(growthIntegral);
            }

            @Override
            public void failed(MessageHead messageHead) {

            }
        });
    }
    private void getDengji( BigDecimal growthIntegral ) {
//        ErrorUtil.hideErrorLayout(recyclerView,errorLinear);

        Map<String, Object> dataMoneny = new HashMap<>();
        Utils.docking(dataMoneny, RequestUtil.REQUEST_08rzq, 1, new DockingUtil.ILoaderListener() {
            @Override
            public void success(String content) {
                JSONObject jsonObject = JSONObject.parseObject(content);
                Integer minePointGrade = jsonObject.getInteger(CommonStr.GRADE);
                JSONArray memberGradeMechanismList = jsonObject.getJSONArray("memberGradeMechanismList");
                JSONObject membeyGradeObject=null;
                for (int i=0;i<memberGradeMechanismList.size();i++){
                    JSONObject jsonObject1 = memberGradeMechanismList.getJSONObject(i);
                    int grade = jsonObject1.getInteger("grade");//vip等级
                    if((grade+1+"").equals(zxHBGrade+"")){
                        membeyGradeObject = jsonObject1;
                        break;
                    }
                }
                if(membeyGradeObject!=null){
                    if(minePointGrade+1 <zxHBGrade){//没达到领取要求
                        BigDecimal needCharge = membeyGradeObject.getBigDecimal("growthIntegral").subtract(growthIntegral);
                        zx_tip_tv.setText("您已充值"+growthIntegral+"元,距离VIP"+zxHBGrade+"还需充值"+needCharge+"元");
                        tv_zx_searchmore_tisheng.setText(UP_LEVEL);
                    }else {
                        zx_tip_tv.setText("您当前等级VIP"+ (minePointGrade+1) +"已达到要求等级");
                        tv_zx_searchmore_tisheng.setText(GET_RED);
                    }
                }

          /*      else {
                    zx_tip_tv.setText("您当前等级VIP"+pointGrade+",可以领取红包了");
                }*/
            }
            @Override
            public void failed(MessageHead messageHead) {
            }
        });
    }







    /**
     * 设置添加屏幕的背景透明度(值越大,透明度越高)
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1f) {
            //恢复屏幕亮度时需要移除flag,否则在切换activity时会有短暂黑屏
            context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        } else {
            context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
        context.getWindow().setAttributes(lp);
    }
}
