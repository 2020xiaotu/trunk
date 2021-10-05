package com.example.new_application.ui.pop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cambodia.zhanbang.rxhttp.net.utils.StringMyUtil;
import com.example.new_application.R;
import com.example.new_application.base.BasePopupWindow;

public class AddServerLabelPop extends BasePopupWindow {
    EditText add_server_label_etv;
    TextView add_server_label_cancel_tv;
    TextView add_server_label_sure_tv;
    String content;
    public AddServerLabelPop(Context context,String content) {
        super(context);
        this.content = content;
        bindView();
        if(StringMyUtil.isNotEmpty(content)){
            add_server_label_etv.setText(content);
        }
    }

    @Override
    public void initView() {
        super.initView();
        rootView = LayoutInflater.from(mContext).inflate(R.layout.add_server_label_pop,null);
    }
    private void bindView() {
        add_server_label_etv = rootView . findViewById(R.id.add_server_label_etv);
        add_server_label_cancel_tv = rootView . findViewById(R.id.add_server_label_cancel_tv);
        add_server_label_sure_tv = rootView . findViewById(R.id.add_server_label_sure_tv);
        add_server_label_sure_tv.setOnClickListener(this);
        add_server_label_cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddServerLabelPop.this.dismiss();
            }
        });
    }


    public EditText getAdd_server_label_etv() {
        return add_server_label_etv;
    }
}
