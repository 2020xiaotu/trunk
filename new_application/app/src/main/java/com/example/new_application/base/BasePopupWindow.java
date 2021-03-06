package com.example.new_application.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.fragment.app.FragmentActivity;

import com.example.new_application.utils.Utils;
import com.hjq.toast.ToastUtils;


public class BasePopupWindow extends PopupWindow implements View.OnClickListener{
    public View rootView;
    public Context mContext;
   private static final int FULL_SCREEN_FLAG =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
    public BasePopupWindow(Context context) {
        super(context);
        mContext=context;
        initView();
        initPop();
    }
    /**
     *
     * @param
     */
    public void initPop() {
        this.setContentView(rootView);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
//        this.getContentView().setSystemUiVisibility(FULL_SCREEN_FLAG);
//        this.setFocusable(focusable);
        //软键盘不会挡着popupwindow
//        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        this.update();
        ColorDrawable dw = new ColorDrawable(0x00FFFFFF);
        this.setBackgroundDrawable(dw);
/*        this.getContentView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                BasePopupWindow.this.getContentView().setSystemUiVisibility(FULL_SCREEN_FLAG);
            }
        });*/
        this.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Activity activity = (Activity) BasePopupWindow.this.mContext;
                Utils.darkenBackground(activity,1f);
                if(mOnDismissListener !=null){
                    mOnDismissListener.onDissmiss();
                }
    /*            if(activity instanceof StartLiveActivity){
                    ImmersionBar.with(activity).hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR).init();
                }*/

            }
        });
    }
    public void showToast(String str){
            ToastUtils.setGravity(Gravity.CENTER,0,0);
            ToastUtils.show(str);

    }

    public void initView() {

    }

    public void errorRefresh(){
    }

    @Override
    public void onClick(View v) {
        if(mOnPopClickListener!=null){
            mOnPopClickListener.onPopClick(v);
        }
    }
    public View getView(int viewId){
        return rootView.findViewById(viewId);
    }
    public interface  OnPopClickListener{
        void onPopClick(View view);
    }
    public   OnPopClickListener mOnPopClickListener;

    public void setOnPopClickListener(OnPopClickListener mOnPopClickListener) {
        this.mOnPopClickListener = mOnPopClickListener;
    }

    public interface  OnRecycleItemClick{
        void onPopItemClick(View view, int  position);
    }
 public   OnRecycleItemClick mOnPopItemClick;

    public void setOnPopItemClick(OnRecycleItemClick mOnPopItemClick) {
        this.mOnPopItemClick = mOnPopItemClick;
    }
/*    public void setFocusableAndupdate(){
        this.setFocusable(true);
        this.update();
    }*/
    public interface OnDismissListener {
        void onDissmiss();
    }
  public OnDismissListener mOnDismissListener;

    public void setmOnDismissListener(OnDismissListener mOnDismissListener) {
        this.mOnDismissListener = mOnDismissListener;
    }
}
