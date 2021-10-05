package com.example.red_pakege.activity.common_activity;


import android.util.Log;

import com.example.red_pakege.base.BaseWebActivity;

import static com.example.red_pakege.config.Constants.WEB_URL;


public class WebActivity extends BaseWebActivity {


    private String url;


    @Override
    public void initView() {
        url = getIntent().getStringExtra(WEB_URL);

    }

    @Override
    protected String getUrl() {

        Log.e(TAG, " url:" + url);
        return url;
    }


}
