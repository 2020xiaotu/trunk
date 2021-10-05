package com.example.red_pakege.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.red_pakege.R;

import static com.example.red_pakege.util.ScreenUtils.darkenBackground;

public class ConfirmStrPop extends PopupWindow {

    private Context mContext;
    private View contentView;
    TextView tv_confirmpop;
    TextView ensure_confirmpop;


    public interface OnClickListener {
        void onClick(String str);
    }
    private OnClickListener mOnClickListener;

    public void setOnTvClickListener(OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    public ConfirmStrPop(Context context,String str) {
        this.mContext = context;
        initView(context,str);
        setPopupWindow();
    }

    private void initView(Context context,String str){
        LayoutInflater inflater = LayoutInflater.from(context);
        contentView = inflater.inflate(R.layout.pop_confirmstr, null);
        tv_confirmpop = contentView.findViewById(R.id.tv_confirstrmpop);
        ensure_confirmpop = contentView.findViewById(R.id.ensure_confirmstrpop);
        tv_confirmpop.setText(str);

        ensure_confirmpop.setOnClickListener(v -> {
            dismiss();
            darkenBackground((Activity)mContext, 1f);//恢复背景亮度
            if (!TextUtils.isEmpty(tv_confirmpop.getText().toString())){
                mOnClickListener.onClick(tv_confirmpop.getText().toString());
            }
        });
    }

    private void setPopupWindow(){
        this.setContentView(contentView);// 设置View
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setAnimationStyle(R.style.pop_buttom_to_top_150);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明

        contentView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = contentView.findViewById(R.id.id_pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                darkenBackground((Activity)mContext, 1f);//恢复背景亮度
            }
        });

    }

    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, 0, 0);

            darkenBackground((Activity) mContext,0.3f);//恢复背景亮度
        } else {
            this.dismiss();
            darkenBackground((Activity)mContext, 1f);//恢复背景亮度

        }
    }
}
