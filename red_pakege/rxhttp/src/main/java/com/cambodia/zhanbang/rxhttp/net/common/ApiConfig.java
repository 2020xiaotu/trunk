package com.cambodia.zhanbang.rxhttp.net.common;

import android.content.Context;
import android.util.ArrayMap;

import com.cambodia.zhanbang.rxhttp.net.utils.AppContextUtils;
import com.cambodia.zhanbang.rxhttp.net.utils.SslSocketConfigure;

import java.io.Serializable;

import androidx.annotation.NonNull;

/**
 * 网络请求配置文件
 */

public class ApiConfig implements Serializable {

    private static String mBaseUrl;
    private static int mDefaultTimeout = 50000;
    private static int mSucceedCode;
    private static ArrayMap<String, String> mHeads;
    private static boolean mOpenHttps;
    private static SslSocketConfigure mSslSocketConfigure;

    private ApiConfig(Builder builder) {
        mBaseUrl = builder.baseUrl;
        mDefaultTimeout = builder.defaultTimeout;
        mSucceedCode = builder.succeedCode;
        mHeads = builder.heads;
        mOpenHttps = builder.openHttps;
        mSslSocketConfigure = builder.sslSocketConfigure;
    }

    public void init(Context appContext) {
        AppContextUtils.init(appContext);
    }

    public static String getBaseUrl() {
        return mBaseUrl;
    }

    public static int getDefaultTimeout() {
        return mDefaultTimeout;
    }

    public static int getSucceedCode() {
        return mSucceedCode;
    }

    public static ArrayMap<String, String> getHeads() {
        return mHeads;
    }

    public static void setHeads(ArrayMap<String, String> mHeads) {
        ApiConfig.mHeads = mHeads;
    }


    public static boolean getOpenHttps() {
        return mOpenHttps;
    }


    public static SslSocketConfigure getSslSocketConfigure() {
        return mSslSocketConfigure;
    }

    public static final class Builder {

        private String baseUrl;

        private int defaultTimeout;

        private int succeedCode;

        private ArrayMap<String, String> heads;

        private boolean openHttps = false;

        private SslSocketConfigure sslSocketConfigure;

        public Builder setSucceedCode(int succeedCode) {
            this.succeedCode = succeedCode;
            return this;
        }

        public Builder setBaseUrl(String mBaseUrl) {
            this.baseUrl = mBaseUrl;
            return this;
        }

        public Builder setDefaultTimeout(int defaultTimeout) {
            this.defaultTimeout = defaultTimeout;
            return this;
        }

        public Builder setOpenHttps(boolean open) {
            this.openHttps = open;
            return this;
        }

        public Builder setSslSocketConfigure(SslSocketConfigure sslSocketConfigure) {
            this.sslSocketConfigure = sslSocketConfigure;
            return this;
        }

        public ApiConfig build() {
            return new ApiConfig(this);
        }
    }
}
