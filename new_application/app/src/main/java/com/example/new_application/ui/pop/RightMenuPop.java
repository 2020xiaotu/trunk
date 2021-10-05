package com.example.new_application.ui.pop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.new_application.R;
import com.example.new_application.adapter.RightMenuAdapter;
import com.example.new_application.base.BasePopupWindow;
import com.example.new_application.bean.RightMenuEntity;
import com.example.new_application.bean.SystemParamsEntity;
import com.example.new_application.ui.activity.mine_fragment_activity.CooperationActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.FeedBackActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.HelpCenterActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.OfficialGroupActivity;
import com.example.new_application.ui.activity.mine_fragment_activity.SettingActivity;
import com.example.new_application.utils.RouteUtils;
import com.example.new_application.utils.TriangleDrawable;
import com.example.new_application.utils.Utils;

import java.util.ArrayList;

public class RightMenuPop extends BasePopupWindow {
    View v_arrow;
    RecyclerView right_menu_pop_recycler;
    RightMenuAdapter rightMenuAdapter;
    ArrayList<RightMenuEntity>rightMenuEntityArrayList = new ArrayList<>();
    Activity activity;
    public RightMenuPop(Context context) {
        super(context);
        activity= (Activity) context;
        setAnimationStyle(R.style.RightTop2PopAnim);
        initRecycler();
    }

    private void initRecycler() {
        rightMenuAdapter = new RightMenuAdapter(R.layout.right_menu_item_layout,rightMenuEntityArrayList);
        right_menu_pop_recycler.setLayoutManager(new LinearLayoutManager(mContext));
        right_menu_pop_recycler.setAdapter(rightMenuAdapter);
        rightMenuAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                RightMenuEntity rightMenuEntity = rightMenuEntityArrayList.get(position);
                if(position==0){
                    /**
                     * 分享
                     */
                    SystemParamsEntity systemParamsEntity = Utils.getSystemParamsEntity();
                    String downLoadPageUrl = systemParamsEntity.getSystemParameterInfoMap().getDownLoadShareContent();
                    RouteUtils.start2Share(mContext,downLoadPageUrl);
                }else if(position == 1 ){
                    /**
                     * 设置
                     */
                    activity.startActivity(new Intent(activity, SettingActivity.class));
                }else if(position == 2){
                    /**
                     * 意见反馈
                     */
                    activity.startActivity(new Intent(activity, FeedBackActivity.class));
                }else if(position == 3){
                    /**
                     * 帮助中心
                     */
                    activity.startActivity(new Intent(activity, HelpCenterActivity.class));
                }else if(position == 4){
                    /**
                     * 联系客服
                     */
                    activity.startActivity(new Intent(activity, CooperationActivity.class));
                }else {
                    activity.startActivity(new Intent(activity, OfficialGroupActivity.class));
                }
                RightMenuPop.this.dismiss();
            }
        });
        rightMenuEntityArrayList.add(new RightMenuEntity(R.drawable.zyxq_fx,"分享"));
        rightMenuEntityArrayList.add(new RightMenuEntity(R.drawable.zyxq_grsz,"个人设置"));
        rightMenuEntityArrayList.add(new RightMenuEntity(R.drawable.zyxq_yjfk,"意见反馈"));
        rightMenuEntityArrayList.add(new RightMenuEntity(R.drawable.zyxq_bzzx,"帮助中心"));
        rightMenuEntityArrayList.add(new RightMenuEntity(R.drawable.zyxq_lxwm,"联系客服"));
        rightMenuEntityArrayList.add(new RightMenuEntity(R.drawable.zyxq_zyqz,"官方群组"));
    }

    @Override
    public void initView() {
        super.initView();
        rootView = LayoutInflater.from(mContext).inflate(R.layout.layout_right_menu,null);
        right_menu_pop_recycler=rootView.findViewById(R.id.right_menu_pop_recycler);
        v_arrow=rootView.findViewById(R.id.v_arrow);
        v_arrow.setBackground(new TriangleDrawable(TriangleDrawable.TOP, Color.parseColor("#181818")));
    }
}
