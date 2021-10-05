package com.example.new_application.ui.pop;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.ChooseTypeAdapter;
import com.example.new_application.base.BasePopupWindow;
import com.example.new_application.bean.MineServerEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChooseServerPop extends BasePopupWindow {
    RecyclerView choose_server_recycler;
    ChooseTypeAdapter chooseTypeAdapter;
    ArrayList<MineServerEntity>mineServerEntityArrayList = new ArrayList<>();

    public ChooseServerPop(Context context) {
        super(context);
        initRecycler();
        requestServerList();
    }

    private void requestServerList() {
        HttpApiUtils.wwwNormalRequest((Activity)mContext, null, RequestUtils.MINE_SERVER_TYPE, new HashMap<String, Object>(), new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<MineServerEntity>  mineServerEntityList = JSONArray.parseArray(result, MineServerEntity.class);
                MineServerEntity mineServerEntity = new MineServerEntity();
                mineServerEntity.setTwoLevelClassificationName("全部");
                mineServerEntity.setOneLevelClassificationId("");
                mineServerEntity.setTwoLevelClassificationId("");
                mineServerEntity.setCheck(true);
                mineServerEntityArrayList.add(mineServerEntity);
                mineServerEntityArrayList.addAll(mineServerEntityList);
                chooseTypeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }

    private void initRecycler() {
        chooseTypeAdapter = new ChooseTypeAdapter(R.layout.choose_server_item,mineServerEntityArrayList);
        choose_server_recycler.setLayoutManager(new GridLayoutManager(mContext,3));
        choose_server_recycler.setAdapter(chooseTypeAdapter);
        chooseTypeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                MineServerEntity mineServerEntity = mineServerEntityArrayList.get(position);
                for (int i = 0; i < mineServerEntityArrayList.size(); i++) {
                    mineServerEntityArrayList.get(i).setCheck(false);
                }
                mineServerEntity.setCheck(true);
                chooseTypeAdapter.notifyDataSetChanged();
                dismiss();
                if(mOnPopItemClick!=null){
                    mOnPopItemClick.onPopItemClick(view,position);
                }
            }
        });
    }

    @Override
    public void initView() {
        super.initView();
        rootView = LayoutInflater.from(mContext).inflate(R.layout.choose_server_pop_layout,null);
        choose_server_recycler = rootView.findViewById(R.id.choose_server_recycler);
    }

    public ArrayList<MineServerEntity> getMineServerEntityArrayList() {
        return mineServerEntityArrayList;
    }
}
