package com.example.red_pakege.adapter.mine_adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.red_pakege.BuildConfig;
import com.example.red_pakege.R;
import com.example.red_pakege.model.BankInfoModel;

import java.util.List;



public class ChoiceBankPopRecyAdapter extends BaseQuickAdapter<BankInfoModel, BaseViewHolder> {

    private String choiceBankName;
    public ChoiceBankPopRecyAdapter(int layoutResId, @Nullable List<BankInfoModel> data, String choiceBankName) {
        super(layoutResId, data);
        this.choiceBankName = choiceBankName;
    }

    @Override
    protected void convert(BaseViewHolder helper, BankInfoModel item) {
        helper.setText(R.id.tv_item_choicebank,item.getName());

        int ResId = mContext.getResources().getIdentifier(item.getResId(),"drawable", BuildConfig.APPLICATION_ID);
        //   Drawable drawable = mContext.getResources().getDrawable(ResId);
        helper.setImageResource(R.id.iv_item_choicebank,ResId);
        if (!choiceBankName.equals(item.getName())){
            helper.setVisible(R.id.iv_item_choicebank_choice,false);
        }
    }




}
