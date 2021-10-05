package com.example.red_pakege.fragment.notice_fragment;

import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.red_pakege.adapter.message_fragment_adapter.notice_activity_adapter.SystemMessageAdapter;
import com.example.red_pakege.base.BaseFragment;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.model.SystemMessageModel;
import com.example.red_pakege.R;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.ArrayList;

public class HbNoticeFragment extends BaseFragment {
    private RecyclerView mRecy;
    private RefreshLayout refreshLayout;
    private ConstraintLayout loadingLinear;
    private LinearLayout nodataLinear;
    private LinearLayout errorLinear;
    private SystemMessageAdapter systemMessageAdapter;
    private ArrayList<SystemMessageModel> systemMessageModelArrayList=new ArrayList<>();

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice, container, false);
        bindView(view);
        initRecycle();
        return view;
    }
    private void bindView(View view) {
        mRecy=view.findViewById(R.id.notice_recycle);
        refreshLayout=view.findViewById(R.id.notice_refresh);
        loadingLinear=view.findViewById(R.id.loading_linear);
        errorLinear=view.findViewById(R.id.error_linear);
        nodataLinear=view.findViewById(R.id.nodata_linear);
    }

    private void initRecycle() {
        systemMessageAdapter=new SystemMessageAdapter(systemMessageModelArrayList,getActivity());
        mRecy.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecy.setAdapter(systemMessageAdapter);
        for (int i = 0; i < 10; i++) {
            systemMessageModelArrayList.add(new SystemMessageModel(i+"垃圾车 星空 心中无别人 星空 心中无别人 后来的我们 垃圾车 星空 心中无别人 后来的我们  后来的我们 垃圾车",
                    "星空 心中无别人 后来的我们 垃圾车星空 心中无别人 后来的我们 垃圾车星空 心中无别人 后来的我们 垃圾车星空 心中无别人 后来的我们 垃圾车星空 心中无别人 后来的我们 垃圾车星空 心中无别人 后来的我们 垃圾车",
                    "2019-11-09 19:27"));
        }

    }

}
