package com.cambodia.zhanbang.rxhttp.sp;

import android.content.Context;
import android.content.SharedPreferences;


import com.cambodia.zhanbang.rxhttp.net.common.RxLibConstants;
import com.cambodia.zhanbang.rxhttp.net.model.UserEntity;
import com.cambodia.zhanbang.rxhttp.net.utils.AppContextUtils;

import static com.cambodia.zhanbang.rxhttp.net.common.RxLibConstants.FRONT_DOMAIN;


/**
 * created  by ganzhe on 2019/9/26.
 */
public class SharedPreferenceHelperImpl implements SharedPreferenceHelper {

    private final SharedPreferences mSharedPreferences;


    public SharedPreferenceHelperImpl() {
        mSharedPreferences = AppContextUtils.getContext().getSharedPreferences(RxLibConstants.SHAREDPREFERENCES_NAME,Context.MODE_PRIVATE);
    }

    @Override
    public void setLoginStatus(boolean loginStatus) {
        mSharedPreferences.edit().putBoolean(RxLibConstants.SP_LOGIN_STATUS,loginStatus).apply();
    }

    @Override
    public boolean getLoginStatus() {
        return mSharedPreferences.getBoolean(RxLibConstants.SP_LOGIN_STATUS,false);
    }

    @Override
    public void setPhoneNum(String strPhone) {
        mSharedPreferences.edit().putString(RxLibConstants.SP_PHONE_NUM,strPhone).apply();
    }

    @Override
    public String getPhoneNum() {
        return mSharedPreferences.getString(RxLibConstants.SP_PHONE_NUM,"");
    }

    @Override
    public void setMsgSwitch(boolean msgSwitchStatus) {
        mSharedPreferences.edit().putBoolean(RxLibConstants.SP_MSGSWITCH_STATUS,msgSwitchStatus).apply();
    }

    @Override
    public boolean getMsgSwitch() {
        return mSharedPreferences.getBoolean(RxLibConstants.SP_MSGSWITCH_STATUS,false);
    }

    @Override
    public void setVoiceSwitch(boolean voiceSwitchStatus) {
        mSharedPreferences.edit().putBoolean(RxLibConstants.SP_VOICESWITCH_STATUS,voiceSwitchStatus).apply();
    }

    @Override
    public boolean getVoiceSwitch() {
        return mSharedPreferences.getBoolean(RxLibConstants.SP_VOICESWITCH_STATUS,false);
    }

    @Override
    public void setToken(String token) {
        mSharedPreferences.edit().putString(RxLibConstants.SP_TOKEN,token).apply();
    }

    @Override
    public String getToken() {
        return mSharedPreferences.getString(RxLibConstants.SP_TOKEN,"");
    }

    @Override
    public void setTokenRefresh(String token_refresh) {
        mSharedPreferences.edit().putString(RxLibConstants.SP_TOKENREFRESH,token_refresh).apply();
    }

    @Override
    public String getTokenRefresh() {
        return mSharedPreferences.getString(RxLibConstants.SP_TOKENREFRESH,"");
    }

    @Override
    public void ClearSp() {
        mSharedPreferences.edit().clear().apply();
    }

    @Override
    public void putObject(String key, Object object) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }

    @Override
    public Object getObject(String key,Object object) {
        if (object instanceof String) {
            return mSharedPreferences.getString(key, "");
        } else if (object instanceof Integer) {
            return mSharedPreferences.getInt(key, 0);
        } else if (object instanceof Boolean) {
            return mSharedPreferences.getBoolean(key, false);
        } else if (object instanceof Float) {
            return mSharedPreferences.getFloat(key, 0);
        } else if (object instanceof Long) {
            return mSharedPreferences.getLong(key, 0L);
        }
        return null;
    }

    @Override
    public void setUserInfo(String userInfo) {
        mSharedPreferences.edit().putString(RxLibConstants.SP_USERINFO,userInfo).apply();
    }

    @Override
    public String getUserInfo() {
        return mSharedPreferences.getString(RxLibConstants.SP_USERINFO,"");
    }

    @Override
    public void setShiJianCha(Long shiJianCha) {
        mSharedPreferences.edit().putLong(RxLibConstants.SHIJIAN_CHA,shiJianCha).apply();
    }

    @Override
    public long getShiJianCha() {
        return mSharedPreferences.getLong(RxLibConstants.SHIJIAN_CHA,0l);
    }

    @Override
    public void setUserId(int userId) {
        mSharedPreferences.edit().putInt(RxLibConstants.USER_ID,userId).apply();
    }

    @Override
    public int getUserId() {
        return mSharedPreferences.getInt(RxLibConstants.USER_ID,0);
    }

    @Override
    public void setImageDomin(String imageDomin) {
        mSharedPreferences.edit().putString(FRONT_DOMAIN,imageDomin).apply();
    }

    @Override
    public String getImageDomin() {
        return mSharedPreferences.getString(FRONT_DOMAIN,"");
    }
}
