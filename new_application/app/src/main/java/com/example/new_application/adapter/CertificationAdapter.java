package com.example.new_application.adapter;

import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.CertificationEntity;
import com.example.new_application.bean.HelpChildEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CertificationAdapter extends BaseQuickAdapter<HelpChildEntity, BaseViewHolder> {
    public CertificationAdapter(int layoutResId, @Nullable List<HelpChildEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, HelpChildEntity helpChildEntity) {
        ImageView certification_item_iv = baseViewHolder.getView(R.id.certification_item_iv);
        TextView certification_item_content_tv = baseViewHolder.getView(R.id.certification_item_content_tv);
        TextView certification_title_tv = baseViewHolder.getView(R.id.certification_title_tv);
        certification_title_tv.setText(helpChildEntity.getTheme());
        certification_item_content_tv.setText(Html.fromHtml(helpChildEntity.getContent()));
        if(helpChildEntity.isOpen()){
            certification_item_iv.setImageResource(R.drawable.cjwt_shang_fwsrz);
            certification_item_content_tv.setVisibility(View.VISIBLE);
        }else {
            certification_item_iv.setImageResource(R.drawable.cjwt_xia_fwsrz);
            certification_item_content_tv.setVisibility(View.GONE);
        }
    }
}
