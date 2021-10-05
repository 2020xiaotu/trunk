package com.example.red_pakege.fragment.mine_fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.red_pakege.R;
import com.example.red_pakege.adapter.mine_adapter.MineTabChildAdapter;
import com.example.red_pakege.base.BaseFragment;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.net.entity.MineTabChildEntity;
import com.example.red_pakege.widget.GridItemDecoration;
import com.example.red_pakege.widget.GridSectionAverageGapItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MineTabChild1Fragment  extends BaseFragment implements BaseQuickAdapter.OnItemClickListener{


    @BindView(R.id.recy_minetab1)
    RecyclerView mRecyclerView;

    private MineTabChildAdapter mineTabChildAdapter;
    private GridLayoutManager gridLayoutManager;
    private List<MineTabChildEntity> mList = new ArrayList<>();


    public static MineTabChild1Fragment newInstance() {
        MineTabChild1Fragment fragment = new MineTabChild1Fragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_minetab1;
    }


    @Override
    protected void initView() {
        mineTabChildAdapter = new MineTabChildAdapter(R.layout.item_minetabchild,mList);
        mineTabChildAdapter.setOnItemClickListener(this);
        gridLayoutManager = new GridLayoutManager(_mActivity,4);
        gridLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
    //    mRecyclerView.addItemDecoration(new GridSectionAverageGapItemDecoration(10,10,20,15));
        mRecyclerView.setAdapter(mineTabChildAdapter);
    }


    @Override
    protected void initEventAndData() {
        mList.clear();
        for (int i=0;i<5;i++){
            MineTabChildEntity entity = new MineTabChildEntity();
            entity.setTitle(""+i);
            mList.add(entity);
        }
        mineTabChildAdapter.replaceData(mList);

    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
            LogUtils.e(""+position);
    }
}