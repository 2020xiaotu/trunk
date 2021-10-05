package com.example.red_pakege.rong_yun.activity;

import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.rong_yun.activity.send_red_pakege.SendJinQiangPakegeActivity;
import com.example.red_pakege.rong_yun.activity.send_red_pakege.SendNiuNiuRedPakegeActivity;
import com.example.red_pakege.rong_yun.activity.send_red_pakege.SendSaoLeiRedPakegeActivity;

import java.util.Locale;

import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;

/*
融云会话界面配置activity
 */
public class ConversationActivity extends BaseActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private TextView actionTitleTv;
    private ImageView actionLeftIv;
    private ImageView actionSendRedIv;
    private ImageView actionGroupInfoIv;

    private Conversation.ConversationType mConversationType;
    private String mTargetId;
    private String title;
    private int typeId;
    private int game;

    private enum RedTypeId{
        NIUNIU , SAOLEI ,FULI ,JINQIANG
    }
    RedTypeId redTypeId;

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        super.initView();
        getIntentDate(getIntent());
        bindActionBar();
    }


    @Override
    protected int getLayout() {
        return R.layout.activity_conversation;
    }

    /**
     * 从 Intent 中得到 跳转会话界面时传递的 Uri
     */
    private void getIntentDate(Intent intent) {

        Uri data = intent.getData();
        //聊天id(可能是聊天室id 也可能是用户id)
        mTargetId = data.getQueryParameter("targetId");
        //获得当前会话类型
        mConversationType = Conversation.ConversationType.valueOf(data.getLastPathSegment().toUpperCase(Locale.getDefault()));
        //标题
        title=data.getQueryParameter("title");
        Bundle bundle = getIntent().getExtras();
        typeId=bundle.getInt("typeId");
        game=bundle.getInt("game");
        switch (typeId){
            case 1:
            redTypeId=RedTypeId.JINQIANG;
            break;
            case 2:
                redTypeId=RedTypeId.SAOLEI;
                break;
            case 3:
                redTypeId=RedTypeId.NIUNIU;
                break;
            case 4:
                redTypeId=RedTypeId.FULI;
                break;
                default:
                    break;
        }
        enterFragment(mConversationType, mTargetId);
    }

    /*
设置actionBar
 */
    protected void bindActionBar( ) {
        toolbar=findViewById(R.id.chat_group_actionbar_toolbar);
        actionTitleTv=findViewById(R.id.chat_group_actionBar_title);
        actionTitleTv.setText(title);
        actionLeftIv=findViewById(R.id.chat_group_action_left_iv);
        actionSendRedIv=findViewById(R.id.chat_group_send_red_pakege_iv);
        actionGroupInfoIv=findViewById(R.id.chat_group_action_member_iv);
        initClick();
            /*
            设置渐变actionBar(不能用ActionBarUtil的方法设置,
            经测试,statusBarUtils 中的setTransparentForWindow(activity)方法会导致点击输入框时,聊天列表不会自动向上收缩输入框的高度)
            改为通过设置Activity的Theme来实现渐变状态栏 在theme中设置backGroupColor 使用layer-list 上面为渐变色,下面为白色 这样收起软键盘时,空白处不会闪渐变的颜色
             */
        initMainActionbar(this,toolbar);
    }
    /*
设置actionBar渐变
 */
    private   void initMainActionbar(Activity activity, View view){
        int[] colors = { activity.getResources().getColor(R.color.mStartColor),     activity.getResources().getColor(R.color.mEndColor)};
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors);
        view .setBackground(gradientDrawable);
    };

    protected void initClick() {
        actionLeftIv.setOnClickListener(this);
        actionSendRedIv.setOnClickListener(this);
        actionGroupInfoIv.setOnClickListener(this);

    }
    /**
     * 加载会话页面 ConversationFragment
     *
     * @param mConversationType
     * @param mTargetId
     */
    private void enterFragment(Conversation.ConversationType mConversationType, String mTargetId ) {
        ConversationFragment fragment = (ConversationFragment) getSupportFragmentManager().findFragmentById(R.id.conversation);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversation").appendPath(mConversationType.getName().toLowerCase())
                .appendQueryParameter("targetId", mTargetId)
                .build();
        fragment.setUri(uri);
    }
    @Override
    public void
    onClick(View v) {
        switch (v.getId()){
            case R.id.chat_group_action_left_iv:
                 finish();
                break;
                //actionBar右侧发红包按钮
            case R.id.chat_group_send_red_pakege_iv:
                switch (redTypeId){
                    case NIUNIU:
                        SendNiuNiuRedPakegeActivity.startAty(ConversationActivity.this,"5.00","20.00",5,20);
                        break;
                    case SAOLEI:
                        SendSaoLeiRedPakegeActivity.startAty(ConversationActivity.this,mTargetId,typeId,game);
                        break;
                    case JINQIANG:
                        SendJinQiangPakegeActivity.startAty(ConversationActivity.this,mTargetId,typeId,game);
                        break;
                    case FULI:
                        //TODO  发福利包
                        break;
                        default:
                            break;
                }
                break;
            case R.id.chat_group_action_member_iv:
//                    showtoast("TODO 查看群成员");
                break;
        }
    }
}
