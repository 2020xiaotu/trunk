package com.example.red_pakege.widget;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.red_pakege.R;
import com.example.red_pakege.adapter.mine_adapter.ChoiceBankPopRecyAdapter;
import com.example.red_pakege.model.BankInfoModel;

import java.util.List;

import static com.example.red_pakege.util.ScreenUtils.darkenBackground;

public class MyChoiceBankPop extends PopupWindow {

    private View contentView;
    private Context mContext;
    private ChoiceBankPopRecyAdapter choiceBankPopRecyAdapter;
    private List<BankInfoModel> dataList;
    private String choiceBankName;

    private RecyclerView recy_bank;

    public interface OnBankItemClickListener {
        void onBankItemClick(int position);
    }
    private OnBankItemClickListener mOnBankItemClickListener;

    public void setOnBankItemClickListener(OnBankItemClickListener onBankItemClickListener) {
        mOnBankItemClickListener = onBankItemClickListener;
    }

    public MyChoiceBankPop(Context context,List<BankInfoModel> dataList,String choiceBankName) {
        super(context);
        this.mContext = context;
        this.dataList = dataList;
        this.choiceBankName = choiceBankName;
        initView(context);
        setPopupWindow();
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        contentView = inflater.inflate(R.layout.pop_choice_bank, null);
        recy_bank = contentView.findViewById(R.id.recy_bank);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recy_bank.setLayoutManager(layoutManager);
        choiceBankPopRecyAdapter = new ChoiceBankPopRecyAdapter(R.layout.item_pop_choice_bank,dataList,choiceBankName);
        recy_bank.setAdapter(choiceBankPopRecyAdapter);
        choiceBankPopRecyAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                dismiss();
                darkenBackground((Activity)mContext, 1f);//??????????????????
                mOnBankItemClickListener.onBankItemClick(position);
            }
        });

    }

    private void setPopupWindow() {

        this.setContentView(contentView);// ??????View
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);// ????????????????????????
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);// ????????????????????????
        this.setFocusable(true);// ?????????????????????
        this.setAnimationStyle(R.style.pop_buttom_to_top_150);// ????????????
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// ??????????????????

        contentView.setOnTouchListener(new View.OnTouchListener() {// ??????????????????????????????????????????

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
                darkenBackground((Activity)mContext, 1f);//??????????????????
            }
        });

    }


    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // ?????????????????????popupwindow
            this.showAsDropDown(parent, 0, 18);

            darkenBackground((Activity) mContext,0.3f);//??????????????????
        } else {
            this.dismiss();
            darkenBackground((Activity)mContext, 1f);//??????????????????

        }
    }








}
