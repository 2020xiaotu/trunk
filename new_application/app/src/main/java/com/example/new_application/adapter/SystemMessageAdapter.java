package com.example.new_application.adapter;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.HomeNoticeEntity;
import com.example.new_application.bean.MessageEntity;
import com.example.new_application.utils.DateUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SystemMessageAdapter extends BaseQuickAdapter<HomeNoticeEntity, BaseViewHolder> {
    public SystemMessageAdapter(int layoutResId, @Nullable List<HomeNoticeEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HomeNoticeEntity homeNoticeEntity) {
       TextView  message_content_tv = baseViewHolder.getView(R.id.message_content_tv);
        TextView  unread_tv = baseViewHolder.getView(R.id.unread_tv);
        unread_tv.setVisibility(View.GONE);
        baseViewHolder.setText(R.id.message_date_tv,DateUtil.formatTime(homeNoticeEntity.getCreatedDate()));
        baseViewHolder.setText(R.id.message_title_tv,homeNoticeEntity.getTheme());
        message_content_tv.setText(Html.fromHtml(homeNoticeEntity.getContent()));
        message_content_tv.setMaxLines(3);
    }
}
