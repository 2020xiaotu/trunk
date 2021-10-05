package com.example.new_application.adapter;

import android.graphics.Color;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.example.new_application.R;
import com.example.new_application.bean.MineServerEntity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReleaseServerAdapter extends BaseQuickAdapter<MineServerEntity, BaseViewHolder> {
    public ReleaseServerAdapter(int layoutResId, @Nullable List<MineServerEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MineServerEntity mineServerEntity) {
       TextView release_server_tv = baseViewHolder.getView(R.id.release_server_tv);
        release_server_tv.setText(mineServerEntity.getTwoLevelClassificationName());
            if(mineServerEntity.isCheck()){
                release_server_tv.setBackgroundResource(R.drawable.release_server_check_shape);
                release_server_tv.setTextColor(Color.parseColor("#222222"));
            }else {
                release_server_tv.setBackgroundResource(R.drawable.release_server_un_check_shape);
                release_server_tv.setTextColor(Color.parseColor("#666666"));
            }

    }
}
