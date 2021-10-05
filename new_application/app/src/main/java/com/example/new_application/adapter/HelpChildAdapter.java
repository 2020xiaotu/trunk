package com.example.new_application.adapter;

import android.text.Html;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.HelpChildEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HelpChildAdapter extends BaseQuickAdapter<HelpChildEntity, BaseViewHolder> {
    public HelpChildAdapter(int layoutResId, @Nullable List<HelpChildEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HelpChildEntity helpChildEntity) {
        TextView help_child_title_tv = baseViewHolder.getView(R.id.help_child_title_tv);
        TextView help_child_content_tv = baseViewHolder.getView(R.id.help_child_content_tv);
        help_child_title_tv.setText(helpChildEntity.getTheme());
        help_child_content_tv.setText(Html.fromHtml(helpChildEntity.getContent()));

    }
}
