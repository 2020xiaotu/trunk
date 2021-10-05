package com.example.red_pakege.activity.add_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.red_pakege.R;
import com.example.red_pakege.activity.agent_center_activity.ChildPlayerActivity;
import com.example.red_pakege.activity.agent_center_activity.MineReportActivity;
import com.example.red_pakege.activity.message_fragment_activity.OnLineKefuActivity;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.Utils;

public class HbAgentCenterActivity extends BaseActivity implements View.OnClickListener {
    /*
    toolBar相关
     */
        private Toolbar toolbar;
        private ImageView actionBarRightIv;
        private ImageView actionBarLeftIv;
        private TextView actionTitleTv;
 //---------------------------------------------------------------------
        private ImageView topBigIv;

        private TextView copyTv;

        private TextView shareCodeTv;

        private LinearLayout applyAgentLinear;
        private LinearLayout shareMakeMoneyLinear;
        private LinearLayout agentRuleLinear;
        private LinearLayout promoteTutorialLinear;
        private LinearLayout childPlayerLinear;
        private LinearLayout mineReportLinear;

        //申请代理pop
        private PopupWindow applyAgentPop;
        private TextView applySureTv;
        private TextView applyCancelTv;
        //提示pop(未达到代理申请条件时弹出)
        private PopupWindow tipPop;
        private TextView tipContentTv;
        private TextView tipSureBtn;
        private ImageView tipDismissIv;
        private boolean canApplyAgent;

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        bindView();
        ActionBarUtil.initMainActionbar(this,toolbar);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_hb_agent_center;
    }

    @Override
    protected void initClick() {
        super.initClick();
        copyTv.setOnClickListener(this);
        actionBarRightIv.setOnClickListener(this);
        actionBarLeftIv.setOnClickListener(this);
        applyAgentLinear.setOnClickListener(this);
        shareMakeMoneyLinear.setOnClickListener(this);
        agentRuleLinear.setOnClickListener(this);
        promoteTutorialLinear.setOnClickListener(this);
        childPlayerLinear.setOnClickListener(this);
        mineReportLinear.setOnClickListener(this);
    }

    private void bindView() {
        toolbar=findViewById(R.id.message_fragent_toolbar);
        actionBarRightIv=findViewById(R.id.action_right_iv);
        actionBarRightIv.setImageResource(R.drawable.kefu);
        topBigIv=findViewById(R.id.agent_center_bg_img);
        actionTitleTv=findViewById(R.id.actionBar_title);
        actionBarLeftIv=findViewById(R.id.action_left_iv);
        actionBarLeftIv.setImageResource(R.drawable.icon_back);
        copyTv=findViewById(R.id.copy_tv);
        shareCodeTv=findViewById(R.id.shareCode_url_tv);
        actionTitleTv.setText("代理中心");
        applyAgentLinear=findViewById(R.id.apply_agent_linear);
        shareMakeMoneyLinear=findViewById(R.id.share_make_money_linear);
        agentRuleLinear=findViewById(R.id.agent_rule_linear);
        promoteTutorialLinear=findViewById(R.id.promote_tutorial_linear);
        childPlayerLinear=findViewById(R.id.child_player_linear);
        mineReportLinear=findViewById(R.id.mine_report_linear);


        //顶部广告图
        Glide.with(this)
//                .load("http://pic27.nipic.com/20130313/9252150_092049419327_2.jpg")
                .load("http://img01.3dmgame.com/uploads/allimg/130417/153_130417091610_1.jpg")
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(12)))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(topBigIv);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.action_left_iv:
                finish();
                break;
            case R.id.action_right_iv:
                startActivity(new Intent(HbAgentCenterActivity.this, OnLineKefuActivity.class));
                break;
            case R.id.copy_tv:
                // 使用application进行注册, 避免内存泄漏
                ClipboardManager clipboardManagerUrl= (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);//实例化clipboardManager对象
                ClipData mClipDataUrl=  ClipData.newPlainText("inviteCodeUrl", shareCodeTv.getText().toString());//复制文本数据到粘贴板  newPlainText
                clipboardManagerUrl.setPrimaryClip(mClipDataUrl);
                Toast.makeText(HbAgentCenterActivity.this,"已复制到粘贴板",Toast.LENGTH_SHORT).show();
                break;
            case R.id.apply_agent_linear:
                //TODO 跳转申请代理
                if(applyAgentPop==null){
                    initApplyAgentPop();
                }
                applyAgentPop.showAtLocation(copyTv, Gravity.CENTER,0,0);
                Utils.darkenBackground(HbAgentCenterActivity.this,0.7f);
                break;
            case R.id.share_make_money_linear:
                // TODO 跳转分享赚钱

                break;
            case R.id.agent_rule_linear:
                //TODO 跳转代理规则

                break;
            case R.id.promote_tutorial_linear:
                //TODO 跳转推广教程

                break;
            case R.id.child_player_linear:
                //TODO 跳转下级玩家
                startActivity(new Intent(HbAgentCenterActivity.this, ChildPlayerActivity.class));
                break;
            case R.id.mine_report_linear:
                //TODO 跳转我的报表
                    startActivity(new Intent(HbAgentCenterActivity.this, MineReportActivity.class));
                break;
            case R.id.apply_agent_cancel_tv:
                applyAgentPop.dismiss();
                break;
            case R.id.apply_agent_sure_tv:
            //TODO 查询用户是否具备申请代理的资格
                applyAgentPop.dismiss();
                if(!canApplyAgent){
                    if(tipPop==null){

                        initTipPop();
                    }
                }

                tipPop.showAtLocation(copyTv,Gravity.CENTER,0,0);
                Utils.darkenBackground(HbAgentCenterActivity.this,0.7f);
                break;
            case R.id.tip_pop_dismiss_iv:
                tipPop.dismiss();
                break;
            case R.id.tip_pop_sure_btn:
                // TODO  提示pop 确定按钮点击
                tipPop.dismiss();
                break;
                default:
                    break;
        }
    }

    private void initTipPop() {
        View tipView =  LayoutInflater.from(HbAgentCenterActivity.this).inflate(R.layout.apply_agent_tip_pop,null);
        tipContentTv=tipView.findViewById(R.id.tip_pop_content_tv);
        tipDismissIv=tipView.findViewById(R.id.tip_pop_dismiss_iv);
        tipSureBtn=tipView.findViewById(R.id.tip_pop_sure_btn);
        tipSureBtn.setOnClickListener(this);
        tipDismissIv.setOnClickListener(this);
        tipPop=new PopupWindow(tipView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);
        tipPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.darkenBackground(HbAgentCenterActivity.this,1f);
            }
        });
    }

    private void initApplyAgentPop() {
        View popView = LayoutInflater.from(HbAgentCenterActivity.this).inflate(R.layout.apply_agent_pop,null);
        applySureTv=popView.findViewById(R.id.apply_agent_sure_tv);
        applyCancelTv=popView.findViewById(R.id.apply_agent_cancel_tv);
        applyCancelTv.setOnClickListener(this);
        applySureTv.setOnClickListener(this);
        applyAgentPop=new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,true);
        applyAgentPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.darkenBackground(HbAgentCenterActivity.this,1f);
            }
        });
    }
}
