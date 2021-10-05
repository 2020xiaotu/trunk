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

public class ChooseTypeAdapter extends BaseQuickAdapter<MineServerEntity, BaseViewHolder> {
    public ChooseTypeAdapter(int layoutResId, @Nullable List<MineServerEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, MineServerEntity mineServerEntity) {
       TextView  server_name_tv = baseViewHolder.getView(R.id.server_name_tv);
        server_name_tv.setText(mineServerEntity.getTwoLevelClassificationName());
        boolean check = mineServerEntity.isCheck();
        if(check){
            server_name_tv.setTextColor(Color.parseColor("#FA6400"));
            server_name_tv.setBackgroundResource(R.drawable.choose_server_check_shape);
        }else {
            server_name_tv.setTextColor(Color.parseColor("#666666"));
            server_name_tv.setBackgroundResource(R.drawable.choose_server_un_check_shape);

        }
    }
}
