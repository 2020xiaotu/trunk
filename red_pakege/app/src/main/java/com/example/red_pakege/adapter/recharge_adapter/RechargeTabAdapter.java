package com.example.red_pakege.adapter.recharge_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.red_pakege.MyApplication;
import com.example.red_pakege.R;
import com.example.red_pakege.fragment.recharge_fragment.GfchargeFragment;
import com.example.red_pakege.fragment.recharge_fragment.OtherchargeFragment;
import com.example.red_pakege.net.entity.RechargeEntreyEntity;
import com.example.red_pakege.util.GlideLoadViewUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

import static com.example.red_pakege.util.ImageUtil.ImageUrlCheck;

/**
 * created  by ganzhe on 2019/11/20.
 */
public class RechargeTabAdapter extends FragmentStatePagerAdapter {
    Context mContext;
   /* String[] titles;
    int icons[];*/
    List<RechargeEntreyEntity.DataBean.BankAllListBean> mList;

    public RechargeTabAdapter(Context context, @NonNull FragmentManager fm, List<RechargeEntreyEntity.DataBean.BankAllListBean> mList) {
        super(fm);
        this.mContext = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment ;
        if (mList.get(position).getType()==0){
            fragment = GfchargeFragment.newInstance(position);
        }else {
            fragment = OtherchargeFragment.newInstance(position);
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return null == mList ? 0: mList.size();
    }



    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mList.get(position).getName();
    }

    public View getTabView(int position){
        View v = LayoutInflater.from(mContext).inflate(R.layout.recharge_tab_item_view, null);
        ImageView iv = v.findViewById(R.id.iv_recharge_tabitem);
        TextView tv = v.findViewById(R.id.tv_recharge_tabitem);
     //   iv.setImageResource(icons[position]);
        GlideLoadViewUtil.FLoadNormalView(mContext,ImageUrlCheck(mList.get(position).getImage()),iv);
        tv.setText(mList.get(position).getName());
        if (position == 0) {
            tv.setTextColor(v.getResources().getColor(R.color.tabSelecterColor));
        }
        return v;
    }
}
