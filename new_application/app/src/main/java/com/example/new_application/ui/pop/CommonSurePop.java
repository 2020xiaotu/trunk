package com.example.new_application.ui.pop;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.new_application.R;
import com.example.new_application.base.BasePopupWindow;


public class CommonSurePop extends BasePopupWindow {
    TextView sure_title_tv;
    TextView sure_tv;
    TextView sure_content_tv;
    String title;
    String content;
    public CommonSurePop(Context context, String title, String content) {
        super(context);
        this.title = title;
        this.content = content;
        initData();
    }

    private void initData() {
        sure_title_tv.setText(title);
        sure_content_tv.setText(Html.fromHtml(content));
    }

    @Override
    public void initView() {
        super.initView();
        rootView = LayoutInflater.from(mContext).inflate(R.layout.common_sure_pop,null);
        sure_title_tv = rootView.findViewById(R.id.sure_title_tv);
        sure_content_tv = rootView.findViewById(R.id.sure_content_tv);
        sure_tv = rootView.findViewById(R.id.sure_tv);
        sure_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             CommonSurePop.this.dismiss();
                if(mOnPopClickListener!=null){
                    mOnPopClickListener.onPopClick(v);
                }
            }
        });
    }

    public TextView getSure_tv() {
        return sure_tv;
    }
}
