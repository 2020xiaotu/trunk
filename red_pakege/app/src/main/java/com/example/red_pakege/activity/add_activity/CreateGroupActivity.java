package com.example.red_pakege.activity.add_activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.util.ActionBarUtil;

public class CreateGroupActivity extends BaseActivity implements View.OnClickListener {
    /*
    toolBar相关
     */
    private Toolbar toolbar;
    private ImageView actionBarLeftIv;
    private TextView actionBarTitleTv;
    private ImageView actionBarRightIv;
//    ----------------------------------------------------------------------------------------
    //创建群button

    private Button createBtn;

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
        return R.layout.activity_create_group;
    }

    private void bindView() {
        toolbar=findViewById(R.id.message_fragent_toolbar);
        actionBarLeftIv=findViewById(R.id.action_left_iv);
        actionBarTitleTv=findViewById(R.id.actionBar_title);
        actionBarRightIv=findViewById(R.id.action_right_iv);
        actionBarRightIv.setVisibility(View.INVISIBLE);
        actionBarTitleTv.setText("创建群");
        actionBarLeftIv.setImageResource(R.drawable.icon_back);
        createBtn=findViewById(R.id.create_group_button);
        createBtn.setOnClickListener(this);
        actionBarLeftIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.action_left_iv:
                finish();
                break;
            case R.id.create_group_button:
                //TODO 创建群聊
                break;
                default:
                    break;
        }
    }
}
