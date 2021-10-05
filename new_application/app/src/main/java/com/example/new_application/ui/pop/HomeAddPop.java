package com.example.new_application.ui.pop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.HomeAddAdapter;
import com.example.new_application.base.BasePopupWindow;
import com.example.new_application.bean.HomeAddEntity;
import com.example.new_application.utils.Utils;

import java.util.ArrayList;

public class HomeAddPop extends BasePopupWindow {
    private ImageView home_add_close_iv;
    RecyclerView home_add_recycler;
    HomeAddAdapter homeAddAdapter ;
    ArrayList<HomeAddEntity>homeAddEntityArrayList = new ArrayList<>();
    public HomeAddPop(Context context) {
        super(context);
        initRecycler();

    }

    private void initRecycler() {
        homeAddAdapter = new HomeAddAdapter(R.layout.home_add_item,homeAddEntityArrayList);
        home_add_recycler.setLayoutManager(new GridLayoutManager(mContext,2));
        home_add_recycler.setAdapter(homeAddAdapter);
        String userType = Utils.getUserInfo().getUserType();
        //   flag;1 运营商发布需求 2 服务商发布服务  3 担保 4 联合运营
        if(userType.equals("1")){
            /**
             * 运营商
             */
            homeAddEntityArrayList.add(new HomeAddEntity("发布我的需求",R.drawable.fbwdxq_icon,1));
        }else {
            homeAddEntityArrayList.add(new HomeAddEntity("发布资源服务",R.drawable.fbzyfw_icon,2));
        }
        String isGuarantee = Utils.getSystemParamsEntity().getIsGuarantee();//是否允许担保0 否1是
        if(isGuarantee.equals("1")){
            homeAddEntityArrayList.add(new HomeAddEntity("委托担保交易",R.drawable.ywwtdb_icon,3));
        }
        if(userType.equals("2")){
            /**
             * 服务商申请联合运营
             */
            homeAddEntityArrayList.add(new HomeAddEntity("申请联合运营",R.drawable.sjrzjm_icon,4));
        }
        homeAddAdapter.notifyDataSetChanged();
        homeAddAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                if(mOnPopItemClick!=null){
                    mOnPopItemClick.onPopItemClick(view,position);
                }
            }
        });
    }

    @Override
    public void initView() {
        super.initView();
        rootView = LayoutInflater.from(mContext).inflate(R.layout.home_add_pop_layout,null);
        home_add_recycler = rootView.findViewById(R.id.home_add_recycler);
        home_add_close_iv = rootView.findViewById(R.id.home_add_close_iv);
        home_add_close_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeAddPop.this.dismiss();
            }
        });
    }

    public RecyclerView getHome_add_recycler() {
        return home_add_recycler;
    }

    public ArrayList<HomeAddEntity> getHomeAddEntityArrayList() {
        return homeAddEntityArrayList;
    }
}
