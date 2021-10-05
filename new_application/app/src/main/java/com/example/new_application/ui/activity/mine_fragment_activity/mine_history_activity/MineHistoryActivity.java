package com.example.new_application.ui.activity.mine_fragment_activity.mine_history_activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidkun.xtablayout.XTabLayout;
import com.example.new_application.R;
import com.example.new_application.base.BaseActivity;
import com.example.new_application.bean.HistoryDeleteAllEvenModel;
import com.example.new_application.bean.HistoryRequestDeleteEvenModel;
import com.example.new_application.bean.HistoryShowDeleteEvenModel;
import com.example.new_application.utils.CommonToolbarUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class MineHistoryActivity extends BaseActivity {

    @BindView(R.id.toolbar_right_iv)
    ImageView toolbar_right_iv;
    @BindView(R.id.toolbar_right_tv)
    TextView toolbar_right_tv;
    @BindView(R.id.history_bottom_linear)
    LinearLayout history_bottom_linear;
    @BindView(R.id.choose_all_tv)
    TextView choose_all_tv;
    @BindView(R.id.delete_choosed_tv)
   public TextView delete_choose_tv;

    @BindView(R.id.mine_history_tab)
    XTabLayout mine_history_tab;
    @BindView(R.id.mine_history_viewPager)
    ViewPager mine_history_viewPager;
    ArrayList<String>titleList = new ArrayList<>();

    MineHistoryTabAdapter mineFollowTabAdapter;
    @Override
    public int getLayoutId() {
        return R.layout.activity_m_ine_history;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    protected void init(Bundle savedInstanceState) {
        CommonToolbarUtil.initToolbar(this,"我的足迹");
        toolbar_right_iv.setVisibility(View.VISIBLE);
        toolbar_right_iv.setImageResource(R.drawable.history_delete_icon);
        toolbar_right_tv.setVisibility(View.GONE);
        toolbar_right_tv.setText("取消");
        initTab();
    }
    private void initTab() {
        titleList.add("浏览过的帖子");
        titleList.add("浏览过的店铺");
        mineFollowTabAdapter = new MineHistoryTabAdapter(getSupportFragmentManager());
        mine_history_viewPager.setAdapter(mineFollowTabAdapter);
        mine_history_tab.setupWithViewPager(mine_history_viewPager);
    }

    @OnClick({R.id.toolbar_right_iv,R.id.toolbar_right_tv,R.id.choose_all_tv,R.id.delete_choosed_tv})
    public void  onClick(View view){
        switch (view.getId()){
            case R.id.toolbar_right_iv:
                history_bottom_linear.setVisibility(View.VISIBLE);
                toolbar_right_iv.setVisibility(View.GONE);
                toolbar_right_tv.setVisibility(View.VISIBLE);
                /**
                 * 点击右上角删除按钮.列表显示是否选中标识
                 */
                EventBus.getDefault().post(new HistoryShowDeleteEvenModel(mine_history_viewPager.getCurrentItem(),true));
                break;
            case R.id.toolbar_right_tv:
                clickToolbarRightTv();
                break;
            case R.id.choose_all_tv:
                /**s
                 * 点击删除全部按钮, 将所有列表都设置为选中状态, 等待删除
                 */
                EventBus.getDefault().post(new HistoryDeleteAllEvenModel(mine_history_viewPager.getCurrentItem()));
                break;
            case R.id.delete_choosed_tv:
                /**
                 * 点击删除所选按钮,将选中的item删除
                 */
                EventBus.getDefault().post(new HistoryRequestDeleteEvenModel(mine_history_viewPager.getCurrentItem()));
                clickToolbarRightTv();
                break;
            default:
                break;

        }
    }

    private void clickToolbarRightTv() {
        history_bottom_linear.setVisibility(View.GONE);
        toolbar_right_iv.setVisibility(View.VISIBLE);
        toolbar_right_tv.setVisibility(View.GONE);
        EventBus.getDefault().post(new HistoryShowDeleteEvenModel(mine_history_viewPager.getCurrentItem(),false));
        delete_choose_tv.setText("删除所选(0)");
    }

    class MineHistoryTabAdapter extends FragmentPagerAdapter {
        public MineHistoryTabAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return MineHistoryFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return titleList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}