package com.example.red_pakege.rong_yun.plugin;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.red_pakege.MyApplication;
import com.example.red_pakege.R;

import io.rong.imkit.RongExtension;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;

/*
群规 Plugin
 */
public class RulePlugin implements IPluginModule {
    Conversation.ConversationType conversationType;
    String targetId;
    @Override
    public Drawable obtainDrawable(Context context) {
//设置插件 Plugin 图标
        return ContextCompat.getDrawable(context, R.drawable.ic_tab_rule);
    }

    @Override
    public String obtainTitle(Context context) {
//设置插件 Plugin 标题
        return "群规";
    }

    @Override
    public void onClick(Fragment fragment, RongExtension rongExtension) {
/// TODO 点击事件
        Toast.makeText(MyApplication.getInstance(),"点击 群规 ",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int i, int i1, Intent intent) {

    }
}
