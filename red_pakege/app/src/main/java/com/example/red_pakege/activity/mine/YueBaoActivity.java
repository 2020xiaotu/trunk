package com.example.red_pakege.activity.mine;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.red_pakege.R;
import com.example.red_pakege.base.BaseActivity;
import com.example.red_pakege.base.IBasePresenter;
import com.example.red_pakege.mvp.contract.YuebaoContract;
import com.example.red_pakege.mvp.presenter.YuebaoPresenter;
import com.example.red_pakege.net.entity.YuebaoInfoEntity;
import com.example.red_pakege.util.ActionBarUtil;

import butterknife.BindView;

public class YueBaoActivity extends BaseActivity<YuebaoPresenter> implements YuebaoContract.IYuebaoView {

    @BindView(R.id.yuebao_v_head)
    LinearLayout yuebao_v_head;
    @BindView(R.id.iv_yuebao_back)
    ImageView iv_yuebao_back;
    @BindView(R.id.iv_yuebao_more)
    ImageView iv_yuebao_more;
    @BindView(R.id.tv_yuebao_totalmoney)
    TextView tv_yuebao_totalmoney;
    @BindView(R.id.tv_yuebao_todaysy)
    TextView tv_yuebao_todaysy;
    @BindView(R.id.leiji_layout)
    ConstraintLayout leiji_layout;
    @BindView(R.id.jizhun_layout)
    ConstraintLayout jizhun_layout;
    @BindView(R.id.hongyun_layout)
    ConstraintLayout hongyun_layout;
    @BindView(R.id.zhuanru_btn)
    Button zhuanru_btn;
    @BindView(R.id.zhuanchu_btn)
    Button zhuanchu_btn;


    @Override
    protected YuebaoPresenter createPresenter() {
        return new YuebaoPresenter();
    }



    @Override
    protected int getLayout() {
        return R.layout.activity_yuebao;
    }

    @Override
    protected void initToolbar() {
        ActionBarUtil.initMainActionbar(this, yuebao_v_head);
    }

    @Override
    protected void initEventAndData() {
        mPresenter.PostYuebaoInfo();

        iv_yuebao_back.setOnClickListener(v -> onBackPressedSupport());
        iv_yuebao_more.setOnClickListener(v -> {

        });
        leiji_layout.setOnClickListener(v -> {startActivity(new Intent(this,YueBaoRecordActivity.class));});
        jizhun_layout.setOnClickListener(v -> {});
        hongyun_layout.setOnClickListener(v -> {});
        zhuanru_btn.setOnClickListener(v -> {});
        zhuanchu_btn.setOnClickListener(v -> {});
    }

    @Override
    public void showYuebaoInfo(YuebaoInfoEntity entity) {

    }


}
