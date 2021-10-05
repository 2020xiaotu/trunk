package com.example.new_application.adapter;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.MineReleaseEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MineReleaseAdapter extends BaseQuickAdapter<MineReleaseEntity, BaseViewHolder> {
    List<MineReleaseEntity> data;
    public MineReleaseAdapter(int layoutResId, @Nullable List<MineReleaseEntity> data) {
        super(layoutResId, data);
        this.data = data;
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MineReleaseEntity mineReleaseEntity) {
        baseViewHolder.setText(R.id.mine_release_id_tv,"编号: "+mineReleaseEntity.getOrderCode());
        baseViewHolder.setText(R.id.mine_release_title_tv,mineReleaseEntity.getTitle());
        baseViewHolder.setText(R.id.mine_release_content_tv,mineReleaseEntity.getContent());
        TextView mine_release_status_tv = baseViewHolder.getView(R.id.mine_release_status_tv);
        String checkStatus = mineReleaseEntity.getCheckStatus();
        if(checkStatus.equals("1")){
            mine_release_status_tv.setText("审核中");
        }else if(checkStatus.equals("2")){
            mine_release_status_tv.setText("已发布");
        }else {
            mine_release_status_tv.setText("未通过");

        }

    }
    public void removeDataByPosition(int position){
        if(position>=0&&position<data.size()){
            data.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,data.size()-1);
        }

    }
}
