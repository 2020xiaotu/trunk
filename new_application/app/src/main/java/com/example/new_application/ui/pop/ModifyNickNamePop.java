package com.example.new_application.ui.pop;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.new_application.R;
import com.example.new_application.base.BasePopupWindow;

import butterknife.BindView;

public class ModifyNickNamePop extends BasePopupWindow {
    EditText modify_nick_name_etv;
    TextView modify_nick_name_cancel_tv;
    TextView modify_nick_name_sure_tv;

    public ModifyNickNamePop(Context context) {
        super(context);
        bindView();
    }

    private void bindView() {
        modify_nick_name_etv = rootView . findViewById(R.id.modify_nick_name_etv);
        modify_nick_name_cancel_tv = rootView . findViewById(R.id.modify_nick_name_cancel_tv);
        modify_nick_name_sure_tv = rootView . findViewById(R.id.modify_nick_name_sure_tv);
        modify_nick_name_sure_tv.setOnClickListener(this);
        modify_nick_name_cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ModifyNickNamePop.this.dismiss();
            }
        });
    }

    @Override
    public void initView() {
        super.initView();
        rootView = LayoutInflater.from(mContext).inflate(R.layout.modify_nick_name_pop,null);
    }

    public EditText getModify_nick_name_etv() {
        return modify_nick_name_etv;
    }
}
