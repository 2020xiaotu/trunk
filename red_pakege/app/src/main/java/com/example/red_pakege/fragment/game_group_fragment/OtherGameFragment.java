package com.example.red_pakege.fragment.game_group_fragment;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.red_pakege.R;
import com.example.red_pakege.adapter.game_group_adapter.OtherGameAdapter;
import com.example.red_pakege.base.BaseFragment;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.model.OtherGameModel;
import com.example.red_pakege.util.RefreshUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import java.lang.ref.SoftReference;
import java.util.ArrayList;

import io.rong.imkit.RongIM;

public class OtherGameFragment extends BaseFragment {
    private enum Argument{CHESS, ELECTRONIC,LEISURE};
    Argument argument;
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private OtherGameAdapter otherGameAdapter;
    private ArrayList<OtherGameModel>otherGameModelArrayList = new ArrayList<>();
    ;
    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_game, container, false);
        int positon= getArguments().getInt("position");
        if(positon==1){
            argument=Argument.CHESS;
        }else if(positon==2){
            argument=Argument.ELECTRONIC;
        }else {
            argument=Argument.LEISURE;
        }
        bindView(view);
        initRefresh();
        initRecycle();
        return view;
    }

    private void initRecycle() {
        otherGameAdapter=new OtherGameAdapter(otherGameModelArrayList,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(otherGameAdapter);
        switch (argument){
            case CHESS:
                otherGameModelArrayList.add(new OtherGameModel(R.drawable.lucky_chess));
                otherGameModelArrayList.add(new OtherGameModel(R.drawable.wz_chess));
                otherGameModelArrayList.add(new OtherGameModel(R.drawable.qg_hb));
                break;
            case ELECTRONIC:
                for (int i = 0; i < 10; i++) {
                    otherGameModelArrayList.add(new OtherGameModel(R.drawable.wz_chess));
                }
                break;
            case LEISURE:
                for (int i = 0; i < 10; i++) {
                    otherGameModelArrayList.add(new OtherGameModel(R.drawable.qg_hb));
                }
                break;
                default:
                    break;

        }
    }

    @Override
    protected void initRefresh() {
        super.initRefresh();
        RefreshUtil.initRefreshLoadMore(new SoftReference<>(getContext()), refreshLayout, new RefreshUtil.OnRefreshLoadMoreLintener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {

            }
        });
    }

    private void bindView(View view) {
        refreshLayout= view.findViewById(R.id.other_game_refresh);
        recyclerView=view.findViewById(R.id.other_game_recycle);
    }

    public static OtherGameFragment newInstance (int positon,int id) {
        OtherGameFragment fragment = new OtherGameFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", positon);
        bundle.putInt("id", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(otherGameModelArrayList!=null){
            otherGameModelArrayList.clear();
            otherGameModelArrayList=null;
        }
    }
}
