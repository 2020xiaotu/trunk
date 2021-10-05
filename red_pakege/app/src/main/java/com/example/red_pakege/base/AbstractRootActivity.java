package com.example.red_pakege.base;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;


import com.cambodia.zhanbang.rxhttp.net.utils.LogUtils;
import com.example.red_pakege.MyApplication;
import com.example.red_pakege.R;
import com.example.red_pakege.util.ActivityUtil;
import com.example.red_pakege.util.ToastUtils;


import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * created  by ganzhe on 2019/9/20.
 */
public abstract class AbstractRootActivity extends SupportActivity {
    private static final String TAG = AbstractRootActivity.class.getSimpleName();
    protected AbstractRootActivity mContext;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        mUnbinder = ButterKnife.bind(this);
        mGetIntentData();
        initView();
        initActionBar();
        initClick();
        initRefresh();
        mContext = this;

        //TODO 添加activity到栈
        ActivityUtil.getInstance().addActivity(this);
        onViewCreated();
        initToolbar();
        initEventAndData();
    }
    protected void mGetIntentData(){}

    protected void initView() { }

    /*
    刷新加载
     */
    protected void initRefresh() { }

    /*
        请求失败 点击刷新的操作
     */
    public void errorRefresh() { }

    /*
    acionBar的初始化
     */
    protected void initActionBar() { }


    /*
    点击事件的绑定
     */
    protected void initClick() { }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
        mUnbinder = null;
        ActivityUtil.getInstance().finishActivity(this);
    }

    /**
     * view 的创建 留给子类实现
     */
    protected abstract void onViewCreated();

    /**
     * 获取布局对象 留给子类实现
     */
    protected abstract int getLayout();

    /**
     * 初始化 toolbar
     */
    protected abstract void initToolbar();


    /**
     * 初始化数据留给子类实现
     */
    protected abstract void initEventAndData();

    protected void showtoast(String str) {
        Toast toast = Toast.makeText(MyApplication.getInstance(), null, Toast.LENGTH_SHORT);
        toast.setText(str);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    protected static Boolean mIsExit = false;

    /**
     * 直接退出APP
     */
    protected void QuickExit() {
        ActivityUtil.getInstance().exitSystem();
    }

    protected void QuickExitBkg() {
        ActivityUtil.getInstance().exitSystem();
    }


    /**
     * 双击退出APP
     */
    protected void doubleClickExit() {
        Timer exitTimer = null;
        if (!mIsExit) {
            mIsExit = true;
            ToastUtils.showToast(getString(R.string.exit_again));
            exitTimer = new Timer();
            exitTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mIsExit = false;
                }
            }, 2000);
        } else {
            ActivityUtil.getInstance().exitSystem();
        }
    }

    /**
     * 双击退出APP和后台
     */
    protected void doubleClickExitAndBkg() {
        Timer exitTimer = null;
        if (!mIsExit) {
            mIsExit = true;
            ToastUtils.showToast(getString(R.string.exit_again));
            exitTimer = new Timer();
            exitTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    mIsExit = false;
                }
            }, 2000);
        } else {
            ActivityUtil.getInstance().AppExit(this);
        }
    }




}
