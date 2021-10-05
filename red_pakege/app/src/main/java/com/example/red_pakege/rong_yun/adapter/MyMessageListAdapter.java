package com.example.red_pakege.rong_yun.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.adapter.MessageListAdapter;
import io.rong.imlib.model.Message;

public class MyMessageListAdapter extends MessageListAdapter {
    public MyMessageListAdapter(Context context) {
        super(context);
    }

    @Override
    protected View newView(Context context, int position, ViewGroup group) {
        return super.newView(context, position, group);
    }
    /*
    重写bindView(),消息类型为HB:ResultNiuNiu 时,隐藏左右两侧的头像和昵称
     */
    @Override
    protected void bindView(View v, int position, UIMessage data) {
        super.bindView(v, position, data);
        MessageListAdapter.ViewHolder holder = (MessageListAdapter.ViewHolder)v.getTag();
        Message message = data.getMessage();
        String objectName = message.getObjectName();
        if(objectName!=null){
            if(objectName.equals("HB:ResultNiuNiu")){
                holder.leftIconView.setVisibility(View.INVISIBLE);
                holder.rightIconView.setVisibility(View.INVISIBLE);
                holder.nameView.setVisibility(View.INVISIBLE);
            }
        }

    }
}
