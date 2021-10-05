package com.example.new_application.ui.activity.mine_fragment_activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.alibaba.fastjson.JSONArray;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.HelpParentAdapter;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.HelpChildEntity;
import com.example.new_application.bean.HelpParentEntity;
import com.example.new_application.net.RequestUtils;
import com.example.new_application.net.api.HttpApiUtils;
import com.example.new_application.utils.CommonToolbarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HelpCenterActivity extends BaseActivity {
    @BindView(R.id.help_center_recycler)
    RecyclerView help_center_recycler;
    ArrayList<HelpParentEntity>helpParentEntityArrayList = new ArrayList<>();
    HelpParentAdapter helpParentAdapter;
    HelpParentEntity index0Entity;
    HelpParentEntity index1Entity;
    HelpParentEntity index2Entity;
    @Override
    public int getLayoutId() {
        return R.layout.activity_help_conter;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        CommonToolbarUtil.initToolbar(this,"帮助中心");
        initRecycler();
        requestItemData(5,0,R.drawable.gywm_icon);
        requestItemData(6,1,R.drawable.jywt_icon);
        requestItemData(7,2,R.drawable.cjwt_icon);
    }

    private void requestItemData(int themetype,int index,int drawableId) {

        HttpApiUtils.pathRequest(this, null, RequestUtils.TIP_CONTENT, themetype+"", new HttpApiUtils.OnRequestLintener() {
            @Override
            public void onSuccess(String result) {
                List<HelpChildEntity> helpChildEntities = JSONArray.parseArray(result, HelpChildEntity.class);
                if(index==0){
                    index0Entity = new HelpParentEntity("关于我们",drawableId,helpChildEntities);
                }else if(index == 1){
                    index1Entity = new HelpParentEntity("交易问题",drawableId,helpChildEntities);
                }else {
                    index2Entity = new HelpParentEntity("常见问题",drawableId,helpChildEntities);
                }
                if(index0Entity !=null&& index1Entity !=null&& index2Entity !=null){
                    helpParentEntityArrayList.add(0,index0Entity);
                    helpParentEntityArrayList.add(1,index1Entity);
                    helpParentEntityArrayList.add(2,index2Entity);
                    helpParentAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFail(String msg) {

            }
        });
    }

    private void initRecycler() {
        helpParentAdapter = new HelpParentAdapter(R.layout.help_center_parent_item,helpParentEntityArrayList);
        help_center_recycler.setLayoutManager(new LinearLayoutManager(this));
        help_center_recycler.setAdapter(helpParentAdapter);
        helpParentAdapter.addChildClickViewIds(R.id.help_parent_linear);
        helpParentAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                HelpParentEntity helpParentEntity = helpParentEntityArrayList.get(position);
                helpParentEntity.setOpen(!helpParentEntity.isOpen());
                helpParentAdapter.notifyDataSetChanged();
            }
        });
    }
}