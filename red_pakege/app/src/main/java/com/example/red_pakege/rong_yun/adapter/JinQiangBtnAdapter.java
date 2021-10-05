package com.example.red_pakege.rong_yun.adapter;

import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;

import com.example.red_pakege.R;
import com.example.red_pakege.model.JinQiangRtnModel;
import com.example.red_pakege.widget.CommonAdapter;
import com.example.red_pakege.widget.CommonHolder;

import org.w3c.dom.ls.LSException;

import java.util.ArrayList;

public class JinQiangBtnAdapter extends CommonAdapter<JinQiangBtnAdapter.MyHolder, JinQiangRtnModel> {
    private ArrayList<JinQiangRtnModel> selectorList = new ArrayList<>();

    public JinQiangBtnAdapter(ArrayList<JinQiangRtnModel> list, ArrayList<JinQiangRtnModel> selectorList) {
        super(list);
        this.selectorList = selectorList;
    }

    @Override
    public void handleViewHolder(MyHolder commonHolder, int position) {
        JinQiangRtnModel itemModel = getItemModel(position);
        RadioButton radioButton = commonHolder.radioButton;
        radioButton.setText(itemModel.getNum()+"");
        int status = itemModel.getStatus();
        if(status==0){
            radioButton.setChecked(false);
        }else {
            radioButton.setChecked(true);
        }
        radioButton.setTag(position);
        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkClick(position);
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
                }
            }
        });
    }

    private void checkClick(int position) {
        JinQiangRtnModel jinQiangRtnModel1 = list.get(position);
        if(jinQiangRtnModel1.getType()==1){//红包个数recycleView,只允许同时选中一个button
            for (int i = 0; i < list.size(); i++) {
                JinQiangRtnModel jinQiangRtnModel = list.get(i);
                if(i==position){
                    jinQiangRtnModel.setStatus(1);
                }else {
                    jinQiangRtnModel.setStatus(0);
                }
            }

        }else {//雷数recycleView可以同时选中多个
//            for (int i = 0; i < list.size(); i++) {
                JinQiangRtnModel jinQiangRtnModel = list.get(position);
                selectorList.remove(jinQiangRtnModel);
                if(jinQiangRtnModel.getStatus()==0){
                    selectorList.add(jinQiangRtnModel);
                    jinQiangRtnModel.setStatus(1);
                }else {
                    jinQiangRtnModel.setStatus(0);
                }
//            }
        }
        notifyDataSetChanged();

    }

    public JinQiangBtnAdapter(ArrayList<JinQiangRtnModel> list) {
        super(list);
    }

    @Override
    public int getLayOutRes() {
        return R.layout.jinqiang_recycle_item;
    }

    public static class MyHolder extends CommonHolder {
        private RadioButton radioButton;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            radioButton=itemView.findViewById(R.id.jin_qiang_num_btn);
        }
    }
}
