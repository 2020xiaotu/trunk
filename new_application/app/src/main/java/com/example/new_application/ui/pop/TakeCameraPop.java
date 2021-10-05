package com.example.new_application.ui.pop;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.example.new_application.R;
import com.example.new_application.base.BasePopupWindow;

public class TakeCameraPop extends BasePopupWindow {
    TextView takeCameratv;
    private TextView choosePhototv;
    private TextView cancelTv;
    public TakeCameraPop(Context context) {
        super(context);
        setAnimationStyle(R.anim.down_to_up_in_150);
        binDView();
    }

    private void binDView() {
        takeCameratv=rootView.findViewById(R.id.forbidden_tv);
        choosePhototv=rootView.findViewById(R.id.set_manager_tv);
        cancelTv=rootView.findViewById(R.id.forbidden_cancel_tv);
        takeCameratv.setOnClickListener(this);
        choosePhototv.setOnClickListener(this);
        cancelTv.setOnClickListener(this);
    }

    @Override
    public void initView() {
        super.initView();
        rootView = LayoutInflater.from(mContext).inflate(R.layout.take_camera_pop_layout,null);
    }
}
