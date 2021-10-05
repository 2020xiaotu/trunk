package com.example.new_application.adapter;

import android.text.Html;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.MessageEntity;
import com.example.new_application.utils.DateUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MessageAdapter extends BaseQuickAdapter<MessageEntity, BaseViewHolder> {
    public MessageAdapter(int layoutResId, @Nullable List<MessageEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MessageEntity messageEntity) {
       TextView  message_content_tv = baseViewHolder.getView(R.id.message_content_tv);
       TextView  unread_tv = baseViewHolder.getView(R.id.unread_tv);
        baseViewHolder.setText(R.id.message_date_tv,DateUtil.formatTime(messageEntity.getCreatedDate()));
        baseViewHolder.setText(R.id.message_title_tv,messageEntity.getTitle());
        message_content_tv.setText(Html.fromHtml(messageEntity.getContent()));
        String isRead = messageEntity.getIsRead();
        if(isRead.equals("1")){
            unread_tv.setVisibility(View.GONE);
        }else {
            unread_tv.setVisibility(View.VISIBLE);
        }
    }
}
