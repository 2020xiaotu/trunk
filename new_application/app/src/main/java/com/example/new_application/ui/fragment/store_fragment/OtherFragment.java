package com.example.new_application.ui.fragment.store_fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.new_application.R;
import com.example.new_application.base.BaseFragment;
import com.example.new_application.utils.CommonStr;

public class OtherFragment extends BaseFragment {


    public static OtherFragment newInstance(int position) {
        OtherFragment fragment = new OtherFragment();
        Bundle args = new Bundle();
        args.putInt(CommonStr.POSITION,position);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_other;
    }
}