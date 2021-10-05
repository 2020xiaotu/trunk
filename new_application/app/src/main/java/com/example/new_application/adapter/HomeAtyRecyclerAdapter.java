package com.example.new_application.adapter;

import android.webkit.WebSettings;
import android.webkit.WebView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.HomeNoticeEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class HomeAtyRecyclerAdapter extends BaseQuickAdapter<HomeNoticeEntity, BaseViewHolder> {
    public HomeAtyRecyclerAdapter(int layoutResId, @Nullable List<HomeNoticeEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder,HomeNoticeEntity RecordsBean) {
      WebView webView =  baseViewHolder.getView(R.id.home_aty_webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        //禁用横向滚动条
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setDefaultTextEncodingName("UTF -8");
        webView.loadDataWithBaseURL(null,"<style> img{ max-width:100%; height:auto;} </style>"+ RecordsBean.getContent(),"text/html", "utf-8",null);

    }
}
