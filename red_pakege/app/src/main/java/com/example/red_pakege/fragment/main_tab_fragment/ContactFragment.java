package com.example.red_pakege.fragment.main_tab_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.red_pakege.R;
import com.example.red_pakege.adapter.contract_adapter.ContractTabPagerAdapter;
import com.example.red_pakege.base.BaseFragment;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.fragment.contract_fragment.ContractChild1Fragment;
import com.example.red_pakege.fragment.contract_fragment.ContractChild2Fragment;
import com.example.red_pakege.fragment.contract_fragment.ContractChild3Fragment;
import com.example.red_pakege.fragment.contract_fragment.ContractChild4Fragment;
import com.example.red_pakege.util.ActionBarUtil;
import com.example.red_pakege.util.Utils;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class ContactFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.iv_commonsearch_left)
    ImageView ivBarLeft;
    @BindView(R.id.tv_commonsearch_title)
    TextView tvBarTitle;
    @BindView(R.id.iv_commonsearch_right)
    ImageView ivBarRight;
    @BindView(R.id.iv_commonsearch_search)
    ImageView ivBarSearch;
    @BindView(R.id.common_search_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.contract_tab)
    TabLayout mTab;
    @BindView(R.id.contract_viewpager)
    ViewPager mViewpager;

    //右侧pop
    private PopupWindow addPop;
    private LinearLayout agentCenterLinear;
    private LinearLayout newPersonLinear;
    private LinearLayout playRuleLinear;
    private LinearLayout createGroupLinear;

    private String[] titles = {"在线客服", "我的好友", "我的群组", "验证消息"};
    private int[] icons = {R.drawable.icon_service_msg, R.drawable.icon_friends_msg, R.drawable.icon_group_msg, R.drawable.icon_verification};
    private ContractTabPagerAdapter contractTabPagerAdapter;
    private Fragment[] mFragments = new Fragment[4];

    public static ContactFragment newInstance() {
        Bundle args = new Bundle();
        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initView() {
        tvBarTitle.setText("通讯录");
        initTab();
    }
    @Override
    protected void initEventAndData() {
        ivBarLeft.setOnClickListener(v -> {
        });
        ivBarRight.setOnClickListener(v -> {
            if (addPop == null) {
                initAddPop();
            }
            addPop.showAsDropDown(ivBarRight, 15, -15);
            Utils.darkenBackground(_mActivity, 0.7f);
        });
    }

    /*
       右侧添加pop初始化
        */
    private void initAddPop() {
        View popView = LayoutInflater.from(getContext()).inflate(R.layout.add_popupwindow, null);
        agentCenterLinear = popView.findViewById(R.id.add_agent_center_linear);
        newPersonLinear = popView.findViewById(R.id.add_new_person_linear);
        playRuleLinear = popView.findViewById(R.id.add_play_rule_linear);
        createGroupLinear = popView.findViewById(R.id.add_create_group_linear);
        agentCenterLinear.setOnClickListener(this);
        newPersonLinear.setOnClickListener(this);
        playRuleLinear.setOnClickListener(this);
        createGroupLinear.setOnClickListener(this);
        addPop = new PopupWindow(popView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        addPop.setAnimationStyle(R.style.popAlphaanim0_3);
        addPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.darkenBackground(getActivity(), 1f);
            }
        });
    }

    private void initTab(){
        mFragments[0] =  ContractChild1Fragment.newInstance();
        mFragments[1] = ContractChild2Fragment.newInstance();
        mFragments[2] = ContractChild3Fragment.newInstance();
        mFragments[3] = ContractChild4Fragment.newInstance();
        for (int i=0;i<titles.length;i++){
            mTab.addTab(mTab.newTab().setText(titles[i]).setIcon(icons[i]));
        }
        contractTabPagerAdapter = new ContractTabPagerAdapter(getChildFragmentManager(),titles,mFragments);
        mViewpager.setAdapter(contractTabPagerAdapter);
        mTab.setupWithViewPager(mViewpager);
        for(int i=0;i<titles.length;i++){
            mTab.getTabAt(i).setText(titles[i]).setIcon(icons[i]);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
