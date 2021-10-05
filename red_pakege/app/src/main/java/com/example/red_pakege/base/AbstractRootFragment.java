package com.example.red_pakege.base;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.red_pakege.MyApplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Fragment 基类  继承SupportFragment （fragmentation）
 * created  by ganzhe on 2019/9/20.
 */
public abstract class AbstractRootFragment extends SupportFragment {

    private static final String TAG = AbstractRootFragment.class.getSimpleName();
    //ButterKnife
    private Unbinder mUnbinder;
    View view;
    public boolean isUnBind=false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(getLayoutId(), container, false);
        mUnbinder= ButterKnife.bind(this,view);
        initView();
        return view;
    }
    /**
     * 初始化 view
     */
    protected  void initView(){
        initActionBar();
        initClick();
        initRefresh();
    }
    public void errorRefresh(){
    }
    protected  void initRefresh(){};

    public void initClick(){

    };


    @Override
    public void onDestroyView() {
        if(mUnbinder!=null){
            mUnbinder.unbind();
            isUnBind=true;
        }
        super.onDestroyView();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initEventAndData();
    }

    protected  void initActionBar(){};
    //获取LayoutId
    protected abstract int getLayoutId();

    public   View getRootView(){
        return view;
    };
    //初始化数据
    protected abstract void initEventAndData();

    /**
     * 回退事件处理
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        Log.e(TAG,"===onBackPressedSupport  调用");
        return super.onBackPressedSupport();
    }

    public void showtoast(String str){
        FragmentActivity activity = getActivity();
        if(activity!=null&&!activity.isFinishing()){
            Toast toast = Toast.makeText(MyApplication.getInstance(), null, Toast.LENGTH_SHORT);
            toast.setText(str);
//            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }
}
