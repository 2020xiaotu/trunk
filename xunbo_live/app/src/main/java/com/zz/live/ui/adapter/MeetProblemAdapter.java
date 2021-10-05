package com.zz.live.ui.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zz.live.R;
import com.zz.live.bean.CommonProblemModel;

import java.util.ArrayList;


public class MeetProblemAdapter extends CommonAdapter<MeetProblemAdapter.MyHolder, CommonProblemModel.DataBean> {
    public MeetProblemAdapter(ArrayList<CommonProblemModel.DataBean> list) {
        super(list);
    }

    @Override
    public void handleViewHolder(MyHolder commonHolder, int position) {
        CommonProblemModel.DataBean itemModel = getItemModel(position);
        TextView problemTv = commonHolder.problemTv;
        if(itemModel.getStatus()==1){
            problemTv.setSelected(true);
        }else {
            problemTv.setSelected(false);
        }
        problemTv.setText(itemModel.getExplain());
        problemTv.setTag(position);
        problemTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.isSelected()){
                    itemModel.setStatus(0);
                    notifyDataSetChanged();
                }else {
                    initItemStatus(position);
                }
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
                }
            }

            private void initItemStatus(int position) {
                for (int i = 0; i < list.size(); i++) {
                    CommonProblemModel.DataBean problemModel = list.get(i);
                    if(position==i){
                        problemModel.setStatus(1);
                    }else {
                        problemModel.setStatus(0);
                    }
                }
                notifyDataSetChanged();
            }

        });
    }

    @Override
    public int getLayOutRes() {
        return R.layout.mine_problem_recycle_item;
    }

    public static class MyHolder extends CommonHolder {
        private TextView problemTv;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            problemTv=itemView.findViewById(R.id.meet_problem_tv);
        }
    }
}
